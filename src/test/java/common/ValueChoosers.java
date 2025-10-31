package common;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class ValueChoosers {

    public static String getRandomTitle(){
        ArrayList<String> listOfTitles = new ArrayList<>();
        listOfTitles.add("Aleksa - Test Case Creation with Existing Title");
        listOfTitles.add("Aleksa - Preview Card Title Extraction");
        listOfTitles.add("Aleksa - Toast Message Validation on Failure");
        listOfTitles.add("Aleksa - Form Submission with Empty Fields");
        listOfTitles.add("Aleksa - Duplicate Test Case Detection");
        listOfTitles.add("Aleksa - UI Element Visibility After Submission");

        return listOfTitles.get(RandomUtils.nextInt(0, listOfTitles.size()));
    }

    public static String getRandomExpected(){
        ArrayList<String> listOfExpected = new ArrayList<>();
        listOfExpected.add("Test Case is Created with Existing Title");
        listOfExpected.add("Test Has Been Extracted With Set Preview Card");
        listOfExpected.add("Toat Message Has Been Validated On Failure");
        listOfExpected.add("Form Sumbited With Empty Fields");
        listOfExpected.add("Duplicate Test Has Been Detected");
        listOfExpected.add("UI Elements Are Vissiable");

        return listOfExpected.get(RandomUtils.nextInt(0, listOfExpected.size()));
    }

    public static String getRandomProjectTitle(){
        ArrayList<String> listOfProjectTitles = new ArrayList<>();
        listOfProjectTitles.add("Aleksa-SmartTest");
        listOfProjectTitles.add("Aleksa-REST API");
        listOfProjectTitles.add("Aleksa-BugTracker");
        listOfProjectTitles.add("Aleksa-Modular");
        listOfProjectTitles.add("Aleksa-Performance");
        listOfProjectTitles.add("Aleksa-Regression");

        return listOfProjectTitles.get(RandomUtils.nextInt(0, listOfProjectTitles.size()));
    }

    public static String getRandomTechnology(){
        ArrayList<String> listOfTechnologies = new ArrayList<>();
        listOfTechnologies.add("HTML/CSS");
        listOfTechnologies.add("Svelte");
        listOfTechnologies.add("TS");
        listOfTechnologies.add("MongoDB");
        listOfTechnologies.add("Redis");
        listOfTechnologies.add("Neo4j");
        listOfTechnologies.add("Angular");

        return listOfTechnologies.get(RandomUtils.nextInt(0, listOfTechnologies.size()));
    }

}