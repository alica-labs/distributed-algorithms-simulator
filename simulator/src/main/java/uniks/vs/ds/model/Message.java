/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.Message.java
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
 * A message travels from one end point of a connection to another endpoit.
 *
 * @author Thomas Weise
 */
public class Message extends SimulationBase implements Cloneable {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the source node
   */
  SimNode m_source;

  /**
   * the destination node
   */
  SimNode m_dest;

  /**
   * the message queue
   */
  Message m_next;

  /**
   * the direction
   */
  boolean m_dir;

  /**
   * the distance to go
   */
  int m_dist;

  /**
   * the connection this message travels on
   */
  Connection m_con;

  /**
   * Create a new message
   */
  public Message() {
    super();
  }

  /**
   * Obtain the source node
   *
   * @return the source node
   */
  public SimNode getSource() {
    return this.m_source;
  }

  /**
   * Obtain the destination node
   *
   * @return the destination node
   */
  public SimNode getDestination() {
    return this.m_dest;
  }

  /**
   * Obtain the direction in which the message is moving on the connection.
   * If <code>true</code> is returned, the message moves from the first
   * node of the connection to the second one. <code>false</code> is
   * returned for the oposite direction.
   *
   * @return the direction in which the message is moving
   */
  public boolean getDirection() {
    return this.m_dir;
  }

  /**
   * Obtain the remaining distance that the message still has to travel
   * until it reaches its destination.
   *
   * @return the remaining distance that the message still has to travel
   *         until it reaches its destination.
   */
  public int getRemainingDistance() {
    return this.m_dist;
  }

  /**
   * Copy this message
   *
   * @return the copy
   */
  final Message copy() {
    try {
      return ((Message) (this.clone()));
    } catch (Throwable t) {
      return this;
    }

  }

  /**
   * Obtain the connection this message was sent over.
   *
   * @return the connection this message was sent over
   */
  public Connection getConnection() {
    return this.m_con;
  }

  /**
   * Reset this model base to the initial state.
   */
  @Override
  public synchronized void reset() {
    this.m_dest = null;
    this.m_dir = false;
    this.m_dist = 0;
    this.m_next = null;
    this.m_sim = null;
    this.m_source = null;
    super.reset();
  }
}
