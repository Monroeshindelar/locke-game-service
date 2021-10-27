package com.mshindelar.lockegameservice;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.Encounter;
import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterMode;
import com.mshindelar.lockegameservice.service.EncounterGenerationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class LockeGameServiceApplicationTests {

	@Autowired
	private EncounterGenerationService encounterGenerationService;

	@Test
	void contextLoads() {
	}
}
