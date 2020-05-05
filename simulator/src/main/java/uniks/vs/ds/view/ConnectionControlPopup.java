/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.view.ConnectionControlPopup.java
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

import java.awt.Component;
import java.util.EventObject;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import org.sfc.gui.ComponentUtils;
import org.sfc.gui.resources.icons.Icons;
import org.sfc.gui.windows.IWindow;

import uniks.vs.ds.model.Connection;
import uniks.vs.ds.model.Simulation;

/**
 * The popup menu for connection controls
 *
 * @author Thomas Weise
 */
final class ConnectionControlPopup extends ContextMenu {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the connection control popup menu
   */
  static final ContextMenu CONTEXT_MENU = new ConnectionControlPopup();

  /**
   * The popup menu of connection controls
   */
  ConnectionControlPopup() {
    super();

    MenuItem menuItem;

    menuItem = new MenuItem("info"); //$NON-NLS-1$
    menuItem.setOnAction( ae ->  {
        ConnectionControl c;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null)
            PropertyWindow.showProperties(n);
        }
    });
    menuItem.setGraphic(new ImageView(Icons.INFO));
    this.getItems().add(menuItem);

    SeparatorMenuItem separator = new SeparatorMenuItem();
    this.getItems().add(separator);

    menuItem = new MenuItem("speed"); //$NON-NLS-1$
    menuItem.setOnAction( ae ->  {
        ConnectionControl c;
        Connection n;
        IWindow w;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null) {
            w = ComponentUtils.getWindow(c);
            SpeedDialog.showDialog((w != null) ? w.getFrame() : null, n);
          }
      }
    });
    menuItem.setGraphic(new ImageView(Icons.SPEED));
    this.getItems().add(menuItem);

    menuItem = new MenuItem("step"); //$NON-NLS-1$
    menuItem.setOnAction( ae ->  {
        ConnectionControl c;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null) {
            n.step();
          }
        }
    });
    menuItem.setGraphic(new ImageView(Icons.APPLY));
    this.getItems().add(menuItem);

    menuItem = new MenuItem("reset"); //$NON-NLS-1$
    menuItem.setOnAction( ae ->  {
        ConnectionControl c;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null) {
            n.reset();
            SpeedDialog.reset(n);
          }
      }
    });
    menuItem.setGraphic(new ImageView(Icons.RESET));
    this.getItems().add(menuItem);

    separator = new SeparatorMenuItem();
    this.getItems().add(separator);

    menuItem = new MenuItem("delete"); //$NON-NLS-1$
    menuItem.setOnAction( ae ->  {
        ConnectionControl c;
        Simulation s;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null) {
            s = n.getSimulation();
            if (s != null)
              s.disconnect(n);
            SpeedDialog.close(n);
            PropertyWindow.closeSpecificWindow(n);
          }
        }
    });
    menuItem.setGraphic(new ImageView(Icons.DELETE));
    this.getItems().add(menuItem);
  }

  /**
   * Get the connection control
   *
   * @param ae
   *          the action event
   * @return the connection control
   */
  static final ConnectionControl getControl(final EventObject ae) {
    Object object;

    object = ae.getSource();
    for (;;) {
      if (object == null)
        return null;
      if (object instanceof ConnectionControl)
        return ((ConnectionControl) object);
      if (object instanceof ContextMenu)
        break;
      if (object instanceof Component) {
        object = ((Component) object).getParent();
      } else
        return null;
    }

//    object = ((ContextMenu) object).getInvoker();
    object = ((ContextMenu) object).getAnchorLocation();
    if (object instanceof ConnectionControl)
      return ((ConnectionControl) object);
    return null;
  }

}
