package de.uniks.vs.simulator.algorithms.example.echo;

import de.uniks.vs.simulator.model.Message;
import de.uniks.vs.simulator.model.Node;

import javafx.scene.paint.Color;

public class EchoNode extends Node {

    private boolean initiator = false;
    private boolean received = false;
    private Node pred;
    private int count = 0;

    public EchoNode(int x, int y) {
        super(x, y);
        this.setColor(Color.WHITE);
    }

    @Override
    public synchronized void reset() {
        initiator = false;
        received = false;
        count = 0;
        EchoUtils.reset();
        super.reset();
    }

    @Override
    public synchronized void trigger() {
        this.initiator = true;
        this.count = 0;
        EchoMessage echoMessage = new EchoMessage(EchoMessage.Type.EXPLORATION);
        echoMessage.setColor(Color.RED);
        this.setColor(Color.RED);
        this.broadcast(echoMessage);
        super.trigger();
    }

    @Override
    public synchronized void onMessageReceived(Message msg) {
        EchoMessage message = (EchoMessage) msg;

        EchoUtils.notice(message);

        if (message.type == EchoMessage.Type.ECHO) {
            this.count++;
            message.getConnection().setColor(Color.GREEN);

            if (this.count == this.getNeighborCount()) {
                this.received = false;

                if (!this.initiator) {
                    this.setColor(Color.GREEN);
                    this.sendTo(message, this.pred);
                } else {
                    this.setColor(Color.BLUE);
                    this.log(EchoUtils.printConnections());
                }
            }
        } else {

            if (!this.received && message.type == EchoMessage.Type.EXPLORATION) {
                this.received = true;
                this.count = 0;
                pred = message.getSource();
                this.setColor(Color.RED);
                this.broadcastExcept(message, msg.getSource());
            }
            this.count++;

            if (this.count == this.getNeighborCount()) {
                this.received = false;

                if (!this.initiator) {
                    EchoMessage echo = new EchoMessage(EchoMessage.Type.ECHO);
                    echo.setColor(Color.GREEN);
                    this.setColor(Color.GREEN);
                    this.sendTo(echo, this.pred);
                }
            }
        }
        super.onMessageReceived(msg);
    }
}
