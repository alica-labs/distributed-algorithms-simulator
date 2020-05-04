/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.SimulationWindow.java
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


import javafx.stage.Stage;
import org.sfc.gui.resources.icons.Icons;
import org.sfc.gui.windows.SfcFrame;
import org.sfc.parallel.simulation.SimulationThread;

import uniks.vs.ds.model.Simulation;

/**
 * The simulation window frame
 *
 * @author Thomas Weise
 */
public class SimulationWindow extends SfcFrame {

    private static final long serialVersionUID = 1L;

    Simulation simulation;
    SimulationThread simulationThread;
    ColumnConstraints gridBagConstraints;
    SplitPane splitPane;
    Slider speedSlider;
    VisualizationControl visualizationControl;

    public SimulationWindow(Stage stage, Group root) {
        super(stage, root);

        System.out.println("SimulationWindow: started ...");

        this.stage.setTitle("Distributed Algorithms Simulation"); //$NON-NLS-1$
        this.setIcon(Icons.GRAPH);

        GridPane panel = new GridPane();
        root.getChildren().add(panel);
        ColumnConstraints gridBagLayout = new ColumnConstraints();
        panel.getColumnConstraints().addAll(gridBagLayout);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setPercentWidth(100);
        columnConstraints.setHalignment(HPos.RIGHT);
        panel.getColumnConstraints().add(columnConstraints);
        panel.prefWidthProperty().bind(stage.widthProperty());
        //    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setHgap(0);
        panel.setVgap(5);

        this.menuBar = this.buildMenu();
        GridPane.setRowIndex(this.menuBar, 0);
        GridPane.setFillWidth(this.menuBar, true);
        GridPane.setColumnSpan(this.menuBar, 2);
        GridPane.setHalignment(this.menuBar, HPos.RIGHT);
        GridPane.setHgrow(this.menuBar, Priority.ALWAYS);
        GridPane.setVgrow(this.menuBar, Priority.ALWAYS);
        panel.getChildren().add(this.menuBar);
//    panel.addRow(0, hBox);

        this.speedSlider = new Slider(0, 100, 0);
        GridPane.setRowIndex(this.speedSlider, 1);
        GridPane.setFillWidth(this.speedSlider, true);
        GridPane.setColumnSpan(this.speedSlider, 2);
        GridPane.setHalignment(this.speedSlider, HPos.RIGHT);
        GridPane.setHgrow(this.speedSlider, Priority.ALWAYS);
        GridPane.setVgrow(this.speedSlider, Priority.ALWAYS);
//    panel.add(this.speedSlider,0,2);
        panel.getChildren().add(this.speedSlider);
//    panel.addRow(1, this.speedSlider);

////    slider.setPaintLabels(false);
////    slider.setPaintTicks(false);
////    slider.setPaintTrack(true);

        this.speedSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                synchronized (SimulationWindow.this) {

                    if (SimulationWindow.this.simulationThread != null)
                        SimulationWindow.this.simulationThread.setSpeed((int) ((double) newValue));
                }
            }
        });

        Insets insets = new Insets(3, 3, 3, 3);
        panel.setPadding(insets);
////    gridBagLayout.setConstraints(slider, new GridBagConstraints(0, 0, 1, 1, 1, 0,
////        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, insets,
////        1, 1));

//    this.setContentPane(panel);
//    this.gridBagConstraints = new ColumnConstraints(0, 1, 1);
//
        this.setSize(700, 500);

        this.init(new Simulation());
