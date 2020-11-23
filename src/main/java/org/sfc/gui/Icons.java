/*
 * Copyright (c) 2006 Thomas Weise
 * Software Foundation Classes
 * http://sourceforge.net/projects/java-sfc
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-02-23
 * Creator          : Thomas Weise
 * Original Filename: /org/sfc/gui/resources/icons/Icons.java
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

import java.net.URL;

//import com.sun.javafx.tk.Toolkit;
import javafx.scene.image.Image;

import org.sfc.io.IO;
import org.sfc.utils.ErrorUtils;


/**
 * This class is used to represent icons.
 *
 * @author Thomas Weise
 */
public final class Icons {

  /** the image tracker */
//  private static final Tracker TRACKER = new Tracker();

  /** the icon for add buttons */
  public static final Image ADD = new Image(
      ("/org/sfc/gui/resources/icons/add.gif")); //$NON-NLS-1$

  /** the icon for algorithms buttons */
  public static final Image ALGORITHM = new Image(
      ("/org/sfc/gui/resources/icons/algorithm.gif")); //$NON-NLS-1$

  /** the icon for apply buttons */
  public static final Image APPLY = new Image(
      ("/org/sfc/gui/resources/icons/apply.gif")); //$NON-NLS-1$

  /** the icon for apply-all buttons */
  public static final Image APPLY_ALL = new Image(
      ("/org/sfc/gui/resources/icons/applyAll.gif")); //$NON-NLS-1$

  /** the icon for attribute buttons */
  public static final Image ATTRIBUTES = new Image(
      ("/org/sfc/gui/resources/icons/attributes.gif")); //$NON-NLS-1$

  /** the icon for backward buttons */
  public static final Image BACKWARD = new Image(
      ("/org/sfc/gui/resources/icons/backward.gif")); //$NON-NLS-1$

  /** the icon for cancelbuttons */
  public static final Image CANCEL = new Image(
      ("/org/sfc/gui/resources/icons/cancel.gif")); //$NON-NLS-1$

  /** the icon for close buttons */
  public static final Image CLOSE = new Image(
      ("/org/sfc/gui/resources/icons/close.gif")); //$NON-NLS-1$

  /** the icon for content buttons */
  public static final Image CONTENT = new Image(
      ("/org/sfc/gui/resources/icons/content.gif")); //$NON-NLS-1$

  /** the icon for convert buttons */
  public static final Image CONVERT = new Image(
      ("/org/sfc/gui/resources/icons/convert.gif")); //$NON-NLS-1$

  /** the icon for copy buttons */
  public static final Image COPY = new Image(
      ("/org/sfc/gui/resources/icons/copy.gif")); //$NON-NLS-1$

  /** the icon for cutbuttons */
  public static final Image CUT = new Image(
      ("/org/sfc/gui/resources/icons/cut.gif")); //$NON-NLS-1$

  /** the icon for german.de buttons */
  public static final Image DE = new Image(
      ("/org/sfc/gui/resources/icons/de.gif")); //$NON-NLS-1$

  /** the icon for definition buttons */
  public static final Image DEFINITION = new Image(
      ("/org/sfc/gui/resources/icons/definition.gif")); //$NON-NLS-1$

  /** the icon for delete buttons */
  public static final Image DELETE = new Image(
      ("/org/sfc/gui/resources/icons/delete.gif")); //$NON-NLS-1$

  /** the icon for dest buttons */
  public static final Image DESTINATION = new Image(
      ("/org/sfc/gui/resources/icons/dest.gif")); //$NON-NLS-1$

  /** the icon for edit buttons */
  public static final Image EDIT = new Image(
      ("/org/sfc/gui/resources/icons/edit.gif")); //$NON-NLS-1$

  /** the icon for edit-properties buttons */
  public static final Image EDIT_PROPERTIES = new Image(
      ("/org/sfc/gui/resources/icons/editProperties.gif")); //$NON-NLS-1$

  /** the icon for en buttons */
  public static final Image EN = new Image(
      ("/org/sfc/gui/resources/icons/en.gif")); //$NON-NLS-1$

  /** the icon for example buttons */
  public static final Image EXAMPLE = new Image(
      ("/org/sfc/gui/resources/icons/example.gif")); //$NON-NLS-1$

  /** the icon for execute buttons */
  public static final Image EXECUTE = new Image(
      ("/org/sfc/gui/resources/icons/execute.gif")); //$NON-NLS-1$

