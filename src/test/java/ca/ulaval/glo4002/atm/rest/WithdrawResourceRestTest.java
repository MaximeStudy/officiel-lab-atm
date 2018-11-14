package ca.ulaval.glo4002.atm.rest;

import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.atm.application.ServiceLocator;
import ca.ulaval.glo4002.atm.application.banking.BankingService;
import ca.ulaval.glo4002.atm.application.banking.ReceiptDto;
import ca.ulaval.glo4002.atm.application.banking.WithdrawalRequest;
import ca.ulaval.glo4002.atm.domain.accounts.AccountNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

public class WithdrawResourceRestTest extends RestTestBase {
    private static final int ACCOUNT_NUMBER = 123123;
    private BankingService bankingServiceMock;
    
    @Before
    public void setUp() {
        bankingServiceMock = ServiceLocator.resolve(BankingService.class);
    }
    
    @Test
    public void withdrawMoneyOnAnAccountWithMoneyResultsInAnAcceptedTransaction() {
        WithdrawalRequest request = new WithdrawalRequest(100);
        ReceiptDto dto = new ReceiptDto(true, 100);
        willReturn(dto).given(bankingServiceMock).withdrawMoney(anyInt(), any());

        givenBaseRequest().body(request)
                .when().put("/accounts/{accountNumber}/withdraw", buildPathParams("accountNumber", ACCOUNT_NUMBER))
                .then().body("accepted", equalTo(dto.accepted))
                .and().body("amount", equalTo((float) dto.amount));
    }
    
    @Test
    public void withdrawMoneyOnAccountThatDoesntExistResultsInA404Error() {
        WithdrawalRequest request = new WithdrawalRequest(100);
        AccountNotFoundException exception = new AccountNotFoundException();
        willThrow(exception).given(bankingServiceMock).withdrawMoney(anyInt(), any());

        givenBaseRequest().body(request)
                .when().put("/accounts/{accountNumber}/withdraw", buildPathParams("accountNumber", ACCOUNT_NUMBER))
                .then().statusCode(Status.NOT_FOUND.getStatusCode());
    }
    
}
