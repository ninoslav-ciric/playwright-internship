package providers;

import org.testng.annotations.DataProvider;

public class TestCasesProviders {
    @DataProvider(name = "NewTestCaseSuccess")
    public static Object[][] provideTestCasesSuccess()
    {
        return new Object[][]
                {
                        {"Nemanja" , "First Test" , "Succesful Test" , new String[]{"Open login page" , "Enter valid data" , "Click login"}}
                };
    }

    @DataProvider(name = "ModifyTestCaseSuccess")
    public static Object[][] provideTestCasesForModificationSuccess()
    {
        return new Object[][]
                {
                        {"Nemanja" , "a1" , "b2" , new String[]{"c3" , "d4" , "e5"}}
                };
    }

    @DataProvider(name = "TestCaseTitle")
    public static Object[][] provideTestCaseTitleSuccess()
    {
        return new Object[][]
                {
                        {"Nemanja"}
                };
    }

    @DataProvider(name = "NewTestCaseMissingTitle")
    public static Object[][] provideTestCasesMissingTitle()
    {
        return new Object[][]
                {
                        {"First Test" , "Succesful Test" , new String[]{"Open login page" , "Enter valid data" , "Click login"}}
                };
    }



}
