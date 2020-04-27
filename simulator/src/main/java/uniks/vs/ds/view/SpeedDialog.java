/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.SpeedDialog.java
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

import java.awt.Frame;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sfc.collections.CollectionUtils;
import org.sfc.gui.windows.SfcDialog;

import uniks.vs.ds.model.Connection;

/**
 * the set speed dialog for connections
 *
 * @author Thomas Weise
 */
final class SpeedDialog extends SfcDialog {
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1;

  /**
   * the connection
   */
  final Connection m_con;

  /**
   * the slider
   */
  private final JSlider m_sl;

  /**
   * the list of dialogs
   */
  private static final List<SpeedDialog> DIALOGS = CollectionUtils
      .createList();

  /**
   * show a new speed dialog
   *
   * @param owner
   *          The owning frame of the dialog.
   * @param con
   *          the connection
   */
  static void showDialog(final Frame owner, final Connection con) {
    int i;
    synchronized (DIALOGS) {
      for (i = (DIALOGS.size() - 1); i >= 0; i--) {
        if (DIALOGS.get(i).m_con == con) {
          DIALOGS.get(i).toFront();
          return;
        }
      }
      DIALOGS.add(new SpeedDialog(owner, con));
    }
  }

  /**
   * close all dialogs
   */
  static void closeAll() {
    int i;
    synchronized (DIALOGS) {
      for (i = (DIALOGS.size() - 1); i >= 0; i--) {
        DIALOGS.remove(i).closeByUser();
      }
    }
  }

  /**
   * reset all dialogs
   */
  static void resetAll() {
    int i;
    synchronized (DIALOGS) {
      for (i = (DIALOGS.size() - 1); i >= 0; i--) {
        DIALOGS.get(i).reset();
      }
    }
  }

  /**
   * reset one specific dialog
   *
   * @param n
   *          the connection of concern
   */
  static void reset(final Connection n) {
    int i;
    SpeedDialog d;
    synchronized (DIALOGS) {
      for (i = (DIALOGS.size() - 1); i >= 0; i--) {
        d = DIALOGS.get(i);
        if (d.m_con == n)
          d.reset();
        return;
      }
    }
  }

  /**
   * close one specific dialog
   *
   * @param n
   *          the connection of concern
   */
  static void close(final Connection n) {
    int i;
    SpeedDialog d;
    synchronized (DIALOGS) {
      for (i = (DIALOGS.size() - 1); i >= 0; i--) {
        d = DIALOGS.get(i);
        if (d.m_con == n) {
          d.closeByUser();
          DIALOGS.remove(i);
          return;
        }
      }
    }
  }

  /**
   * Create a new <code>SpeedDialog</code>.
   *
   * @param owner
   *          The owning frame of the dialog.
   * @param con
   *          the connection
   */
  private SpeedDialog(final Frame owner, final Connection con) {
    super(owner);
    JSlider js;

    this.setModal(false);

    this.m_con = con;
    this.setTitle("speed: " + con.getNode1().getID() + //$NON-NLS-1$
        "<->" + con.getNode2().getID());//$NON-NLS-1$

    this.m_sl = js = new JSlider();
    js.setMinimum(0);
    js.setMaximum(50);
    js.setValue(this.m_con.getSpeed());

    js.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        Object o;
        int k;

        o = e.getSource();
        if (o instanceof JSlider) {
          k = ((JSlider) o).getValue();
          SpeedDialog.this.m_con.setSpeed((k >= 50) ? 10000000 : k);
        }
      }
    });

    this.setContentPane(js);
    this.pack();
    this.setVisible(true);
  }

  /**
   * dispose this dialog
   */
  @Override
  public void dispose() {
    synchronized (DIALOGS) {
      DIALOGS.remove(this);
    }
    super.dispose();
  }

  /**
   * reset this dialog
   */
  final void reset() {
    this.m_sl.setValue(Math.min(50, this.m_con.getSpeed()));
    this.invalidate();
    this.repaint();
  }
}
