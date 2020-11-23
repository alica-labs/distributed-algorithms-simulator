package de.uniks.vs.simulator.model.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.uniks.vs.simulator.model.Node;
import org.sfc.collections.CollectionUtils;

public final class NodeTypes {

  private static final List<NodeFactory> FACTORIES = CollectionUtils
      .createList();

  private static final Comparator<NodeFactory> C = new Comparator<NodeFactory>() {
    public int compare(final NodeFactory o1, final NodeFactory o2) {
      return String.CASE_INSENSITIVE_ORDER.compare(o1.getNodeTypeName(),
          o2.getNodeTypeName());
    }
  };

  public static synchronized final void registerFactory(
      final NodeFactory f) {
    if (f != null)
      FACTORIES.add(f);
  }

  public static synchronized final void registerNodeType(
      final String name, final Class<? extends Node> clazz) {
    FACTORIES.add(new DefaultNodeFactory(name, clazz));
  }

  public static synchronized final void registerNodeType(
      final Class<? extends Node> clazz) {
    FACTORIES.add(new DefaultNodeFactory(clazz));
  }

  public static synchronized NodeFactory[] getNodeFactories() {
    NodeFactory[] n;

    n = new NodeFactory[FACTORIES.size()];
    n = FACTORIES.toArray(n);
    Arrays.sort(n, C);
    return n;
  }
}
