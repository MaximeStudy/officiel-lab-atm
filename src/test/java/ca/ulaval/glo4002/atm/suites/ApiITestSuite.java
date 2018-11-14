package ca.ulaval.glo4002.atm.suites;


import ca.ulaval.glo4002.atm.AtmServer;
import ca.ulaval.glo4002.atm.contexts.ApiOnlyTestContext;
import ca.ulaval.glo4002.atm.rest.WithdrawResourceRestTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ WithdrawResourceRestTest.class })
public class ApiITestSuite {

    public static final int TEST_SERVER_PORT = 9292;

    private static AtmServer atmServer;

    @BeforeClass
    public static void setUpClass() {
        atmServer = new AtmServer();
        atmServer.start(TEST_SERVER_PORT, new ApiOnlyTestContext(), false);
    }

    @AfterClass
    public static void tearDownClass() {
        if (atmServer != null) {
            atmServer.stop();
        }
    }

}