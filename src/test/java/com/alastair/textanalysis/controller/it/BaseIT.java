package com.alastair.textanalysis.controller.it;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alastair.textanalysis.TextAnalysisConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TextAnalysisConfig.class })
@ActiveProfiles(profiles = "noRunner")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Rollback
public class BaseIT {

}
