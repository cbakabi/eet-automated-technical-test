package stepDefs;

import org.springframework.test.context.ContextConfiguration;

@io.cucumber.spring.CucumberContextConfiguration
@ContextConfiguration(locations = "file:src/test/resources/cucumber.xml")
public class CucumberContextConfiguration {

}
