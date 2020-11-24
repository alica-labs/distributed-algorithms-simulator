package org.sfc.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.io.Serializable;

public class MouseEventHandler implements EventHandler, Serializable {

  @Override
  public void handle(Event event) {
    System.out.println("MEH: " + event.getEventType());

    if (event instanceof MouseEvent)
      processMouseEvent((MouseEvent)event);
  }

  protected void processMouseEvent(final MouseEvent e) {

    if (MouseEvent.MOUSE_CLICKED.equals(e.getEventType()))
      mouseClicked(e);
    if (MouseEvent.MOUSE_PRESSED.equals(e.getEventType()))
      mousePressed(e);
    if (MouseEvent.MOUSE_RELEASED.equals(e.getEventType()))
      mouseReleased(e);
    if (MouseEvent.MOUSE_ENTERED.equals(e.getEventType()))
      mouseEntered(e);
    if (MouseEvent.MOUSE_EXITED.equals(e.getEventType()))
      mouseExited(e);
  }

  public void mouseClicked(MouseEvent e) {
    System.out.println("mouseClicked not implemented");
  }

  public void mousePressed(MouseEvent e) {
    System.out.println("mousePressed not implemented");
  }

  public void mouseReleased(MouseEvent e) {
    System.out.println("mouseReleased not implemented");
  }

  public void mouseEntered(MouseEvent e) {
    System.out.println("mouseEntered not implemented");
  }

  public void mouseExited(MouseEvent e) {
    System.out.println("mouseExited not implemented");
  }
}
