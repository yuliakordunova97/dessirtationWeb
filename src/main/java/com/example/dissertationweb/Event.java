package com.example.dissertationweb;

import java.util.*;

public class Event {
    int tEi;
    int tEj;
    int tLj;
    int tLi;
    int i;
    int j;

    public Event() {
    }

    public Event(int tEj, int i, int j) {
        this.tEj = tEj;
        this.i = i;
        this.j = j;
    }

    public Event(int tEj, int tLi, int i, int j) {
        this.tEj = tEj;
        this.tLi = tLi;
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "eventNumber=" + i +
                ", tE=" +  tEj+
                ", prevEvent=" + j;
    }

    public void calculateEvent(Set<Graph> networkGraph){
        List<Graph> networkGraphList = new ArrayList<>(networkGraph);

//        Set<Event> calculateEarlyEvent = new LinkedHashSet<>();
//        Set<Event> calculateEarlyEvent2 = new LinkedHashSet<>();

        int n;
        int numberOfLastEvent = networkGraph.size()-1; // необхідне для визначення кількості подій на мережевому графі
        n = networkGraphList.get(numberOfLastEvent).j;




//        for (Graph gr: networkGraphList){
//            for (int i = 0; i <= n; i++){
//                for ( j = 1; j <= n; j++) {
//                    if (i == 0 & j == 1){
//                        tEj = tEi = 0;
//                        j = 0;
//                        Event event = new Event(tEj, i , j);
//                        calculateEarlyEvent.add(event);
//                    }
////                    if (gr.i == i & gr.j == j){
////                        for (Event event: calculateEarlyEvent){
////                            if (event.j == j){
////                                tEj = event.tEj + gr.usS.storyPoint;
////                                Event event1 = new Event(tEj, i , j);
////                                calculateEarlyEvent2.add(event1);
////                            }
////                        }
////                    }
//                }
//                //calculateEarlyEvent.addAll(calculateEarlyEvent2);
//            }
//        }
//        for (Event e: calculateEarlyEvent){
//            System.out.println(e.toString());
//        }
//
        System.out.println(n);

    }




}