//    this.setVisible(true);
    }

    private MenuBar buildMenu() {
        Menu menu;
        MenuItem menuItem;

        // menu
        menuBar = new MenuBar();
//    VBox hBox = new VBox(menuBar);
//    this.root.getChildren().add(hBox);

        menu = new Menu("simulation"); //$NON-NLS-1$
        menu.setGraphic(new ImageView(Icons.GRAPH));

        menuItem = new MenuItem("new"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
                    PropertyWindow.closeAllPropertyWindows();
                    SimulationWindow.this.init(new Simulation());
                }
        );
        menuItem.setGraphic(new ImageView(Icons.NEW));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("store"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            synchronized (SimulationWindow.this) {
                if (SimulationWindow.this.simulation != null)
                    SimulationIO.storeSimulation(SimulationWindow.this.simulation);
            }
        });
        menuItem.setGraphic(new ImageView(Icons.SAVE));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("load"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            Simulation simulation;

            simulation = SimulationIO.loadSimulation();
            if (simulation != null)
                synchronized (SimulationWindow.this) {
                    SimulationWindow.this.init(simulation);
                }
        });
        menuItem.setGraphic(new ImageView(Icons.OPEN));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("print"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            VisualizationControl visualizationControl;
            synchronized (SimulationWindow.this) {
                visualizationControl = SimulationWindow.this.visualizationControl;
                if (visualizationControl != null)
                    SimulationIO.printSimulation(visualizationControl);
            }
        });
        menuItem.setGraphic(new ImageView(Icons.PRINT));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("reset"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            SimulationWindow.this.reset();
        });
        menuItem.setGraphic(new ImageView(Icons.RESET));
        menu.getItems().add(menuItem);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        menu.getItems().add(separator);

        menuItem = new MenuItem("exit"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            SimulationWindow.this.close();
            System.exit(0);
        });
        menuItem.setGraphic(new ImageView(Icons.EXIT));
        menu.getItems().add(menuItem);

        this.menuBar.getMenus().add(menu);

        return this.menuBar;
    }

    final void reset() {
        if (this.simulation != null) {
            this.simulation.reset();
            SpeedDialog.resetAll();
        }
    }

    final void init(final Simulation simulation) {

        GridPane container = this.getContentPane();

        if (this.simulationThread != null) {
            this.simulationThread.abort();
            this.simulationThread = null;
        }
        if (this.splitPane != null) {
            container.getChildren().remove(this.splitPane);
            this.splitPane = null;
        }
        if (this.simulation != null) {
            this.simulation = null;
        }
        if (this.visualizationControl != null) {
            this.visualizationControl = null;
        }

        this.simulation = simulation;

        this.visualizationControl = new VisualizationControl(this.simulation);
        ScrollPane simPane = new ScrollPane(this.visualizationControl);
        simPane.fitToWidthProperty().set(true);
        simPane.fitToHeightProperty().set(true);
        SplitPane.setResizableWithParent(simPane, Boolean.TRUE);

        ScrollPane logPane = new ScrollPane(new LogPanel(this.simulation));
        logPane.fitToWidthProperty().set(true);
        logPane.fitToHeightProperty().set(true);
        SplitPane.setResizableWithParent(logPane, Boolean.TRUE);
        this.splitPane = new SplitPane(simPane, logPane);
//        this.splitPane.setDividerPositions((((int) this.getWidth()) * 3) >>> 2);
        this.splitPane.setDividerPositions(0.75);

//    this.splitPane.setOneTouchExpandable(true);
//    splitPane.setResizeWeight(1.0d);
//    ((GridBagLayout) (container.getLayout())).setConstraints(splitPane, this.gridBagConstraints);

        GridPane.setRowIndex(this.splitPane, 2);
        GridPane.setFillWidth(this.splitPane, true);
        GridPane.setColumnSpan(this.splitPane, 2);
        GridPane.setHalignment(this.splitPane, HPos.RIGHT);
        GridPane.setHgrow(this.splitPane, Priority.ALWAYS);
        GridPane.setVgrow(this.splitPane, Priority.ALWAYS);
        container.getChildren().add(this.splitPane);

//    this.validate();
//        this.repaint();

        this.simulationThread = new SimulationThread(this.simulation);
        this.speedSlider.setValue(0);
        this.simulationThread.start();
//    SpeedDialog.closeAll();
    }

}
