/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.Node.java
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

import java.util.ArrayList;
import java.util.List;

import org.sfc.collections.CollectionUtils;
import org.sfc.parallel.simulation.IStepable;

/**
 * This class represents a node in our network.
 *
 * @author Thomas Weise
 */
public class Node extends SimulationBase implements IStepable {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the connections
   */
  final List<Connection> m_connections;

  /**
   * the text of this node
   */
  private String m_text;

  /**
   * the x-coordinate of the node
   */
  private final int m_x;

  /**
   * the y-coordinate of the node
   */
  private final int m_y;

  /**
   * the id of the node
   */
  private int m_id;

  /**
   * the count of messages sent
   */
  private int m_sent;

  /**
   * the count of messages received
   */
  private int m_received;

  /**
   * The node
   *
   * @param x
   *          the x-coordinate
   * @param y
   *          the y-coordinate
   */
  public Node(final int x, final int y) {
    super();
    this.m_connections = CollectionUtils.createList(-1);
    this.m_x = x;
    this.m_y = y;
    this.m_text = ""; //$NON-NLS-1$
  }

  /**
   * Obtain the id of this node
   *
   * @return the id of this node
   */
  public int getID() {
    return this.m_id;
  }

  /**
   * Obtain the text of this node
   *
   * @return the text of this node
   */
  public String getText() {
    return this.m_text;
  }

  /**
   * Set the text of this node.
   *
   * @param s
   *          the new text
   */
  public void setText(final String s) {
    this.m_text = (s != null) ? s : ""; //$NON-NLS-1$
  }

  /**
   * Obtain the x-coordinate of this node
   *
   * @return the x-coordinate of this node
   */
  public int getX() {
    return this.m_x;
  }

  /**
   * Obtain the y-coordinate of this node
   *
   * @return the y-coordinate of this node
   */
  public int getY() {
    return this.m_y;
  }

  /**
   * perform a step in this node
   */
  public synchronized void step() {
    //
  }

  /**
   * This method is invoked whenever a message arrives at this node.
   *
   * @param msg
   *          the message just received
   */
  public synchronized void onMessageReceived(final Message msg) {
    this.m_received++;
  }

  /**
   * Broadcast a message to all neighbors.
   *
   * @param msg
   *          the message to broadcast
   * @return the number of nodes we've sent a message to
   */
  public synchronized int broadcast(final Message msg) {
    int i, t;
    List<Connection> c;
    Connection x;
    Message m;
    Node v;
    Simulation s;

    if (msg == null)
      return 0;

    c = this.m_connections;
    s = this.m_sim;
    msg.m_sim = this.m_sim;
    msg.m_source = this;
    t = c.size();
    for (i = (t - 1); i >= 0; i--) {
      m = msg.copy();
      x = c.get(i);
      v = x.getNode1();
      m.m_dir = (v == this);
      if (m.m_dir)
        v = x.getNode2();
      m.m_dest = v;
      x.send(m);
      this.m_sent++;
      if (s != null) {
        //TODO: is this a problem ???
        //synchronized (s) {
          s.m_msgCount++;
        //}
      }
    }

    return t;
  }

  /**
   * Broadcast a message to all (except one) neighbors.
   *
   * @param msg
   *          the message to broadcast
   * @param n
   *          the one and only node to which no message should be send
   * @return the number of nodes we've sent a message to
   */
  public synchronized int broadcastExcept(final Message msg, final Node n) {
    int i, t;
    List<Connection> c;
    Connection x;
    Message m;
    Node v;
    Simulation s;

    if (n == null)
      this.broadcast(msg);

    if (msg == null)
      return 0;

    s = this.m_sim;
    c = this.m_connections;
    msg.m_source = this;
    msg.m_sim = this.m_sim;
    t = 0;
    for (i = (c.size() - 1); i >= 0; i--) {
      x = c.get(i);
      if ((x.getNode1() == n) || (x.getNode2() == n))
        continue;
      this.m_sent++;
      t++;
      if (s != null) {
        synchronized (s) {
          s.m_msgCount++;
        }
      }
      m = msg.copy();
      v = x.getNode1();
      m.m_dir = (v == this);
      if (m.m_dir)
        v = x.getNode2();
      m.m_dest = v;
      x.send(m);
    }
    return t;
  }