  /** the icon for exit buttons */
  public static final Image EXIT = new Image(
      ("/org/sfc/gui/resources/icons/exit.gif")); //$NON-NLS-1$

  /** the icon for file buttons */
  public static final Image FILE = new Image(
      ("/org/sfc/gui/resources/icons/file.gif")); //$NON-NLS-1$

  /** the icon for folder buttons */
  public static final Image FOLDER = new Image(
      ("/org/sfc/gui/resources/icons/folder.gif")); //$NON-NLS-1$

  /** the icon for forward buttons */
  public static final Image FORWARD = new Image(
      ("/org/sfc/gui/resources/icons/forward.gif")); //$NON-NLS-1$

  /** the icon for graph buttons */
  public static final Image GRAPH = new Image(
      ("/org/sfc/gui/resources/icons/graph.gif")); //$NON-NLS-1$

  /** the icon for info buttons */
  public static final Image INFO = new Image(
      ("/org/sfc/gui/resources/icons/info.gif")); //$NON-NLS-1$

  /** the icon for help buttons */
  public static final Image HELP = new Image(
      ("/org/sfc/gui/resources/icons/help.gif")); //$NON-NLS-1$

  /** the icon for help support buttons */
  public static final Image HELP_SUPPORT = new Image(
      ("/org/sfc/gui/resources/icons/helpSupport.gif")); //$NON-NLS-1$

  /** the icon for image buttons */
  public static final Image IMAGE = new Image(
      ("/org/sfc/gui/resources/icons/image.gif")); //$NON-NLS-1$

  /** the icon for insert buttons */
  public static final Image INSERT = new Image(
      ("/org/sfc/gui/resources/icons/insert.gif")); //$NON-NLS-1$

  /** the icon for language buttons */
  public static final Image LANGUAGE = new Image(
      ("/org/sfc/gui/resources/icons/language.gif")); //$NON-NLS-1$

  /** the icon for list buttons */
  public static final Image LIST = new Image(
      ("/org/sfc/gui/resources/icons/list.gif")); //$NON-NLS-1$

  /** the icon for list2 buttons */
  public static final Image LIST2 = new Image(
      ("/org/sfc/gui/resources/icons/list2.gif")); //$NON-NLS-1$

  /** the icon for listItem buttons */
  public static final Image LIST_ITEM = new Image(
      ("/org/sfc/gui/resources/icons/listItem.gif")); //$NON-NLS-1$

  /** the icon for new buttons */
  public static final Image NEW = new Image(
      ("/org/sfc/gui/resources/icons/new.gif")); //$NON-NLS-1$

  /** the icon for newFolder buttons */
  public static final Image NEW_FOLDER = new Image(
      ("/org/sfc/gui/resources/icons/newFolder.gif")); //$NON-NLS-1$

  /** the icon for newLeaf buttons */
  public static final Image NEW_LEAF = new Image(
      ("/org/sfc/gui/resources/icons/newLeaf.gif")); //$NON-NLS-1$

  /** the icon for node buttons */
  public static final Image NODE = new Image(
      ("/org/sfc/gui/resources/icons/node.gif")); //$NON-NLS-1$

  /** the icon for ok buttons */
  public static final Image OK = new Image(
      ("/org/sfc/gui/resources/icons/ok.gif")); //$NON-NLS-1$

  /** the icon for open buttons */
  public static final Image OPEN = new Image(
      ("/org/sfc/gui/resources/icons/open.gif")); //$NON-NLS-1$

  /** the icon for paste buttons */
  public static final Image PASTE = new Image(
      ("/org/sfc/gui/resources/icons/paste.gif")); //$NON-NLS-1$

  /** the icon for print buttons */
  public static final Image PRINT = new Image(
      ("/org/sfc/gui/resources/icons/print.gif")); //$NON-NLS-1$

  /** the icon for program buttons */
  public static final Image PROGRAM = new Image(
      ("/org/sfc/gui/resources/icons/program.gif")); //$NON-NLS-1$

  /** the icon for redo buttons */
  public static final Image REDO = new Image(
      ("/org/sfc/gui/resources/icons/redo.gif")); //$NON-NLS-1$

  /** the icon for remark buttons */
  public static final Image REMARK = new Image(
      ("/org/sfc/gui/resources/icons/remark.gif")); //$NON-NLS-1$

  /** the icon for reset buttons */
  public static final Image RESET = new Image(
      ("/org/sfc/gui/resources/icons/reset.gif")); //$NON-NLS-1$

