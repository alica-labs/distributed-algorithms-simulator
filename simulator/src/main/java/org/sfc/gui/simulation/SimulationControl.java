/*
 * Copyright (c) 2007 Thomas Weise for sigoa
 * Simple Interface for Global Optimization Algorithms
 * http://www.sigoa.org/
 *
 * E-Mail           : info@sigoa.org
 * Creation Date    : 2007-08-28
 * Creator          : Thomas Weise
 * Original Filename: org.sfc.gui.simulation.SimulationControl.java
 * Last modification: 2007-08-28
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

package org.sfc.gui.simulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import org.sfc.gui.ComponentUtils;
import org.sfc.gui.SfcComponent;
import org.sfc.parallel.simulation.SimulationThread;

/**
 * The simulation control
 *
 * @author Thomas Weise
 */
public class SimulationControl extends SfcComponent {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * The action listener for single steps
   */
  static final EventHandler SINGLE_STEP = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
      ((SimulationControl) (((Node) (event.getSource())).getParent())).m_thread
              .step();
    }
  };

  /**
   * the simulation thread to be controled
   */
  final SimulationThread m_thread;

  /**
   * the slider
   */
  private final Slider m_speed;

  /**
   * Create a new simulation control
   *
   * @param thread
   *          the thread to be controlled
   */
  public SimulationControl(final SimulationThread thread) {
    super();

    Slider sl;
    Button b;

    this.m_speed = sl = new Slider( 0, 100, 0);
//    sl.setPaintLabels(false);
//    sl.setPaintTicks(false);
//    sl.setPaintTrack(true);
    sl.valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        SimulationControl.this.updateSpeed();
      }
    });

    this.m_thread = thread;
    this.m_thread.setSpeed(0);

    this.getColumnConstraints().addAll(new ColumnConstraints());
//    ComponentUtils.putGrid(this, sl, 0, 0, 1, 1, 1, 1,
//        GridBagConstraints.CENTER, GridBagConstraints.BOTH);

    b = new Button("step"); //$NON-NLS-1$
    b.setOnAction(SINGLE_STEP);
    this.addButton(b);
  }

  /**^
   * Add a button to this control
   *
   * @param button
   *          the button to add
   */
  public void addButton(final Button button) {
//    ComponentUtils.putGrid(this, button, this.getComponentCount(), 0, 1,
//        1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);
  }

  /**
   * Internally update the speed
   */
  synchronized final void updateSpeed() {
    this.m_thread.setSpeed((int) this.m_speed.getValue());
  }

  /**
   * Obtain the simulation thread of this control
   *
   * @return the simulation thread of this control
   */
  public SimulationThread getSimulationThread() {
    return this.m_thread;
  }

}
