package hello;

import java.util.Vector;

import javax.microedition.location.Location;

import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.GridLayout;

/**
 * Cartographie main Form.
 * 
 * @author Yacine Touil
 */
public class CartographyMarkForm extends Form implements GPSListener {
  
  /** label choix */
  private Label lChoice = new Label("");
  /** label direction */
  private Label lDirection = new Label("");
  /** label followingStation */
  private Label lPreviousStationTitle = new Label("Arrêt précédent : ");
  /** label followingStation */
  private Label lPreviousStation = new Label("");
  /** label nextStation */
  private Label lNextStationTitle = new Label("Prochain arrêt : ");
  /** label nextStation */
  private Label lNextStation = new Label("");
  
  /** route */
  private Vector route = new Vector();
  
  /**
   * default constructor : initialization of the Form
   * @param parent parent
   */
  public CartographyMarkForm() {
    setTitle(Constants.MAIN_SCREEN_TITLE);
    setTransitionOutAnimator(CommonTransitions.createFade(400));
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    setScrollableY(false);
    
    lChoice.getStyle().setBgTransparency(0);
    lDirection.getStyle().setBgTransparency(0);
    lPreviousStationTitle.getStyle().setBgTransparency(0);
    lPreviousStation.getStyle().setBgTransparency(0);
    lNextStationTitle.getStyle().setBgTransparency(0);
    lNextStation.getStyle().setBgTransparency(0);
    
//    Container container = new Container();
//    GroupLayout layout = new GroupLayout(container);
//    container.setLayout(layout);
//    layout.setAutocreateGaps(true);
//    layout.setAutocreateContainerGaps(true);
//    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
//    hGroup.add(layout.createParallelGroup().add(lPreviousStationTitle).add(lPreviousStation)).
//           add(layout.createParallelGroup().add(lNextStationTitle).add(lNextStation));
//    layout.setHorizontalGroup(hGroup);
//    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
//    vGroup.add(layout.createParallelGroup(GroupLayout.BASELINE).add(lPreviousStationTitle).add(lPreviousStation)).
//           add(layout.createParallelGroup(GroupLayout.BASELINE).add(lNextStationTitle).add(lNextStation));
//    layout.setVerticalGroup(vGroup);

    Container container = new Container();
    GridLayout layout = new GridLayout(2, 2);
    container.setLayout(layout);
    container.addComponent(lPreviousStationTitle);
    container.addComponent(lPreviousStation);
    container.addComponent(lNextStationTitle);
    container.addComponent(lNextStation);
    
    addComponent(lChoice);
    addComponent(lDirection);
    addComponent(container);
  }

  /**
   * show this form
   * @param network network
   * @param line line
   * @param direction direction
   * @param start start
   * @param finish finish
   */
  public void show(String network, String line, String direction, String start,
      String finish) {
    lChoice.setText(network + " / " + line);
    lDirection.setText(direction);
    lPreviousStation.setText(start);
    lNextStation.setText(finish);
    revalidate();
    show();
  }
  
  /**
   * @return route
   */
  public Vector getRoute() {
    return route;
  }

  /**
   * mark GPS position for the current station
   */
  public void mark() {
    GPSRetriever ret = new GPSRetriever(this);
    ret.start();
    
  }

  /**
   * @see hello.GPSListener#receive(javax.microedition.location.Location)
   */
  public void receive(Location location) {
    // create a org.kercoin.tekku.model.Station
    route.addElement(location);
  }

}
