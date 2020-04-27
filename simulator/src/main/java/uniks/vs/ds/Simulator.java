/*
 * Copyright (c) 2007 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.Main.java
 * Last modification: 2008-04-01
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

package uniks.vs.ds;

import uniks.vs.ds.model.NodeTypes;
import uniks.vs.ds.view.SimulationWindow;

/**
 * the main class
 *
 * @author Thomas Weise
 */
public class Simulator {

  /**
   * the main program called at startup
   *
   * @param args
   *          the command line arguments
   */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    SimulationWindow w = new SimulationWindow();

    w.setVisible(true);
  }
}