package hello;

import com.sun.lwuit.Component;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.plaf.Style;

/**
 * Simple progress indicator component that fills out the progress made.
 * Progress is assumed to always be horizontal in this widget
 */
public class ProgressBar extends Component {

  /** percent */
  private byte percent;
  /** unfilled */
  private Image unfilled;
  /** filled */
  private Image filled;

  /**
   * The default constructor uses internal rendering to draw the progress
   */
  public ProgressBar() {
    setFocusable(false);
  }

  /**
   * Allows indicating the progress using a filled/unfilled images. The unfilled
   * image is always drawn and the filled image is drawn on top with clipping to
   * indicate the amount of progress made.
   * 
   * @param unfilled an image containing the progress bar without any of its
   *        content being filled (with the progress color)
   * @param filled an image identicall to unfilled in every way except that
   *        progress is completed in this bar.
   */
  public ProgressBar(Image unfilled, Image filled) {
    this();
    this.unfilled = unfilled;
    this.filled = filled;
  }

  /**
   * Indicate to LWUIT the component name for theming in this case "Progress"
   */
  public String getUIID() {
    return "Progress";
  }

  /**
   * Indicates the percent of progress made
   */
  public byte getProgress() {
    return percent;
  }

  /**
   * Indicates the percent of progress made, this method is thread safe and can
   * be invoked from any thread although discression should still be kept so one
   * thread doesn't regress progress made by another thread...
   */
  public void setProgress(byte percent) {
    this.percent = percent;
    repaint();
  }

  /**
   * Return the size we would generally like for the component
   */
  protected Dimension calcPreferredSize() {
    if (filled != null) {
      return new Dimension(filled.getWidth(), filled.getHeight());
    } else {
      // we don't really need to be in the font height but this provides
      // a generally good indication for size expectations
      return new Dimension(Display.getInstance().getDisplayWidth(), Font
          .getDefaultFont().getHeight());
    }
  }

  /**
   * Paint the progress indicator
   */
  public void paint(Graphics g) {
    int width = (int) ((((float) percent) / 100.0f) * getWidth());
    if (filled != null) {
      if (filled.getWidth() != getWidth()) {
        filled = filled.scaled(getWidth(), getHeight());
        unfilled = unfilled.scaled(getWidth(), getHeight());
      }

      // draw based on two user supplied images
      g.drawImage(unfilled, getX(), getY());
      g.clipRect(getX(), getY(), width, getHeight());
      g.drawImage(filled, getX(), getY());
    } else {
      // draw based on simple graphics primitives
      Style s = getStyle();
      g.setColor(s.getBgColor());
      int curve = getHeight() / 2 - 1;
      g.fillRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve,
          curve);
      g.setColor(s.getFgColor());
      g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve,
          curve);
      g.clipRect(getX(), getY(), width - 1, getHeight() - 1);
      g.setColor(getSelectedStyle().getBgColor());
      g.fillRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve,
          curve);
      g.setColor(s.getFgColor());
      g.drawRoundRect(getX(), getY(), getWidth() - 1, getHeight() - 1, curve,
          curve);
    }
  }
}
