package com.mshindelar.lockegameservice.service;
import com.mshindelar.lockegameservice.controller.GameGenerationController;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameGenerationService {

    @Autowired
    private GameGenerationRepository gameGenerationRepository;

    private static Logger logger = LoggerFactory.getLogger(GameGenerationController.class);

    public List<Integer> getAllGenerationIds() {
        return this.gameGenerationRepository.findAll()
                .stream()
                .map(GameGeneration::getGenerationId)
                .sorted()
                .collect(Collectors.toList());
    }

    public GameGeneration getByGenerationId(int generationId) {
        return this.gameGenerationRepository.findByGenerationId(generationId)
                .orElse(null);
    }
}