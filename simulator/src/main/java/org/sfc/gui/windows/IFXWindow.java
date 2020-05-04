package org.sfc.gui.windows;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class IFXWindow extends Scene {

    protected Stage stage;
    protected Scene scene;
    protected Group root;

    protected String title;
    protected int width;
    protected int height;
    protected MenuBar menuBar;

    public IFXWindow(Stage stage, Group root) {
        super(root);
        this.stage = stage;
        this.root = root;
    }

//    protected void setContentPane(Pane panel) {
//        this.root = panel;
//    }

    protected void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    protected GridPane getContentPane() {return (GridPane) this.root.getChildren().get(0);}

    protected void validate() { }

    protected void repaint() { }

    protected void toFront() { }

    protected void pack() { }

    protected void dispose() {}

    public void setTitle(String title) { this.title = title; }

    public void setVisible(boolean visible) { }

    protected void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        stage.setHeight(this.height);
        stage.setWidth(this.width);
    }
}
