package de.uniks.vs.simulator.model;

import java.util.ArrayList;
import java.util.List;

import de.uniks.vs.simulator.model.utils.IPropertyDest;
import de.uniks.vs.simulator.simulation.Simulation;
import org.sfc.parallel.simulation.IStepable;

public class Node extends Item implements IStepable {

  final List<Connection> connections = new ArrayList<>();
  private String text;
  private int x;
  private int y;
  private int id;
  private int send;
  private int received;

  public Node(int x, int y) {
    super();
    this.x = x;
    this.y = y;
    this.text = "";
  }

  public synchronized void step() {}

  public int getID() {
    return this.id;
  }
  public String getText() {
    return this.text;
  }
  public void setText(final String s) {
    this.text = (s != null) ? s : "";
  }
  public int getX() {
    return this.x;
  }
  public int getY() {
    return this.y;
  }
  public List<Connection> getConnections() {
    return connections;
  }

  public synchronized void onMessageReceived(final Message msg) {
    this.received++;
  }

  public synchronized int broadcast(final Message msg) {

    if (msg == null)
      return 0;

    List<Connection> connections = this.connections;
    Simulation simulation = this.mSim;
    msg.mSim = this.mSim;
    msg.source = this;
    int size = connections.size();

    for (int i = (size - 1); i >= 0; i--) {
      Message message = msg.copy();
      Connection connection = connections.get(i);
      Node node = connection.getNode1();
      message.direction = (node == this);

      if (message.direction)
        node = connection.getNode2();
      message.target = node;
      connection.send(message);
      this.send++;

      if (simulation != null) {
        synchronized (simulation) {
          simulation.incMsgCount();
        }
      }
    }
    return size;
  }

  public synchronized int broadcastExcept(final Message bMessage, final Node bNode) {

    if (bNode == null)
      this.broadcast(bMessage);

    if (bMessage == null)
      return 0;
    Simulation simulation = this.mSim;
    List<Connection> connections = this.connections;
    bMessage.source = this;
    bMessage.mSim = this.mSim;
    int count = 0;

    for (int i = (connections.size() - 1); i >= 0; i--) {
      Connection connection = connections.get(i);
      if ((connection.getNode1() == bNode) || (connection.getNode2() == bNode))
        continue;
      this.send++;
      count++;
      if (simulation != null) {
        synchronized (simulation) {
          simulation.incMsgCount();
        }
      }
      Message message = bMessage.copy();
      Node node = connection.getNode1();
      message.direction = (node == this);
      if (message.direction)
        node = connection.getNode2();
      message.target = node;
      connection.send(message);
    }
    return count;
  }

  public synchronized int sendTo(final Message msg, final Node n) {

    if (msg == null)
      return 0;

    List<Connection> c = this.connections;
    Simulation s = this.mSim;
    msg.mSim = this.mSim;

    for (int i = (c.size() - 1); i >= 0; i--) {
      Connection x = c.get(i);

      if (x.getNode1() == n) {
        msg.direction = false;
      } else {
        if (x.getNode2() == n) {
          msg.direction = true;
        } else
          continue;
      }

      msg.target = n;
      msg.source = this;
      x.send(msg.copy());
      this.send++;
      if (s != null) {
        synchronized (s) {
          s.incMsgCount();
        }
      }
      return 1;
    }
    return 0;
  }

  public synchronized int sendTo(final Message msg, final int id) {

    if (msg == null)
      return 0;

    List<Connection> c = this.connections;
    Simulation s = this.mSim;
    msg.mSim = this.mSim;

    for (int i = (c.size() - 1); i >= 0; i--) {
      Connection x = c.get(i);
      Node n;

      if ((n = x.getNode1()).getID() == id) {
        msg.direction = false;
      } else {
        if ((n = x.getNode2()).getID() == id) {
          msg.direction = true;
        } else
          continue;
      }

      msg.target = n;
      msg.source = this;
      x.send(msg.copy());
      this.send++;
      if (s != null) {
        synchronized (s) {
          s.incMsgCount();
        }
      }
      return 1;
    }
    return 0;
  }

  public final void setId(final int id) {
    this.id = id;
  }

  public void init() { }

  @Override
  public synchronized void toStringBuilder(final StringBuilder sb) {
    super.toStringBuilder(sb);
    sb.append(':');
    sb.append(this.id);
  }

  public synchronized void trigger() {
    this.log("triggered"); //$NON-NLS-1$
  }

  @Override
  public synchronized void reset() {
    this.text = ""; //$NON-NLS-1$
    this.send = 0;
    this.received = 0;
    super.reset();
  }

  @Override
  public synchronized void writeProperties(final IPropertyDest pd) {
    super.writeProperties(pd);
    pd.writeProperty("text", this.getText()); //$NON-NLS-1$
    pd.writeProperty(
        "#connections", String.valueOf(this.connections.size()));//$NON-NLS-1$
    pd.writeProperty("#msgs sent", String.valueOf(this.send));//$NON-NLS-1$
    pd.writeProperty("#msgs received", String.valueOf(this.received));//$NON-NLS-1$
  }

  public synchronized int getConnectionCount() {
    return this.connections.size();
  }
  public synchronized Connection getConnection(final int idx) {
    return this.connections.get(idx);
  }
  public synchronized int getNeighborCount() {
    return this.connections.size();
  }

  public synchronized ArrayList<Integer> getNeighbors() {
    ArrayList<Integer> ids = new ArrayList<>();

    for (Connection connection: this.connections) {

      if (connection.getNode1().getID() != this.getID())
        ids.add(connection.getNode1().getID());
      else
        ids.add(connection.getNode2().getID());
    }
    return ids;
  }

  public synchronized Node getNeighbor(final int idx) {
    Connection c = this.connections.get(idx);
    Node n = c.getNode1();
    return ((n == this) ? (c.getNode2()) : n);
  }

}
