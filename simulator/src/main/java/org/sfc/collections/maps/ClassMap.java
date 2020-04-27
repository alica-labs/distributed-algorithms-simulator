/*
 * Copyright (c) 2009 Thomas Weise for sigoa
 * Simple Interface for Global Optimization Algorithms
 * http://www.sigoa.org/
 * 
 * E-Mail           : info@sigoa.org
 * Creation Date    : 2009-03-13
 * Creator          : Thomas Weise
 * Original Filename: org.sfc.collections.maps.ClassMap.java
 * Last modification: 2009-03-13
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

package org.sfc.collections.maps;

/**
 * This map holds items with classes as keys and also allow using the class
 * hierarchy for querying.
 * 
 * @param <V>
 *          the value type
 * @author Thomas Weise
 */
public class ClassMap<V> extends DefaultMap<Class<?>, V> {
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1;

  /**
   * Create a new hash map.
   */
  public ClassMap() {
    this(-1);
  }

  /**
   * Create a new hash map.
   * 
   * @param initialCapacity
   *          The initial capacity wanted for this map, set this parameter
   *          to <code>-1</code> to use a reasonable default.
   */

  public ClassMap(final int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Returns the value to which the specified key is mapped in this
   * identity hash map, or <tt>null</tt> if the map contains no mapping
   * for this key. A return value of <tt>null</tt> does not
   * <i>necessarily</i> indicate that the map contains no mapping for the
   * key; it is also possible that the map explicitly maps the key to
   * <tt>null</tt>. The <tt>containsKey</tt> method may be used to
   * distinguish these two cases.
   * 
   * @param key
   *          the key whose associated value is to be returned.
   * @return the value to which this map maps the specified key, or
   *         <tt>null</tt> if the map contains no mapping for this key.
   * @see #put(Object, Object)
   */
  @Override
  public V get(final Object key) {
    V res;
    Class<?> c;

    res = super.get(key);
    if (res != null)
      return res;

    if (!(key instanceof Class))
      return null;
    c = ((Class<?>) key);

    c = c.getSuperclass();
    while (c != null) {
      res = super.get(c);
      if (res != null)
        return res;
      c = c.getSuperclass();
    }

    return null;
  }

}
