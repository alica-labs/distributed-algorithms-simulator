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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.sfc.gui.PopupMouseListener;
import org.sfc.gui.resources.icons.Icons;

import uniks.vs.ds.model.Node;
import uniks.vs.ds.model.Simulation;

/**
 * The popup menu for node controls
 *
 * @author Thomas Weise
 */
final class NodeControlPopup extends JPopupMenu {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the shared instance
   */
  static final MouseListener ML = new PopupMouseListener(
      new NodeControlPopup()) {
    private static final long serialVersionUID = 1L;

    @Override
    public void mouseClicked(MouseEvent e) {
      Object o;
      super.mouseClicked(e);
      if (e.getButton() == MouseEvent.BUTTON1) {
        o = e.getSource();
        if (o instanceof NodeControl) {
          ((NodeControl) o).select();
        }
      }
    }
  };

  /**
   * The popup menu of node controls
   */
  NodeControlPopup() {
    super();

    JMenuItem j;

    j = new JMenuItem("info"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        NodeControl c;
        Node n;
        c = getControl(ae);
        if (c != null) {
          n = c.getNode();
          if (n != null)
            PropertyWindow.showProperties(n);
        }
      }
    });
    this.add(j);
    j.setIcon(Icons.INFO);

    this.addSeparator();

    j = new JMenuItem("trigger"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        NodeControl c;
        Component cc;
        Node n;
        c = getControl(ae);
        if (c != null) {
          n = c.getNode();
          if (n != null) {
            n.trigger();
          }
          cc = c.getParent();
          if (cc != null) {
            cc.repaint();
          }
        }
      }
    });
    j.setIcon(Icons.EXECUTE);
    this.add(j);

    j = new JMenuItem("step"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        NodeControl c;
        Node n;
        c = getControl(ae);
        if (c != null) {
          n = c.getNode();
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
        NodeControl c;
        Node n;
        c = getControl(ae);
        if (c != null) {
          n = c.getNode();
          if (n != null) {
            n.reset();
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
        NodeControl c;
        Simulation s;
        Node n;
        c = getControl(ae);
        if (c != null) {
          n = c.getNode();
          if (n != null) {
            s = n.getSimulation();
            if (s != null)
              s.removeNode(n);
            PropertyWindow.closeSpecificWindow(n);
          }
        }
      }
    });
    j.setIcon(Icons.DELETE);
    this.add(j);
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
      if (o instanceof JPopupMenu)
        break;
      if (o instanceof Component) {
        o = ((Component) o).getParent();
      } else
        return null;
    }

    o = ((JPopupMenu) o).getInvoker();
    if (o instanceof NodeControl)
      return ((NodeControl) o);
    return null;
  }

}
