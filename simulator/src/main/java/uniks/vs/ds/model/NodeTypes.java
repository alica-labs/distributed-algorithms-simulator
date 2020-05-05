/*
 * Copyright (c) 2009 Thomas Weise
 *
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.de.uniks.vs.ds.model.NodeTypes.java
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

package uniks.vs.ds.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.sfc.collections.CollectionUtils;

/**
 * The node type manager
 *
 * @author Thomas Weise
 */
public final class NodeTypes {

  /**
   * the internal factories
   */
  private static final List<INodeFactory> FACTORIES = CollectionUtils
      .createList();

  /**
   * the node comparator
   */
  private static final Comparator<INodeFactory> C = new Comparator<INodeFactory>() {
    public int compare(final INodeFactory o1, final INodeFactory o2) {
      return String.CASE_INSENSITIVE_ORDER.compare(o1.getNodeTypeName(),
          o2.getNodeTypeName());
    }
  };

  /**
   * Register a new node factory.
   *
   * @param f
   *          the node factory to be registered
   */
  public static synchronized final void registerFactory(
      final INodeFactory f) {
    if (f != null)
      FACTORIES.add(f);
  }

  /**
   * Register a new node type.
   *
   * @param name
   *          the name of the new node type
   * @param clazz
   *          the class of the new node type
   */
  public static synchronized final void registerNodeType(
      final String name, final Class<? extends SimNode> clazz) {
    FACTORIES.add(new DefaultNodeFactory(name, clazz));
  }

  /**
   * Register a new node type.
   *
   * @param clazz
   *          the class of the new node type
   */
  public static synchronized final void registerNodeType(
      final Class<? extends SimNode> clazz) {
    FACTORIES.add(new DefaultNodeFactory(clazz));
  }

  /**
   * Obtain all known node factories.
   *
   * @return an array containing all known node factories
   */
  public static synchronized INodeFactory[] getNodeFactories() {
    INodeFactory[] n;

    n = new INodeFactory[FACTORIES.size()];
    n = FACTORIES.toArray(n);
    Arrays.sort(n, C);
    return n;
  }
}
