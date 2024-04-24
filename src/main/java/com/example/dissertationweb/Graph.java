package com.example.dissertationweb;

import java.util.*;

public class Graph {
    UsS usS;
    int i;
    int j;
    MinTime minTime;
    MaxTime maxTime;

    int iMinTime;
    int iMaxTime;
    int jMinTime;
    int jMaxTime;
    public int reserveFull;
    int reserveFree;
    int reserveIndependent;

    public Graph(UsS usS, int i, int j) {
        this.usS = usS;
        this.i = i;
        this.j = j;
    }
    public Graph() {
    }

    public Graph(int i) {
    }

    public UsS getUsS() {
        return usS;
    }

    public void setUsS(UsS usS) {
        this.usS = usS;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return  "UserStory=" + usS.number +
                ": [" + i + " ("+iMinTime + ", " + iMaxTime + ")"+
                ", " + j + " ("+jMinTime + ", " + jMaxTime + ")"+
                ']' + " reserveFull: " + reserveFull +
                ", reserveFree: " + reserveFree +
                ", reserveIndependent: " + reserveIndependent;
    }


    public Set<Graph> buildNetworkGraph(List<UsS> userStories) {
        Set<Graph> networkGraph = new LinkedHashSet<>();
        //networkGraph.add(new Graph(new UsS(0, 0), 0, 1));
        int i = 1;
        int j = 1;


        Set<Graph> networkGraph2 = new LinkedHashSet<>();


        for (UsS usS : userStories) {
            if (usS.previousStories.size() == 1 && usS.previousStories.get(0).number == 0) {
                j++;
                Graph graph = new Graph(usS, i, j);
                networkGraph.add(graph);
                graph.setJ(j);
            } else if (usS.previousStories.size() == 1 && usS.previousStories.get(0).number != 0) {
                for (Graph graph : networkGraph) {
                    if (usS.previousStories.get(0).number == graph.usS.number) {
                        j++;
                        Graph graph1 = new Graph(usS, graph.getJ(), j);
                        networkGraph2.add(graph1);
                        break;
                    }
                }
                networkGraph.addAll(networkGraph2);
            } else if (usS.previousStories.size() > 1) {
                j++;
                int k = 0;
                while (k < usS.previousStories.size()) {
                    for (Graph graph : networkGraph) {
                        if (graph.usS.number == usS.previousStories.get(k).number) {
                            UsS fict = new UsS(0, 0);
                            Graph graphFict = new Graph(fict, graph.getJ(), j);
                            networkGraph2.add(graphFict);
                        }
                    }
                    k++;
                }
                i = j;
                j++;
                Graph graph4 = new Graph(usS, i, j);
                networkGraph2.add(graph4);

                for (Graph graph : networkGraph) {
                    if (graph.usS.number == usS.number) {
                        graph.setJ(j);
                        break;
                    }
                }
                networkGraph.addAll(networkGraph2);
            }
        }

        for (Graph graph : networkGraph) {
            System.out.println(graph.toString());
        }
        //calculateEarlyAndLateEvents(networkGraph);
        return networkGraph;
    }

//    void calculateEarlyAndLateEvents(Set<Graph> networkGraph) {
//
//        //calculateEarlyEvents(networkGraph);
//        calculateLateEvents(networkGraph);
//
//        //calculate Late Events
//
//    }

//    Set<Graph> calculateEarlyEvents(Set<Graph> networkGraph) {
//        List<Graph> networkGraphList = new ArrayList<>(networkGraph);
//        Set<Graph> newGraphList = new LinkedHashSet<>();
//
//        int n;
//        if (networkGraphList.size()>=1) {
//            int numberOfLastEvent = networkGraph.size() - 1;
//            n = networkGraphList.get(numberOfLastEvent).j;
//
//
//            int tEi;
//            int tEj;
//
//            Graph graph0 = new Graph(new UsS(0, 0), 0, 1);
//            MinTime minTime0 = new MinTime(0);
//            graph0.minTime = minTime0;
//            newGraphList.add(graph0);
//
//            for (int k = 0; k < networkGraphList.size(); k++) {
//                if (k == 0) {
//                    tEi = 0;
//                    Graph graphk = networkGraphList.get(0);
//                    tEj = tEi + graphk.usS.storyPoint;
//                    MinTime minTimeOb = new MinTime(tEi, tEj);
//                    graphk.minTime = minTimeOb;
//                    newGraphList.add(graphk);
//                } else {
//                    for (int i = 1; i <= n; i++) {
//                        for (int j = 2; j <= n; j++) {
//                            if (networkGraphList.get(k).usS.previousStories.size() == 1) {
//                                int a1 = networkGraphList.get(k).usS.previousStories.get(0).number; // № попередньої роботи
//                                if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i == 1) {
//                                    tEi = networkGraphList.get(a1).minTime.tEi;
//                                    tEj = tEi + networkGraphList.get(k).usS.storyPoint;
//                                    MinTime minTime2 = new MinTime(tEi, tEj);
//                                    networkGraphList.get(k).minTime = minTime2;
//                                    newGraphList.add(networkGraphList.get(k));
//                                } else if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i > 1) {
//                                    UsS a = networkGraphList.get(k).usS.previousStories.get(0);
//                                    for (Graph graph : networkGraphList) {
//                                        if (graph.usS.equals(a)) {
//                                            int max = graph.minTime.tEj;
//                                            tEj = max + networkGraphList.get(k).usS.storyPoint;
//                                            MinTime minTime3 = new MinTime(max, tEj);
//                                            networkGraphList.get(k).minTime = minTime3;
//                                            newGraphList.add(networkGraphList.get(k));
//                                        }
//                                    }
//                                }
//                            } else if (networkGraphList.get(k).usS.previousStories.size() > 1) {
//                                UsS a = networkGraphList.get(k).usS.previousStories.get(0);
//                                if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i > 1) {
//                                    for (Graph graph : networkGraphList) {
//                                        if (graph.usS.equals(a)) {
//                                            int eI = graph.j;
//                                            int eJ = networkGraphList.get(k).i;
//                                            int max = graph.minTime.tEj;
//
//                                            for (int h = 1; h < networkGraphList.get(k).usS.previousStories.size(); h++) {
//                                                UsS a1 = networkGraphList.get(k).usS.previousStories.get(h);
//                                                for (Graph graph1 : networkGraphList) {
//                                                    if (graph1.usS.equals(a1)) {
//                                                        int eI1 = graph1.j;
//                                                        int max1 = graph1.minTime.tEj;
//                                                        if (max1 > max) {
//                                                            max = max1;
//                                                            eI = eI1;
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                            MinTime minTime4 = new MinTime(max, max);
//                                            Graph newGraph = new Graph(new UsS(0, 0), eI, eJ);
//                                            newGraph.minTime = minTime4;
//                                            newGraphList.add(newGraph);
//
//                                            tEj = max + networkGraphList.get(k).usS.storyPoint;
//                                            MinTime minTime5 = new MinTime(max, tEj);
//
//                                            networkGraphList.get(k).minTime = minTime5;
//                                            newGraphList.add(networkGraphList.get(k));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        System.out.println();
//
//        for (Graph g : newGraphList) {
//            System.out.println(g.toString() + " " + g.minTime.tEi + " " + g.minTime.tEj);
//        }
//        return newGraphList;
//    }
//
//   Set<Graph> calculateLateEvents(Set<Graph> networkGraph){
//        calculateEarlyEvents(networkGraph);
//        List<Graph> networkGraphList = new ArrayList<>(networkGraph);
//        Set<Graph> newGraphList = new LinkedHashSet<>();
//        List<GraphParameters> arrayListWithGraphParameters = new ArrayList();
//        Set<Graph> graphWithLateEvents = new LinkedHashSet<>();
//        Collections.reverse(networkGraphList);
//        networkGraphList.get(0).maxTime = new MaxTime(networkGraphList.get(0).minTime.tEj, networkGraphList.get(0).minTime.tEj);
//        graphWithLateEvents.add(networkGraphList.get(0));
//        int tLi;
//        int tLj;
//        for (int k = 0; k < networkGraphList.size(); k++) {
//            for (int s = 1; s < networkGraphList.size(); s++) {
//                if (networkGraphList.get(k).i == networkGraphList.get(s).j) {
//                    tLi = networkGraphList.get(k).maxTime.tLi - networkGraphList.get(k).usS.storyPoint;
//                    tLj = tLi - networkGraphList.get(s).usS.storyPoint;
//                    MaxTime maxTime = new MaxTime(tLi, tLj);
//                    networkGraphList.get(s).maxTime = maxTime;
//                    graphWithLateEvents.add(networkGraphList.get(s));
//                }
//            }
//        }
//
//        Graph graphZero = new Graph(new UsS(0, 0), 0, 1);
//        graphZero.maxTime = new MaxTime(0, 0);
//        graphWithLateEvents.add(graphZero);
//
//        for (Graph gL : graphWithLateEvents) {
//            for (Graph gE : newGraphList) {
//                if (gL.j == gE.j) {
//                    gE.maxTime = gL.maxTime;
//                }
//            }
//        }
//
//        System.out.println();
//
//        for (Graph g : newGraphList) {
//            System.out.println(g.toString() + " " + g.minTime.tEi + " " + g.maxTime.tLi);
//        }
//
//
//        setParametersToGraph(arrayListWithGraphParameters, newGraphList);
//
//        System.out.println();
//
//        criticalPathAndTimeReserves(arrayListWithGraphParameters);
//
//        return newGraphList;
//    }
//
//    void setParametersToGraph(List<GraphParameters> arrayListWithGraphParameters, Set<Graph> newGraphList){
//        for (Graph g : newGraphList) {
//            GraphParameters graphParameters = new GraphParameters(g.j, g.minTime.tEj, g.maxTime.tLi);
//            arrayListWithGraphParameters.add(graphParameters);
//        }
//        for (GraphParameters graphParameters : arrayListWithGraphParameters) {
//            System.out.println(graphParameters.toString());
//        }
//
//        for (GraphParameters gp: arrayListWithGraphParameters){
//            for (Graph g: newGraphList){
//                if (gp.event == g.i){
//                    g.iMinTime = gp.eventTE;
//                    g.iMaxTime = gp.eventTL;
//                }
//                if (gp.event == g.j){
//                    g.jMinTime = gp.eventTE;
//                    g.jMaxTime = gp.eventTL;
//                }
//            }
//        }
//
//
//        for (Graph g: newGraphList){
//            System.out.println(g);
//        }
//
//        //List<Graph> newFullGraphList = new ArrayList<>(newGraphList);
//        for (Graph graph:  newGraphList){
//            graph.reserveFull = graph.jMaxTime - graph.iMinTime - graph.usS.storyPoint;
//            graph.reserveFree = graph.jMinTime - graph.iMinTime - graph.usS.storyPoint;
//            graph.reserveIndependent = graph.jMinTime - graph.iMaxTime - graph.usS.storyPoint;
//        }
//
//        System.out.println();
//        for (Graph g: newGraphList){
//            System.out.println(g);
//        }
//    }
//
//    void criticalPathAndTimeReserves(List<GraphParameters> graphParametrsList) {
//        for (GraphParameters g : graphParametrsList) {
//            if (g.eventTE == g.eventTL) {
//                g.isCritical = 0;
//            } else {
//                g.isCritical = g.eventTL - g.eventTE;
//            }
//        }
//    }


}
