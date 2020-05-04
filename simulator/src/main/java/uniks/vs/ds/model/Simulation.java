/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.model.Simulation.java
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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.sfc.collections.CollectionUtils;
import org.sfc.io.IO;
import org.sfc.parallel.simulation.IStepable;
import org.sfc.utils.ErrorUtils;

/**
 * The main simulation interface.
 *
 * @author Thomas Weise
 */
public class Simulation extends ModelBase implements IStepable {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the list of nodes
   */
  private final List<SimNode> m_Sim_nodes;

  /**
   * the list of connections
   */
  private final List<Connection> m_connections;

  /**
   * the simulation listeners
   */
  private final List<ISimulationListener> m_listeners;

  /**
   * the id counter
   */
  private int m_idCnt;

  /**
   * the message count;
   */
  int m_msgCount;

  /**
   * create a new simulation
   */
  public Simulation() {
    super();
    this.m_Sim_nodes = CollectionUtils.createList(-1);
    this.m_connections = CollectionUtils.createList(-1);
    this.m_listeners = CollectionUtils.createList(-1);
  }

  /**
   * perform a step.
   */
  public synchronized void step() {
    int i;
    List<SimNode> n;
    List<Connection> c;

    n = this.m_Sim_nodes;
    for (i = (n.size() - 1); i >= 0; i--) {
      if (i < n.size()) {
        n.get(i).step();
      }
    }

    c = this.m_connections;
    for (i = (c.size() - 1); i >= 0; i--) {
      if (i < c.size()) {
        c.get(i).step();
      }
    }

    this.onAfterStep();
  }

  /**
   * remove a node from the simulation
   *
   * @param n
   *          the node to be removed
   */
  public synchronized void removeNode(final SimNode n) {
    int i;
    List<Connection> cc;

    if (this.m_Sim_nodes.remove(n)) {
      cc = n.m_connections;
      for (i = (cc.size() - 1); i >= 0; i--) {
        this.disconnect(cc.get(i));
      }
      synchronized (n) {
        n.m_sim = null;
      }

      for (ISimulationListener l : this.m_listeners) {
        l.onNodeRemoved(n);
      }
    }
  }

  /**
   * add a node to the simulation
   *
   * @param n
   *          the node added
   */
  public synchronized void addNode(final SimNode n) {
    if ((!(this.m_Sim_nodes.contains(n))) && (this.m_Sim_nodes.add(n))) {
      synchronized (n) {
        n.m_sim = this;
        n.setId(++this.m_idCnt);
        n.init();
      }
      for (ISimulationListener l : this.m_listeners) {
        l.onNodeAdded(n);
      }
    }
  }

  /**
   * remove a connection from the simulation
   *
   * @param con
   *          the connection to be removed
   */
  public synchronized void disconnect(final Connection con) {
    SimNode n;
    if (this.m_connections.remove(con)) {
      n = con.getNode1();
      synchronized (n) {
        n.m_connections.remove(con);
      }
      n = con.getNode2();
      synchronized (n) {
        n.m_connections.remove(con);
      }
      for (ISimulationListener l : this.m_listeners) {
        l.onNodesDisconnected(con);
      }
      synchronized (con) {
        con.m_sim = null;
      }
    }
  }

  /**
   * Add a simulation listener
   *
   * @param n
   *          the new listener
   */
  public synchronized void addListener(final ISimulationListener n) {
    this.m_listeners.add(n);
  }

  /**
   * remove a simulation listener
   *
   * @param n
   *          the listener to be removed
   */
  public synchronized void removeListener(final ISimulationListener n) {
    this.m_listeners.remove(n);
  }

  /**
   * Connect two nodes in the simulation
   *
   * @param n1
   *          the first node to be connected
   * @param n2
   *          the second node to be connected
   * @return the new connection
   */
  public synchronized Connection connectNodes(final SimNode n1, final SimNode n2) {
    Connection n;

    synchronized (n1) {
      for (Connection c : n1.m_connections) {
        if ((c.getNode1() == n2) || (c.getNode2() == n2))
          return c;
      }
      n = new Connection(n1, n2);
      n1.m_connections.add(n);
    }
    synchronized (n2) {
      n2.m_connections.add(n);
    }
    this.m_connections.add(n);

    for (ISimulationListener l : this.m_listeners) {
      l.onNodesConnected(n);
    }
    synchronized (n) {
      n.m_sim = this;
    }

    return n;
  }

  /**
   * this method is called after a simulation step has been performed
   */
  final void onAfterStep() {
    for (ISimulationListener l : this.m_listeners) {
      l.onAfterStep();
    }
  }

