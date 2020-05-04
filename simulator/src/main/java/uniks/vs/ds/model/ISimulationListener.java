/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.ISimulationListener.java
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

/**
 * The simulation listener interface
 *
 * @author Thomas Weise
 */
public interface ISimulationListener {

  /**
   * A node was added
   *
   * @param n
   *          the node
   */
  public abstract void onNodeAdded(final SimNode n);

  /**
   * A node was removed
   *
   * @param n
   *          the removed node
   */
  public abstract void onNodeRemoved(final SimNode n);

  /**
   * Two nodes were connected by a new connection
   *
   * @param newCon
   *          the new connection
   */
  public abstract void onNodesConnected(final Connection newCon);

  /**
   * Two nodes were discconnected
   *
   * @param disCon
   *          the removed connection
   */
  public abstract void onNodesDisconnected(final Connection disCon);

  /**
   * this method is called after a simulation step has been performed
   */
  public abstract void onAfterStep();

  /**
   * This method is invoked if a string has been written to the log by a
   * source.
   *
   * @param logged
   *          the string logged
   * @param source
   *          the object that logged the string
   */
  public abstract void onLog(final String logged, final ModelBase source);
}
