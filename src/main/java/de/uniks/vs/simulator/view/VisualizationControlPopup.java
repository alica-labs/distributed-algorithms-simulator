package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.model.utils.NodeFactory;
import de.uniks.vs.simulator.model.utils.NodeTypes;
import de.uniks.vs.simulator.simulation.Simulation;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import javafx.stage.Window;

final class VisualizationControlPopup extends ContextMenu {

    static double s_x;
    static double s_y;

    VisualizationControlPopup() {
        super();

        MenuItem menuItem = new MenuItem("info");
        NodeFactory[] nodeFactories = NodeTypes.getNodeFactories();;
        int i;

        menuItem.setOnAction(ae -> {
            MenuItem item = (MenuItem) ae.getSource();
            ContextMenu parentPopup = item.getParentPopup();
            Node ownerNode = parentPopup.getOwnerNode();

            if (ownerNode != null) {
                Simulation n = (((SimVisualization) ownerNode).getSimulation());
                if (n != null)
                    PropertyWindow.showProperties(n);
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.INFO));
        this.getItems().add(menuItem);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        this.getItems().add(separator);

        for (i = 0; i < nodeFactories.length; i++) {
            menuItem = new MenuItem(nodeFactories[i].getNodeTypeName());
//            try {
//                String s = getClass().getResource(".").toURI().toString();
//                System.out.println(s);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            menuItem.setGraphic(new ImageView(Icons.ADD));
            menuItem.setOnAction(new FactoryEventHandler(nodeFactories[i]));
            this.getItems().add(menuItem);
        }

        separator = new SeparatorMenuItem();
        this.getItems().add(separator);


        menuItem = new MenuItem("connect all"); //$NON-NLS-1$
        menuItem.setOnAction(ae -> {
            MenuItem item = (MenuItem)ae.getSource();
            Node ownerNode = item.getParentPopup().getOwnerNode();

            if (ownerNode instanceof SimVisualization) {
                ((SimVisualization) ownerNode).getSimulation().connectAll();
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.EXECUTE));
        this.getItems().add(menuItem);

        menuItem = new MenuItem("connect as ring"); //$NON-NLS-1$
        menuItem.setOnAction(ae -> {
            MenuItem item = (MenuItem) ae.getSource();
            Node ownerNode = item.getParentPopup().getOwnerNode();

            if (ownerNode instanceof SimVisualization) {
                ((SimVisualization) ownerNode).getSimulation().connectAsRing();
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.EXECUTE));
        this.getItems().add(menuItem);

        separator = new SeparatorMenuItem();
        this.getItems().add(separator);

        menuItem = new MenuItem("trigger all"); //$NON-NLS-1$
        menuItem.setOnAction(ae -> {
            Object o;
            Node c;

            o = ae.getSource();
            if (o instanceof Window) {
                for (c = ((Node) o); c != null; c = c.getParent()) {
//            if (c instanceof ContextMenu) {
//              c = ((ContextMenu) c).getInvoker();
//            }
                    if (c instanceof SimVisualization)
                        break;
                }

                if (c != null) {
                    ((SimVisualization) c).getSimulation().trigger();
                }
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.EXECUTE));
        this.getItems().add(menuItem);

        menuItem = new MenuItem("step all"); //$NON-NLS-1$
        menuItem.setOnAction(ae -> {
            Object o;
            Node c;

            o = ae.getSource();
            if (o instanceof Node) {
                for (c = ((Node) o); c != null; c = c.getParent()) {
//            if (c instanceof ContextMenu) {
//              c = ((PopupWindow) c).getInvoker();
//            }
                    if (c instanceof SimVisualization)
                        break;
                }

                if (c != null) {
                    ((SimVisualization) c).getSimulation().step();
                }
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.APPLY));
        this.getItems().add(menuItem);

        menuItem = new MenuItem("reset all"); //$NON-NLS-1$
        menuItem.setOnAction(ae -> {
            Object o;
            Node c;

            o = ae.getSource();
            if (o instanceof Node) {
                for (c = ((Node) o); c != null; c = c.getParent()) {
//            if (c instanceof ContextMenu) {
//              c = ((JPopupMenu) c).getInvoker();
//            }
                    if (c instanceof SimVisualization)
                        break;
                }

                if (c != null) {
                    ((SimVisualization) c).getSimulation().reset();
                }
            }

            SpeedDialog.resetAll();
        });
//        menuItem.setGraphic(new ImageView(Icons.RESET));
        this.getItems().add(menuItem);
    }

    private static final class FactoryEventHandler implements EventHandler {

        private final NodeFactory nodeFactory;

        FactoryEventHandler(final NodeFactory nodeFactory) {
            super();
            this.nodeFactory = nodeFactory;
        }

        public void actionPerformed(Event e) {

            Object object = e.getSource();

            if (! (object instanceof MenuItem)) {
                return;
            }

            MenuItem menuItem = (MenuItem) object;
            ContextMenu contextMenu = menuItem.getParentPopup();
            object = contextMenu.getOwnerNode();

//            for (; ; ) {
//                if (object == null)
//                    return;
//                if (object instanceof MenuItem)
//                    break;
//                if (object instanceof Node) {
//                    object = ((Node) object).getParent();
//                } else
//                    return;
//            }
//
//            contextMenu = ((ContextMenu) object);
//            object = contextMenu.getOwnerWindow();
//            for (; ; ) {
//                if (object == null)
//                    return;
//                if (object instanceof VisualizationControl)
//                    break;
//                if (object instanceof Node) {
//                    object = ((Node) object).getParent();
//                } else
//                    return;
//            }

            SimVisualization visualizationControl = ((SimVisualization) object);
            Simulation simulation = visualizationControl.getSimulation();

            if (simulation != null) {
                simulation.addNode(this.nodeFactory.createNode(s_x, s_y));
            }

        }

        @Override
        public void handle(Event event) {
            this.actionPerformed(event);
        }
    }

}
