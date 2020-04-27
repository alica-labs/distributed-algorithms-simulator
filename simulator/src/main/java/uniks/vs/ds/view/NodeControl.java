/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.NodeControl.java
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import uniks.vs.ds.model.Node;

/**
 * This control represents a node
 *
 * @author Thomas Weise
 */
final class NodeControl extends SimCtrlBase {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the default font.
   */
  private static final Font[] F2 = new Font[] {Font.decode("Arial--15"), //$NON-NLS-1$
      Font.decode("Arial--14"), //$NON-NLS-1$
      Font.decode("Arial--13"), //$NON-NLS-1$
      Font.decode("Arial--12"), //$NON-NLS-1$
      Font.decode("Arial--11"), //$NON-NLS-1$
      Font.decode("Arial--10"), //$NON-NLS-1$
      Font.decode("Arial--9"), //$NON-NLS-1$
      Font.decode("Arial--8"), //$NON-NLS-1$
  };

  /**
   * the node this control is assigned to
   */
  final Node m_node;

  /**
   * the label
   */
  final JLabel m_label;

  /**
   * is this node selected?
   */
  private boolean m_selected;

  /**
   * Create a new node control
   *
   * @param node
   *          the node this control is assigned to
   */
  public NodeControl(final Node node) {
    super();

    String s;
    JLabel j;

    this.setToolTipText("unassigned"); //$NON-NLS-1$
    this.m_node = node;

    this.add(new NodeCircle());
    this.add(this.m_label = j = new JLabel(s = node.getText()));
    j.setSize(100, 100);
    j.setVisible((s.length() > 0));
    j.setAlignmentX(0.5f);
    j.setAlignmentY(0.5f);
    j.setForeground(Color.BLACK);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.onAfterStep();
    this.addMouseListener(NodeControlPopup.ML);

  }

