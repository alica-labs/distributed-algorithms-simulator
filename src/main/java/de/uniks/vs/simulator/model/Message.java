package de.uniks.vs.simulator.model;

public class Message extends Item implements Cloneable {

  Node source;
  Node target;
  Message nextMessage;
  boolean direction;
  int distance;
  Connection connection;

  public Message() {
    super();
  }

  final Message copy() {
    try {
      return ((Message) (this.clone()));
    } catch (Throwable t) {
      return this;
    }
  }

  @Override
  public synchronized void reset() {
    this.target = null;
    this.direction = false;
    this.distance = 0;
    this.nextMessage = null;
    this.mSim = null;
    this.source = null;
    super.reset();
  }

  public Node getSource() {
    return this.source;
  }
  public Node getDestination() {
    return this.target;
  }
  public boolean getDirection() {
    return this.direction;
  }
  public int getRemainingDistance() {
    return this.distance;
  }
  public Connection getConnection() {
    return this.connection;
  }
}
