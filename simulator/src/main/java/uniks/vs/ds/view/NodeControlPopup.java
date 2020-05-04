/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.NodeControlPopup.java
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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.sfc.gui.PopupMouseEventHandler;
import org.sfc.gui.resources.icons.Icons;

import uniks.vs.ds.model.SimNode;
import uniks.vs.ds.model.Simulation;

/**
 * The popup menu for node controls
 *
 * @author Thomas Weise
 */
final class NodeControlPopup extends ContextMenu {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the shared instance
   */
  static final EventHandler ML = new PopupMouseEventHandler (
      new NodeControlPopup()) {
    private static final long serialVersionUID = 1L;

    @Override
    public void mouseClicked(ContextMenuEvent e) {
      Object o;
      super.mouseClicked(e);
      System.out.println("NCP: implementation missing !!");
//      if (e.getButton() == MouseButton.PRIMARY) {
//        o = e.getSource();
//        if (o instanceof NodeControl) {
//          ((NodeControl) o).select();
//        }
//      }
    }
  };

  /**
   * The popup menu of node controls
   */
  NodeControlPopup() {
    super();

    MenuItem j;

    j = new MenuItem("info"); //$NON-NLS-1$
    j.setOnAction(ae -> {
        NodeControl c;
        SimNode n;
        c = getControl(ae);
        if (c != null) {
          n = c.getSimNode();
          if (n != null)
            PropertyWindow.showProperties(n);
        }
    });
    this.getItems().add(j);
    j.setGraphic(new ImageView(Icons.INFO));

    SeparatorMenuItem separator = new SeparatorMenuItem();
    this.getItems().add(separator);

    j = new MenuItem("trigger"); //$NON-NLS-1$
    j.setOnAction(ae -> {
        NodeControl c;
        Node cc;
        SimNode n;
        c = getControl(ae);
        if (c != null) {
          n = c.getSimNode();
          if (n != null) {
            n.trigger();
          }
          cc = c.getParent();
          if (cc != null) {
//            cc.repaint();
          }
        }
    });
    j.setGraphic(new ImageView(Icons.EXECUTE));
    this.getItems().add(j);

    j = new MenuItem("step"); //$NON-NLS-1$
    j.setOnAction(ae -> {
        NodeControl c;
        SimNode n;
        c = getControl(ae);
        if (c != null) {
          n = c.getSimNode();
          if (n != null) {
            n.step();
          }
        }
    });
    j.setGraphic(new ImageView(Icons.EXECUTE));
    this.getItems().add(j);

    j = new MenuItem("reset"); //$NON-NLS-1$
    j.setOnAction(ae -> {
        NodeControl c;
        SimNode n;
        c = getControl(ae);
        if (c != null) {
          n = c.getSimNode();
          if (n != null) {
            n.reset();
          }
        }
    });
    j.setGraphic(new ImageView(Icons.RESET));
    this.getItems().add(j);

    separator = new SeparatorMenuItem();
    this.getItems().add(separator);

    j = new MenuItem("delete"); //$NON-NLS-1$
    j.setOnAction(ae ->  {
        NodeControl c;
        Simulation s;
        SimNode n;
        c = getControl(ae);
        if (c != null) {
          n = c.getSimNode();
          if (n != null) {
            s = n.getSimulation();
            if (s != null)
              s.removeNode(n);
            PropertyWindow.closeSpecificWindow(n);
          }
        }
    });
    j.setGraphic(new ImageView(Icons.DELETE));
    this.getItems().add(j);
  }

  /**
   * Get the node control
   *
   * @param ae
   *          the action event
   * @return the node control
   */
  static final NodeControl getControl(final ActionEvent ae) {
    Object o;

    o = ae.getSource();
    for (;;) {
      if (o == null)
        return null;
      if (o instanceof ContextMenu)
        break;
      if (o instanceof Node) {
        o = ((Node) o).getParent();
      } else
        return null;
    }

    o = ((ContextMenu) o).getOwnerNode();
    if (o instanceof NodeControl)
      return ((NodeControl) o);
    return null;
  }

}
