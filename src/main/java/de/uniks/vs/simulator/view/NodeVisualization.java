package de.uniks.vs.simulator.view;

import de.uniks.vs.simulator.model.Node;
import de.uniks.vs.ui.GraphItem;
import de.uniks.vs.ui.GraphNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.sfc.gui.MouseEventHandler;

import java.awt.font.FontRenderContext;

final class NodeVisualization extends Visualization {

    private static final Font[] F2 = new Font[]{
            Font.font("Arial--15"),
            Font.font("Arial--14"),
            Font.font("Arial--13"),
            Font.font("Arial--12"),
            Font.font("Arial--11"),
            Font.font("Arial--10"),
            Font.font("Arial--9"),
            Font.font("Arial--8"),
    };

    static final EventHandler ML = new MouseEventHandler() {

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("NV: mouseReleased");

            if (e.getButton() == MouseButton.PRIMARY) {
                System.out.println("NV: Second Button");
                Object source = e.getSource();

                if (source instanceof GraphNode) {
//                    ((GraphNode) source).select();
                }
            }
        }
    };

    final Node simNode;
    private final GraphNode graphNode;
    private final Text text;
    private boolean selected;

    public NodeVisualization(final Node simNode) {
        super();

        this.setToolTipText("unassigned");
        this.simNode = simNode;
        this.graphNode = createNode();
        text = graphNode.getText();
        text.setScaleX(0.7);
        text.setScaleY(0.7);
        text.setVisible(text.getText().length() > 0);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.BLACK);
        text.setText(this.simNode.getText());
        graphNode.setX(simNode.getX());
        graphNode.setY(simNode.getY());
        this.add(graphNode);
        this.onAfterStep();
//        graphNode.addEventHandler(MouseEvent.ANY, NodeVisualization.ML);
        graphNode.addEventHandler(MouseEvent.ANY, NodeVisualizationPopup.ML);
    }

    @Override
    public int getX() {
        return this.simNode.getX();
    }

    @Override
    public int getY() {
        return this.simNode.getY();
    }

    public GraphNode createNode() {
        Rectangle node = createRectangle(-1, -1, "standard");
        Text text = new Text();
        GraphNode graphNode = new GraphNode();
        graphNode.addItem(node, text);
        return graphNode;
    }

    private Rectangle createRectangle(double x, double y, String type) {
        final double DEFAULT_WIDTH 	= 16;
        final double DEFAULT_HEIGHT	= 16;
        final String STANDARD 	= "standard";
        final String ROUND 		= "round";

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(DEFAULT_WIDTH);
        rectangle.setHeight(DEFAULT_HEIGHT);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.WHITE);

        if (STANDARD.equals(type)) {
            rectangle.setArcWidth(20);
            rectangle.setArcHeight(20);
        }
        else if (ROUND.equals(type)) {
            rectangle.setWidth(30);
            rectangle.setHeight(20);
            rectangle.setArcWidth(30);
            rectangle.setArcHeight(20);
        }
        return rectangle;
    }

    private void add(GraphItem item) {

        item.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

                if (MouseEvent.MOUSE_ENTERED.equals(event.getEventType())) {

                    if (((MouseEvent)event).getButton() == MouseButton.PRIMARY) {
                        System.out.println("NV: MouseButton.PRIMARY");
                        select();
                    }
                    else if (((MouseEvent)event).getButton() == MouseButton.SECONDARY) {
                        System.out.println("NV: MouseButton.SECONDARY");
                    }
                }
            }
        });

        item.addEventHandler(EventType.ROOT, NodeVisualizationPopup.ML);
    }

//  @Override
    public String getToolTipText() {
        StringBuilder sb;
        String s, t;

        if (this.simNode == null)
            return super.getToolTipText();
        s = this.simNode.getText();
        t = this.simNode.getClass().getSimpleName();
        if (s != null) {
            sb = new StringBuilder(t);
            sb.append(' ');
            sb.append(s);
            return sb.toString();
        }

        return t;
    }

    public Node getSimNode() {
        return this.simNode;
    }

    public void select() {
        SimVisualization v;

        if (this.selected) {
            System.out.println("NV: deselected");
            this.deselect();
            return;
        }
        System.out.println("NV: selected");

        v = this.getSimVisualization();
        if (v == null)
            return;

        if (v.nodeVisualization != null) {
            System.out.println("NV: connectNodes");

            this.simNode.getSimulation()
                    .connectNodes(this.simNode, v.nodeVisualization.simNode);
            v.nodeVisualization.deselect();
            return;
        }

        v.nodeVisualization = this;

        this.selected = true;
        //this.revalidate();
        //v.repaint();
    }

    public void deselect() {
        SimVisualization v;
        if (this.selected) {
            v = this.getSimVisualization();
            if (v.nodeVisualization == this)
                v.nodeVisualization = null;
            this.selected = false;
            //this.revalidate();
            //v.repaint();
        }
    }

    private final SimVisualization getSimVisualization() {
        javafx.scene.Node c;
        for (c = this.getParent(); c != null; c = c.getParent()) {
            if (c instanceof SimVisualization)
                return ((SimVisualization) c);
        }
        return null;
    }

    public GraphNode getGraphNode() {
        return graphNode;
    }

    /**
     * Paints this node control
     *
//     * @param g the graphics context to paint on
     * @return
     */
