package de.uniks.vs.simulator.model;

import de.uniks.vs.simulator.model.utils.IPropertyDest;
import de.uniks.vs.simulator.simulation.Simulation;
import org.sfc.text.Textable;
import java.io.Serializable;

public abstract class Model extends Textable implements Serializable {

  public Model() {
    super();
  }

  public abstract Simulation getSimulation();
  public abstract void log(final String str);

  @Override
  public void toStringBuilder(final StringBuilder sb) {
    sb.append(this.getClass().getSimpleName());
  }
  public void writeProperties(final IPropertyDest pd) { pd.writeProperty("type", this.getClass().getSimpleName()); }
  public void reset() { }
}
