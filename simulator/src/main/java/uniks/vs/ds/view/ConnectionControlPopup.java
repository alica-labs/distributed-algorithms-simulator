/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.ConnectionControlPopup.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
final class ConnectionControlPopup extends JPopupMenu {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the connection control popup menu
   */
  static final JPopupMenu CP = new ConnectionControlPopup();

  /**
   * The popup menu of connection controls
   */
  ConnectionControlPopup() {
    super();

    JMenuItem j;

    j = new JMenuItem("info"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        ConnectionControl c;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null)
            PropertyWindow.showProperties(n);
        }
      }
    });
    j.setIcon(Icons.INFO);
    this.add(j);

    this.addSeparator();

    j = new JMenuItem("speed"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
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
      }
    });
    j.setIcon(Icons.SPEED);
    this.add(j);

    j = new JMenuItem("step"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        ConnectionControl c;
        Connection n;
        c = getControl(ae);
        if (c != null) {
          n = c.getConnection();
          if (n != null) {
            n.step();
          }
        }
      }
    });
    j.setIcon(Icons.APPLY);
    this.add(j);

    j = new JMenuItem("reset"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
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
      }
    });
    j.setIcon(Icons.RESET);
    this.add(j);

    this.addSeparator();

    j = new JMenuItem("delete"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
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
      }
    });
    j.setIcon(Icons.DELETE);
    this.add(j);
  }

  /**
   * Get the connection control
   *
   * @param ae
   *          the action event
   * @return the connection control
   */
  static final ConnectionControl getControl(final EventObject ae) {
    Object o;

    o = ae.getSource();
    for (;;) {
      if (o == null)
        return null;
      if (o instanceof ConnectionControl)
        return ((ConnectionControl) o);
      if (o instanceof JPopupMenu)
        break;
      if (o instanceof Component) {
        o = ((Component) o).getParent();
      } else
        return null;
    }

    o = ((JPopupMenu) o).getInvoker();
    if (o instanceof ConnectionControl)
      return ((ConnectionControl) o);
    return null;
  }

}
