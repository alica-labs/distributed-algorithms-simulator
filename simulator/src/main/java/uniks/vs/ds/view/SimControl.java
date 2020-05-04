/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.SimCtrlBase.java
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

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;

/**
 * the basic simulation control
 *
 * @author Thomas Weise
 */
class SimControl extends Pane {
  private Tooltip toolTip;

  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the text has changed
   */
  protected void onAfterStep() {
    //this.revalidate();
  }

  protected void setComponentZOrder(NodeControl nodeControl, int order) {
    this.getChildren().add(order, nodeControl);
  }

//  protected void paint(Graphics g) {
//
//  }

  protected void setToolTipText(String text) {
    if (this.toolTip == null) {
      Tooltip toolTip = new Tooltip();
      toolTip.setStyle("-fx-font: normal bold 4 Langdon; "
              + "-fx-base: #AE3522; "
              + "-fx-text-fill: orange;");
      this.setTooltip(toolTip);
    }
    toolTip.setText(text);
  }

  protected void setTooltip(Tooltip tooltip) {
    this.toolTip = tooltip;
  }

  protected String getToolTipText() {
    return this.toolTip.getText();
  }

//  public double getWidth() {
//    return 1;
//  }
//
//  public double getHeight() {
//    return 1;
//  }
}
