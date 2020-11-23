package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.model.*;
import de.uniks.vs.simulator.simulation.Simulation;
import de.uniks.vs.ui.GraphNode;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.ContextMenuEvent;
import org.sfc.collections.CollectionUtils;
import org.sfc.gui.PopupMouseEventHandler;

import java.util.ArrayList;
import java.util.Map;

final class SimVisualization extends Visualization implements SimulationListener {

    EventHandler POPUP_MOUSE_EVENT_HANDLER = new PopupMouseEventHandler(new VisualizationControlPopup()) {

        @Override
        protected boolean shouldPopup(final ContextMenuEvent e) {

            if (super.shouldPopup(e)) {
                Object object = e.getSource();

                if (!(object instanceof SimVisualization))
                    return false;

                if (e.getPickResult().getIntersectedNode() != SimVisualization.this)
                    return false;

                SimVisualization simVisualization = ((SimVisualization) object);
                double x = e.getX();
                double y = e.getY();

                for (int i = (simVisualization.getComponentCount() - 1); i >= 0; i--) {
                    javafx.scene.Node component = simVisualization.getComponent(i);

                    if (component instanceof ConnectionVisualization) {
                        ConnectionVisualization connectionVisualization = ((ConnectionVisualization) component);
                        double vx = x - connectionVisualization.tx;
                        double vy = y - connectionVisualization.ty;

                        if ((vx >= 0) && (vx < connectionVisualization.getWidth()) && (vy >= 0)
                                && (vy <= connectionVisualization.getHeight())) {

                            if (Math
                                    .abs((((vx - connectionVisualization.x1) * (connectionVisualization.y2 - connectionVisualization.y1))
                                            - ((vy - connectionVisualization.y1) * (connectionVisualization.x2 - connectionVisualization.x1)))
                                            / connectionVisualization.r) < 11) {
                                ConnectionControlPopup.CONTEXT_MENU.show(connectionVisualization, vx, vy);
                                return false;
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
            VisualizationControlPopup.s_x = e.getSceneX();
            VisualizationControlPopup.s_y = e.getSceneY();
        }
    };

    private Simulation simulation;
    private Map<Node, NodeVisualization> nodeVisualizations;
    private Map<Connection, ConnectionVisualization> connectionVisualizations;
    private int maxX;
    private int maxY;
    private int nodeZOrder;
    NodeVisualization nodeVisualization;

    public SimVisualization(final Simulation simulation) {
        super();
        int i;
        this.simulation = simulation;
        this.nodeVisualizations = CollectionUtils.createMap();
        this.connectionVisualizations = CollectionUtils.createMap();

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
//        System.out.println("VC: implementation missing !!");
//
//        if (event.getSource() == VisualizationControl.this.nodeControl) {
//          VisualizationControl.this.nodeControl = null;
//        }
            }

            public void componentAdded(Event e) {//
            }

            public void componentRemoved(Event e) {
                if (e.getSource() == SimVisualization.this.nodeVisualization) {
                    SimVisualization.this.nodeVisualization = null;
                }
            }
        });

        this.addEventHandler(Event.ANY, new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
//        System.out.println("VC: implementation missing !!");
//
//        if (event.getSource() == VisualizationControl.this.nodeControl) {
//          VisualizationControl.this.nodeControl = null;
//        }
            }

            public void componentAdded(Event e) {//
            }

            public void componentRemoved(Event e) {
                if (e.getSource() == SimVisualization.this.nodeVisualization) {
                    SimVisualization.this.nodeVisualization = null;
                }
            }
        });

        this.onAfterStep();
        this.setOnContextMenuRequested(POPUP_MOUSE_EVENT_HANDLER);
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void onNodeAdded(final Node simNode) {
        NodeVisualization nodeVisualization = new NodeVisualization(simNode);
        this.nodeVisualizations.put(simNode, nodeVisualization);
        Platform.runLater(() -> {
            this.getChildren().add(nodeVisualization);
            System.out.println("SV: node added");
        });
        this.setComponentZOrder(nodeVisualization, this.nodeZOrder++);
        this.onAfterStep();
    }

    public void onNodeRemoved(final Node n) {
        javafx.scene.Node c;

        c = this.nodeVisualizations.remove(n);
        if (c != null) {
            Platform.runLater(() -> {
                this.getChildren().remove(c);
                System.out.println("SV: node remove");
            });
            this.nodeZOrder--;
        }
        this.onAfterStep();
    }

    public void onNodesConnected(final Connection newCon) {
        ConnectionVisualization c;

        c = new ConnectionVisualization(newCon);
        this.connectionVisualizations.put(newCon, c);
        Platform.runLater(() -> {
            this.getChildren().add(0, c);
            System.out.println("SV: connect added");
        });
        this.onAfterStep();
    }

    public void onNodesDisconnected(final Connection disCon) {
        ConnectionVisualization connectionControl;
        connectionControl = this.connectionVisualizations.remove(disCon);

        if (connectionControl != null)
            Platform.runLater(() -> {
                this.getChildren().remove(connectionControl);
                System.out.println("SV: connect removed");
            });

        this.onAfterStep();
    }

    @Override
    public void onAfterStep() {
        int i, maxX, maxY;
        javafx.scene.Node node;

        maxX = 0;
        maxY = 0;

        for (i = (this.getComponentCount() - 1); i >= 0; i--) {
            node = this.getComponent(i);

            if (node instanceof Visualization)
                ((Visualization) node).onAfterStep();

            maxX = Math.max(maxX, ((Visualization) node).getX() + (int) ((Visualization) node).getWidth());
            maxY = Math.max(maxY, ((Visualization) node).getY() + (int) ((Visualization) node).getHeight());
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
    public void onLog(final String logged, final Model source) {
        //
    }

    private javafx.scene.Node getComponent(int index) {
        ArrayList<Visualization> controls = new ArrayList<>(this.nodeVisualizations.values());
        controls.addAll(this.connectionVisualizations.values());
        return controls.get(index);
    }

    private int getComponentCount() {
        int count = this.nodeVisualizations.size() + this.connectionVisualizations.size();
        return count;
    }

    public NodeVisualization getNodeVisualization(GraphNode graphNode) {

        for (NodeVisualization nodeVisualization : nodeVisualizations.values()) {

            if (nodeVisualization.getGraphNode() == graphNode)
                return nodeVisualization;
        }
        return null;
    }
}
