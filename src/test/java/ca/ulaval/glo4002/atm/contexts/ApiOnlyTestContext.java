package ca.ulaval.glo4002.atm.contexts;

import ca.ulaval.glo4002.atm.application.ServiceLocator;
import ca.ulaval.glo4002.atm.application.banking.BankingService;
import ca.ulaval.glo4002.atm.contexts.Context;

import static org.mockito.Mockito.mock;

public class ApiOnlyTestContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.registerSingleton(BankingService.class, mock(BankingService.class));
    }

}
