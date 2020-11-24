package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.model.Connection;
import de.uniks.vs.simulator.model.Model;
import de.uniks.vs.simulator.model.Node;

public interface SimulationListener {

  public abstract void onNodeAdded(final Node n);
  public abstract void onNodeRemoved(final Node n);
  public abstract void onNodesConnected(final Connection newCon);
  public abstract void onNodesDisconnected(final Connection disCon);
  public abstract void onAfterStep();
  public abstract void onLog(final String logged, final Model source);
}
