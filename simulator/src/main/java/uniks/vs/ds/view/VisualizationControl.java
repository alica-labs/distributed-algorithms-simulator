/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.view.VisualizationControl.java
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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import org.sfc.collections.CollectionUtils;
import org.sfc.gui.PopupMouseEventHandler;
import uniks.vs.ds.model.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * This control represents a whole simulation
 *
 * @author Thomas Weise
 */
final class VisualizationControl extends SimControl implements ISimulationListener {
  private static final long serialVersionUID = 1L;

  static final EventHandler POPUP_MOUSE_EVENT_HANDLER = new PopupMouseEventHandler ( new VisualizationControlPopup() ) {
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean shouldPopup(final ContextMenuEvent e) {
      Object object;
      VisualizationControl visualizationControl;
      Node component;
      int i;
      double x, y, vx, vy;
      ConnectionControl connectionControl;

      if (super.shouldPopup(e)) {
        object = e.getSource();

        if (!(object instanceof VisualizationControl))
          return false;

        visualizationControl = ((VisualizationControl) object);
        x = e.getX();
        y = e.getY();

        synchronized (visualizationControl) {

          for (i = (visualizationControl.getComponentCount() - 1); i >= 0; i--) {
            component = visualizationControl.getComponent(i);

            if (component instanceof ConnectionControl) {
              connectionControl = ((ConnectionControl) component);
              vx = x - connectionControl.m_tx;
              vy = y - connectionControl.m_ty;

              if ((vx >= 0) && (vx < connectionControl.getWidth()) && (vy >= 0)
                  && (vy <= connectionControl.getHeight())) {

                if (Math
                    .abs((((vx - connectionControl.m_x1) * (connectionControl.m_y2 - connectionControl.m_y1)) - ((vy - connectionControl.m_y1) * (connectionControl.m_x2 - connectionControl.m_x1)))
                        / connectionControl.m_r) < 11) {
                  ConnectionControlPopup.CONTEXT_MENU.show(connectionControl, vx, vy);
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
    protected synchronized void popup(final ContextMenuEvent e) {
      super.popup(e);
      VisualizationControlPopup.s_x = e.getScreenX();
      VisualizationControlPopup.s_y = e.getScreenY();
    }
  };

  private  Simulation simulation;
  private  Map<SimNode, NodeControl> nodeControls;
  private  Map<Connection, ConnectionControl> connectionControls;
  private int maxX;
  private int maxY;
  private int nodeZOrder;
  NodeControl nodeControl;

  public VisualizationControl(final Simulation simulation) {
    super();
    int i;
    this.simulation = simulation;
    this.nodeControls = CollectionUtils.createMap();
    this.connectionControls = CollectionUtils.createMap();

    this.simulation.addListener(this);

    synchronized (simulation) {
      for (i = (simulation.getNodeCount() - 1); i >= 0; i--) {
        this.onNodeAdded(simulation.getNode(i));
      }
      for (i = (simulation.getConnectionCount() - 1); i >= 0; i--) {
        this.onNodesConnected(simulation.getConnection(i));
      }
    }

    this.removeEventHandler(Event.ANY, new EventHandler<Event>() {

      @Override
      public void handle(Event event) {
        System.out.println("VC: implementation missing !!");

        if (event.getSource() == VisualizationControl.this.nodeControl) {
          VisualizationControl.this.nodeControl = null;
        }
      }
      public void componentAdded(Event e) {//
      }

      public void componentRemoved(Event e) {
        if (e.getSource() == VisualizationControl.this.nodeControl) {
          VisualizationControl.this.nodeControl = null;
        }
      }
    });

//    this.addEventHandler(Event.ANY, new EventHandler<Event>() {
//
//      @Override
//      public void handle(Event event) {
//        System.out.println("VC: implementation missing !!");
//
//        if (event.getSource() == VisualizationControl.this.nodeControl) {
//          VisualizationControl.this.nodeControl = null;
//        }
//      }
//      public void componentAdded(Event e) {//
//      }
//
//      public void componentRemoved(Event e) {
//        if (e.getSource() == VisualizationControl.this.nodeControl) {
//          VisualizationControl.this.nodeControl = null;
//        }
//      }
//    });

    this.onAfterStep();
    this.setOnContextMenuRequested(POPUP_MOUSE_EVENT_HANDLER);
  }

  /**
   * Obtain the simulation this control is used for.
   *
   * @return the simulation this control is used for
   */
  public Simulation getSimulation() {
    return this.simulation;
  }

  public void onNodeAdded(final SimNode simNode) {
    NodeControl nodeControl;

    nodeControl = new NodeControl(simNode);
    this.nodeControls.put(simNode, nodeControl);
//    this.getChildren().add(nodeControl);
    this.setComponentZOrder(nodeControl, this.nodeZOrder++);
    this.onAfterStep();
  }

  public void onNodeRemoved(final SimNode n) {
    Node c;

    c = this.nodeControls.remove(n);
    if (c != null) {
      this.getChildren().remove(c);
      this.nodeZOrder--;
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
    this.connectionControls.put(newCon, c);
    this.getChildren().add(c);
    this.onAfterStep();
  }

  /**
   * Two nodes were discconnected
   *
   * @param disCon
   *          the removed connection
   */
  public void onNodesDisconnected(final Connection disCon) {
    ConnectionControl connectionControl;
    connectionControl = this.connectionControls.remove(disCon);

    if (connectionControl != null)
      this.getChildren().remove(connectionControl);
    this.onAfterStep();
  }

  /**
   * this method is called after a simulation step has been performed
   */
  @Override
  public void onAfterStep() {
    int i, maxX, maxY;
    Node node;

    maxX = 0;
    maxY = 0;

    for (i = (this.getComponentCount() - 1); i >= 0; i--) {
      node = this.getComponent(i);

      if (node instanceof SimControl)
        ((SimControl) node).onAfterStep();

      maxX = Math.max(maxX, ((NodeControl)node).getSimNode().getX() +  (int)((NodeControl)node).getWidth());
      maxY = Math.max(maxY,  ((NodeControl)node).getSimNode().getY() +  (int)((NodeControl)node).getHeight());
    }

    this.maxX = Math.max(500, maxX);
    this.maxY = Math.max(maxY, 500);

    super.onAfterStep();
//    this.repaint();
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
//  @Override
//  public Dimension getPreferredSize() {
//    return new Dimension(this.maxX + 10, this.maxY + 10);
//  }
//
//  /**
//   * Gets the maximum size of this component.
//   *
//   * @return a dimension object indicating this component's maximum size
//   */
//  @Override
//  public Dimension getMaximumSize() {
//    return new Dimension(Integer.MAX_VALUE, Integer.MIN_VALUE);
//    // this.getPreferredSize();
//  }
//
//  /**
//   * Gets the minimum size of this component.
//   *
//   * @return a dimension object indicating this component's minimum size
//   */
//  @Override
//  public Dimension getMinimumSize() {
//    return this.getPreferredSize();
//  }

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

  private Node getComponent(int index) {
    ArrayList<SimControl> controls = new ArrayList<>(this.nodeControls.values());
    controls.addAll(this.connectionControls.values());
    return controls.get(index);
  }

  private int getComponentCount() {
   int count =  this.nodeControls.size() + this.connectionControls.size();
    return count;
  }

}
