package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class AllureUtil {
    @Step("{0}")
    public static void logStep(final String message){
        Allure.step(message);
        System.out.println(message);
    }

    @Step("{0}")
    public static void beforeSuiteLog(final String message){
        Allure.step(message);
        System.out.println(message);
    }
}
