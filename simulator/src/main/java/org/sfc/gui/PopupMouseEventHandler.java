/*
 * Copyright (c) 2006 Thomas Weise
 * Software Foundation Classes
 * http://sourceforge.net/projects/java-sfc
 * 
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-02-23
 * Creator          : Thomas Weise
 * Original Filename: org.sfc.gui.PopupMouseListener.java
 * Last modification: 2007-02-23
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

package org.sfc.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

/**
 * This class is a mouse listener that can be used to trigger popup menus.
 * 
 * @author Thomas Weise
 */
public class PopupMouseEventHandler implements EventHandler, Serializable {

  private static final long serialVersionUID = 1L;

  private final ContextMenu contextMenu;

  public PopupMouseEventHandler(final ContextMenu contextMenu) {
    super();
    this.contextMenu = contextMenu;
  }

  @Override
  public void handle(Event event) {
      processMouseEvent((ContextMenuEvent)event);
  }

  protected boolean shouldPopup(final ContextMenuEvent e) {
    return !e.isConsumed();
  }

  protected void popup(final ContextMenuEvent e) {
    this.contextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY());
  }

  protected void processMouseEvent(final ContextMenuEvent e) {
    if (this.shouldPopup(e))
      this.popup(e);
  }

  public void mouseClicked(ContextMenuEvent e) {
    this.processMouseEvent(e);
  }

  public void mousePressed(ContextMenuEvent e) {
    this.processMouseEvent(e);
  }

  public void mouseReleased(ContextMenuEvent e) {
    this.processMouseEvent(e);
  }

  public void mouseEntered(ContextMenuEvent e) {
    this.processMouseEvent(e);
  }

  public void mouseExited(ContextMenuEvent e) {
    this.processMouseEvent(e);
  }
}
