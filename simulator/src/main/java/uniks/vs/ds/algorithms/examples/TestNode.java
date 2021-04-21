package uniks.vs.ds.algorithms.examples;

import java.awt.Color;

import uniks.vs.ds.algorithms.ColorUtils;
import uniks.vs.ds.model.Message;
import uniks.vs.ds.model.Node;

/**
 * This is a dummy algorithm for testing
 *
 * @author Thomas Weise
 */
public class TestNode extends Node {

    /**
     * the maximum allowed colors
     */
    static final int MAX_COLOR = 5;

    /**
     * The test node
     *
     * @param x
     *          the x-coordinate
     * @param y
     *          the y-coordinate
     */
    public TestNode(final int x, final int y) {
        super(x, y);
        this.setColor(ColorUtils.getRandomColor(MAX_COLOR));
    }

    /**
     * This method can directly be called from somewhere in order to start
     * something. Currently it does nothing, so fill it with life!!!
     */
    @Override
    public synchronized void trigger() {
        Message m;

        m = new Message();
        m.setColor(this.getColor());
        this.broadcast(m);

        super.trigger();
    }

    /**
     * This method is invoked whenever a message arrives at this node.
     *
     * @param msg
     *          the message just received
     */
    @Override
    public synchronized void onMessageReceived(final Message msg) {
        Color c, c2;
        Message m;

        c = msg.getColor();

        if (c == this.getColor()) {
            do {
                c2 = ColorUtils.getRandomColor(MAX_COLOR);
            } while (c2 == c);

            this.setColor(c2);
            m = new Message();
            m.setColor(c2);
            this.broadcast(m);

        }

        super.onMessageReceived(msg);
    }

    /**
     * Reset this model base to the initial state.
     */
    @Override
    public synchronized void reset() {
        super.reset();
        this.setColor(ColorUtils.getRandomColor(MAX_COLOR));
    }

}