  /**
   * Returns the tooltip string that has been set with
   * <code>setToolTipText</code>.
   *
   * @return the text of the tool tip
   * @see #TOOL_TIP_TEXT_KEY
   */
  @Override
  public String getToolTipText() {
    StringBuilder sb;
    String s, t;

    if (this.m_node == null)
      return super.getToolTipText();
    s = this.m_node.getText();
    t = this.m_node.getClass().getSimpleName();
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
  public Node getNode() {
    return this.m_node;
  }

  /**
   * select this node
   */
  public void select() {
    VisualizationControl v;

    if (this.m_selected) {
      this.deselect();
      return;
    }

    v = this.getVC();
    if (v == null)
      return;

    if (v.m_sel != null) {
      this.m_node.getSimulation()
          .connectNodes(this.m_node, v.m_sel.m_node);
      v.m_sel.deselect();
      return;
    }

    v.m_sel = this;

    this.m_selected = true;
    this.revalidate();
    v.repaint();
  }

  /**
   * deselect this node
   */
  public void deselect() {
    VisualizationControl v;
    if (this.m_selected) {
      v = this.getVC();
      if (v.m_sel == this)
        v.m_sel = null;
      this.m_selected = false;
      this.revalidate();
      v.repaint();
    }
  }

  /**
   * Obtain the visualization control
   *
   * @return the visualization control
   */
  private final VisualizationControl getVC() {
    Component c;
    for (c = this.getParent(); c != null; c = c.getParent()) {
      if (c instanceof VisualizationControl)
        return ((VisualizationControl) c);
    }
    return null;
  }

  /**
   * Paints this node control
   *
   * @param g
   *          the graphics context to paint on
   */
  @Override
  public void paint(final Graphics g) {
    Color c;
    int w, h;

    if (this.m_selected) {
      c = g.getColor();
      g.setColor(Color.BLUE);
      w = this.getWidth();
      h = this.getHeight();
      g.fillRect(0, 0, w, h);
      g.setColor(c);
    }

    super.paint(g);
  }

  /**
   * the text has changed
   */
  @Override
  protected void onAfterStep() {
    String s;
    int i;

    s = this.m_node.getText();

    if (((i = s.length()) > 0)) {
      this.m_label.setText(s);
      i /= 4;
      this.m_label.setFont((i < F2.length) ? F2[i] : F2[F2.length - 1]);
      this.m_label.setVisible(true);
    } else
      this.m_label.setVisible(false);

    this.setSize(this.getPreferredSize());

    this.setLocation(this.m_node.getX() - (this.getWidth() >>> 1),
        this.m_node.getY() - (this.getHeight() >>> 1));

    super.onAfterStep();
  }

  /**
   * the default font.
   */
  static final Font F = Font.decode("Arial-BOLD-18"); //$NON-NLS-1$

  /**
   * the default font render context.
   */
  static final FontRenderContext FRC = new FontRenderContext(null, true,
      true);

  /**
   * the default diameter
   */
  static final int D = 30;

  // /**
  // * the size of the node circle
  // */
  // static final Dimension SZE = new Dimension(D, D);

  /**
   * the node circle
   *
   * @author Thomas Weise
   */
  private final class NodeCircle extends Component {
    /**
     * the serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * the id of this node
     */
    private final String m_id;

    /**
     * the x-coordinate of the id string
     */
    private final int m_idX;

    /**
     * the y-coordinate of the id string
     */
    private final int m_idY;

    /**
     * Create a new circle.
     */
    NodeCircle() {
      super();

      Rectangle2D r;
      this.m_id = String.valueOf(NodeControl.this.m_node.getID());

      r = F.getStringBounds(this.m_id, FRC);
      this.m_idX = ((int) ((0.5d * (D - r.getWidth())) + 0.5));
      this.m_idY = ((int) ((D - (0.5d * r.getHeight())) + 0.5));

      this.setFont(F);
      this.setForeground(Color.BLACK);
      this.setBounds(0, 0, D, D);
    }

    /**
     * Paints this node control
     *
     * @param g
     *          the graphics context to paint on
     */
    @Override
    public void paint(final Graphics g) {
      this.doPaint(g, false);
    }

    /**
     * Prints this component.
     *
     * @param g
     *          the graphics context to use for printing
     * @see #paint(Graphics)
     */
    @Override
    public void print(Graphics g) {
      this.doPaint(g, true);
    }

    /**
     * Paints this node control
     *
     * @param g
     *          the graphics context to paint on
     * @param print
     *          <code>true</code> if printing, <code>false</code>
     *          otherwise
     */
    private final void doPaint(final Graphics g, final boolean print) {
      Font f, of;
      String id;
      Color oc;

      f = this.getFont();
      of = g.getFont();
      g.setFont(f);
      oc = g.getColor();

      g.setColor(this.getBackground());
      g.fillOval(0, 0, D - 1, D - 1);
      if (print) {
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, D - 1, D - 1);
      }

      id = this.m_id;
      g.setColor(this.getForeground());

      g.drawString(id, this.m_idX, this.m_idY);

      g.setColor(oc);
      g.setFont(of);

    }

    /**
     * Gets the preferred size of this component.
     *
     * @return a dimension object indicating this component's preferred
     *         size
     */
    @Override
    public Dimension getPreferredSize() {
      return new Dimension(D, D);
    }

    /**
     * Gets the maximum size of this component.
     *
     * @return a dimension object indicating this component's maximum size
     */
    @Override
    public Dimension getMaximumSize() {
      return this.getPreferredSize();
    }

    /**
     * Gets the minimum size of this component.
     *
     * @return a dimension object indicating this component's minimum size
     */
    @Override
    public Dimension getMinimumSize() {
      return this.getPreferredSize();
    }

    /**
     * Gets the background color of this component.
     *
     * @return this component's background color; if this component does
     *         not have a background color, the background color of its
     *         parent is returned
     */
    @Override
    public Color getBackground() {
      return NodeControl.this.m_node.getColor();
    }

    /**
     * Returns the current width of this component. This method is
     * preferable to writing <code>component.getBounds().width</code>,
     * or <code>component.getSize().width</code> because it doesn't cause
     * any heap allocations.
     *
     * @return the current width of this component
     */
    @Override
    public int getWidth() {
      return D;
    }

    /**
     * Returns the current height of this component. This method is
     * preferable to writing <code>component.getBounds().height</code.,
     * or <code>component.getSize().height</code> because it
     * doesn't cause any heap allocations.
     *
     * @return the current height of this component
     */
    @Override
    public int getHeight() {
      return D;
    }
  }
}
