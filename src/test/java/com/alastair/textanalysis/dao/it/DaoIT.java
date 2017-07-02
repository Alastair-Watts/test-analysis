package com.alastair.textanalysis.dao.it;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alastair.textanalysis.TextAnalysisConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TextAnalysisConfig.class })
@ActiveProfiles(profiles = "noRunner")
public class DaoIT {

}