  /**
   * Obtain the current node count
   *
   * @return the current node count
   */
  public synchronized int getNodeCount() {
    return this.m_Sim_nodes.size();
  }

  /**
   * Obtain the node at the specified index.
   *
   * @param index
   *          the index to obtain the node at
   * @return the node at the specified index.
   */
  public synchronized SimNode getNode(final int index) {
    return this.m_Sim_nodes.get(index);
  }

  /**
   * Obtain the current connection count
   *
   * @return the current connection count
   */
  public synchronized int getConnectionCount() {
    return this.m_connections.size();
  }

  /**
   * Obtain the connection at the specified index.
   *
   * @param index
   *          the index to obtain the connection at
   * @return the connection at the specified index.
   */
  public synchronized Connection getConnection(final int index) {
    return this.m_connections.get(index);
  }

  /**
   * Log a message.
   *
   * @param str
   *          the string to be logged
   */
  @Override
  public void log(final String str) {
    this.log(str, this);
  }

  /**
   * Log a message.
   *
   * @param str
   *          the string to be logged
   * @param source
   *          the source object
   */
  synchronized final void log(final String str, final ModelBase source) {
    for (ISimulationListener l : this.m_listeners) {
      l.onLog(str, source);
    }
  }

  /**
   * Obtain the simulation this node is part of.
   *
   * @return the simulation this node is part of or <code>null</code> if
   *         none exists
   */
  @Override
  public Simulation getSimulation() {
    return this;
  }

  /**
   * Reset this model base to the initial state.
   */
  @Override
  public synchronized void reset() {
    List<Connection> m;
    List<SimNode> n;
    int i;

    m = this.m_connections;
    for (i = (m.size() - 1); i >= 0; i--) {
      m.get(i).reset();
    }

    n = this.m_Sim_nodes;
    for (i = (n.size() - 1); i >= 0; i--) {
      n.get(i).reset();
    }
    this.m_msgCount = 0;
    super.reset();

    this.onAfterStep();
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
    pd.writeProperty("#nodes", String.valueOf(this.m_Sim_nodes.size()));//$NON-NLS-1$
    pd.writeProperty(
        "#connections", String.valueOf(this.m_connections.size()));//$NON-NLS-1$
    pd.writeProperty("#msg", String.valueOf(this.m_msgCount));//$NON-NLS-1$
  }

  /**
   * trigger all nodes
   */
  public synchronized void trigger() {
    List<SimNode> n;
    int i;

    n = this.m_Sim_nodes;
    for (i = (n.size() - 1); i >= 0; i--) {
      n.get(i).trigger();
    }

    this.onAfterStep();
  }

  /**
   * Serializa a simulation to the given destination.
   *
   * @param sim
   *          the simulation to be serialized
   * @param dest
   *          the destination
   */
  public static final void storeSimulation(final Simulation sim,
      final Object dest) {
    OutputStream os;
    ObjectOutputStream oos;

    os = IO.getOutputStream(dest);
    if (os != null) {
      try {
        oos = new ObjectOutputStream(os);

        try {
          synchronized (sim) {
            oos.writeObject(sim);
          }
        } catch (Throwable t) {
          ErrorUtils.onError(t);
        } finally {
          oos.close();
          os = null;
        }
      } catch (Throwable t) {
        ErrorUtils.onError(t);
      } finally {
        try {
          if (os != null)
            os.close();
        } catch (Throwable t) {
          ErrorUtils.onError(t);
        }
      }
    } else
      ErrorUtils.onError(new IOException("Could not create file.")); //$NON-NLS-1$
  }

  /**
   * Load a simulation from the given destination.
   *
   * @param source
   *          the source to load from
   * @return the simulation or <code>null</code> if loading failed
   */
  public static final Simulation loadSimulation(final Object source) {
    InputStream is;
    ObjectInputStream ois;
    Object o;

    is = IO.getInputStream(source);
    if (is != null) {
      try {
        ois = new ObjectInputStream(is);
        try {
          do {
            o = ois.readObject();
            if (o instanceof Simulation)
              return ((Simulation) o);
          } while (o != null);
        } catch (Throwable t) {
          ErrorUtils.onError(t);
        } finally {
          ois.close();
          is = null;
        }
      } catch (Throwable t) {
        ErrorUtils.onError(t);
      } finally {
        try {
          if (is != null)
            is.close();
        } catch (Throwable t) {
          ErrorUtils.onError(t);
        }
      }
    } else
      ErrorUtils.onError(new IOException("Could not open file.")); //$NON-NLS-1$

    return null;
  }
}
