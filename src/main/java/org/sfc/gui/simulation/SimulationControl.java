package org.sfc.gui.simulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import org.sfc.gui.SfcComponent;
import org.sfc.parallel.simulation.SimulationThread;

// maybe deprecated
@Deprecated
public class SimulationControl extends SfcComponent {

  static final EventHandler SINGLE_STEP = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
      ((SimulationControl) (((Node) (event.getSource())).getParent())).thread
              .step();
    }
  };

  final SimulationThread thread;
  private final Slider slider;

  public SimulationControl(final SimulationThread thread) {
    super();
    this.slider = new Slider( 0, 100, 0);
//    sl.setPaintLabels(false);
//    sl.setPaintTicks(false);
//    sl.setPaintTrack(true);
    this.slider .valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        SimulationControl.this.updateSpeed();
      }
    });

    this.thread = thread;
    this.thread.setSpeed(0);

    this.getColumnConstraints().addAll(new ColumnConstraints());
//    ComponentUtils.putGrid(this, sl, 0, 0, 1, 1, 1, 1,
//        GridBagConstraints.CENTER, GridBagConstraints.BOTH);

    Button button = new Button("step"); //$NON-NLS-1$
    button.setOnAction(SINGLE_STEP);
    this.addButton(button);
  }

  public void addButton(final Button button) {
//    ComponentUtils.putGrid(this, button, this.getComponentCount(), 0, 1,
//        1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);
  }

  synchronized final void updateSpeed() {
    this.thread.setSpeed((int) this.slider.getValue());
  }

  public SimulationThread getSimulationThread() {
    return this.thread;
  }

}
