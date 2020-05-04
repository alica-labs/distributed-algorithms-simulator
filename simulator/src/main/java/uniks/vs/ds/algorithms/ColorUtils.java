/*
 * Copyright (c) 2007 Thomas Weise
 * 
 * E-Mail           : tweise@gmx.de
 * Creation Date    : 2007-03-26
 * Creator          : Thomas Weise
 * Original Filename: uniks.vs.ds.algorithms.ColorUtils.java
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

package uniks.vs.ds.algorithms;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import org.sfc.collections.CollectionUtils;
import org.sfc.math.Mathematics;
import org.sfc.utils.ErrorUtils;
import org.sfc.utils.HashUtils;

/**
 * This class contains some helper utilities for colors.
 * 
 * @author Thomas Weise
 */
public final class ColorUtils {

	/**
	 * the nodes colors
	 */
	private static final Color[] COLORS;

	/**
	 * the color names
	 */
	private static final String[] NAMES;

	static {
		List<Color> l;
		List<String> s;
		Field[] ff;
		Field f;
		int i, m;
		Color c;
		String ss;

		l = CollectionUtils.createList();
		s = CollectionUtils.createList();
//		l.add(Color.WHITE);
//		s.add("white"); //$NON-NLS-1$
		l.add(Color.ORANGE);
		s.add("orange"); //$NON-NLS-1$
		l.add(Color.RED);
		s.add("red"); //$NON-NLS-1$
		l.add(Color.GREEN);
		s.add("green"); //$NON-NLS-1$
		l.add(Color.BLUE);
		s.add("blue"); //$NON-NLS-1$
		l.add(Color.YELLOW);
		s.add("yellow"); //$NON-NLS-1$

		ff = Color.class.getDeclaredFields();
		for (i = (ff.length - 1); i >= 0; i--) {
			f = ff[i];
			m = f.getModifiers();
			if (Modifier.isStatic(m) && Modifier.isPublic(m) && Color.class.isAssignableFrom(f.getClass())) {
				ss = f.getName().toLowerCase();
				if (ss.contains("gray") || //$NON-NLS-1$
						ss.contains("grey")) //$NON-NLS-1$
					continue;
				try {
					c = ((Color) (f.get(null)));
					if ((c != null) && (!(l.contains(c))) && (!(Color.BLACK.equals(c)))) {
						l.add(c);
						s.add(f.getName().toLowerCase());
					}
				} catch (Throwable t) {
					//
				}
			}
		}

		m = l.size();
		for (i = 1; i < m; i++) {
			if (s.get(i).startsWith("bright") || //$NON-NLS-1$
					s.get(i).startsWith("light") || //$NON-NLS-1$
					s.get(i).startsWith("dark")) //$NON-NLS-1$
				continue;

			c = l.get(i).brighter();
			if (!(l.contains(c))) {
				l.add(c);
				s.add("light " + s.get(i)); //$NON-NLS-1$
			}
			c = l.get(i).darker();
			if (!(l.contains(c))) {
				l.add(c);
				s.add("dark " + s.get(i)); //$NON-NLS-1$
			}
		}

		COLORS = l.toArray(new Color[l.size()]);
		NAMES = s.toArray(new String[s.size()]);
	}

	/**
	 * the maximum color count
	 */
	public static final int MAX_COLOR_COUNT = COLORS.length;

	/**
	 * the internally shared randomizer
	 */
	private static final Random RAND = new Random();

	/**
	 * the last color index
	 */
	private static int s_lc;

	/**
	 * Obtain a random color.
	 * 
	 * @return the random color
	 */
	public static final Color getRandomColor() {
		return getRandomColor(MAX_COLOR_COUNT);
	}

	/**
	 * Obtain a random color.
	 * 
	 * @param maxColor
	 *            the maximum count of possible colors to chose from
	 * @return the random color
	 */
	public static final Color getRandomColor(final int maxColor) {
		int c;
		synchronized (RAND) {
			do {
				c = RAND.nextInt(maxColor);
			} while ((maxColor > 1) && (c == s_lc));
			s_lc = c;
			return COLORS[c];
		}
	}

	/**
	 * R
	 * 
	 * @param index
	 *            the index
	 * @return the color at that index
	 */
	public static final Color getColor(final int index) {
		return COLORS[Mathematics.modulo(index, MAX_COLOR_COUNT)];
	}

	/**
	 * Obtain the index of the specified color
	 * 
	 * @param c
	 *            the color to get the index of
	 * @return the index of the color <code>c</code>
	 */
	public static final int getColorIndex(final Color c) {
		int i;

		for (i = (MAX_COLOR_COUNT - 1); i > 0; i--) {
			if (COLORS[i].equals(c))
				return i;
		}
		return 0;
	}

	/**
	 * Obtain the name of the specified color
	 * 
	 * @param c
	 *            the color to get the name of
	 * @return the name of the color <code>c</code>
	 */
	public static final String getColorName(final Color c) {
		int i;

		if (c == null)
			return String.valueOf(null);

		for (i = (MAX_COLOR_COUNT - 1); i >= 0; i--) {
			if (COLORS[i].equals(c))
				return NAMES[i];
		}

		return Integer.toHexString(c.getRed()) + Integer.toHexString(c.getBlue()) + Integer.toHexString(c.getGreen());
	}

	/**
	 * Computes deterministically a color from an object.
	 * 
	 * @param id
	 *            the id of the new color
	 * @return the color
	 */
	public static final Color objectToColor(final Object id) {
		return intToColor(HashUtils.objectHashCode(id));
	}

	/**
	 * Computes deterministically a color from an integer.
	 * 
	 * @param id
	 *            the id of the new color
	 * @return the color
	 */
	public static final Color intToColor(final int id) {
		int h, r, g, b, s;
		if ((id >= 0) && (id < MAX_COLOR_COUNT))
			return COLORS[id];

		h = id;

		do {
			h = HashUtils.intHashCode(h);
			r = (h & 0xff);
			g = ((h >> 8) & 0xff);
			b = ((h >> 16) & 0xff);
			s = (r + g + b);
		} while ((s < 280) || (s > 600));

		return new Color(r, g, b);
	}

	/**
	 * The forbidden constructor.
	 */
	private ColorUtils() {
		ErrorUtils.doNotCall();
	}

}
