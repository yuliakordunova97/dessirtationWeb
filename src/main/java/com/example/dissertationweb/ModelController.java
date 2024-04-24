package com.example.dissertationweb;

import com.example.dissertationweb.UsS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ModelController {
    List<UsS> usSList = new ArrayList<>();
    Graph graph = new Graph();
    Set<Graph> graphSet = new LinkedHashSet<>();


    List<GraphParameters> calculateEarlyEvents(Set<Graph> networkGraph) {
        List<Graph> networkGraphList = new ArrayList<>(networkGraph);
        Set<Graph> newGraphList = new LinkedHashSet<>();
        List<GraphParameters> arrayListWithGraphParameters = new ArrayList();

        int n;
        int numberOfLastEvent = networkGraph.size() - 1;
        n = networkGraphList.get(numberOfLastEvent).j;

        int tEi;
        int tEj;

        Graph graph0 = new Graph();
        graph0.usS = new UsS(0,0);
        graph0.i = 0;
        graph0.j = 1;
        MinTime minTime0 = new MinTime(0);
        graph0.minTime = minTime0;
        MaxTime maxTime0 = new MaxTime(0, 0);
        graph0.maxTime = maxTime0;
        newGraphList.add(graph0);

        for (int k = 0; k < networkGraphList.size(); k++) {
            if (k == 0) {
                tEi = 0;
                Graph graphk = networkGraphList.get(k);

                tEj = tEi + graphk.usS.storyPoint;
                MinTime minTimeOb = new MinTime(tEi, tEj);
                graphk.minTime = minTimeOb;
                MaxTime maxTime = new MaxTime(0, 0);
                graphk.maxTime = maxTime;
                newGraphList.add(graphk);
            } else {
                for (int i = 1; i <= n; i++) {
                    for (int j = 2; j <= n; j++) {
                        if (networkGraphList.get(k).usS.previousStories.size() == 1) {
                            int a1 = networkGraphList.get(k).usS.previousStories.get(0).number; // № попередньої роботи
                            if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i == 1) {
                                tEi = networkGraphList.get(a1).minTime.tEi;
                                tEj = tEi + networkGraphList.get(k).usS.storyPoint;
                                MinTime minTime2 = new MinTime(tEi, tEj);
                                networkGraphList.get(k).minTime = minTime2;
                                newGraphList.add(networkGraphList.get(k));
                            } else if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i > 1) {
                                UsS a = networkGraphList.get(k).usS.previousStories.get(0);
                                for (Graph graph : networkGraphList) {
                                    if (graph.usS.equals(a)) {
                                        int max = graph.minTime.tEj;
                                        tEj = max + networkGraphList.get(k).usS.storyPoint;
                                        MinTime minTime3 = new MinTime(max, tEj);
                                        networkGraphList.get(k).minTime = minTime3;
                                        newGraphList.add(networkGraphList.get(k));
                                    }
                                }
                            }
                        } else if (networkGraphList.get(k).usS.previousStories.size() > 1) {
                            UsS a = networkGraphList.get(k).usS.previousStories.get(0);
                            if (i == networkGraphList.get(k).i && networkGraphList.get(k).j == j && i > 1) {
                                for (Graph graph : networkGraphList) {
                                    if (graph.usS.equals(a)) {
                                        int eI = graph.j;
                                        int eJ = networkGraphList.get(k).i;
                                        int max = graph.minTime.tEj;

                                        for (int h = 1; h < networkGraphList.get(k).usS.previousStories.size(); h++) {
                                            UsS a1 = networkGraphList.get(k).usS.previousStories.get(h);
                                            for (Graph graph1 : networkGraphList) {
                                                if (graph1.usS.equals(a1)) {
                                                    int eI1 = graph1.j;
                                                    int max1 = graph1.minTime.tEj;
                                                    if (max1 > max) {
                                                        max = max1;
                                                        eI = eI1;
                                                    }
                                                }
                                            }
                                        }
                                        MinTime minTime4 = new MinTime(max, max);
                                        Graph newGraph = new Graph(new UsS(0, 0), eI, eJ);
                                        newGraph.minTime = minTime4;
                                        newGraphList.add(newGraph);

                                        tEj = max + networkGraphList.get(k).usS.storyPoint;
                                        MinTime minTime5 = new MinTime(max, tEj);

                                        networkGraphList.get(k).minTime = minTime5;
                                        newGraphList.add(networkGraphList.get(k));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println();

        for (Graph g : newGraphList) {
            System.out.println(g.toString() + " " + g.minTime.tEi + " " + g.minTime.tEj);
        }

        //calculate Late Events

        Set<Graph> graphWithLateEvents = new LinkedHashSet<>();
        Collections.reverse(networkGraphList);

        networkGraphList.get(0).maxTime = new MaxTime(networkGraphList.get(0).minTime.tEj, networkGraphList.get(0).minTime.tEj);
        graphWithLateEvents.add(networkGraphList.get(0));
        int tLi;
        int tLj;
        for (int k = 0; k < networkGraphList.size(); k++) {
            for (int s = 1; s < networkGraphList.size(); s++) {
                if (networkGraphList.get(k).i == networkGraphList.get(s).j) {
                    tLi = networkGraphList.get(k).maxTime.tLi - networkGraphList.get(k).usS.storyPoint;
                    tLj = tLi - networkGraphList.get(s).usS.storyPoint;
                    MaxTime maxTime = new MaxTime(tLi, tLj);
                    networkGraphList.get(s).maxTime = maxTime;
                    graphWithLateEvents.add(networkGraphList.get(s));
                }

            }
        }

//        Graph graphZero = new Graph(new UsS(0, 0), 0, 1);
//        graphZero.maxTime = new MaxTime(0, 0);
//        graphWithLateEvents.add(graphZero);
//
//        graphWithLateEvents.add(graph0);
        System.out.println(graphWithLateEvents.size());
        for (Graph gL : graphWithLateEvents) {
            for (Graph gE : newGraphList) {
                if (gL.j == gE.j) {
                    gE.maxTime = gL.maxTime;
                }
            }
        }

        System.out.println();

        for (Graph g : newGraphList) {
            System.out.println(g.toString() + " " + g.minTime.tEi + " " + g.maxTime.tLi);
        }


        for (Graph g : newGraphList) {
            GraphParameters graphParameters = new GraphParameters(g.j, g.minTime.tEj, g.maxTime.tLi);
            arrayListWithGraphParameters.add(graphParameters);
        }

        System.out.println();

        criticalPathAndTimeReserves(arrayListWithGraphParameters);

        Set<Graph> transferListToSet = new LinkedHashSet<>(networkGraphList);

        resultsOfManagementSystem(arrayListWithGraphParameters,transferListToSet);

        for (GraphParameters graphParameters : arrayListWithGraphParameters) {
            System.out.println(graphParameters.toString());
        }

        return arrayListWithGraphParameters;
    }

    public List<Graph> resultsOfManagementSystem (List<GraphParameters> arrayListWithGraphParameters, Set<Graph> newGraphList){
        for (GraphParameters gp: arrayListWithGraphParameters){
            for (Graph g: newGraphList){
                if (gp.event == g.i){
                    g.iMinTime = gp.eventTE;
                    g.iMaxTime = gp.eventTL;
                }
                if (gp.event == g.j){
                    g.jMinTime = gp.eventTE;
                    g.jMaxTime = gp.eventTL;
                }
            }
        }

        for (Graph g: newGraphList){
            System.out.println(g);
        }

        List<Graph> newFullGraphList = new ArrayList<>(newGraphList);
        for (Graph graph:  newFullGraphList){
            graph.reserveFull = graph.jMaxTime - graph.iMinTime - graph.usS.storyPoint;
            graph.reserveFree = graph.jMinTime - graph.iMinTime - graph.usS.storyPoint;
            graph.reserveIndependent = graph.jMinTime - graph.iMaxTime - graph.usS.storyPoint;
        }

        System.out.println();
        for (Graph g: newFullGraphList){
            System.out.println(g);
        }

        return newFullGraphList;
    }

    public void criticalPathAndTimeReserves(List<GraphParameters> graphParametrsList) {
        for (GraphParameters g : graphParametrsList) {
            if (g.eventTE == g.eventTL) {
                g.isCritical = 0;
            } else {
                g.isCritical = g.eventTL - g.eventTE;
            }
        }
    }

    @GetMapping("/")
    public String showForm() {
        return "usSForm";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("usS", new UsS());
        return "usSForm";
    }

    @PostMapping("/add")
    public String addObject(UsS usS, String previousStoriesString, Model model) {
            usS.setPreviousStoriesStr(previousStoriesString, usSList);
            usSList.add(usS);
            //calculateEarlyEvents(graph.buildNetworkGraph(usSList));
        return "redirect:/form";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("usSList", usSList);

        model.addAttribute("networkGraph", graph.buildNetworkGraph(usSList));
        graphSet.addAll(graph.buildNetworkGraph(usSList));
        //graph.calculateEarlyAndLateEvents(graphSet);

        //calculateLateEvents(graph.buildNetworkGraph(usSList));
        return "usSList";
    }

    @GetMapping("/networkGraphParameters")
    public String networkGraphPar(Model model) {
//        model.addAttribute("networkGraph", graphSet);
//        model.addAttribute("networkGraph", graph.buildNetworkGraph(usSList));
        model.addAttribute("graphParametersList", calculateEarlyEvents(graphSet));
        model.addAttribute("networkGraph", resultsOfManagementSystem(calculateEarlyEvents(graphSet), graph.buildNetworkGraph(usSList)));
        return "networkParameters";
    }

}
