package provider;

import com.github.javafaker.Faker;
import constants.DataProviderNames;
import constants.ErrorMessages;
import constants.SuccessMessages;
import org.testng.annotations.DataProvider;

public class TestCasesProvider
{
    private static final Faker faker = new Faker();

    @DataProvider(name = DataProviderNames.CREATE_TEST_CASES_WITH_VALID_DATA)
    public static Object[][] createTestCasesWithValidData()
    {
        return new Object[][]
                {
                        {"With One Test Step",
                                faker.lorem().sentence(),
                                faker.lorem().sentence(),
                                "Success",
                                new String[] {"Step: Default"},
                                true,
                                SuccessMessages.CREATE_TEST_CASE
                        },
                        {"With Multiple Test Step",
                                faker.lorem().sentence(),
                                faker.lorem().sentence(),
                                "Success",
                                new String[]{
                                        "Step 1: Open",
                                        "Step 2: Login",
                                        "Step 3: Verify"
                                },
                                false,
                                SuccessMessages.CREATE_TEST_CASE
                        }
                };
    }

    @DataProvider(name = DataProviderNames.CREATE_TEST_CASES_WITH_INVALID_DATA)
    public static Object[][] createTestCasesWithInvalidData()
    {
        return new Object[][]{
                {
                        "With missing title",
                        "",
                        faker.lorem().sentence(),
                        "Fail",
                        new String[]{"Step 1: Do something"},
                        true,
                        ErrorMessages.TITLE_REQUIRED
                },
                {
                        "With missing expected result",
                        faker.lorem().sentence(),
                        faker.lorem().sentence(),
                        "",
                        new String[]{"Step 1: Do something"},
                        false,
                        ErrorMessages.EXPECTED_RESULT_REQUIRED
                },
                {
                        "With missing steps",
                        faker.lorem().sentence(),
                        faker.lorem().sentence(),
                        "Fail",
                        new String[]{},
                        true,
                        ErrorMessages.STEPS_REQUIRED
                }
        };
    }
}
