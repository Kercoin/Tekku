package hello;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.InputConnection;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

/**
 * resource loader
 * <p>
 * @author Yacine Touil
 */
public class ResourcesLoader {

  /** localResources */
  private static boolean localResources;
  /** RESOURCE_NAMES */
  private static final String[] RESOURCE_NAMES = {"resources", "duke",
      "javaTheme", "businessTheme", "starTheme", "oceanfishTheme", "images"};

  /**
   * load resources
   * @return <code>Resources</code>
   */
  public static Resources load(MIDlet midlet, ResourceListener listener) {
    try {
      InputStream stream = ResourcesLoader.class.getResourceAsStream(
          "/resources.res");
      Resources r2 = null;
      if (stream == null) {
        localResources = false;
        try {
          RecordStore.openRecordStore(RESOURCE_NAMES[0], false)
              .closeRecordStore();
          Resources r1 = getResource(Constants.DEFAULT_THEME);
          UIManager.getInstance().setThemeProps(
              r1.getTheme(r1.getThemeResourceNames()[0]));

          return getResource("resources");
        } catch (Exception ignor) {
          // this exception is expected the 1st time the application is executed
        }
        downloadResources(midlet, listener);
        
        return null;
      } else {
        localResources = true;
        r2 = Resources.open(stream);
        stream.close();
      }

      // resources are built during the build process open the build.xml file
      // to figure out how to construct the resource files
      Resources r1 = Resources.open("/" + Constants.DEFAULT_THEME + ".res");
      UIManager.getInstance().setThemeProps(
          r1.getTheme(r1.getThemeResourceNames()[0]));
      return r2;
    } catch (Throwable ex) {
      ex.printStackTrace();
      Dialog.show("Exception", ex.getMessage(), "OK", null);
    }
    return null;
  }
  /**
   * Downloads resources for the very first activation of the form if it is
   * running with light deployment where the resources are not packaged in the
   * JAR itself
   * @param midlet midlet is used for getting application properties
   * @param listener notify the listener when loading is done.
   */
  private static void downloadResources(final MIDlet midlet,
      final ResourceListener listener) {
    // download resources from the internet and install them in the RMS
    // while showing a progress indicator
    Form pleaseWait = new Form("Download");
    pleaseWait.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    pleaseWait.addComponent(new Label("Downloading Resources"));
    final Label progressInfo = new Label("Starting");
    progressInfo.setAlignment(Component.CENTER);
    final ProgressBar prog = new ProgressBar();
    pleaseWait.addComponent(progressInfo);
    pleaseWait.addComponent(prog);
    pleaseWait.show();
    new Thread() {

      public void run() {
        try {
          byte[] buffer = new byte[4096];
          for (int i = 0; i < RESOURCE_NAMES.length; i++) {
            setText("Downloading: " + RESOURCE_NAMES[i]);
            byte percent = (byte) (100.0f * ((i + 0.25f) /
                ((float) RESOURCE_NAMES.length)));
            prog.setProgress(percent);
            String url = midlet.getAppProperty(RESOURCE_NAMES[i] + "-url");
            InputConnection inputCon = (InputConnection) Connector.open(url,
                Connector.READ);
            InputStream stream = inputCon.openInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int size = stream.read(buffer);
            while (size > -1) {
              output.write(buffer, 0, size);
              size = stream.read(buffer);
            }
            stream.close();
            inputCon.close();
            setText("Storing: " + RESOURCE_NAMES[i]);
            RecordStore store = RecordStore.openRecordStore(RESOURCE_NAMES[i],
                true);
            byte[] array = output.toByteArray();
            store.addRecord(array, 0, array.length);
            store.closeRecordStore();
          }
          setText("Done!");
          prog.setProgress((byte) 100);
          Resources r1 = getResource(Constants.DEFAULT_THEME);
          UIManager.getInstance().setThemeProps(
              r1.getTheme(r1.getThemeResourceNames()[0]));

          listener.resourceLoaded(getResource("resources"));
        } catch (Exception ex) {
          ex.printStackTrace();
          Dialog.show("Exception", ex.getMessage(), "OK", null);
        }
      }
      
      private void setText(String text) {
        try {
          synchronized (progressInfo) {
            progressInfo.setText(text);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    
    }.start();
  }

  /**
   * Used instead of using the Resources API to allow us to fetch locally
   * downloaded resources
   * @param name the name of the resource
   * @return a resources object
   */
  public static Resources getResource(String name) throws IOException {
    if (localResources) {
      return Resources.open(Constants.ROOT_DIR + name + Constants.RES_EXT);
    }

    byte[] resourceData = null;
    try {
      resourceData = RecordStore.openRecordStore(name, false).enumerateRecords(
          null, null, false).nextRecord();
    } catch (RecordStoreException ex) {
      ex.printStackTrace();
      throw new IOException(ex.getMessage());
    }
    return Resources.open(new ByteArrayInputStream(resourceData));
  }

}
