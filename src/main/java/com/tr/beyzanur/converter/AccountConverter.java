package com.tr.beyzanur.converter;

import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Customer;
import com.tr.beyzanur.model.enums.AccountStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    public Account convertToEntity(AccountRequestDto dto) {
        Account account = new Account();
        if (dto != null){
            BeanUtils.copyProperties(dto,account);
        }
        return account;
    }

    public Account convertToEntityFromResponse(AccountResponseDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getCustomerResponseDto().getId());

        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountStatus(dto.getAccountStatus());
        account.setIban(dto.getIban());
        account.setCustomer(customer);
        account.setBalance(dto.getBalance());
        account.setCurrency(dto.getCurrency());
        account.setAccountType(dto.getAccountType());
        account.setIsDeleted(dto.getIsDeleted());
        account.setBankCode(dto.getBankCode());
        account.setCreatedDate(dto.getCreatedDate());
        return account;
    }

    public AccountResponseDto convertToResponseDto (Account entity) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        if (entity.getCustomer() != null){
            BeanUtils.copyProperties(entity.getCustomer(), customerResponseDto);
        }

        if (entity != null){
            BeanUtils.copyProperties(entity,accountResponseDto);
        }
        accountResponseDto.setCustomerResponseDto(customerResponseDto);
        return accountResponseDto;
    }

    public AccountResponseDto convertToDto(Account entity, AccountStatus accountStatus) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(entity.getId());
        accountResponseDto.setAccountStatus(accountStatus);
        accountResponseDto.setAccountNumber(entity.getAccountNumber());
        accountResponseDto.setAccountType(entity.getAccountType());
        accountResponseDto.setBalance(entity.getBalance());
        accountResponseDto.setCreatedDate(entity.getCreatedDate());
        accountResponseDto.setModifiedDate(entity.getModifiedDate());

        return accountResponseDto;
    }
}