  /** the icon for resetAll buttons */
  public static final Image RESET_ALL = new Image(
      ("/org/sfc/gui/resources/icons/resetAll.gif")); //$NON-NLS-1$

  /** the icon for save buttons */
  public static final Image SAVE = new Image(
      ("/org/sfc/gui/resources/icons/save.gif")); //$NON-NLS-1$

  /** the icon for save-all buttons */
  public static final Image SAVE_ALL = new Image(
      ("/org/sfc/gui/resources/icons/saveAll.gif")); //$NON-NLS-1$

  /** the icon for save-as buttons */
  public static final Image SAVE_AS = new Image(
      ("/org/sfc/gui/resources/icons/saveAs.gif")); //$NON-NLS-1$

  /** the icon for screenshots buttons */
  public static final Image SCREENSHOT = new Image(
      ("/org/sfc/gui/resources/icons/screenshot.gif")); //$NON-NLS-1$

  /** the icon for source buttons */
  public static final Image SOURCE = new Image(
      ("/org/sfc/gui/resources/icons/source.gif")); //$NON-NLS-1$

  /** the icon for speed buttons */
  public static final Image SPEED = new Image(
      ("/org/sfc/gui/resources/icons/speed.gif")); //$NON-NLS-1$

  /** the icon for stop execution buttons */
  public static final Image STOP_EXECUTION = new Image(
      ("/org/sfc/gui/resources/icons/stopExecution.gif")); //$NON-NLS-1$

  /** the icon for table buttons */
  public static final Image TABLE = new Image(
      ("/org/sfc/gui/resources/icons/table.gif")); //$NON-NLS-1$

  /** the icon for tableRow buttons */
  public static final Image TABLE_ROW = new Image(
      ("/org/sfc/gui/resources/icons/tableRow.gif")); //$NON-NLS-1$

  /** the icon for tableSplit buttons */
  public static final Image TABLE_SPLIT = new Image(
      ("/org/sfc/gui/resources/icons/tableSplit.gif")); //$NON-NLS-1$

  /** the icon for tableUnite buttons */
  public static final Image TABLE_UNITE = new Image(
      ("/org/sfc/gui/resources/icons/tableUnite.gif")); //$NON-NLS-1$

  /** the icon for tableUniteAbove buttons */
  public static final Image TABLE_UNITE_ABOVE = new Image(
      ("/org/sfc/gui/resources/icons/tableUniteAbove.gif")); //$NON-NLS-1$

  /** the icon for tableUniteBelow buttons */
  public static final Image TABLE_UNITE_BELOW = new Image(
      ("/org/sfc/gui/resources/icons/tableUniteBelow.gif")); //$NON-NLS-1$

  /** the icon for talbeUniteLeft buttons */
  public static final Image TABLE_UNITE_LEFT = new Image(
      ("/org/sfc/gui/resources/icons/tableUniteLeft.gif")); //$NON-NLS-1$

  /** the icon for tableUniteRight buttons */
  public static final Image TABLE_UNITE_RIGHT = new Image(
      ("/org/sfc/gui/resources/icons/tableUniteRight.gif")); //$NON-NLS-1$

  /** the icon for task buttons */
  public static final Image TASK = new Image(
      ("/org/sfc/gui/resources/icons/task.gif")); //$NON-NLS-1$

  /** the icon for textblock buttons */
  public static final Image TEXTBLOCK = new Image(
      ("/org/sfc/gui/resources/icons/textblock.gif")); //$NON-NLS-1$

  /** the icon for theorem buttons */
  public static final Image THEOREM = new Image(
      ("/org/sfc/gui/resources/icons/theorem.gif")); //$NON-NLS-1$

  /** the icon for tip buttons */
  public static final Image TIP = new Image(
      ("/org/sfc/gui/resources/icons/tip.gif")); //$NON-NLS-1$

  /** the icon for tree buttons */
  public static final Image TREE = new Image(
      ("/org/sfc/gui/resources/icons/tree.gif")); //$NON-NLS-1$

  /** the icon for undo buttons */
  public static final Image UNDO = new Image(
      ("/org/sfc/gui/resources/icons/undo.gif")); //$NON-NLS-1$

  /** the icon for url buttons */
  public static final Image URL = new Image(
      ("/org/sfc/gui/resources/icons/url.gif")); //$NON-NLS-1$

