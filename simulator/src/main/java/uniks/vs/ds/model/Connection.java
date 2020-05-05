/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.model.Connection.java
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
import org.sfc.parallel.simulation.IStepable;

/**
 * This class represents a connection in our network.
 *
 * @author Thomas Weise
 */
public class Connection extends SimulationBase implements IStepable {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the default speed
   */
  private static final int DEFAULT_SPEED = 10;

  /**
   * The first node of the connection
   */
  private final SimNode m_Sim_node1;

  /**
   * The second node of the connection
   */
  private final SimNode m_Sim_node2;

  /**
   * the length
   */
  private final int m_length;

  /**
   * the message queue
   */
  volatile Message m_queue;

  /**
   * the last message
   */
  volatile Message m_last;

  /**
   * the message count;
   */
  private volatile int m_msgCount;

  /**
   * the connection speed.
   */
  private volatile int m_speed;

  /**
   * Create a new connection
   *
   * @param n1
   *          The first node of the connection
   * @param n2
   *          The second node of the connection
   */
  Connection(final SimNode n1, final SimNode n2) {
    super();

    int x, y;

    this.m_Sim_node1 = n1;
    this.m_Sim_node2 = n2;
    this.m_speed = DEFAULT_SPEED;

    x = n1.getX();
    y = n1.getY();

    x -= n2.getX();
    y -= n2.getY();

    this.m_length = ((int) (0.5d + Math.sqrt(((double) x * x)
        + ((double) y * y))));
  }

  /**
   * Obtain the approximate time a message will travel on this connection.
   *
   * @return the approximate time a message will travel on this connection
   */
  public int getApproximateMessageLatency() {
    return ((int) (0.5d + (this.m_length * 0.1d)));
  }

  /**
   * Obtain the first node of the connection
   *
   * @return the first node of the connection
   */
  public SimNode getNode1() {
    return this.m_Sim_node1;
  }

  /**
   * Obtain the second node of the connection
   *
   * @return the second node of the connection
   */
  public SimNode getNode2() {
    return this.m_Sim_node2;
  }

  /**
   * Obtain the current length of the connection
   *
   * @return the current length of the connection
   */
  public double getLength() {
    return this.m_length;
  }

  /**
   * Obtain the speed of this connection.
   *
   * @return the speed of this connection.
   */
  public int getSpeed() {
    return this.m_speed;
  }

  /**
   * Set the speed of this connection
   *
   * @param speed
   *          the speed of this connection
   */
  public synchronized void setSpeed(final int speed) {
    this.m_speed = speed;
  }

  /**
   * Perform a step in this connection
   */
  public synchronized void step() {
    Message m, mn, n;

    m = mn = this.m_queue;
    for (; m != null; m = n) {
      n = m.m_next;
      if ((m.m_dist -= this.m_speed) <= 0) {
        m.m_dist = 0;
        mn = n;
        this.m_queue = mn;
        if (mn == null)
          this.m_last = null;
        m.m_next = null;
        this.onMessageReceived(m);
        m.m_dest.onMessageReceived(m);
      }
    }

  }

  /**
   * Obtain the first message in the message queue, if any.
   *
   * @return the first message in the message queue, if any.
   */
  public synchronized Message getFirstQueuedMessage() {
    return this.m_queue;
  }

  /**
   * Obtain the message in the queue right after <code>msg</code>.
   *
   * @param msg
   *          the message we want to find the successor of
   * @return the successor message, or <code>null</code> if none exists
   */
  public synchronized Message getNextQeuedMessage(final Message msg) {
    return (msg == null) ? null : msg.m_next;
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
    sb.append("connection: "); //$NON-NLS-1$
    sb.append(this.m_Sim_node1.getID());
    sb.append("<->"); //$NON-NLS-1$
    sb.append(this.m_Sim_node2.getID());
  }

  /**
   * Send a message
   *
   * @param msg
   *          the message to be sent
   */
  synchronized final void send(final Message msg) {
    msg.m_next = null;
    if (this.m_last == null) {
      this.m_queue = msg;
      this.m_last = msg;
    } else {
      this.m_last.m_next = msg;
      this.m_last = msg;
    }
    this.m_msgCount++;
    msg.m_con = this;
    msg.m_dist = this.m_length;
    this.onMessageSent(msg);
  }

  /**
   * Obtain the default color.
   *
   * @return the default color
   */
  @Override
  protected Color getDefaultColor() {
    return Color.BLACK;
  }

  /**
   * This method is called whenever a message is sent over this connection.
   *
   * @param msg
   *          the message sent
   */
  protected void onMessageSent(final Message msg) {
    //
  }

  /**
   * This method is called whenever a message has successfuly reached the
   * end of this connection and thus was received by the node there.
   *
   * @param msg
   *          the message received
   */
  protected void onMessageReceived(final Message msg) {
    //
  }

  /**
   * Reset this model base to the initial state.
   */
  @Override
  public synchronized void reset() {
    this.m_queue = null;
    this.m_last = null;
    this.m_msgCount = 0;
    this.m_speed = DEFAULT_SPEED;
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
    pd.writeProperty("node 1", String.valueOf(this.m_Sim_node1.getID()));//$NON-NLS-1$
    pd.writeProperty("node 2", String.valueOf(this.m_Sim_node2.getID()));//$NON-NLS-1$
    pd.writeProperty("speed", String.valueOf(this.m_speed));//$NON-NLS-1$
    pd.writeProperty("#msgs", String.valueOf(this.m_msgCount));//$NON-NLS-1$
  }
}
