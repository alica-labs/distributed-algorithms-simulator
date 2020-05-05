/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2009-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.model.DefaultNodeFactory.java
 * Last modification: 2008-04-01
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

package uniks.vs.ds.model;

import java.lang.reflect.Constructor;

import org.sfc.utils.ErrorUtils;

/**
 * The default node factory.
 *
 * @author Thomas Weise
 */
public class DefaultNodeFactory implements INodeFactory {
  /**
   * the node type name.
   */
  private final String m_name;

  /**
   * the default parameters.
   */
  private static final Class<?>[] P = new Class[] { int.class, int.class };

  /**
   * the constructor
   */
  private final Constructor<? extends SimNode> m_const;

  /**
   * Create a new default node factory.
   *
   * @param clazz
   *          the class of the new node type
   */
  public DefaultNodeFactory(final Class<? extends SimNode> clazz) {
    this(clazz.getSimpleName(), clazz);
  }

  /**
   * Create a new default node factory.
   *
   * @param name
   *          the name of the new node type
   * @param clazz
   *          the class of the new node type
   */
  public DefaultNodeFactory(final String name,
      final Class<? extends SimNode> clazz) {
    super();
    Constructor<? extends SimNode> cc;

    this.m_name = name;

    try {
      cc = clazz.getConstructor(P);
    } catch (Throwable t) {
      ErrorUtils.onError(t);
      cc = null;
    }
    this.m_const = cc;
  }

  /**
   * Create a new node the specified x and y-coordinates.
   *
   * @param x
   *          the x-coordinate for the new node
   * @param y
   *          the y-coordinate for the new node
   * @return the new node
   */
  public SimNode createNode(double x, double y) {
    try {
      return this.m_const.newInstance(new Object[] { new Integer((int)x),
          new Integer((int)y) });
    } catch (Throwable t) {
      ErrorUtils.onError(t);
      return null;
    }
  }

  /**
   * Obtain a human readable description of the node's type name.
   *
   * @return The node name.
   */
  public String getNodeTypeName() {
    return this.m_name;
  }
}
