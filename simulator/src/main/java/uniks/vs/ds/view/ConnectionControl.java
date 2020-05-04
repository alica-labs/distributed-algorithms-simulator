/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.ConnectionControl.java
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
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import uniks.vs.ds.model.Connection;
import uniks.vs.ds.model.Message;
import uniks.vs.ds.model.Node;

/**
 * This control represents a connection
 *
 * @author Thomas Weise
 */
final class ConnectionControl extends SimCtrlBase {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the message diameter
   */
  private static final int MSG_D2 = 7;

  /**
   * the message diameter 2
   */
  private static final int MSG_D = (MSG_D2 << 1);

  /**
   * the message diameter 4
   */
  private static final int MSG_D4 = (MSG_D2 << 1);

  /**
   * the connection this control is assigned to
   */
  private Connection m_connection;

  /** the first x coordinate */
  int m_x1;

  /** the first y coordinate */
  int m_y1;

  /** the second x coordinate */
  int m_x2;

  /** the second y coordinate */
  int m_y2;

  /**
   * the r value
   */
  final double m_r;

  /**
   * the top x
   */
  final int m_tx;

  /**
   * the top y
   */
  final int m_ty;

  /**
   * the length
   */
  private final double m_len;

  /**
   * Create a new connection control
   *
   * @param connection
   *          the connection this control is assigned to
   */
  public ConnectionControl(final Connection connection) {
    super();
    Node n;
    int x1, y1, x2, y2, d;

    this.m_connection = connection;

    n = this.m_connection.getNode1();
    x1 = n.getX();
    y1 = n.getY();
    n = this.m_connection.getNode2();
    x2 = n.getX();
    y2 = n.getY();

    if (x1 < x2) {
      this.m_tx = x1;
      this.m_x1 = 0;
      this.m_x2 = x2 -= x1;
    } else {
      this.m_tx = x2;
      d = x1 - x2;
      x1 = x2;
      this.m_x1 = x2 = d;
      this.m_x2 = 0;
    }

    if (y1 < y2) {
      this.m_ty = y1;
      this.m_y2 = y2 -= y1;
      this.m_y1 = 0;
    } else {
      this.m_ty = y2;
      d = y1 - y2;
      this.m_y2 = 0;
      y1 = y2;
      this.m_y1 = y2 = d;
    }

    this.m_len = connection.getLength();

    this.m_r = // Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 -
    // y1));
    Math.sqrt(x2 * x2 + y1 * y1);

    if (x2 <= MSG_D4) {
      d = (MSG_D4 + 1 - x2);
      if ((d & 1) != 0)
        d++;
      x2 += d;
      d >>>= 1;
      if (x1 < d)
        d = x1;
      x1 -= d;
      this.m_x1 += d;
      this.m_x2 += d;
    }

    if (y2 <= MSG_D4) {
      d = (MSG_D4 + 1 - y2);
      if ((d & 1) != 0)
        d++;
      y2 += d;
      d >>>= 1;
      if (y1 < d)
        d = y1;
      y1 -= d;
      this.m_y1 += d;
      this.m_y2 += d;
    }

    this.setBounds(x1, y1, x2, y2);
    this.onAfterStep();
  }

  /**
   * Obtain the connection that this control belongs to
   *
   * @return the connection that this control belongs to
   */
  public Connection getConnection() {
    return this.m_connection;
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
   * Paints this node control
   *
   * @param g
   *          the graphics context to paint on
   * @param print
   *          <code>true</code> if printing, <code>false</code>
   *          otherwise
   */
  private final void doPaint(final Graphics g, final boolean print) {
    Color oc;
    Message msg;
    Connection c;
    int x, y;
    double d;

    oc = g.getColor();
    g.setColor(this.m_connection.getColor());

    g.drawLine(this.m_x1, this.m_y1, this.m_x2, this.m_y2);
    c = this.m_connection;
    synchronized (c) {
      for (msg = c.getFirstQueuedMessage(); msg != null; msg = c
          .getNextQeuedMessage(msg)) {
        d = msg.getRemainingDistance();
        g.setColor(msg.getColor());

        if (msg.getDirection())
          d = this.m_len - d;
        d /= this.m_len;

        x = (int) ((this.m_x2 * d) + ((1 - d) * this.m_x1)) - MSG_D2;
        y = (int) ((this.m_y2 * d) + ((1 - d) * this.m_y1)) - MSG_D2;

        g.fillOval(x, y, MSG_D - 1, MSG_D - 1);
        if (print) {
          g.setColor(Color.BLACK);
          g.drawOval(x, y, MSG_D - 1, MSG_D - 1);
        }
      }
    }

    g.setColor(oc);
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
   * Processes mouse events occurring on this component by dispatching them
   * to any registered <code>MouseListener</code> objects.
   *
   * @param e
   *          the mouse event
   */
  @Override
  protected void processMouseEvent(MouseEvent e) {
    super.processMouseEvent(e);
  }
}
