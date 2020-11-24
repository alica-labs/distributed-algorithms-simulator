package de.uniks.vs.simulator.simulation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.uniks.vs.simulator.model.Connection;
import de.uniks.vs.simulator.model.utils.IPropertyDest;
import de.uniks.vs.simulator.model.Model;
import de.uniks.vs.simulator.model.Node;
import de.uniks.vs.simulator.view.SimulationListener;
import org.sfc.io.IO;
import org.sfc.parallel.simulation.IStepable;
import org.sfc.utils.ErrorUtils;

public class Simulation extends Model implements IStepable {

  private final List<Node> nodes = new ArrayList<>();
  private final List<Connection> connections = new ArrayList<>();
  private final List<SimulationListener> listeners = new ArrayList<>();
  private int idCnt;
  int msgCount;

  public Simulation() {
    super();
  }

  public void incMsgCount() {
    this.msgCount++;
  }

  public synchronized void step() {
    int i;
    List<Node> n;
    List<Connection> c;

    n = this.nodes;
    for (i = (n.size() - 1); i >= 0; i--) {
      if (i < n.size()) {
        n.get(i).step();
      }
    }

    c = this.connections;
    for (i = (c.size() - 1); i >= 0; i--) {
      if (i < c.size()) {
        c.get(i).step();
      }
    }

    this.onAfterStep();
  }

  public synchronized void removeNode(final Node n) {

    if (this.nodes.remove(n)) {
      List<Connection> cc = n.getConnections();

      for (int i = (cc.size() - 1); i >= 0; i--) {
        this.disconnect(cc.get(i));
      }
      synchronized (n) {
        n.setSim(null);
      }

      for (SimulationListener l : this.listeners) {
        l.onNodeRemoved(n);
      }
    }
  }

  public synchronized void addNode(final Node n) {
    if ((!(this.nodes.contains(n))) && (this.nodes.add(n))) {
        n.setSim(this);
        n.setId(++this.idCnt);
        n.init();
      for (SimulationListener l : this.listeners) {
        l.onNodeAdded(n);
      }
    }
  }

  public synchronized void disconnect(final Connection con) {

    if (this.connections.remove(con)) {
      Node n = con.getNode1();
      synchronized (n) {
        n.getConnections().remove(con);
      }
      n = con.getNode2();
      synchronized (n) {
        n.getConnections().remove(con);
      }
      for (SimulationListener l : this.listeners) {
        l.onNodesDisconnected(con);
      }
      synchronized (con) {
        con.setSim(null);
      }
    }
  }

  public synchronized void addListener(final SimulationListener n) {
    this.listeners.add(n);
  }

  public synchronized void removeListener(final SimulationListener n) {
    this.listeners.remove(n);
  }

  public synchronized Connection connectNodes(final Node n1, final Node n2) {
    Connection n;
    synchronized (n1) {
      for (Connection c : n1.getConnections()) {
        if ((c.getNode1() == n2) || (c.getNode2() == n2))
          return c;
      }
      n = new Connection(n1, n2);
      n1.getConnections().add(n);
    }
    synchronized (n2) {
      n2.getConnections().add(n);
    }
    this.connections.add(n);

    for (SimulationListener l : this.listeners) {
      l.onNodesConnected(n);
    }
    synchronized (n) {
      n.setSim(this);
    }

    return n;
  }

  final void onAfterStep() {
    for (SimulationListener l : this.listeners) {
      l.onAfterStep();
    }
  }

  public synchronized int getNodeCount() {
    return this.nodes.size();
  }

  public List<Node> getAllNodes() {
    return this.nodes;
  }

  public synchronized Node getNode(final int index) {
    return this.nodes.get(index);
  }

  public synchronized int getConnectionCount() {
    return this.connections.size();
  }

  public synchronized Connection getConnection(final int index) {
    return this.connections.get(index);
  }

  @Override
  public void log(final String str) {
    this.log(str, this);
  }

  public synchronized final void log(final String str, final Model source) {
    for (SimulationListener l : this.listeners) {
      l.onLog(str, source);
    }
  }

  @Override
  public Simulation getSimulation() {
    return this;
  }

  @Override
  public synchronized void reset() {
    List<Connection> m = this.connections;

    for (int i = (m.size() - 1); i >= 0; i--) {
      m.get(i).reset();
    }
    List<Node> n = this.nodes;

    for (int i = (n.size() - 1); i >= 0; i--) {
      n.get(i).reset();
    }
    this.msgCount = 0;
    super.reset();
    this.onAfterStep();
  }

  @Override
  public synchronized void writeProperties(final IPropertyDest pd) {
    super.writeProperties(pd);
    pd.writeProperty("#nodes", String.valueOf(this.nodes.size()));
    pd.writeProperty("#connections", String.valueOf(this.connections.size()));
    pd.writeProperty("#msg", String.valueOf(this.msgCount));
  }

  public synchronized void connectAll() {
    for (Node node :this.nodes) {

      for (Node neighbor : this.nodes) {

        if (neighbor != node)
          this.connectNodes(node,neighbor);
      }
    }
    this.onAfterStep();
  }

  public synchronized void connectAsRing() {
    int nodeCount = this.getNodeCount();
    for(int index = 1; index < nodeCount; index++) {
      Node node1 = this.nodes.get(index-1);
      Node node2 = this.nodes.get(index);
      this.connectNodes(node1,node2);
    }
    Node node1 = this.nodes.get(nodeCount-1);
    Node node2 = this.nodes.get(0);
    this.connectNodes(node1,node2);
    this.onAfterStep();
  }

  public synchronized void trigger() {
    List<Node> n;
    int i;

    n = this.nodes;
    for (i = (n.size() - 1); i >= 0; i--) {
      n.get(i).trigger();
    }

    this.onAfterStep();
  }

  public static final void storeSimulation(final Simulation sim, final Object dest) {
    OutputStream os = IO.getOutputStream(dest);

    if (os != null) {
      try {
        ObjectOutputStream oos = new ObjectOutputStream(os);

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
      ErrorUtils.onError(new IOException("Could not create file."));
  }

  public static final Simulation loadSimulation(final Object source) {
    InputStream is = IO.getInputStream(source);

    if (is != null) {
      try {
        ObjectInputStream ois = new ObjectInputStream(is);
        try {
          Object o;
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
      ErrorUtils.onError(new IOException("Could not open file."));
    return null;
  }
}
