package hello;

import com.sun.lwuit.ComboBox;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GroupLayout;

/**
 * Cartographie main Form.
 * 
 * @author Yacine Touil
 */
public class CartographyMainForm extends Form {
  
  /** comboBox network */
  private ComboBox cbNetwork;
  /** comboBox line */
  private ComboBox cbLine;
  
  /**
   * default constructor : initialization of the Form
   * @param parent parent
   */
  public CartographyMainForm() {
    setTitle(Constants.MAIN_SCREEN_TITLE);
    setTransitionOutAnimator(CommonTransitions.createFade(400));
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    setScrollableY(false);
    
    Label lChoiceTitle = new Label("Choix du parcours 1/2");
    lChoiceTitle.getStyle().setBgTransparency(0);
    
    Label lNetwork = new Label("Réseau");
    lNetwork.getStyle().setBgTransparency(0);
    String[] networks = {"RATP (Ile-de-France)"};
    cbNetwork = new ComboBox(networks);
    
    Label lLine = new Label("Ligne");
    lLine.getStyle().setBgTransparency(0);
    String[] lines = {"RER A", "RER B"};
    cbLine = new ComboBox(lines);

    Container container = new Container();
    GroupLayout layout = new GroupLayout(container);
    container.setLayout(layout);
    layout.setAutocreateGaps(true);
    layout.setAutocreateContainerGaps(true);
    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
    hGroup.add(layout.createParallelGroup().add(lNetwork).add(lLine)).
           add(layout.createParallelGroup().add(cbNetwork).add(cbLine));
    layout.setHorizontalGroup(hGroup);
    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
    vGroup.add(layout.createParallelGroup(GroupLayout.BASELINE).add(lNetwork).add(cbNetwork)).
           add(layout.createParallelGroup(GroupLayout.BASELINE).add(lLine).add(cbLine));
    layout.setVerticalGroup(vGroup);

    addComponent(lChoiceTitle);
    addComponent(container);
  }
  
  /**
   * @return network
   */
  public String getNetwork() {
    return "" + cbNetwork.getSelectedItem();
  }
  
  /**
   * @return line
   */
  public String getLine() {
    return "" + cbLine.getSelectedItem();
  }
  
}
