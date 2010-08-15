package hello;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GroupLayout;

/**
 * Cartographie selection Form.
 * 
 * @author Yacine Touil
 */
public class CartographySelectionForm extends Form {
  
  /** label choix */
  private Label lChoice = new Label("");
  
  /** comboBox direction */
  private ComboBox cbDirection;
  /** comboBox start */
  private ComboBox cbStart;
  /** comboBox finish */
  private ComboBox cbFinish;
  
  /**
   * default constructor : initialization of the Form
   * @param parent parent
   */
  public CartographySelectionForm() {
    setTitle(Constants.MAIN_SCREEN_TITLE);
    setTransitionOutAnimator(CommonTransitions.createFade(400));
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    setScrollableY(false);
    
    Label lChoiceTitle = new Label("Choix du parcours 2/2");
    lChoiceTitle.getStyle().setBgTransparency(0);
    lChoice.getStyle().setBgTransparency(0);
    
    Label lDirection = new Label("Direction");
    lDirection.getStyle().setBgTransparency(0);
    String[] directions = {"Boissy-Saint-Léger", "La Défense"};
    cbDirection = new ComboBox(directions);
    
    Label lStart = new Label("Départ");
    lStart.getStyle().setBgTransparency(0);
    String[] stationsStart = {"Les Halles", "Auber"};
    cbStart = new ComboBox(stationsStart);
    
    Label lFinish = new Label("Arivée");
    lFinish.getStyle().setBgTransparency(0);
    String[] stationsFinish = {"---", "Auber", "La Défense"};
    cbFinish = new ComboBox(stationsFinish);

    Container container = new Container();
    GroupLayout layout = new GroupLayout(container);
    container.setLayout(layout);
    layout.setAutocreateGaps(true);
    layout.setAutocreateContainerGaps(true);
    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
    hGroup.add(layout.createParallelGroup().add(lDirection).add(lStart).add(lFinish)).
           add(layout.createParallelGroup().add(cbDirection).add(cbStart).add(cbFinish));
    layout.setHorizontalGroup(hGroup);
    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
    vGroup.add(layout.createParallelGroup(GroupLayout.BASELINE).add(lDirection).add(cbDirection)).
           add(layout.createParallelGroup(GroupLayout.BASELINE).add(lStart).add(cbStart)).
           add(layout.createParallelGroup(GroupLayout.BASELINE).add(lFinish).add(cbFinish));
    layout.setVerticalGroup(vGroup);

    addComponent(lChoice);
    addComponent(container);
  }
  
  /**
   * show this form
   * @param network network
   * @param line line
   */
  public void show(String network, String line) {
    lChoice.setText(network + " / " + line);
    show();
  }

  /**
   * @return direction
   */
  public String getDirection() {
    return "" + cbDirection.getSelectedItem();
  }

  /**
   * @return start
   */
  public String getStart() {
    return "" + cbStart.getSelectedItem();
  }

  /**
   * @return finish
   */
  public String getFinish() {
    return "" + cbFinish.getSelectedItem();
  }
  
}
