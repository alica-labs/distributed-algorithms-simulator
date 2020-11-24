package de.uniks.vs.simulator.model.utils;

import java.lang.reflect.Constructor;

import de.uniks.vs.simulator.model.Node;
import org.sfc.utils.ErrorUtils;

public class DefaultNodeFactory implements NodeFactory {

  private final String m_name;
  private static final Class<?>[] P = new Class[] { int.class, int.class };
  private final Constructor<? extends Node> m_const;

  public DefaultNodeFactory(final Class<? extends Node> clazz) {
    this(clazz.getSimpleName(), clazz);
  }

  public DefaultNodeFactory(final String name, final Class<? extends Node> clazz) {
    super();
    Constructor<? extends Node> cc;
    this.m_name = name;

    try {
      cc = clazz.getConstructor(P);
    } catch (Throwable t) {
      ErrorUtils.onError(t);
      cc = null;
    }
    this.m_const = cc;
  }

  public Node createNode(double x, double y) {
    try {
      return this.m_const.newInstance(new Object[] { Integer.valueOf((int)x),
              Integer.valueOf((int)y) });
    } catch (Throwable t) {
      ErrorUtils.onError(t);
      return null;
    }
  }

  public String getNodeTypeName() {
    return this.m_name;
  }
}
