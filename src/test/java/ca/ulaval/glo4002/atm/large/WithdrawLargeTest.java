package ca.ulaval.glo4002.atm.large;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import ca.ulaval.glo4002.atm.application.banking.WithdrawalRequest;
import ca.ulaval.glo4002.atm.rest.RestTestBase;

public class WithdrawLargeTest extends RestTestBase {
    @Test
    public void canWithdrawMoney() {
        WithdrawalRequest request = new WithdrawalRequest(100);

        givenBaseRequest().body(request)
                .when()
                .put("/accounts/{accountNumber}/withdraw", buildPathParams("accountNumber", ACCOUNT_NUMBER_WITH_MONEY))
                .then().statusCode(Status.OK.getStatusCode());
    }
}