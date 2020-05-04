/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.SimulationIO.java
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

package uniks.vs.ds.view;

import javax.swing.JFileChooser;

import org.sfc.gui.FileChooser;
import org.sfc.gui.Printer;
import org.sfc.gui.Printer.DefaultBook;
import org.sfc.utils.ErrorUtils;

import uniks.vs.ds.model.Simulation;

/**
 * The simulation io helper
 *
 * @author Thomas Weise
 */
final class SimulationIO {

  /**
   * the filter
   */
  private static final FileChooser.Filter FILTER = new FileChooser.Filter(
      "*.sim (simulation file)", //$NON-NLS-1$
      "sim");//$NON-NLS-1$

  /**
   * Store a simulation
   *
   * @param sim
   *          the simulation to be stored
   */
  public static final void storeSimulation(final Simulation sim) {
    FileChooser fc;

    fc = new FileChooser();
    fc.setFileFilter(FILTER);
    if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)) {
      Simulation.storeSimulation(sim, fc.getSelectedFile());
    }
  }

  /**
   * Load a simulation
   *
   * @return sim the loaded simulation, or <code>null</code> if nothing
   *         was loaded
   */
  public static final Simulation loadSimulation() {
    FileChooser fc;

    fc = new FileChooser();
    fc.setFileFilter(FILTER);
    if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
      return Simulation.loadSimulation(fc.getSelectedFile());
    }
    return null;
  }

  /**
   * Print the simulation.
   *
   * @param vc
   *          the simulation to be printed
   */
  public static final void printSimulation(final VisualizationControl vc) {
    DefaultBook b;

    synchronized (vc) {
      synchronized (vc.getSimulation()) {

        b = Printer.createBook("Simulation Print"); //$NON-NLS-1$
        b.appendComponent(vc);
        b.print();
      }
    }
  }

  /**
   * the hidden and forbidden constructor
   */
  private SimulationIO() {
    ErrorUtils.doNotCall();
  }
}
