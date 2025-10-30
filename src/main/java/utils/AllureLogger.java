package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AllureLogger {
    @Step("{0}")
    public static void logStep(final String message){
        System.out.println(message);
        Allure.step(message);

    }

    @Step("{0}")
    public static void beforeSuiteLog(final String message){
        System.out.println(message);
    }
}
