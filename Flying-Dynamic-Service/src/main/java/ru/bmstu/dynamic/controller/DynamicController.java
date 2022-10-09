package ru.bmstu.dynamic.controller;

import org.springframework.web.bind.annotation.*;
import ru.bmstu.dynamic.model.DynamicParameters;
import ru.bmstu.dynamic.service.DynamicService;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {

    private final DynamicService dynamicService;

    public DynamicController(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    @GetMapping("/high")
    public DynamicParameters getParametersByHigh(@RequestParam Double high) {
        if (high < -2000 && high > 90000){
            throw new IllegalArgumentException("Значение не может быть в таком диапазоне");
        }
        return dynamicService.getParameterByHigh(high);
    }
}
