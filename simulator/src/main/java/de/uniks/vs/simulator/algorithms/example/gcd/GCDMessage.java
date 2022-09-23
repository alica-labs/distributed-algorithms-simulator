package de.uniks.vs.simulator.algorithms.example.gcd;


import de.uniks.vs.simulator.model.Message;

public class GCDMessage extends Message {

    private int value;

    public void setContent(int value) {
        this.value = value;
    }

    public int getContent() {
        return value;
    }
}
