package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.simulation.Simulation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.sfc.gui.windows.SfcFrame;
import de.uniks.vs.physics.Physics;
import org.sfc.parallel.simulation.SimulationThread;

public class SimulationWindow extends SfcFrame {

    private Simulation simulation;
    private Physics physics;
    private SimulationThread simulationThread;
    private ScrollPane simPane;
    private Slider speedSlider;
    private final AnchorPane anchorPane;
    private SimVisualization visualizationControl;

    public SimulationWindow(Stage stage, Group root, String title) {
        super(stage, root);
        System.out.println("SimulationWindow: started ...");
        this.stage.setTitle(title);
//        this.setIcon(Icons.GRAPH);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        anchorPane = new AnchorPane();
        root.getChildren().add(vbox);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(anchorPane);
        this.menuBar = this.buildMenu();
        hbox.getChildren().add(this.menuBar);
        this.speedSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                synchronized (SimulationWindow.this) {

                    if (SimulationWindow.this.simulationThread != null) {
                        double value = (double) newValue/10-5;
                        double newSpeed = 1 / (1 + Math.exp(-value)) * 100;
                        SimulationWindow.this.simulationThread.setSpeed((int) (newSpeed));
                    }
                }
            }
        });
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

        menu = new Menu();
        Label label = new Label("menu");
        label.setPrefWidth(125);
        menu.setGraphic(label);
//        menu.setGraphic(new ImageView(Icons.GRAPH));

        menuItem = new MenuItem("new"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
                    PropertyWindow.closeAllPropertyWindows();
                    SimulationWindow.this.init(new Simulation());
                }
        );
//        menuItem.setGraphic(new ImageView(Icons.NEW));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("store"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            synchronized (SimulationWindow.this) {
                if (SimulationWindow.this.simulation != null)
                    SimulationIO.storeSimulation(SimulationWindow.this.simulation);
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.SAVE));
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
//        menuItem.setGraphic(new ImageView(Icons.OPEN));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("print"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            SimVisualization visualizationControl;
            synchronized (SimulationWindow.this) {
                visualizationControl = SimulationWindow.this.visualizationControl;
                if (visualizationControl != null)
                    SimulationIO.printSimulation(visualizationControl);
            }
        });
//        menuItem.setGraphic(new ImageView(Icons.PRINT));
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("reset"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            SimulationWindow.this.reset();
        });
//        menuItem.setGraphic(new ImageView(Icons.RESET));
        menu.getItems().add(menuItem);

        SeparatorMenuItem separator = new SeparatorMenuItem();
        menu.getItems().add(separator);

        menuItem = new MenuItem("exit"); //$NON-NLS-1$
        menuItem.setOnAction(e -> {
            SimulationWindow.this.close();
            System.exit(0);
        });
//        menuItem.setGraphic(new ImageView(Icons.EXIT));
        menu.getItems().add(menuItem);
        this.menuBar.getMenus().add(menu);
        this.speedSlider = new Slider(0, 100, 0);
        this.speedSlider.prefWidthProperty().bind(menuBar.widthProperty());
        Menu sliderMenu = new Menu();
        sliderMenu.setGraphic(this.speedSlider);
        this.menuBar.getMenus().add(sliderMenu);
        this.menuBar.prefWidthProperty().bind(stage.widthProperty());
        return this.menuBar;
    }

    final void reset() {
        if (this.simulation != null) {
            this.simulation.reset();
            SpeedDialog.resetAll();
        }
    }

    final void init(final Simulation simulation) {

        if (this.simulationThread != null) {
            this.simulationThread.abort();
            this.simulationThread = null;
        }
        if (this.simPane != null) {
            this.simPane = null;
        }
        if (this.simulation != null) {
            this.simulation = null;
        }
        if (this.visualizationControl != null) {
            this.visualizationControl = null;
        }

        this.simulation = simulation;
        this.physics = new Physics();

        this.visualizationControl = new SimVisualization(this.simulation);
        this.visualizationControl.init();
        simPane = new ScrollPane(this.visualizationControl);
        simPane.setFitToHeight(true);
        simPane.setFitToWidth(true);
        anchorPane.setMinSize(Double.MAX_VALUE, Double.MAX_VALUE);
        anchorPane.getChildren().add(simPane);
        AnchorPane.setTopAnchor(simPane, 0.0);
        AnchorPane.setBottomAnchor(simPane, 0.0);
        AnchorPane.setLeftAnchor(simPane, 0.0);
        AnchorPane.setRightAnchor(simPane, 0.0);
        //    this.validate();
//        this.repaint();
        this.simulationThread = new SimulationThread(this.simulation);
        this.speedSlider.setValue(0);
        this.simulationThread.start();
        SpeedDialog.closeAll();
    }

}
