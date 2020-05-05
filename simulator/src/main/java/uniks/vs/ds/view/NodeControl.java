/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.view.NodeControl.java
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

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import uniks.vs.ds.model.SimNode;

import java.awt.font.FontRenderContext;


/**
 * This control represents a node
 *
 * @author Thomas Weise
 */
final class NodeControl extends SimControl {
    /**
     * the serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * the default font.
     */
    private static final Font[] F2 = new Font[]{
            Font.font("Arial--15"), //$NON-NLS-1$
            Font.font("Arial--14"), //$NON-NLS-1$
            Font.font("Arial--13"), //$NON-NLS-1$
            Font.font("Arial--12"), //$NON-NLS-1$
            Font.font("Arial--11"), //$NON-NLS-1$
            Font.font("Arial--10"), //$NON-NLS-1$
            Font.font("Arial--9"), //$NON-NLS-1$
            Font.font("Arial--8"), //$NON-NLS-1$
    };

    /**
     * the node this control is assigned to
     */
    final SimNode simNode;

    /**
     * the label
     */
    final Label label;

    /**
     * is this node selected?
     */
    private boolean selected;

    /**
     * Create a new node control
     *
     * @param simNode the node this control is assigned to
     */
    public NodeControl(final SimNode simNode) {
        super();

        String string;
        Label label;

        this.setToolTipText("unassigned"); //$NON-NLS-1$
        this.simNode = simNode;

        this.add(new NodeCircle());
        this.add(this.label = label = new Label(string = simNode.getText()));
        label.setScaleX(100);
        label.setScaleY(100);
        label.setVisible((string.length() > 0));
        label.setAlignment(Pos.CENTER);
//        label.setAlignmentX(0.5f);
//        label.setAlignmentY(0.5f);
        label.setTextFill(Color.BLACK);
//        Foreground(Color.BLACK);
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.onAfterStep();
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, NodeControlPopup.ML);

    }

    private void add(Object o) {

    }

    /**
     * Returns the tooltip string that has been set with
     * <code>setToolTipText</code>.
     *
     * @return the text of the tool tip
     */
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

    /**
     * Obtain the node that this control belongs to
     *
     * @return the node that this control belongs to
     */
    public SimNode getSimNode() {
        return this.simNode;
    }

    /**
     * select this node
     */
    public void select() {
        VisualizationControl v;

        if (this.selected) {
            this.deselect();
            return;
        }

        v = this.getVC();
        if (v == null)
            return;

        if (v.nodeControl != null) {
            this.simNode.getSimulation()
                    .connectNodes(this.simNode, v.nodeControl.simNode);
            v.nodeControl.deselect();
            return;
        }

        v.nodeControl = this;

        this.selected = true;
        //this.revalidate();
        //v.repaint();
    }

    /**
     * deselect this node
     */
    public void deselect() {
        VisualizationControl v;
        if (this.selected) {
            v = this.getVC();
            if (v.nodeControl == this)
                v.nodeControl = null;
            this.selected = false;
            //this.revalidate();
            //v.repaint();
        }
    }

    /**
     * Obtain the visualization control
     *
     * @return the visualization control
     */
    private final VisualizationControl getVC() {
        Node c;
        for (c = this.getParent(); c != null; c = c.getParent()) {
            if (c instanceof VisualizationControl)
                return ((VisualizationControl) c);
        }
        return null;
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
        String s;
        int i;

        s = this.simNode.getText();

        if (((i = s.length()) > 0)) {
            this.label.setText(s);
            i /= 4;
            this.label.setFont((i < F2.length) ? F2[i] : F2[F2.length - 1]);
            this.label.setVisible(true);
        } else
            this.label.setVisible(false);

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
            this.id = String.valueOf(NodeControl.this.simNode.getID());

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
