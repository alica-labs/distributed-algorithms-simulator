/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.INodeFactory.java
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
 * The basic interface for node creation.
 *
 * @author Thomas Weise
 */
public interface INodeFactory {

  /**
   * Create a new node the specified x and y-coordinates.
   *
   * @param x
   *          the x-coordinate for the new node
   * @param y
   *          the y-coordinate for the new node
   * @return the new node
   */
  public abstract SimNode createNode(final double x, final double y);

  /**
   * Obtain a human readable description of the node's type name.
   *
   * @return The node name.
   */
  public abstract String getNodeTypeName();

}
