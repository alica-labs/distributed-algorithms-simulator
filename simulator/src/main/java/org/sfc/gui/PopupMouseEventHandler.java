package org.sfc.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class PopupMouseEventHandler implements EventHandler, Serializable {

  private final ContextMenu contextMenu;

  public PopupMouseEventHandler(final ContextMenu contextMenu) {
    super();
    this.contextMenu = contextMenu;
  }

  @Override
  public void handle(Event event) {
//    System.out.println("PMEH: " + event.getEventType());

    if (event instanceof ContextMenuEvent)
      processMouseEvent((ContextMenuEvent)event);
  }

  protected boolean shouldPopup(final ContextMenuEvent e) {
    return !e.isConsumed();
  }

  protected void popup(final ContextMenuEvent e) {
    this.contextMenu.show((Node) e.getSource(), e.getScreenX(), e.getScreenY());
  }

  protected void popup(MouseEvent e) {
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
