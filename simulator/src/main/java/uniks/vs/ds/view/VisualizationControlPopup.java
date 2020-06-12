/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.VisualizationControlPopup.java
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

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.sfc.gui.resources.icons.Icons;

import uniks.vs.ds.model.INodeFactory;
import uniks.vs.ds.model.NodeTypes;
import uniks.vs.ds.model.Simulation;

/**
 * The popup menu for visualization controls
 *
 * @author Thomas Weise
 */
final class VisualizationControlPopup extends JPopupMenu {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the x coordinate
   */
  static int s_x;

  /**
   * the y coordinate
   */
  static int s_y;

  /**
   * The popup menu of visualization controls
   */
  VisualizationControlPopup() {
    super();

    JMenuItem j;
    INodeFactory[] f;
    int i;

    f = NodeTypes.getNodeFactories();

    j = new JMenuItem("info"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        Object o;
        Component c;
        Simulation n;

        o = ae.getSource();
        if (o instanceof Component) {
          for (c = ((Component) o); c != null; c = c.getParent()) {
            if (c instanceof JPopupMenu) {
              c = ((JPopupMenu) c).getInvoker();
            }
            if (c instanceof VisualizationControl)
              break;
          }

          if (c != null) {
            n = (((VisualizationControl) c).getSimulation());
            if (n != null)
              PropertyWindow.showProperties(n);
          }
        }

      }
    });
    j.setIcon(Icons.INFO);
    this.add(j);
    this.addSeparator();

    for (i = 0; i < f.length; i++) {
      j = new JMenuItem(f[i].getNodeTypeName());
      j.setIcon(Icons.ADD);
      j.addActionListener(new FactoryAL(f[i]));
      this.add(j);
    }

    this.addSeparator();

    j = new JMenuItem("connect all"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        Object o;
        Component c;

        o = ae.getSource();
        if (o instanceof Component) {
          for (c = ((Component) o); c != null; c = c.getParent()) {
            if (c instanceof JPopupMenu) {
              c = ((JPopupMenu) c).getInvoker();
            }
            if (c instanceof VisualizationControl)
              break;
          }

          if (c != null) {
            ((VisualizationControl) c).getSimulation().connectAll();
          }
        }

      }
    });
    j.setIcon(Icons.EXECUTE);
    this.add(j);

    this.addSeparator();

    j = new JMenuItem("trigger all"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        Object o;
        Component c;

        o = ae.getSource();
        if (o instanceof Component) {
          for (c = ((Component) o); c != null; c = c.getParent()) {
            if (c instanceof JPopupMenu) {
              c = ((JPopupMenu) c).getInvoker();
            }
            if (c instanceof VisualizationControl)
              break;
          }

          if (c != null) {
            ((VisualizationControl) c).getSimulation().trigger();
          }
        }

      }
    });
    j.setIcon(Icons.EXECUTE);
    this.add(j);

    j = new JMenuItem("step all"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        Object o;
        Component c;

        o = ae.getSource();
        if (o instanceof Component) {
          for (c = ((Component) o); c != null; c = c.getParent()) {
            if (c instanceof JPopupMenu) {
              c = ((JPopupMenu) c).getInvoker();
            }
            if (c instanceof VisualizationControl)
              break;
          }

          if (c != null) {
            ((VisualizationControl) c).getSimulation().step();
          }
        }

      }
    });
    j.setIcon(Icons.APPLY);
    this.add(j);

    j = new JMenuItem("reset all"); //$NON-NLS-1$
    j.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent ae) {
        Object o;
        Component c;

        o = ae.getSource();
        if (o instanceof Component) {
          for (c = ((Component) o); c != null; c = c.getParent()) {
            if (c instanceof JPopupMenu) {
              c = ((JPopupMenu) c).getInvoker();
            }
            if (c instanceof VisualizationControl)
              break;
          }

          if (c != null) {
            ((VisualizationControl) c).getSimulation().reset();
          }
        }

        SpeedDialog.resetAll();
      }
    });
    j.setIcon(Icons.RESET);
    this.add(j);
  }

  /**
   * This internal class embodies a node factory button.
   *
   * @author Thomas Weise
   */
  private static final class FactoryAL implements ActionListener {
    /**
     * the serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * the internal node factory
     */
    private final INodeFactory m_f;

    /**
     * Create a new node factory menu item.
     *
     * @param f
     *          the node factory
     */
    FactoryAL(final INodeFactory f) {
      super();
      this.m_f = f;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     *          the event
     */
    public void actionPerformed(ActionEvent e) {
      Object o;
      JPopupMenu j;
      VisualizationControl c;
      Simulation n;

      o = e.getSource();
      for (;;) {
        if (o == null)
          return;
        if (o instanceof JPopupMenu)
          break;
        if (o instanceof Component) {
          o = ((Component) o).getParent();
        } else
          return;
      }

      j = ((JPopupMenu) o);

      o = j.getInvoker();

      for (;;) {
        if (o == null)
          return;
        if (o instanceof VisualizationControl)
          break;
        if (o instanceof Component) {
          o = ((Component) o).getParent();
        } else
          return;
      }

      c = ((VisualizationControl) o);

      n = c.getSimulation();
      if (n != null) {
        n.addNode(this.m_f.createNode(s_x, s_y));
      }

    }
  }

}
