package com.example.dissertationweb;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Graph {
        UsS usS;
        int i;
        int j;

    public Graph() {
    }

    public Graph(UsS usS, int i, int j) {
            this.usS = usS;
            this.i = i;
            this.j = j;
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
                    ": [" + i +
                    ", " + j +
                    ']';
        }


    public Set<Graph> buildNetworkGraph(List<UsS> userStories) {
        Set<Graph> networkGraph = new LinkedHashSet<>();
        int i = 1;
        int j = 1;

        for (UsS usS : userStories) {
            if (usS.previousStories.size() == 1 && usS.previousStories.get(0).number == 0) {
                j++;
                Graph graph = new Graph(usS, i, j);
                networkGraph.add(graph);
                graph.setJ(j);
            }
        }

        Set<Graph> networkGraph2 = new LinkedHashSet<>();

        for (UsS usS : userStories) {
            if (usS.previousStories.size() == 1) {
                for (Graph graph : networkGraph) {
                    if (usS.previousStories.get(0).number == graph.usS.number) {
                        j++;
                        Graph graph1 = new Graph(usS, graph.getJ(), j);
                        networkGraph2.add(graph1);
                        break;
                    }
                }
                networkGraph.addAll(networkGraph2);
            }
            else if (usS.previousStories.size() > 1) {
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
        return networkGraph;
    }


}
