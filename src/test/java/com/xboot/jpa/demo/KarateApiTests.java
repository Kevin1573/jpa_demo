package com.xboot.jpa.demo;

import com.intuit.karate.core.Feature;
import com.intuit.karate.junit5.Karate;
import cucumber.api.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * 注释
 *
 * @author xboot
 **/
@SpringBootTest(
        classes = JpaDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
public class KarateApiTests  {


    @Karate.Test
    public Karate testUnitTest() {
        return Karate.run("sample.feature").relativeTo(getClass());
//        return Karate.run("classpath:features/test1.feature")
//                .outputCucumberJson(true);
//        return new Karate()
//                .features(Feature.read("src/test/resources/features"))
//                .tags("@unitTest")
//                .relativeTo(getClass())
//                .outputCucumberJson(true);
    }
}
