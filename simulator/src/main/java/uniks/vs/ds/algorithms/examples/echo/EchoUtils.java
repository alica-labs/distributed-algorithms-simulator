package uniks.vs.ds.algorithms.examples.echo;

import uniks.vs.ds.model.Connection;

import java.util.HashMap;

public class EchoUtils {

    static HashMap<Connection, Integer> connections = new HashMap<>();

    public static void notice(EchoMessage message) {
        Connection connection = message.getConnection();
        Integer count = connections.get(connection);

        if (count != null) {
            count++;
        }
        else
            count = 1;

        connections.put(connection, count);
    }

    public static String printConnections() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer count: connections.values() ) {
            stringBuilder.append(count + ",");
        }
        return stringBuilder.toString();
    }

    public static void reset() {
        connections.clear();
    }
}
