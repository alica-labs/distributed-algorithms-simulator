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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;

import javafx.stage.Window;
import org.sfc.gui.resources.icons.Icons;

import uniks.vs.ds.model.INodeFactory;
import uniks.vs.ds.model.NodeTypes;
import uniks.vs.ds.model.Simulation;

/**
 * The popup menu for visualization controls
 *
 * @author Thomas Weise
 */
final class VisualizationControlPopup extends ContextMenu {
    /**
     * the serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * the x coordinate
     */
    static double s_x;

    /**
     * the y coordinate
     */
    static double s_y;

    /**
     * The popup menu of visualization controls
     */
    VisualizationControlPopup() {
        super();

        MenuItem menuItem;
        INodeFactory[] f;
        int i;

        f = NodeTypes.getNodeFactories();

        menuItem = new MenuItem("info"); //$NON-NLS-1$

        menuItem.setOnAction(ae -> {
            MenuItem item = (MenuItem) ae.getSource();
            ContextMenu parentPopup = item.getParentPopup();
            Node ownerNode = parentPopup.getOwnerNode();

            if (ownerNode != null) {
                Simulation n = (((VisualizationControl) ownerNode).getSimulation());
                if (n != null)
                    PropertyWindow.showProperties(n);
            }
        });
        menuItem.setGraphic(new ImageView(Icons.INFO));
        this.getItems().add(menuItem);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        this.getItems().add(separator);

        for (i = 0; i < f.length; i++) {
            menuItem = new MenuItem(f[i].getNodeTypeName());
            menuItem.setGraphic(new ImageView(Icons.ADD));
            menuItem.setOnAction(new FactoryEventHandler(f[i]));
            this.getItems().add(menuItem);
        }

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
                    if (c instanceof VisualizationControl)
                        break;
                }

                if (c != null) {
                    ((VisualizationControl) c).getSimulation().trigger();
                }
            }
        });
        menuItem.setGraphic(new ImageView(Icons.EXECUTE));
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
                    if (c instanceof VisualizationControl)
                        break;
                }

                if (c != null) {
                    ((VisualizationControl) c).getSimulation().step();
                }
            }
        });
        menuItem.setGraphic(new ImageView(Icons.APPLY));
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
                    if (c instanceof VisualizationControl)
                        break;
                }

                if (c != null) {
                    ((VisualizationControl) c).getSimulation().reset();
                }
            }

            SpeedDialog.resetAll();
        });
        menuItem.setGraphic(new ImageView(Icons.RESET));
        this.getItems().add(menuItem);
    }

    /**
     * This internal class embodies a node factory button.
     *
     * @author Thomas Weise
     */
    private static final class FactoryEventHandler implements EventHandler {
        /**
         * the serial version uid
         */
        private static final long serialVersionUID = 1L;

        /**
         * the internal node factory
         */
        private final INodeFactory nodeFactory;

        /**
         * Create a new node factory menu item.
         *
         * @param f the node factory
         */
        FactoryEventHandler(final INodeFactory f) {
            super();
            this.nodeFactory = f;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event
         */
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

            VisualizationControl visualizationControl = ((VisualizationControl) object);
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
