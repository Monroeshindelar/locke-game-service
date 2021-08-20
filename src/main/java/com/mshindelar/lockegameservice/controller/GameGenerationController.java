package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.configuration.LockeGameServiceConfiguration;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import com.mshindelar.lockegameservice.repository.SquadlockeRepository;
import com.mshindelar.lockegameservice.service.GameGenerationService;
import com.mshindelar.lockegameservice.service.SquadlockeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
