package uniks.vs.ds.algorithms.examples.echo;

import uniks.vs.ds.model.Message;

public class EchoMessage extends Message {
    public Type type;

    public EchoMessage(Type type) {
        this.type = type;
    }

    public enum Type {
        EXPLORATION,
        ECHO
    }
}
