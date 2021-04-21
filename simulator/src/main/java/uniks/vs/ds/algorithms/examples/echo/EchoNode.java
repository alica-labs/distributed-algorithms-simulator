package uniks.vs.ds.algorithms.examples.echo;

import uniks.vs.ds.model.Message;
import uniks.vs.ds.model.Node;

import java.awt.Color;

    public class EchoNode extends Node {

        private int needed;
        private Node prev;

        public EchoNode(final int x, final int y) {
            super(x, y);
        }

        @Override
        public synchronized void reset() {
            this.needed = 0;
            this.prev = null;
            super.reset();
        }

        @Override
        public synchronized void trigger() {
            Message message;
            this.setColor(Color.RED);
            message = new Message();
            message.setColor(Color.RED);
            this.needed = this.broadcast(message);
            this.setText(String.valueOf(this.needed));

            if (this.needed <= 0)
                this.setColor(Color.GREEN);
            super.trigger();
        }

        @Override
        public synchronized void onMessageReceived(final Message msg) {
            Message message;

            if (Color.RED.equals(msg.getColor())) {

                if (Color.WHITE.equals(this.getColor())) {
                    this.setColor(Color.RED);
                    this.prev = msg.getSource();
                    message = new Message();
                    message.setColor(Color.RED);
                    this.needed = this.broadcastExcept(message, msg.getSource());
                    this.setText(String.valueOf(this.needed));

                    if (this.needed <= 0) {
                        this.setColor(Color.GREEN);

                        if (this.prev != null) {
                            message = new Message();
                            message.setColor(Color.GREEN);
                            this.sendTo(message, this.prev);
                        }
                    }
                    return;
                }
            }

            if (Color.RED.equals(this.getColor())) {
                this.setText(String.valueOf(--this.needed));

                if (this.needed <= 0) {
                    this.setColor(Color.GREEN);
                    if (this.prev != null) {
                        message = new Message();
                        message.setColor(Color.GREEN);
                        this.sendTo(message, this.prev);
                    }
                }
            }

            if (Color.GREEN.equals(msg.getColor()))
                msg.getConnection().setColor(Color.GREEN);
            super.onMessageReceived(msg);
        }
    }


