/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.VisualizationControl.java
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
import java.awt.Dimension;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import org.sfc.collections.CollectionUtils;
import org.sfc.gui.PopupMouseListener;

import uniks.vs.ds.model.Connection;
import uniks.vs.ds.model.ISimulationListener;
import uniks.vs.ds.model.ModelBase;
import uniks.vs.ds.model.Node;
import uniks.vs.ds.model.Simulation;

/**
 * This control represents a whole simulation
 *
 * @author Thomas Weise
 */
final class VisualizationControl extends SimCtrlBase implements
    ISimulationListener {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the mouse listener
   */
  static final MouseListener ML = new PopupMouseListener(
      new VisualizationControlPopup()) {
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean shouldPopup(final MouseEvent e) {
      Object o;
      VisualizationControl v;
      Component c;
      int i, x, y, vx, vy;
      ConnectionControl cc;

      if (super.shouldPopup(e)) {
        o = e.getSource();
        if (!(o instanceof VisualizationControl))
          return false;

        v = ((VisualizationControl) o);
        x = e.getX();
        y = e.getY();
        synchronized (v) {
          for (i = (v.getComponentCount() - 1); i >= 0; i--) {
            c = v.getComponent(i);
            if (c instanceof ConnectionControl) {
              cc = ((ConnectionControl) c);
              vx = x - cc.m_tx;
              vy = y - cc.m_ty;
              if ((vx >= 0) && (vx < cc.getWidth()) && (vy >= 0)
                  && (vy <= cc.getHeight())) {

                if (Math
                    .abs((((vx - cc.m_x1) * (cc.m_y2 - cc.m_y1)) - ((vy - cc.m_y1) * (cc.m_x2 - cc.m_x1)))
                        / cc.m_r) < 11) {
                  ConnectionControlPopup.CP.show(cc, vx, vy);
                  return false;
                }

              }
            }
          }
        }

        return true;
      }
      return false;
    }

    @Override
    protected synchronized void popup(final MouseEvent e) {
      super.popup(e);
      VisualizationControlPopup.s_x = e.getX();
      VisualizationControlPopup.s_y = e.getY();
    }

  };

  /**
   * the simulation this control belongs to
   */
  private final Simulation m_simulation;

  /**
   * the node controls
   */
  private final Map<Node, NodeControl> m_nCtrl;

  /**
   * the connection controls
   */
  private final Map<Connection, ConnectionControl> m_cCtrl;

  /**
   * the max x
   */
  private int m_maxX;

  /**
   * the max y
   */
  private int m_maxY;

  /**
   * the node z order
   */
  private int m_nodeZ;

  /**
   * the selected node control
   */
  NodeControl m_sel;

  /**
   * create a new simulation control
   *
   * @param simulation
   *          the simulation this control belongs to
   */
  public VisualizationControl(final Simulation simulation) {
    super();

    int i;

    this.m_simulation = simulation;
    this.m_nCtrl = CollectionUtils.createMap();
    this.m_cCtrl = CollectionUtils.createMap();
    this.m_simulation.addListener(this);

    synchronized (simulation) {
      for (i = (simulation.getNodeCount() - 1); i >= 0; i--) {
        this.onNodeAdded(simulation.getNode(i));
      }
      for (i = (simulation.getConnectionCount() - 1); i >= 0; i--) {
        this.onNodesConnected(simulation.getConnection(i));
      }
    }

    this.addContainerListener(new ContainerListener() {
      public void componentAdded(ContainerEvent e) {//
      }

      public void componentRemoved(ContainerEvent e) {
        if (e.getChild() == VisualizationControl.this.m_sel) {
          VisualizationControl.this.m_sel = null;
        }
      }
    }

    );

    this.onAfterStep();
    this.addMouseListener(ML);
  }

  /**
   * Obtain the simulation this control is used for.
   *
   * @return the simulation this control is used for
   */
  public Simulation getSimulation() {
    return this.m_simulation;
  }

  /**
   * A node was added
   *
   * @param n
   *          the node
   */
  public void onNodeAdded(final Node n) {
    NodeControl c;

    c = new NodeControl(n);
    this.m_nCtrl.put(n, c);
    this.add(c);
    this.setComponentZOrder(c, this.m_nodeZ++);
    this.onAfterStep();
  }

  /**
   * A node was removed
   *
   * @param n
   *          the removed node
   */
  public void onNodeRemoved(final Node n) {
    Component c;

    c = this.m_nCtrl.remove(n);
    if (c != null) {
      this.remove(c);
      this.m_nodeZ--;
    }
    this.onAfterStep();
  }

  /**
   * Two nodes were connected by a new connection
   *
   * @param newCon
   *          the new connection
   */
  public void onNodesConnected(final Connection newCon) {
    ConnectionControl c;

    c = new ConnectionControl(newCon);
    this.m_cCtrl.put(newCon, c);
    this.add(c);
    this.onAfterStep();
  }

  /**
   * Two nodes were discconnected
   *
   * @param disCon
   *          the removed connection
   */
  public void onNodesDisconnected(final Connection disCon) {
    Component c;

    c = this.m_cCtrl.remove(disCon);
    if (c != null)
      this.remove(c);
    this.onAfterStep();
  }

  /**
   * this method is called after a simulation step has been performed
   */
  @Override
  public void onAfterStep() {
    int i, maxX, maxY;
    Component c;

    maxX = 0;
    maxY = 0;

    for (i = (this.getComponentCount() - 1); i >= 0; i--) {
      c = this.getComponent(i);
      if (c instanceof SimCtrlBase)
        ((SimCtrlBase) c).onAfterStep();

      maxX = Math.max(maxX, c.getX() + c.getWidth());
      maxY = Math.max(maxY, c.getY() + c.getHeight());
    }

    this.m_maxX = Math.max(500, maxX);
    this.m_maxY = Math.max(maxY, 500);

    super.onAfterStep();
    this.repaint();
  }

  // /**
  // * Returns the size of this component in the form of a
  // * <code>Dimension</code> object. The <code>height</code> field of
  // * the <code>Dimension</code> object contains this component's height,
  // * and the <code>width</code> field of the <code>Dimension</code>
  // * object contains this component's width.
  // *
  // * @return a <code>Dimension</code> object that indicates the size of
  // * this component
  // */
  // @Override
  // public Dimension getSize() {
  // return new Dimension(this.m_maxX + 1, this.m_maxY + 1);
  // }

  /**
   * Gets the preferred size of this component.
   *
   * @return a dimension object indicating this component's preferred size
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.m_maxX + 10, this.m_maxY + 10);
  }

  /**
   * Gets the maximum size of this component.
   *
   * @return a dimension object indicating this component's maximum size
   */
  @Override
  public Dimension getMaximumSize() {
    return new Dimension(Integer.MAX_VALUE, Integer.MIN_VALUE);
    // this.getPreferredSize();
  }

  /**
   * Gets the minimum size of this component.
   *
   * @return a dimension object indicating this component's minimum size
   */
  @Override
  public Dimension getMinimumSize() {
    return this.getPreferredSize();
  }

  /**
   * This method is invoked if a string has been written to the log by a
   * source.
   *
   * @param logged
   *          the string logged
   * @param source
   *          the object that logged the string
   */
  public void onLog(final String logged, final ModelBase source) {
    //
  }

}
