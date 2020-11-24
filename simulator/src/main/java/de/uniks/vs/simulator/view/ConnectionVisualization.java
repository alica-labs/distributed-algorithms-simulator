package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.model.Connection;
import de.uniks.vs.simulator.model.Message;
import de.uniks.vs.simulator.model.Node;
import de.uniks.vs.ui.GraphEdge;
import de.uniks.vs.ui.GraphItem;
import de.uniks.vs.ui.GraphNode;
import de.uniks.vs.ui.manager.GraphItemManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;

final class ConnectionVisualization extends Visualization {

    private static final int MSG_D2 = 7;
    private static final int MSG_D = (MSG_D2 << 1);
    private static final int MSG_D4 = (MSG_D2 << 1);

    private Connection connection;
    private GraphEdge graphEdge;

    int x1;
    int y1;
    int x2;
    int y2;

    final double r;
    final int tx;
    final int ty;

//    private final double len;
    private ArrayList<Integer> msgIds = new ArrayList();
    private ArrayList<Integer> currentMsgIds = new ArrayList();

    @Override
    public int getX() {
        return x1;
    }

    @Override
    public int getY() {
        return y1;
    }

    public GraphEdge createEdge(Connection connection) {
        Node node1 = connection.getNode1();
        Node node2 = connection.getNode2();

        Line line = createEdge(
                node1.getX() + DEFAULT_WIDTH / 2,
                node1.getY() + DEFAULT_HEIGHT / 2,
                node2.getX() + DEFAULT_WIDTH / 2,
                node2.getY() + DEFAULT_HEIGHT / 2);

//    line.setId(""+id);

//    if (GraphItem.NEW.equals(edge.getStatus()))
//      line.setStroke(Color.LIGHTGREEN);
//
//    else if (GraphItem.DELETED.equals(edge.getStatus()))
//      line.setStroke(Color.RED);

        Text text = new Text(node1.getX(), node1.getY(), "");
        text.setBoundsType(TextBoundsType.VISUAL);

//    if ("DD on".equals(edge.getStatus())) {
        line.getStrokeDashArray().addAll(20d, 10d);
//      text.setText(id + " (DD)");
//    }

        GraphEdge graphEdge = new GraphEdge();
        graphEdge.setId("_");
        graphEdge.addItem(line, text);
        return graphEdge;
    }

    private Line createEdge(double posX, double posY, double x, double y) {
        Line line = new Line();
        line.setStartX(posX);
        line.setStartY(posY);
        line.setEndX(x);
        line.setEndY(y);
        line.setStroke(Color.BLACK);
        line.setStroke(Color.DARKGRAY);
        line.setStrokeWidth(1.0);
        return line;
    }


    public ConnectionVisualization(final Connection connection) {
        super();
        this.connection = connection;
        Node n1 = this.connection.getNode1();
        Node n2 = this.connection.getNode2();
        int x1 = n1.getX();
        int y1 = n1.getY();
        int x2 = n2.getX();
        int y2 = n2.getY();

        if (x1 < x2) {
            this.tx = x1;
            this.x1 = 0;
            this.x2 = x2 -= x1;
        } else {
            this.tx = x2;
            int d = x1 - x2;
            x1 = x2;
            this.x1 = x2 = d;
            this.x2 = 0;
        }

        if (y1 < y2) {
            this.ty = y1;
            this.y2 = y2 -= y1;
            this.y1 = 0;
        } else {
            this.ty = y2;
            int d = y1 - y2;
            this.y2 = 0;
            y1 = y2;
            this.y1 = y2 = d;
        }

//        this.len = connection.getLength();

        this.r = // Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                Math.sqrt(x2 * x2 + y1 * y1);

        if (x2 <= MSG_D4) {
            int d = (MSG_D4 + 1 - x2);
            if ((d & 1) != 0)
                d++;
            x2 += d;
            d >>>= 1;
            if (x1 < d)
                d = x1;
            x1 -= d;
            this.x1 += d;
            this.x2 += d;
        }

        if (y2 <= MSG_D4) {
            int d = (MSG_D4 + 1 - y2);
            if ((d & 1) != 0)
                d++;
            y2 += d;
            d >>>= 1;
            if (y1 < d)
                d = y1;
            y1 -= d;
            this.y1 += d;
            this.y2 += d;
        }
        this.graphEdge = createEdge(connection);
        this.getChildren().add(0, graphEdge);
//    this.setBounds(x1, y1, x2, y2);
        this.onAfterStep();
    }

