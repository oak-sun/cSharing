package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import platform.model.Json;
import platform.service.Services;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestControllers {

    final String DATE_F = "yyyy/MM/dd HH:mm:ss";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_F);

    @Autowired
    Services service;


    @GetMapping("/code/{id}")
    public ModelAndView getCode(@PathVariable int id,
                                HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView();
        model.addObject("code",
                         service.findCodeJsonById(id).getCode());
        model.addObject("date",
                         service.findCodeJsonById(id).getDate());
        model.setViewName("Code");
        return model;
    }

    @GetMapping("/api/code/{id}")
    @ResponseBody
    public HashMap<String, String> codeAPI(@PathVariable int id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code",
                 service.findCodeJsonById(id).getCode());
        map.put("date",
                 service.findCodeJsonById(id).getDate());
        return map;
    }

    @GetMapping("/code/new")
    public ModelAndView newCodeHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        return new ModelAndView("new");
    }

    @GetMapping("/api/code/latest")
    @ResponseBody
    public List<Json> latestApi() {
        List<Json> latest = service.findAll();
        Collections.reverse(latest);
        return latest
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/code/new")
    public HashMap<String, String> postCode(@RequestBody Json json) {
        Json create = service.save(new Json(json.getCode(),
                                            LocalDateTime
                                                    .now()
                                                    .format(dtf)));
        HashMap<String, String> map = new HashMap<>();
        map.put("id",
                create.getId().toString());
        return map;
    }
}