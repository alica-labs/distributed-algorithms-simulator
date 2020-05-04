/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.PropertyWindow.java
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

import java.util.List;

import javafx.scene.Group;
import javafx.scene.control.TableView;

import javafx.stage.Stage;
import org.sfc.collections.CollectionUtils;
import org.sfc.gui.windows.SfcFrame;

import uniks.vs.ds.model.Connection;
import uniks.vs.ds.model.ISimulationListener;
import uniks.vs.ds.model.ModelBase;
import uniks.vs.ds.model.SimNode;
import uniks.vs.ds.model.Simulation;

/**
 * This window is used to show the properties of a model item
 *
 * @author Thomas Weise
 */
public class PropertyWindow extends SfcFrame implements
    ISimulationListener {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the simulation
   */
  private final ModelBase m_item;

  /**
   * the table model
   */
  private final TableView.TableViewSelectionModel m_model;

  /**
   * the simulation
   */
  private final Simulation m_s;

  /**
   * the property list
   */
  private static final List<PropertyWindow> LST = CollectionUtils
      .createList();

  /**
   * show the properties window for the specified model item
   *
   * @param item
   *          the model item
   */
  static final void showProperties(final ModelBase item) {
    int i;
    PropertyWindow propertyWindow;
    synchronized (LST) {
      for (i = (LST.size() - 1); i >= 0; i--) {
        propertyWindow = null;
        if (i < LST.size()) {
          try {
            propertyWindow = LST.get(i);

            if (propertyWindow.m_item == item) {
              propertyWindow.toFront();
              return;
            }
          } catch (Throwable t) {
            //
          }
        }
      }
      new PropertyWindow(item, null, null);
    }
  }

  /**
   * Create a new simulation window
   *
   * @param item
   *          the model item
   */
  private PropertyWindow(final ModelBase item, Stage stage, Group root) {
      super(stage, root);

    synchronized (LST) {
      LST.add(this);
    }

//    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

//    this.m_model = new TM();
    this.m_item = item;

    TableView t;

    t = new TableView();
    this.m_model = t.getSelectionModel();
//    t.setAutoResizeMode(Table.AUTO_RESIZE_ALL_COLUMNS);
//    t.setAutoscrolls(true);
//    t.setShowHorizontalLines(false);
//    t.setCellSelectionEnabled(true);

//    this.setContentPane(new ScrollPane(t));

    this.m_s = item.getSimulation();
    if (this.m_s != null)
      this.m_s.addListener(this);

    this.onAfterStep();
    this.pack();
    this.setVisible(true);

  }

  /**
   * A node was added
   *
   * @param n
   *          the node
   */
  public void onNodeAdded(final SimNode n) {
    this.onAfterStep();
  }

  /**
   * A node was removed
   *
   * @param n
   *          the removed node
   */
  public void onNodeRemoved(final SimNode n) {
    if (n == this.m_item)
      this.close();
    else
      this.onAfterStep();
  }

  /**
   * Two nodes were connected by a new connection
   *
   * @param newCon
   *          the new connection
   */
  public void onNodesConnected(final Connection newCon) {
    this.onAfterStep();
  }

  /**
   * Two nodes were discconnected
   *
   * @param disCon
   *          the removed connection
   */
  public void onNodesDisconnected(final Connection disCon) {
    if (disCon == this.m_item)
      this.close();
    else
      this.onAfterStep();
  }

  /**
   * Releases all of the native screen resources used by this
   * <code>Window</code>, its subcomponents, and all of its owned
   * children.
   */
  @Override
  public void dispose() {

    synchronized (LST) {
      LST.remove(this);
    }
    if (this.m_s != null)
      this.m_s.removeListener(this);
    super.dispose();
  }

  /**
   * this method is called after a simulation step has been performed
   */
  @SuppressWarnings("unchecked")
  public void onAfterStep() {
    TableView.TableViewSelectionModel m;
    this.setTitle("properties of " + this.m_item.toString()); //$NON-NLS-1$
    m = this.m_model;
//    m.clear();
//    this.m_item.writeProperties(m);
//    m.fireTableDataChanged();
  }

  /**
   * close a specific property window
   *
   * @param b
   *          the window modelbase
   */
  static final void closeSpecificWindow(final ModelBase b) {
    int i;
    PropertyWindow w;
    synchronized (LST) {
      for (i = (LST.size() - 1); i >= 0; i--) {
        w = null;
        if (i < LST.size()) {
          try {
            w = LST.get(i);

            if (w.m_item == b) {
              w.closeByUser();
              LST.remove(w);
              return;
            }
          } catch (Throwable t) {
            //
          }
        }
      }
    }
  }

  /**
   * close all property windows
   */
  static final void closeAllPropertyWindows() {
    int i;
    PropertyWindow w;
    synchronized (LST) {
      for (i = (LST.size() - 1); i >= 0; i--) {
        w = null;
        if (i < LST.size()) {
          try {
            w = LST.get(i);
            w.closeByUser();
          } catch (Throwable t) {
            //
          } finally {
            LST.remove(w);
          }
        }
      }
    }
  }

  /**
   * This method is invoked if a string has been written to the log by a
   * source.
   *
   * @param logged
   *          the string logged
   * @param source
   *          the object that logged the string
   */
  public void onLog(final String logged, final ModelBase source) {
    //
  }

  /**
   * the internal table model
   *
   * @author Thomas Weise
   */
//  private static final class TM extends TableViewArrayListSelectionModel implements IPropertyDest {
//    /**
//     * the serial version uid
//     */
//    private static final long serialVersionUID = 1L;
//
//    /** the property column */
//    private static final String C1 = "property"; //$NON-NLS-1$
//
//    /** the value column */
//    private static final String C2 = "value"; //$NON-NLS-1$
//
//    /**
//     * the first strings
//     */
//    private final List<String> m_s1;
//
//    /**
//     * the second strings
//     */
//    private final List<String> m_s2;
//
//    /**
//     * create the table model
//     */
//    TM() {
//      super();
//      this.m_s1 = CollectionUtils.createList();
//      this.m_s2 = CollectionUtils.createList();
//    }
//
//    /**
//     * Write a property in the form of a name-value pair
//     *
//     * @param name
//     *          the property name
//     * @param value
//     *          the property value
//     */
//    public synchronized void writeProperty(final String name,
//        final String value) {
//      this.m_s1.add(name);
//      this.m_s2.add(value);
//    }
//
//    /**
//     * clear the table
//     */
//    public synchronized void clear() {
//      this.m_s1.clear();
//      this.m_s2.clear();
//    }
//
//    /**
//     * Returns a column name.
//     *
//     * @param column
//     *          the column being queried
//     * @return a string containing the default name of <code>column</code>
//     */
//    @Override
//    public String getColumnName(final int column) {
//      return ((column <= 0) ? C1 : C2);
//    }
//
//    /**
//     * Returns a column given its name.
//     *
//     * @param columnName
//     *          string containing name of column to be located
//     * @return the column with <code>columnName</code>, or -1 if not
//     *         found
//     */
//    @Override
//    public int findColumn(final String columnName) {
//      return (C1.equals(columnName) ? 0 : 1);
//    }
//
//    /**
//     * Returns the value for the cell at <code>columnIndex</code> and
//     * <code>rowIndex</code>.
//     *
//     * @param rowIndex
//     *          the row whose value is to be queried
//     * @param columnIndex
//     *          the column whose value is to be queried
//     * @return the value Object at the specified cell
//     */
//    public synchronized Object getValueAt(final int rowIndex,
//        final int columnIndex) {
//      return ((columnIndex <= 0) ? this.m_s1.get(rowIndex) : this.m_s2
//          .get(rowIndex));
//    }
//
//    /**
//     * Returns the number of rows in the model.
//     *
//     * @return the number of rows in the model
//     */
//    public synchronized int getRowCount() {
//      return this.m_s1.size();
//    }
//
//    /**
//     * Returns the number of columns in the model.
//     *
//     * @return the number of columns in the model
//     */
//    public int getColumnCount() {
//      return 2;
//    }
//  }
}
