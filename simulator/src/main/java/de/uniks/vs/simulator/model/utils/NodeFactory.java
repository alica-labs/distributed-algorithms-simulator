package de.uniks.vs.simulator.model.utils;

import de.uniks.vs.simulator.model.Node;

public interface NodeFactory {

  public abstract Node createNode(final double x, final double y);
  public abstract String getNodeTypeName();
}
