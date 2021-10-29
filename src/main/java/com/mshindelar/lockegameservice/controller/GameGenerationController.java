package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.service.GameGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/generations")
@Slf4j
public class GameGenerationController {

    @Autowired
    private GameGenerationService gameGenerationService;

    private static Logger logger = LoggerFactory.getLogger(GameGenerationController.class);

    @GetMapping("/game-ids/all")
    private List<Integer> getAllGenerationIds() {
        return this.gameGenerationService.getAllGenerationIds();
    }

    @GetMapping("{generationId}")
    private GameGeneration getByGenerationId(@PathVariable("generationId") int generationId) { return this.gameGenerationService.getByGenerationId(generationId); }
}
