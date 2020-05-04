/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.SimulationBase.java
 * Last modification: 2009-04-01
 *                by: Thomas Weise
 *
 * License          : GNU LESSER GENERAL PUBLIC LICENSE
 *                    Version 2.1, February 1999
 *                    You should have received a copy of this license along
 *                    with this library; if not, write to theFree Software
 *                    Foundation, Inc. 51 Franklin Street, Fifth Floor,
 *                    Boston, MA 02110-1301, USA or download the license
 *                    under http://www.gnu.org/licenses/lgpl.html or
 *                    http://www.gnu.org/copyleft/lesser.html.
 *
 * Warranty         : This software is provided "as is" without any
 *                    warranty; without even the implied warranty of
 *                    merchantability or fitness for a particular purpose.
 *                    See the Gnu Lesser General Public License for more
 *                    details.
 */

package uniks.vs.ds.model;

import javafx.scene.paint.Color;
import uniks.vs.ds.algorithms.ColorUtils;

/**
 * This is the internal base class for all entities part of the simulation.
 *
 * @author Thomas Weise
 */
class SimulationBase extends ModelBase {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the color of this node
   */
  private Color m_color;

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

  /**
   * the simulation
   */
  Simulation m_sim;

  /**
   * Create a new simulation base object.
   */
  SimulationBase() {
    super();
    this.m_color = this.getDefaultColor();
  }

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

  /**
   * Obtain the default color.
   *
   * @return the default color
   */
  protected Color getDefaultColor() {
    return Color.WHITE;
  }

  /**
   * Set the color of this node.
   *
   * @param s
   *          the new color
   */
  public void setColor(final Color s) {
    this.m_color = (s != null) ? s : this.getDefaultColor();
  }

  /**
   * Obtain this node's color
   *
   * @return this node's color
   */
  public Color getColor() {
    return this.m_color;
  }

  /**
   * Obtain the simulation this node is part of.
   *
   * @return the simulation this node is part of or <code>null</code> if
   *         none exists
   */
  @Override
  public Simulation getSimulation() {
    return this.m_sim;
  }

  /**
   * Log a message.
   *
   * @param str
   *          the string to be logged
   */
  @Override
  public synchronized void log(final String str) {
    if (this.m_sim != null)
      this.m_sim.log(str, this);
  }

  /**
   * Reset this model base to the initial state.
   */
  @Override
  public synchronized void reset() {
    this.m_color = this.getDefaultColor();
    super.reset();
  }

  /**
   * Write all the properties of this model base item to the given property
   * destination
   *
   * @param pd
   *          the destination
   */
  @Override
  public synchronized void writeProperties(final IPropertyDest pd) {
    super.writeProperties(pd);
    pd.writeProperty("color", ColorUtils.getColorName(this.m_color));//$NON-NLS-1$
    // this.getColorName());
  }
}
