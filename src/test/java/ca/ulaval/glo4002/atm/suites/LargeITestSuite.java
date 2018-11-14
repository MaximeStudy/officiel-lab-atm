package ca.ulaval.glo4002.atm.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.ulaval.glo4002.atm.AtmServer;
import ca.ulaval.glo4002.atm.contexts.LargeTestContext;
import ca.ulaval.glo4002.atm.large.WithdrawLargeTest;

@RunWith(Suite.class)
@SuiteClasses({ WithdrawLargeTest.class })
public class LargeITestSuite {

    public static final int TEST_SERVER_PORT = 9292;

    private static AtmServer atmServer;

    @BeforeClass
    public static void setUpClass() {
        atmServer = new AtmServer();
        atmServer.start(TEST_SERVER_PORT, new LargeTestContext());
    }

    @AfterClass
    public static void tearDownClass() {
        if (atmServer != null) {
            atmServer.stop();
        }
    }
}
