package com.mindhub.homebanking.RepositoriesTest;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mindhub.homebanking.service.CardUtils.generateUniqueAccountNumber;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CardUtilsTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void areDiferentsCVV(){
        String cvv1 = CardUtils.generateCVV();
        String cvv2 = CardUtils.generateCVV();
        assertThat(cvv1, not(equalTo(cvv2)));
    }

    @Test
    public void CVVisnotNull(){
        String cvv1 = CardUtils.generateCVV();
        assertThat(cvv1, not(equalTo(null)));
    }
    @Test
    public void CardNumberAreDiferents(){
        String CN = CardUtils.generateCardNumber();
        String CN2 = CardUtils.generateCardNumber();
        assertThat(CN, not(equalTo(CN2)));
    }
    @Test
    public void CardNumberIsNotNull(){
        String CN = CardUtils.generateCardNumber();
        assertThat(CN, not(equalTo(null)));
    }

}