//  @Override
//    public void paint(final Graphics g) {
//        Color c;
//        int w, h;
//
//        if (this.selected) {
//            c = g.getColor();
//            g.setColor(Color.BLUE);
//            w = this.getWidth();
//            h = this.getHeight();
//            g.fillRect(0, 0, w, h);
//            g.setColor(c);
//        }
//
//        super.paint(g);
//    }
//    public double getHeight() {
//        return 250;
//    }
//
//    public double getWidth() {
//        return 400;
//    }

    /**
     * the text has changed
     */
    @Override
    protected void onAfterStep() {
//        System.out.println("NV: " + this.getSimNode().getID());
        Text text = this.getGraphNode().getText();
//        System.out.println("NV: " + text.getText());
        String simNodeText = this.simNode.getText();
        Color color = this.simNode.getColor();
//        System.out.println("NV: " + simNodeText);
        this.getGraphNode().setColor(color);
        int i;

        if (((i = simNodeText.length()) > 0)) {

            text.setText(simNodeText);
            i /= 4;
            text.setFont((i < F2.length) ? F2[i] : F2[F2.length - 1]);
            text.setVisible(true);
        } else
            text.setVisible(false);

//        this.setSize(this.getPreferredSize());

//        this.setLocation(this.simNode.getX() - (this.getWidth() >>> 1),
//                this.simNode.getY() - (this.getHeight() >>> 1));
        this.setTranslateX(this.simNode.getX() - (((int)this.getWidth()) >>> 1));
        this.setTranslateY(this.simNode.getY() - (((int)this.getHeight()) >>> 1));

        super.onAfterStep();
    }

    /**
     * the default font.
     */
    static final Font FONT = Font.font("Arial-BOLD-18"); //$NON-NLS-1$

    /**
     * the default font render context.
     */
    static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    /**
     * the default diameter
     */
    static final int DIAMETER = 30;

    // /**
    // * the size of the node circle
    // */
    // static final Dimension SZE = new Dimension(D, D);

    /**
     * the node circle
     *
     * @author Thomas Weise
     */
    private final class NodeCircle extends Region {
        /**
         * the serial version uid
         */
        private static final long serialVersionUID = 1L;
        private final Circle circle;

        /**
         * the id of this node
         */
        private String id;

        /**
         * the x-coordinate of the id string
         */
        private int x;

        /**
         * the y-coordinate of the id string
         */
        private int y;
        private Font font;
        private Color color;

        /**
         * Create a new circle.
         */
        NodeCircle() {
            super();

            Rectangle2D rectangle2D;
            this.id = String.valueOf(NodeVisualization.this.simNode.getID());

            this.circle = new Circle(10, 30, 30, Color.BLACK);
            this.getChildren().add(this.circle);
//            rectangle2D = FONT.getStringBounds(this.id, FONT_RENDER_CONTEXT);
//            this.x = ((int) ((0.5d * (DIAMETER - rectangle2D.getWidth())) + 0.5));
//            this.y = ((int) ((DIAMETER - (0.5d * rectangle2D.getHeight())) + 0.5));

            this.setFont(FONT);
            this.setForeground(Color.BLACK);
            this.setBounds(0, 0, DIAMETER, DIAMETER);

        }

        private void setBounds(int x, int y, int width, int height) {

        }

        private void setForeground(Color color) {
            this.color = color;
        }

        private void setFont(Font font) {
            this.font = font;
        }



        /**
         * Paints this node control
         *
         * @param g the graphics context to paint on
         */
//        @Override
//        public void paint(final Graphics g) {
//            this.doPaint(g, false);
//        }

        /**
         * Prints this component.
         *
         * @param g the graphics context to use for printing
         * @see #paint(Graphics)
         */
//        @Override
//        public void print(Graphics g) {
//            this.doPaint(g, true);
//        }

        /**
         * Paints this node control
         *
         * @param g     the graphics context to paint on
         * @param print <code>true</code> if printing, <code>false</code>
         *              otherwise
         */
//        private final void doPaint(final Graphics g, final boolean print) {
//            Font f, of;
//            String id;
//            Color oc;
//
//            f = this.getFont();
//            of = g.getFont();
//            g.setFont(f);
//            oc = g.getColor();
//
//            g.setColor(this.getBackground());
//            g.fillOval(0, 0, D - 1, D - 1);
//            if (print) {
//                g.setColor(Color.BLACK);
//                g.drawOval(0, 0, D - 1, D - 1);
//            }
//
//            id = this.m_id;
//            g.setColor(this.getForeground());
//
//            g.drawString(id, this.m_idX, this.m_idY);
//
//            g.setColor(oc);
//            g.setFont(of);
//
//        }

        /**
         * Gets the preferred size of this component.
         *
         * @return a dimension object indicating this component's preferred
         * size
         */
//        @Override
//        public Dimension getPreferredSize() {
//            return new Dimension(D, D);
//        }

        /**
         * Gets the maximum size of this component.
         *
         * @return a dimension object indicating this component's maximum size
         */
//        @Override
//        public Dimension getMaximumSize() {
//            return this.getPreferredSize();
//        }

        /**
         * Gets the minimum size of this component.
         *
         * @return a dimension object indicating this component's minimum size
         */
//        @Override
//        public Dimension getMinimumSize() {
//            return this.getPreferredSize();
//        }

        /**
         * Gets the background color of this component.
         *
         * @return this component's background color; if this component does
         * not have a background color, the background color of its
         * parent is returned
         */
//        @Override
//        public Color getBackground() {
//            return NodeControl.this.simNode.getColor();
//        }

        /**
         * Returns the current width of this component. This method is
         * preferable to writing <code>component.getBounds().width</code>,
         * or <code>component.getSize().width</code> because it doesn't cause
         * any heap allocations.
         *
         * @return the current width of this component
         */
//        @Override
//        public int getWidth() {
//            return DIAMETER;
//        }

        /**
         * Returns the current height of this component. This method is
         * preferable to writing <code>component.getBounds().height</code.,
         * or <code>component.getSize().height</code> because it
         * doesn't cause any heap allocations.
         *
         * @return the current height of this component
         */
//        @Override
//        public int getHeight() {
//            return DIAMETER;
//        }
    }
}