  /**
   * Send a message to a specified neighbor node.
   *
   * @param msg
   *          the message to broadcast
   * @param n
   *          the node to send the message to
   * @return the number of nodes we've sent a message to
   */
  public synchronized int sendTo(final Message msg, final Node n) {
    int i;
    List<Connection> c;
    Connection x;
    Simulation s;

    if (msg == null)
      return 0;

    c = this.m_connections;
    s = this.m_sim;
    msg.m_sim = this.m_sim;
    for (i = (c.size() - 1); i >= 0; i--) {
      x = c.get(i);

      if (x.getNode1() == n) {
        msg.m_dir = false;
      } else {
        if (x.getNode2() == n) {
          msg.m_dir = true;
        } else
          continue;
      }

      msg.m_dest = n;
      msg.m_source = this;
      x.send(msg.copy());
      this.m_sent++;
      if (s != null) {
        synchronized (s) {
          s.m_msgCount++;
        }
      }
      return 1;
    }

    return 0;
  }

  /**
   * Send a message to a neighbor node specified via its id.
   *
   * @param msg
   *          the message to broadcast
   * @param id
   *          the id of the node to send the message to
   * @return the number of nodes we've sent a message to
   */
  public synchronized int sendTo(final Message msg, final int id) {
    int i;
    List<Connection> c;
    Connection x;
    Simulation s;
    Node n;

    if (msg == null)
      return 0;

    c = this.m_connections;
    s = this.m_sim;
    msg.m_sim = this.m_sim;
    for (i = (c.size() - 1); i >= 0; i--) {
      x = c.get(i);

      if ((n = x.getNode1()).getID() == id) {
        msg.m_dir = false;
      } else {
        if ((n = x.getNode2()).getID() == id) {
          msg.m_dir = true;
        } else
          continue;
      }

      msg.m_dest = n;
      msg.m_source = this;
      x.send(msg.copy());
      this.m_sent++;
      if (s != null) {
        synchronized (s) {
          s.m_msgCount++;
        }
      }
      return 1;
    }

    return 0;
  }

  /**
   * set this node's id
   *
   * @param id
   *          the id
   */
  final void setId(final int id) {
    this.m_id = id;
  }

  /**
   * Initialize this node. This method is called exactly once, when the
   * node is added to the simulation.
   */
  protected void init() {
    //
  }

  /**
   * Append this object's textual representation to a string builder.
   *
   * @param sb
   *          The string builder to append to.
   * @see #toString()
   */
  @Override
  public synchronized void toStringBuilder(final StringBuilder sb) {
    super.toStringBuilder(sb);
    sb.append(':');
    sb.append(this.m_id);
  }

  /**
   * This method can directly be called from somewhere in order to start
   * something. Currently it does nothing, so fill it with life!!!
   */
  public void trigger() {
//    this.log("triggered"); //$NON-NLS-1$
  }

  /**
   * Reset this node to the initial state.
   */
  @Override
  public synchronized void reset() {
    this.m_text = ""; //$NON-NLS-1$
    this.m_sent = 0;
    this.m_received = 0;
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
    pd.writeProperty("text", this.getText()); //$NON-NLS-1$
    pd.writeProperty(
        "#connections", String.valueOf(this.m_connections.size()));//$NON-NLS-1$
    pd.writeProperty("#msgs sent", String.valueOf(this.m_sent));//$NON-NLS-1$
    pd.writeProperty("#msgs received", String.valueOf(this.m_received));//$NON-NLS-1$
  }

  /**
   * Obtain the count of connections attached to this node.
   *
   * @return the count of connections attached to this node.
   */
  public synchronized int getConnectionCount() {
    return this.m_connections.size();
  }

  /**
   * Obtain the connection at the specified index.
   *
   * @param idx
   *          the index to obtain the connection at.
   * @return the connection at the specified index
   */
  public synchronized Connection getConnection(final int idx) {
    return this.m_connections.get(idx);
  }

  /**
   * Obtain the count of neighbors of this node.
   *
   * @return the count of neighbors of this node.
   */
  public synchronized int getNeighborCount() {
    return this.m_connections.size();
  }

  public synchronized ArrayList<Integer> getNeighbors() {
    ArrayList<Integer> ids = new ArrayList<>();

    for (Connection connection: this.m_connections) {

      if (connection.getNode1().getID() != this.getID())
        ids.add(connection.getNode1().getID());
      else
        ids.add(connection.getNode2().getID());
    }
    return ids;
  }

  /**
   * Obtain the node at the specified index.
   *
   * @param idx
   *          the index to obtain the node at.
   * @return the node at the specified index
   */
  public synchronized Node getNeighbor(final int idx) {
    Connection c;
    Node n;
    c = this.m_connections.get(idx);
    n = c.getNode1();
    return ((n == this) ? (c.getNode2()) : n);
  }

}