  /**
   * Waits until the image image was fully loaded
   *
   * @param image
   *          The image to load
   * @return true if everything went ok, false otherwise.
   */
//  public static final boolean trackImage(final Image image) {
//    return TRACKER.track(image);
//  }

  /**
   * Load an image from the specified resource.
   *
   * @param resource
   *          the identifier of the resource to load the image from.
   * @return the image
   */
  public static final Image loadImage(final Object resource) {
    URL s;

    Image img;

    s = IO.getResourceUrl(resource);

    if (s == null)
      return null;

    try {
      img = new Image(s.getPath());
//      if (trackImage(img))
        return img;
    } catch (Throwable t) {
     ErrorUtils.onError(t);
    }

    return null;
  }

  /**
   * The internal, forbidden constructor.
   */
  private Icons() {
    ErrorUtils.doNotCall();
  }

//  /**
//   * This class will be used to track images down. This class is fragile.
//   * It is only one instance allowed to exists. You can only use it
//   * indirectly via track_image.
//   */
//  private static final class Tracker implements ImageObserver {
//
//    /** the image to track */
//    private Image image;
//
//    /** tells if everything went ok */
//    private int flags;
//
//    /**
//     * the internal constructor
//     */
//    Tracker() {
//      super();
//    }
//
//    /**
//     * This method is called when information about an image which was
//     * previously requested using an asynchronous interface becomes
//     * available. Asynchronous interfaces are method calls such as
//     * getWidth(ImageObserver) and drawImage(img, x, y, ImageObserver)
//     * which take an ImageObserver object as an argument. Those methods
//     * register the caller as interested either in information about the
//     * overall image itself (in the case of getWidth(ImageObserver)) or
//     * about an output version of an image (in the case of the
//     * drawImage(img, x, y, [w, h,] ImageObserver) call). This method
//     * should return true if further updates are needed or false if the
//     * required information has been acquired. The image which was being
//     * tracked is passed in using the img argument. Various constants are
//     * combined to form the infoflags argument which indicates what
//     * information about the image is now available. The interpretation of
//     * the x, y, width, and height arguments depends on the contents of the
//     * infoflags argument. The infoflags argument should be the bitwise
//     * inclusive OR of the following flags: WIDTH, HEIGHT, PROPERTIES,
//     * SOMEBITS, FRAMEBITS, ALLBITS, ERROR, ABORT.
//     *
//     * @param img
//     *          the image being observed.
//     * @param infoflgs
//     *          the bitwise inclusive OR of the following flags: WIDTH,
//     *          HEIGHT, PROPERTIES, SOMEBITS, FRAMEBITS, ALLBITS, ERROR,
//     *          ABORT.
//     * @param x
//     *          the x coordinate.
//     * @param y
//     *          the y coordinate.
//     * @param width
//     *          the width.
//     * @param height
//     *          the height.
//     * @return false if the infoflags indicate that the image is completely
//     *         loaded; true otherwise.
//     */
//    public final boolean imageUpdate(final Image img, final int infoflgs,
//        final int x, final int y, final int width, final int height) {
//      int infoflags;
//
//      infoflags = infoflgs;
//      synchronized (this) {
//        if ((infoflags & ABORT) != 0)
//          infoflags |= ERROR;
//        this.flags |= infoflags;
//
//        if (((this.flags & (ALLBITS | FRAMEBITS)) != 0)
//            || ((this.flags & ERROR) != 0)) {
//          notifyAll();
//          return false;
//        }
//      }
//      return true;
//    }
//
//    /**
//     * Executes the tracking.
//     *
//     * @param image
//     *          The image to track.
//     * @return true if everything went ok, false othwise.
//     */
//    public synchronized final boolean track(final Image image) {
//      Toolkit toolkit;
//
//      this.image = image;
//      this.flags = 0;
//
//      toolkit = Toolkit.getToolkit();
//
//      synchronized (toolkit) {
//        if (!imageUpdate(this.image, toolkit.checkImage(this.image, -1, -1,
//            null), 0, 0, 0, 0)) {
//          return (this.flags & ERROR) == 0;
//        }
//        toolkit.prepareImage(this.image, -1, -1, this);
//      }
//
//      for (;;) {
//        try {
//          wait();
//          break;
//        } catch (InterruptedException ie) { //
//        } catch (Throwable th) {
//          this.flags = ERROR;
//          break;
//        }
//      }
//
//      return (this.flags & ERROR) == 0;
//    }
//  }

}
