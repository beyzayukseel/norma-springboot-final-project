package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.converter.AccountConverter;
import com.tr.beyzanur.converter.CustomerConverter;
import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.dto.response.BalanceDto;
import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Card;
import com.tr.beyzanur.model.enums.AccountStatus;
import com.tr.beyzanur.model.enums.AccountType;
import com.tr.beyzanur.model.enums.CardStatus;
import com.tr.beyzanur.model.enums.CardType;
import com.tr.beyzanur.repository.AccountRepository;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.util.generator.NumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final CustomerConverter customerConverter;

    @Override
    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Account not found"));
        AccountStatus accountStatus = account.getAccountStatus();
        if (!(accountStatus == AccountStatus.APPROVED)) {
            throw new ServiceOperationException.NotValidException("Account is not valid for use");
        }
        log.info("Account ID -> {} date: {} getting", account.getId(), new Date());

        return accountConverter.convertToResponseDto(account);

    }

    @Transactional
    @Override
    public synchronized void demandAccountCreation(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setAccountNumber(NumberGenerator.accountNumberGenerator());
        account.setAccountType(accountRequestDto.getAccountType());
        account.setAccountStatus(AccountStatus.PENDING);
        account.setBalance(account.getBalance());
        account.setCustomer(customerConverter.convertToEntity(accountRequestDto.getCustomerResponseDto()));
        account.setCreatedDate(LocalDate.now());
        account.setBankCode(NumberGenerator.accountNumberGenerator());
        account.setCurrency(accountRequestDto.getCurrency());
        account.setIsDeleted(Boolean.FALSE);

        Long customerId = account.getCustomer().getId();
        Boolean checkCustomerHasAccount = accountRepository.existsByCustomerId(customerId);

        AccountType accountType = accountRequestDto.getAccountType();

        accountRepository.save(account);

        log.info("Account ID -> {} date: {} saved", account.getId(), new Date());

        if (!(checkCustomerHasAccount)) {
            if (accountType == AccountType.DEPO) {
                Card card = new Card();
                card.setCustomer(customerConverter.convertToEntity(accountRequestDto.getCustomerResponseDto()));
                card.setCardStatus(CardStatus.PENDING);
                card.setIsDeleted(Boolean.FALSE);
                card.setBoundary(BigDecimal.ZERO);
                card.setCardNumber(NumberGenerator.accountNumberGenerator());
                card.setCardType(CardType.BANK);
                card.setDebt(BigDecimal.ZERO);
                System.out.println(account.getId());
                account.getCards().add(card);
            }
        }
        accountRepository.save(account);
    }

    @Override
    public List<AccountResponseDto> getDemandAccountsCreationList() {
        return accountRepository.getAccountsByAccountStatus(AccountStatus.PENDING)
                .stream()
                .map(accountConverter::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAccount(Account account){
        log.info("Account ID -> {} date: {} saved", account.getId(), new Date());
        accountRepository.save(account);
    }

    @Override
    public List<AccountResponseDto> getDenyAccountsCreationList() {
        return accountRepository.getAccountsByAccountStatus(AccountStatus.DENY)
                .stream()
                .map(accountConverter::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto approveAccountCreationDemand(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setAccountStatus(AccountStatus.APPROVED);
        accountRepository.save(account);
        return accountConverter.convertToDto(account, AccountStatus.APPROVED);
    }

    @Override
    public void checkAccountsCustomerIdsAreMatch(UUID sourceIbanNumber,UUID destinationIbanNumber){
         Account source = accountRepository.findByIban(sourceIbanNumber);
         Account destination = accountRepository.findByIban(destinationIbanNumber);

         if (source.getAccountType() != destination.getAccountType()){
             if (!source.getCustomer().getId().equals(destination.getCustomer().getId())){
                    throw new ServiceOperationException.NotValidException("Customer ids are not match, please try with" +
                            "with different iban number");
             }
         }
    }

    @Override
    public AccountResponseDto denyAccountCreationDemand(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setAccountStatus(AccountStatus.DENY);
        accountRepository.save(account);
        return accountConverter.convertToDto(account, AccountStatus.DENY);
    }

    @Override
    public List<AccountResponseDto> getAccountsByCustomerId(Long userId) {
        return accountRepository.getAccountsByCustomerId(userId)
                .stream()
                .map(accountConverter::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BalanceDto getCustomerBalance(Long customerId) {
        List<Account> accountList = accountRepository.getAccountsByCustomerId(customerId);
        BalanceDto balanceDto = new BalanceDto();
        for (Account account : accountList) {
            if (account.getAccountType() == AccountType.DEPO) {
                balanceDto.setTotal(balanceDto.getTotal().add(account.getBalance()));
                balanceDto.setDepositBalance(balanceDto.getDepositBalance().add(account.getBalance()));
            } else {
                balanceDto.setTotal(balanceDto.getTotal().add(account.getBalance()));
                balanceDto.setSavingBalance(balanceDto.getSavingBalance().add(account.getBalance()));
            }
        }
        return balanceDto;
    }

    @Override
    public Boolean checkCustomerHasBalance(Long customerId){
        List <Account> accountList = accountRepository.getAccountsByCustomerId(customerId);
        for (Account account : accountList){
            int accountBalanceCompare =account.getBalance().compareTo(BigDecimal.ZERO);
            if ( accountBalanceCompare == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean existAccountById(Long accountId) {
        return accountRepository.existsById(accountId);
    }

    @Override
    public Boolean exitsByIban(UUID iban){
        return accountRepository.existsByIban(iban);
    }

    @Override
    public Account findByIban(UUID iban){
        return accountRepository.findByIban(iban);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Account not found"));

        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new ServiceOperationException.CanNotDeletedException("Account can not deleted");
        }

        if (account.getIsDeleted()) {
            throw new ServiceOperationException.AlreadyDeletedException("Account has already been deleted");
        } else {
            log.info("Account ID -> {} date: {} deleted", account.getId(), new Date());
            account.setIsDeleted(Boolean.TRUE);
            accountRepository.save(account);
        }
    }

    @Override
    public void hardDeleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Account not found"));
        log.info("Account ID -> {} date: {} hard deleted", account.getId(), new Date());
        accountRepository.deleteById(account.getId());
    }
}
