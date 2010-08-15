package hello;

import java.util.Vector;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

/**
 * Cartography MIDlet
 * <p>
 * @author Yacine Touil
 */
public class CartographyMIDlet extends MIDlet implements ActionListener,
  ResourceListener  {

  /** EXIT_COMMAND */
  private static final int EXIT_COMMAND = 1;
  /** NEXT_COMMAND */
  private static final int NEXT_COMMAND = 2;
  /** BACK_COMMAND */
  private static final int BACK_COMMAND = 3;
  /** GO_COMMAND */
  private static final int GO_COMMAND = 4;
  /** MARK_COMMAND */
  private static final int MARK_COMMAND = 5;
  /** FINISH_COMMAND */
  private static final int FINISH_COMMAND = 6;
  /** VALIDATE_COMMAND */
  private static final int VALIDATE_COMMAND = 7;
  
  /** exitCommand */
  private static final Command exitCommand = new Command("Exit", EXIT_COMMAND);
  /** nextCommand */
  private static final Command nextCommand = new Command("Suite", NEXT_COMMAND);
  /** backCommand */
  private static final Command backCommand = new Command("Retour", BACK_COMMAND);
  /** goCommand */
  private static final Command goCommand = new Command("Go!", GO_COMMAND);
  /** markCommand */
  private static final Command markCommand = new Command("Marque", MARK_COMMAND);
  /** finishCommand */
  private static final Command finishCommand = new Command("Terminé", FINISH_COMMAND);
  /** validateCommand */
  private static final Command validateCommand = new Command("Valider", VALIDATE_COMMAND);
  
  /** mainMenu */
  private static CartographyMainForm mainForm;

  /** mainMenu */
  private static CartographySelectionForm selectionForm;

  /** mainMenu */
  private static CartographyMarkForm markForm;

  /** mainMenu */
  private static CartographyValidationForm validationForm;

  public CartographyMIDlet() {
  }

  /** @see javax.microedition.midlet.MIDlet#startApp() */
  protected void startApp() throws MIDletStateChangeException {
    Display.init(this);
    Resources r = ResourcesLoader.load(this, this);
    if (r != null) {
      resourceLoaded(r);
    }
    // else : wait notification from resource loader.
  }

  /** @see ResourceListener#resourceLoaded(com.sun.lwuit.util.Resources) */
  public void resourceLoaded(Resources r) {
    UIManager.getInstance().setResourceBundle(r.getL10N("localize", "default"));

    mainForm = new CartographyMainForm();
    mainForm.addCommand(exitCommand);
    mainForm.addCommand(nextCommand);
    mainForm.addCommandListener(this);
    
    selectionForm = new CartographySelectionForm();
    selectionForm.addCommand(backCommand);
    selectionForm.addCommand(goCommand);
    selectionForm.addCommandListener(this);
    
    markForm = new CartographyMarkForm();
    markForm.addCommand(markCommand);
    markForm.addCommand(finishCommand);
    markForm.addCommandListener(this);
    
    validationForm = new CartographyValidationForm();
    validationForm.addCommand(validateCommand);
    validationForm.addCommandListener(this);
    
    mainForm.show();
  }

  /** @see javax.microedition.midlet.MIDlet#pauseApp() */
  protected void pauseApp() {
  }

  /** @see javax.microedition.midlet.MIDlet#destroyApp(boolean) */
  protected void destroyApp(boolean unconditional)
      throws MIDletStateChangeException {
  }

  /**
   * Invoked when a command is clicked. We could derive from Command but that
   * would require 3 separate classes.
   */
  public void actionPerformed(ActionEvent evt) {
    Command cmd = evt.getCommand();
    switch (cmd.getId()) {
      case NEXT_COMMAND:
        String network = mainForm.getNetwork();
        String line = mainForm.getLine();
        selectionForm.show(network, line);
        break;

      case BACK_COMMAND:
        mainForm.refreshTheme();
        mainForm.show();
        // for series 40 devices
        System.gc();
        System.gc();
        break;
        
      case GO_COMMAND:
      case VALIDATE_COMMAND:
        network = mainForm.getNetwork();
        line = mainForm.getLine();
        String direction = selectionForm.getDirection();
        String start = selectionForm.getStart();
        String finish = selectionForm.getFinish();
        markForm.show(network, line, direction, start, finish);
        break;
      
      case MARK_COMMAND:
        markForm.mark();
        break;
      
      case FINISH_COMMAND:
        network = mainForm.getNetwork();
        line = mainForm.getLine();
        direction = selectionForm.getDirection();
        start = selectionForm.getStart();
        finish = selectionForm.getFinish();
        Vector route = markForm.getRoute();
        validationForm.show(network, line, direction, start, finish, route);
        break;
      
      case EXIT_COMMAND:
        notifyDestroyed();
        break;
    }
  }

}
