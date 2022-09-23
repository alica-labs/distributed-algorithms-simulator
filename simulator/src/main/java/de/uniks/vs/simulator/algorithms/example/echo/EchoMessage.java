package de.uniks.vs.simulator.algorithms.example.echo;

import de.uniks.vs.simulator.model.Message;

public class EchoMessage extends Message {
    public EchoMessage.Type type;

    public EchoMessage(Type type) {
        this.type = type;
    }

    public enum Type {
        EXPLORATION,
        ECHO
    }
}
