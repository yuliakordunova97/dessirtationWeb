package com.example.dissertationweb;

public class GraphParameters {
    public int event;
    public int eventTE;
    public int eventTL;

    public int isCritical;

    public GraphParameters(int event, int eventTE) {
        this.event = event;
        this.eventTE = eventTE;
    }

    public GraphParameters(int event, int eventTE, int eventTL) {
        this.event = event;
        this.eventTE = eventTE;
        this.eventTL = eventTL;
    }

    public GraphParameters() {

    }

    @Override
    public String toString() {
        return  event +
                " (" + eventTE +
                ", " + eventTL +
                ") is critical = " + isCritical;
    }
}
