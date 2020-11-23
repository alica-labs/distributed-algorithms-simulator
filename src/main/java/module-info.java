module simulator {
    requires javafx.controls;
    requires java.xml;
    requires java.logging;
    requires java.desktop;
    requires ui;
    requires physics;
    exports de.uniks.vs.simulator;
    exports de.uniks.vs.simulator.model;
    exports de.uniks.vs.simulator.model.utils;
    exports de.uniks.vs.simulator.view;
    exports de.uniks.vs.simulator.simulation;
    exports de.uniks.vs.simulator.algorithms;
    exports org.sfc.collections;
}