/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.model.ModelBase.java
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

import java.io.Serializable;

import org.sfc.text.Textable;

/**
 * The base class for everything in the modes
 *
 * @author Thomas Weise
 */
public abstract class ModelBase extends Textable implements Serializable {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * create a new model base
   */
  ModelBase() {
    super();
  }

  /**
   * Append this object's textual representation to a string builder.
   *
   * @param sb
   *          The string builder to append to.
   * @see #toString()
   */
  @Override
  public void toStringBuilder(final StringBuilder sb) {
    sb.append(this.getClass().getSimpleName());
  }

  /**
   * Obtain the simulation this node is part of.
   *
   * @return the simulation this node is part of or <code>null</code> if
   *         none exists
   */
  public abstract Simulation getSimulation();

  /**
   * Log a message.
   *
   * @param str
   *          the string to be logged
   */
  public abstract void log(final String str);

  /**
   * Reset this model base to the initial state.
   */
  public void reset() {
    //
  }

  /**
   * Write all the properties of this model base item to the given property
   * destination
   *
   * @param pd
   *          the destination
   */
  public void writeProperties(final IPropertyDest pd) {
    pd.writeProperty("type", this.getClass().getSimpleName()); //$NON-NLS-1$
  }
}
