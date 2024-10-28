package helpers;

import io.qameta.allure.Step;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggingAssertion {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAssertion.class);
    @Step("Asserting that {message}")
    public static <T> void assertThat(T actual, Matcher<? super T> matcher, String message) {
        logger.info("Asserting that {} {}", actual, matcher);
        MatcherAssert.assertThat(actual, matcher);
    }
}
