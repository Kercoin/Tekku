package hello;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.location.Location;
import javax.microedition.location.QualifiedCoordinates;
import javax.microedition.midlet.MIDlet;

/**
 * TestGPS
 * <p>
 * @author Yacine Touil
 */
public class TestGPS extends MIDlet implements CommandListener, GPSListener {

  private Display display;
  private Form form;
  private Command cmdExit, cmdOK;
  private StringItem si;

  public TestGPS() {
    display = Display.getDisplay(this);
    form = new Form("Location Api test");
    cmdExit = new Command("Exit", Command.EXIT, 5);
    cmdOK = new Command("OK", Command.OK, 1);
    si = new StringItem("Geo Location", "Click OK");
    form.append(si);
    form.addCommand(cmdOK);
    form.addCommand(cmdExit);
    form.setCommandListener(this);
  }

  public void startApp() {
    display.setCurrent(form);
  }

  public void pauseApp() {
  }

  public void destroyApp(boolean flag) {
    notifyDestroyed();
  }

  public void commandAction(Command c, Displayable d) {
    if (c == cmdOK) {
      GPSRetriever ret = new GPSRetriever(this);
      ret.start();

    } else if (c == cmdExit) {
      destroyApp(false);
    }
  }

  public void displayString(String string) {
    si.setText(string);
  }

  /**
   * @see hello.GPSListener#receive(javax.microedition.location.Location)
   */
  public void receive(Location location) {
    QualifiedCoordinates c = location.getQualifiedCoordinates();

    String string = "Speed : " + location.getSpeed()
      + "\nTimestamp : " + location.getTimestamp();
    if (location.isValid() && c != null) {
      // Use coordinate information
      double lat = c.getLatitude();
      double lon = c.getLongitude();
      double alt = c.getAltitude();
      double horizontalAccuracy = c.getHorizontalAccuracy();
      double verticalAccuracy = c.getVerticalAccuracy();
      
      string += "\nLatitude : " + lat
        + "\nLongitude : " + lon
        + "\nAltitude : " + alt
        + "\nhorizontalAccuracy : " + horizontalAccuracy
        + "\nverticalAccuracy : " + verticalAccuracy;
    } else {
      string = "Location API failed";
    }
  }
}
