package com.tr.beyzanur.service;

import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.dto.response.BalanceDto;
import com.tr.beyzanur.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponseDto getAccountById(Long id);

    void demandAccountCreation(AccountRequestDto accountRequestDto);

    List<AccountResponseDto> getDemandAccountsCreationList();

    void saveAccount(Account account);

    List<AccountResponseDto> getDenyAccountsCreationList();

    AccountResponseDto approveAccountCreationDemand(Long accountId);

    void checkAccountsCustomerIdsAreMatch(UUID sourceIbanNumber, UUID sourceDestinationNumber);

    AccountResponseDto denyAccountCreationDemand(Long accountId);

    List<AccountResponseDto> getAccountsByCustomerId(Long customerId);

    BalanceDto getCustomerBalance(Long customerId);

    Boolean exitsByIban(UUID iban);

    Account findByIban(UUID iban);


    void deleteAccount(Long id);

    void hardDeleteAccount(Long id);

    Boolean checkCustomerHasBalance(Long customerId);

    Boolean existAccountById(Long accountId);
}
