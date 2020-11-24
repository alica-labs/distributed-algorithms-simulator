package de.uniks.vs.simulator.model;

import de.uniks.vs.simulator.model.utils.IPropertyDest;
import javafx.scene.paint.Color;
import org.sfc.parallel.simulation.IStepable;

public class Connection extends Item implements IStepable {

    private static final int DEFAULT_SPEED = 10;

    private final Node node1;
    private final Node node2;
    private final int length;

    private Message message;
    private Message lastMessage;
    private int msgCount;
    private int speed;

    public Connection(final Node node1, final Node node2) {
        super();
        this.node1 = node1;
        this.node2 = node2;
        this.speed = DEFAULT_SPEED;
        int x = node1.getX();
        int y = node1.getY();
        x -= node2.getX();
        y -= node2.getY();
        this.length = ((int) (0.5d + Math.sqrt(((double) x * x) + ((double) y * y))));
    }

    public synchronized void step() {
        Message m, mn, n;
        m = mn = this.message;

        for (; m != null; m = n) {
            n = m.nextMessage;

            if ((m.distance -= this.speed) <= 0) {
                m.distance = 0;
                mn = n;
                this.message = mn;

                if (mn == null)
                    this.lastMessage = null;
                m.nextMessage = null;
                this.onMessageReceived(m);
                m.target.onMessageReceived(m);
            }
        }
    }

    synchronized final void send(final Message message) {
        message.nextMessage = null;

        if (this.lastMessage == null) {
            this.message = message;
            this.lastMessage = message;
        } else {
            this.lastMessage.nextMessage = message;
            this.lastMessage = message;
        }
        this.msgCount++;
        message.connection = this;
        message.distance = this.length;
        this.onMessageSent(message);
    }

    @Override
    protected Color getDefaultColor() {
        return Color.BLACK;
    }
    public Node getNode1() {
        return this.node1;
    }
    public Node getNode2() {
        return this.node2;
    }
    public double getLength() {
        return this.length;
    }
    public int getSpeed() {
        return this.speed;
    }
    public synchronized void setSpeed(final int speed) {
        this.speed = speed;
    }
    protected void onMessageSent(final Message msg) { }

    protected void onMessageReceived(final Message msg) { }

    public synchronized Message getNextQeuedMessage(final Message msg) {
        return (msg == null) ? null : msg.nextMessage;
    }

    public synchronized Message getFirstQueuedMessage() {
        return this.message;
    }

//    public int getApproximateMessageLatency() {
//        return ((int) (0.5d + (this.length * 0.1d)));
//    }

    @Override
    public void toStringBuilder(final StringBuilder sb) {
        sb.append("connection: ");
        sb.append(this.node1.getID());
        sb.append("<->");
        sb.append(this.node2.getID());
    }

    @Override
    public synchronized void reset() {
        this.message = null;
        this.lastMessage = null;
        this.msgCount = 0;
        this.speed = DEFAULT_SPEED;
        super.reset();
    }

    @Override
    public synchronized void writeProperties(final IPropertyDest pd) {
        super.writeProperties(pd);
        pd.writeProperty("node 1", String.valueOf(this.node1.getID()));
        pd.writeProperty("node 2", String.valueOf(this.node2.getID()));
        pd.writeProperty("speed", String.valueOf(this.speed));
        pd.writeProperty("#msgs", String.valueOf(this.msgCount));
    }
}
