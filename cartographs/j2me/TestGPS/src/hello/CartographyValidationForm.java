package hello;

import java.util.Vector;

import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.list.DefaultListModel;

/**
 * Cartographie main Form.
 * 
 * @author Yacine Touil
 */
public class CartographyValidationForm extends Form {
  
  /** label choix */
  private Label lChoice = new Label("");
  /** label direction */
  private Label lDirection = new Label("");
  /** route list */
  private List lRoute = new List();
  
  /**
   * default constructor : initialization of the Form
   * @param parent parent
   */
  public CartographyValidationForm() {
    setTitle(Constants.MAIN_SCREEN_TITLE);
    setTransitionOutAnimator(CommonTransitions.createFade(400));
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    setScrollableY(false);
    
    Label lTitle = new Label("Résumé du parcours");
    lTitle.getStyle().setBgTransparency(0);
    lChoice.getStyle().setBgTransparency(0);
    lDirection.getStyle().setBgTransparency(0);
    
    Container container = new Container();
    GridLayout layout = new GridLayout(2, 2);
    container.setLayout(layout);
    
    addComponent(lTitle);
    addComponent(lChoice);
    addComponent(lDirection);
    addComponent(lRoute);
  }

  public void show(String network, String line, String direction, String start,
      String finish, Vector route) {
    lChoice.setText(network + " / " + line);
    lDirection.setText(direction);
    lRoute.setModel(new DefaultListModel(route));
    revalidate();
    show();
  }
  
}