    public Connection getConnection() {
        return this.connection;
    }


    public void onAfterStep() {
        this.updateMsgNodes(this.graphEdge);
        this.cleanupMsgNodes(this.graphEdge);
    }

    private void cleanupMsgNodes(final GraphItem graphItem) {

        for (Integer id : this.msgIds) {

            if (!this.currentMsgIds.contains(id))
                graphItem.removeItem(id);
        }
        this.msgIds.clear();
        this.msgIds.addAll(this.currentMsgIds);
    }

    private final void updateMsgNodes(final GraphItem graphItem) {
        this.currentMsgIds.clear();
        Connection connection = this.connection;

        for (Message msg = connection.getFirstQueuedMessage(); msg != null; msg = connection.getNextQeuedMessage(msg)) {
            this.currentMsgIds.add(msg.hashCode());
            GraphItem msgNode = (GraphItem) graphItem.getItem(String.valueOf(msg.hashCode()));

            if (msgNode != null && !(msgNode instanceof GraphNode))
                return;
            else if (msgNode == null) {
                msgNode = createNode(msg.getColor());
                msgNode.setGraphItemID(String.valueOf(msg.hashCode()));
                GraphItem finalMsgNode = msgNode;
                Platform.runLater(() -> { graphItem.addChild(finalMsgNode); });
                this.msgIds.add(msg.hashCode());
            }
            GraphNode msgGraphNode = (GraphNode) msgNode;
            double distance = msg.getRemainingDistance();

            if (msg.getDirection())
                distance = this.connection.getLength() - distance;
            distance /= this.connection.getLength();

            int x = (int) ((this.graphEdge.getLine().getEndX() * distance) + ((1 - distance) * this.graphEdge.getLine().getStartX()));// - MSG_D2;
            int y = (int) ((this.graphEdge.getLine().getEndY() * distance) + ((1 - distance) * this.graphEdge.getLine().getStartY()));// - MSG_D2;

            msgGraphNode.setX(x);
            msgGraphNode.setY(y);
        }
    }

    public GraphNode createNode(Color color) {
        GraphNode graphNode = (GraphNode)GraphItemManager.getInstance().get(GraphNode.class);

        if (graphNode == null) {
            graphNode = (GraphNode)GraphItemManager.getInstance().create(GraphNode.class);;
            Rectangle rectangle = createRectangle(0, 0);
            rectangle.setFill(color);
            Text text = new Text();
            graphNode.addItem(rectangle, text);
        }
        else {
            modifyRectangle(0, 0, graphNode.getRectangle());
            graphNode.getRectangle().setFill(color);
            graphNode.getText().setText("");
        }
        return graphNode;
    }

    private Rectangle createRectangle(double x, double y) {
        Rectangle rectangle = new Rectangle();
        return modifyRectangle(x, y, rectangle);
    }

    private Rectangle modifyRectangle(double x, double y, Rectangle rectangle) {
        rectangle.setWidth(5);
        rectangle.setHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.BLACK);
        return rectangle;
    }

//  /**
//   * Prints this component.
//   *
//   * @param g
//   *          the graphics context to use for printing
//   * @see #paint(Graphics)
//   */
//  @Override
//  public void print(Graphics g) {
//    this.doPaint(g, true);
//  }

//  @Override
//  protected void processMouseEvent(MouseEvent e) {
//    super.processMouseEvent(e);
//  }
}
