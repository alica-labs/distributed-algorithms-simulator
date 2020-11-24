package de.uniks.vs.simulator.view;

import de.uniks.vs.ui.GraphNode;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.sfc.gui.PopupMouseEventHandler;

import de.uniks.vs.simulator.model.Node;
import de.uniks.vs.simulator.simulation.Simulation;

final class NodeVisualizationPopup extends ContextMenu {

    static final EventHandler ML = new PopupMouseEventHandler(new NodeVisualizationPopup()) {

        @Override
        public void handle(Event event) {

            if (MouseEvent.MOUSE_ENTERED.equals(event.getEventType())) {

                if (((MouseEvent)event).getButton() == MouseButton.PRIMARY) {
                    System.out.println("NVP: MouseButton.PRIMARY");
                }
                else if (((MouseEvent)event).getButton() == MouseButton.SECONDARY) {
                    System.out.println("NVP: MouseButton.SECONDARY");
                    this.mouseClicked((MouseEvent)event);
                }
            }
        }

        public void mouseClicked(MouseEvent e) {
            System.out.println("MEHPopup: " + e.getEventType());
            PickResult pickResult = e.getPickResult();
            System.out.println(e.getEventType());

            if (e.getButton() == MouseButton.PRIMARY) {
                Object o = e.getSource();

                if (o instanceof NodeVisualization) {
                  ((NodeVisualization) o).select();
                }
            }

            if (e.getButton() == MouseButton.SECONDARY) {
                Object o = e.getSource();

                if (o instanceof GraphNode) {
                    super.popup(e);
                }
            }
        }
    };

    public NodeVisualizationPopup() {
        super();
        MenuItem j = new MenuItem("info");
        j.setOnAction(ae -> {
            NodeVisualization c;
            Node n;
            c = getControl(ae);
            if (c != null) {
                n = c.getSimNode();
                if (n != null)
                    PropertyWindow.showProperties(n);
            }
        });
        this.getItems().add(j);
//    j.setGraphic(new ImageView(Icons.INFO));

        SeparatorMenuItem separator = new SeparatorMenuItem();
        this.getItems().add(separator);

        j = new MenuItem("trigger");
        j.setOnAction(ae -> {
            NodeVisualization c = getControl(ae);

            if (c != null) {
                Node n = c.getSimNode();

                if (n != null) {
                    n.trigger();
                }
                javafx.scene.Node cc = c.getParent();

                if (cc != null) {
//                  cc.repaint();
                }
            }
        });
//    j.setGraphic(new ImageView(Icons.EXECUTE));
        this.getItems().add(j);

        j = new MenuItem("step"); //$NON-NLS-1$
        j.setOnAction(ae -> {
            NodeVisualization c;
            Node n;
            c = getControl(ae);
            if (c != null) {
                n = c.getSimNode();
                if (n != null) {
                    n.step();
                }
            }
        });
//    j.setGraphic(new ImageView(Icons.EXECUTE));
        this.getItems().add(j);

        j = new MenuItem("reset"); //$NON-NLS-1$
        j.setOnAction(ae -> {
            NodeVisualization c;
            Node n;
            c = getControl(ae);
            if (c != null) {
                n = c.getSimNode();
                if (n != null) {
                    n.reset();
                }
            }
        });
//    j.setGraphic(new ImageView(Icons.RESET));
        this.getItems().add(j);

        separator = new SeparatorMenuItem();
        this.getItems().add(separator);

        j = new MenuItem("delete"); //$NON-NLS-1$
        j.setOnAction(ae -> {
            NodeVisualization c;
            Simulation s;
            Node n;
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
//    j.setGraphic(new ImageView(Icons.DELETE));
        this.getItems().add(j);
    }

    static final NodeVisualization getControl(final ActionEvent ae) {
        Object o;

        o = ae.getSource();
        for (; ; ) {
            if (o == null)
                return null;
            if (o instanceof ContextMenu)
                break;
            if (o instanceof MenuItem) {
                o = ((MenuItem) o).getParentPopup();
                continue;
            }
            if (o instanceof javafx.scene.Node) {
                o = ((javafx.scene.Node) o).getParent();
            } else
                return null;
        }
        o = ((ContextMenu) o).getOwnerNode();
        GraphNode graphNode = ((GraphNode) o);
        o = ((GraphNode) o).getParent();

        if (o instanceof SimVisualization) {
            o = ((SimVisualization) o).getNodeVisualization(graphNode);
        }
        if (o instanceof NodeVisualization)
            return ((NodeVisualization) o);
        return null;
    }

}
