package de.uniks.vs.simulator.model;

import de.uniks.vs.simulator.algorithms.ColorUtils;
import de.uniks.vs.simulator.model.utils.IPropertyDest;
import de.uniks.vs.simulator.simulation.Simulation;
import javafx.scene.paint.Color;

class Item extends Model {

  private Color color;
  Simulation mSim;

  Item() {
    super();
    this.color = this.getDefaultColor();
  }

  protected Color getDefaultColor() {
    return Color.WHITE;
  }
  public void setColor(final Color s) {
    this.color = (s != null) ? s : this.getDefaultColor();
  }
  public Color getColor() {
    return this.color;
  }
  public Simulation getSim() { return mSim; }
  public void setSim(Simulation sim) { this.mSim = sim; }

  @Override
  public Simulation getSimulation() {
    return this.mSim;
  }

  @Override
  public synchronized void log(final String str) {
    if (this.mSim != null)
      this.mSim.log(str, this);
  }

  @Override
  public synchronized void reset() {
    this.color = this.getDefaultColor();
    super.reset();
  }

  @Override
  public synchronized void writeProperties(final IPropertyDest pd) {
    super.writeProperties(pd);
    pd.writeProperty("color", ColorUtils.getColorName(this.color));//$NON-NLS-1$
    // this.getColorName());
  }
  // /**
  // * the known colors
  // */
  // private static final Color[] COLS = new Color[] { Color.BLACK,
  // Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN,
  // Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,
  // Color.RED, Color.WHITE, Color.YELLOW };
  //
  // /**
  // * the strings
  // */
  // private static final String[] STRS = new String[] { "black",
  // //$NON-NLS-1$
  // "blue", //$NON-NLS-1$
  // "cyan", //$NON-NLS-1$
  // "darkGray", //$NON-NLS-1$
  // "gray", //$NON-NLS-1$
  // "green", //$NON-NLS-1$
  // "lightGray", //$NON-NLS-1$
  // "magenta", //$NON-NLS-1$
  // "orange", //$NON-NLS-1$
  // "pink", //$NON-NLS-1$
  // "red", //$NON-NLS-1$
  // "white", //$NON-NLS-1$
  // "yellow" //$NON-NLS-1$
  // };



  // /**
  // * Append this object's textual representation to a string builder.
  // *
  // * @param sb
  // * The string builder to append to.
  // * @see #toString()
  // */
  // @Override
  // public synchronized void toStringBuilder(final StringBuilder sb) {
  // super.toStringBuilder(sb);
  //
  // sb.append('[');
  // sb.append(ColorUtils.getName(this.m_color));
  // // this.getColorName());
  // sb.append(']');
  // }

  // /**
  // * Obtain the name of this object's current color
  // *
  // * @return the name of this object's current color
  // */
  // private final String getColorName() {
  // int i;
  // Color y;
  //
  // y = this.m_color;
  //
  // for (i = (COLS.length - 1); i >= 0; i--) {
  // if (COLS[i].equals(y)) {
  // return STRS[i];
  // }
  // }
  //
  // return Integer.toHexString(y.getRed())
  // + Integer.toHexString(y.getBlue())
  // + Integer.toHexString(y.getGreen());
  // }
}
