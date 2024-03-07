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
    private List<UsS> usSList = new ArrayList<>();
    Graph graph = new Graph();


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
        return "redirect:/form";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("usSList", usSList);
        model.addAttribute("networkGraph", graph.buildNetworkGraph(usSList));
        return "usSList";
    }

    @GetMapping("/networkGraphParameters")
    public String networkGraphPar(Model model) {
//        model.addAttribute("usSList", usSList);
//        model.addAttribute("networkGraph", graph.buildNetworkGraph(usSList));
        return "networkParameters";
    }

}
