package de.uniks.vs.simulator;

import de.uniks.vs.simulator.algorithms.example.TestNode;
import de.uniks.vs.simulator.algorithms.example.echo.EchoNode;
import de.uniks.vs.simulator.algorithms.example.gcd.GCDNode;
import de.uniks.vs.simulator.model.utils.NodeTypes;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;
import javafx.stage.Stage;
import de.uniks.vs.simulator.view.SimulationWindow;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.NoSuchElementException;

public class Simulator extends Application {

    protected String title = "^._.^   Distributed Systems Simulation   v0.1";

    @Override
    public void start(Stage stage) {
        NodeTypes.registerNodeType(TestNode.class);
        NodeTypes.registerNodeType(EchoNode.class);
        NodeTypes.registerNodeType(GCDNode.class);
        this.startApplication(stage);
    }

    protected void startApplication(Stage stage) {
        SimulationWindow simulationWindow = new SimulationWindow(stage, new Group(), title);
        stage.setScene(simulationWindow);
        stage.initStyle(StageStyle.DECORATED);
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                Screen screen = getScreenForRectangle(p.x, p.y);
                Rectangle2D bounds = screen.getBounds();
                stage.setX(bounds.getMinX() + 100);
                stage.setY(bounds.getMinY() + 100);
                stage.centerOnScreen();
            }

            public Screen getScreenForRectangle(double x, double y) {

                for (Screen screen : Screen.getScreensForRectangle(x, y, 1., 1.)) {
                    return screen;
                }
                throw new NoSuchElementException("Cannot determine screen for stage.");
            }
        });
        stage.show();
    }
}