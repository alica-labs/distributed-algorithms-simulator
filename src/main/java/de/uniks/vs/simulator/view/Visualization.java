package de.uniks.vs.simulator.view;

import de.uniks.vs.ui.panes.GraphPane;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;

class Visualization extends GraphPane {

  public  static final double DEFAULT_WIDTH 	= 16;
  public  static final double DEFAULT_HEIGHT	= 16;

  private Tooltip toolTip;

  protected void onAfterStep() {
    //this.revalidate();
  }

  protected void setComponentZOrder(NodeVisualization nodeControl, int order) {
//    this.getChildren().add(order, nodeControl.getGraphNode());
    Platform.runLater(()->{
      this.getChildren().add(order, nodeControl.getGraphNode());

      System.out.println("V: added");
    });
  }

  protected void setToolTipText(String text) {
    if (this.toolTip == null) {
      Tooltip toolTip = new Tooltip();
      toolTip.setStyle("-fx-font: normal bold 4 Langdon; "
              + "-fx-base: #AE3522; "
              + "-fx-text-fill: orange;");
      this.setTooltip(toolTip);
    }
    toolTip.setText(text);
  }

  protected void setTooltip(Tooltip tooltip) {
    this.toolTip = tooltip;
  }

  protected String getToolTipText() {
    return this.toolTip.getText();
  }

  public int getX() {return 0;}
  public int getY() {return 0;}
}
