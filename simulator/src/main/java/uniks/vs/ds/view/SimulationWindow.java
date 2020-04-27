/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.view.SimulationWindow.java
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
import java.awt.Container;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sfc.gui.resources.icons.Icons;
import org.sfc.gui.windows.SfcFrame;
import org.sfc.parallel.simulation.SimulationThread;

import uniks.vs.ds.model.Simulation;

/**
 * The simulation window frame
 *
 * @author Thomas Weise
 */
public class SimulationWindow extends SfcFrame {
  /**
   * the serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * the simulation
   */
  Simulation m_sim;

  /**
   * the simulation thread
   */
  SimulationThread m_t;

  /**
   * the constraints
   */
  private final GridBagConstraints m_c;

  /**
   * the split panel
   */
  private JSplitPane m_p;

  /**
   * the slider
   */
  private final JSlider m_speed;

  /**
   * the visualization control
   */
  VisualizationControl m_vc;

  /**
   * Create a new simulation window
   */
  public SimulationWindow() {
    super();

    JPanel c;
    GridBagLayout gbl;
    Insets in;
    JSlider sl;

    this.setTitle("Distributed Algorithms Simulation"); //$NON-NLS-1$
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    // this.setIcon(Icons.GRAPH.getImage());

    this.buildMenu();

    c = new JPanel();
    gbl = new GridBagLayout();

    c.setLayout(gbl);

    this.m_speed = sl = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    sl.setPaintLabels(false);
    sl.setPaintTicks(false);
    sl.setPaintTrack(true);
    sl.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        Object o;

        o = e.getSource();
        if (o instanceof JSlider) {
          synchronized (SimulationWindow.this) {
            if (SimulationWindow.this.m_t != null)
              SimulationWindow.this.m_t.setSpeed(((JSlider) o).getValue());
          }
        }
      }
    });

    in = new Insets(3, 3, 3, 3);

    gbl.setConstraints(sl, new GridBagConstraints(0, 0, 1, 1, 1, 0,
        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, in,
        1, 1));

    c.add(sl);

    this.setContentPane(c);

    this.m_c = new GridBagConstraints(0, 1, 1, 1, 1, 1,
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, in, 1, 1);

    this.setSize(700, 500);

    this.init(new Simulation());
    this.setVisible(true);
  }

  /**
   * build the menu
   */
  private final void buildMenu() {
    JMenuBar jmb;
    JMenu jm;
    JMenuItem jmi;

    // menu
    jmb = new JMenuBar();
    jm = new JMenu("simulation"); //$NON-NLS-1$
    jm.setIcon(Icons.GRAPH);

    jmi = new JMenuItem("new"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        PropertyWindow.closeAllPropertyWindows();
        SimulationWindow.this.init(new Simulation());
        System.gc();
        System.gc();
      }
    });
    jmi.setIcon(Icons.NEW);
    jm.add(jmi);

    jmi = new JMenuItem("store"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        synchronized (SimulationWindow.this) {
          if (SimulationWindow.this.m_sim != null)
            SimulationIO.storeSimulation(SimulationWindow.this.m_sim);
        }
      }
    });
    jmi.setIcon(Icons.SAVE);
    jm.add(jmi);

    jmi = new JMenuItem("load"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        Simulation s;

        s = SimulationIO.loadSimulation();
        if (s != null)
          synchronized (SimulationWindow.this) {
            SimulationWindow.this.init(s);
          }
      }
    });
    jmi.setIcon(Icons.OPEN);
    jm.add(jmi);

    jmi = new JMenuItem("print"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        VisualizationControl vc;
        synchronized (SimulationWindow.this) {
          vc = SimulationWindow.this.m_vc;
          if (vc != null)
            SimulationIO.printSimulation(vc);
        }
      }

    });
    jmi.setIcon(Icons.PRINT);
    jm.add(jmi);

    jmi = new JMenuItem("reset"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        SimulationWindow.this.reset();
        System.gc();
        System.gc();
      }
    });
    jmi.setIcon(Icons.RESET);
    jm.add(jmi);

    jm.addSeparator();

    jmi = new JMenuItem("exit"); //$NON-NLS-1$
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        SimulationWindow.this.close();
        System.gc();
        System.exit(0);
      }
    });
    jmi.setIcon(Icons.EXIT);
    jm.add(jmi);

    jmb.add(jm);
    this.setJMenuBar(jmb);
  }

  /**
   * reset this window
   */
  final void reset() {
    if (this.m_sim != null) {
      this.m_sim.reset();
      SpeedDialog.resetAll();
    }
  }

  /**
   * initialize this window
   *
   * @param s
   *          the simulation used for initialization
   */
  final void init(final Simulation s) {
    JSplitPane c;
    Container cc;

    cc = this.getContentPane();

    if (this.m_t != null) {
      this.m_t.abort();
      this.m_t = null;
    }
    if (this.m_p != null) {
      cc.remove(this.m_p);
      this.m_p = null;
    }
    if (this.m_sim != null) {
      this.m_sim = null;
    }
    if (this.m_vc != null) {
      this.m_vc = null;
    }

    this.m_sim = s;

    JScrollPane pane = new JScrollPane(this.m_vc = new VisualizationControl(this.m_sim));
    Component component = pane.getComponent(0);
    component.setBackground(new Color(230, 230, 230));
  
    this.m_p = c = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
            pane,
        new JScrollPane(new LogPanel(this.m_sim)));

//    this.m_p = c = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
//        new JScrollPane(this.m_vc = new VisualizationControl(this.m_sim)),
//        new JScrollPane(new LogPanel(this.m_sim)));

    c.setOneTouchExpandable(true);
    c.setDividerLocation((this.getWidth() * 3) >>> 2);
    c.setResizeWeight(1.0d);
    ((GridBagLayout) (cc.getLayout())).setConstraints(c, this.m_c);
    cc.add(c);

    this.validate();
    this.repaint();

    this.m_t = new SimulationThread(this.m_sim);
    this.m_speed.setValue(0);
    this.m_t.start();
    SpeedDialog.closeAll();
  }

}
