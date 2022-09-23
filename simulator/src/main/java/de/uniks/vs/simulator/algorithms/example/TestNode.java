package de.uniks.vs.simulator.algorithms.example;

import de.uniks.vs.simulator.algorithms.ColorUtils;
import de.uniks.vs.simulator.model.Message;
import de.uniks.vs.simulator.model.Node;

import java.awt.*;

public class TestNode extends Node {

    private static final int MAX_COLOR = 5;

    /**
     * The node
     *
     * @param x the x-coordinate
     * @param y
     */
    public TestNode(int x, int y) {
        super(x, y);
        this.setColor(ColorUtils.getRandomColor(MAX_COLOR));
    }

    @Override
    public synchronized void trigger() {
        Message m = new Message();
        m.setColor(this.getColor());
        this.broadcast(m);

        super.trigger();
    }

    @Override
    public synchronized void onMessageReceived(Message msg) {
        Message m = new Message();
        m.setColor(msg.getColor());
        this.broadcastExcept(msg, msg.getSource());

        super.onMessageReceived(msg);
    }
}
