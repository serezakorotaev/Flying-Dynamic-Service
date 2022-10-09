package ru.bmstu.dynamic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.dynamic.service.GraphService;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphService service;

    public GraphController(GraphService service) {
        this.service = service;
    }

    @GetMapping
    public void drawing() {
        service.getAllDraws();
    }
}
