package de.uniks.vs.simulator.algorithms.example.gcd;

import de.uniks.vs.simulator.algorithms.ColorUtils;
import de.uniks.vs.simulator.model.Message;
import de.uniks.vs.simulator.model.Node;

public class GCDNode extends Node {

    private int value;
    private int gcd;

    public GCDNode(int x, int y) {
        super(x, y);
        this.setColor(ColorUtils.getRandomColor(5));
        this.value = GCDUtils.getNextNumber();
//        this.value =  GCDUtils.getRandomNumber(30);
        this.setGCD(this.value);
    }

    @Override
    public synchronized void trigger() {
        GCDMessage message = new GCDMessage();
        message.setContent(gcd);
        message.setColor(this.getColor());
        this.broadcast(message);
        super.trigger();
    }

    @Override
    public synchronized void onMessageReceived(Message msg) {
        GCDMessage message = (GCDMessage) msg;
        int e = message.getContent();

        if(e == this.gcd)
            return;

        if (e < this.gcd) {
            this.setGCD(GCDUtils.mods(this.gcd, e));
        }
        message.setContent(this.gcd);
        message.setColor(this.getColor());
        this.broadcast(message);
        super.onMessageReceived(msg);
    }

    private void setGCD(int gcd) {
        this.gcd = gcd;
        this.setText(this.value + " " + this.gcd);
        log(this.value + " " + this.gcd);
    }
}
