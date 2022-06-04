package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.converter.CustomerAddressConverter;
import com.tr.beyzanur.converter.CustomerConverter;
import com.tr.beyzanur.converter.UserConverter;
import com.tr.beyzanur.dto.request.CreateCustomerDto;
import com.tr.beyzanur.dto.request.UpdateCustomerDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.model.Customer;
import com.tr.beyzanur.model.User;
import com.tr.beyzanur.repository.CustomerRepository;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.service.CardService;
import com.tr.beyzanur.service.CustomerService;
import com.tr.beyzanur.service.UserService;
import com.tr.beyzanur.util.generator.FirstBankPasswordGenerator;
import com.tr.beyzanur.util.generator.NumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserDetailsImpl userDetails;
    private final CustomerConverter customerConverter;
    private final UserService userService;
    private final CardService cardService;
    private final AccountService accountService;
    private final CustomerAddressConverter customerAddressConverter;

    @Override
    public String createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = new Customer();
        customer.setCustomerNumber(NumberGenerator.accountNumberGenerator());
        customer.setIsDeleted(Boolean.FALSE);
        customer.setCustomerAddress(customerAddressConverter.convertToEntity(createCustomerDto.getCustomerAddressResponseDto()));
        String password = FirstBankPasswordGenerator.firstBankPasswordGenerator();
        createCustomerDto.getCreateUserDto().setPassword(password);
        User user = userDetails.register(createCustomerDto.getCreateUserDto());
        customer.setUser(user);
        customerRepository.save(customer);
        log.info("Customer ID -> {} date: {} saved", customer.getId(), new Date());
        return password;
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        log.info("Customer ID -> {} date: {} getting", id, new Date());
        return customerConverter.convertToDto(customer);
    }

    @Override
    public void updateCustomer(UpdateCustomerDto updateCustomerDto) {
        customerRepository.findById(updateCustomerDto.getId()).orElseThrow();
        Customer customer = customerConverter.convertToEntity(updateCustomerDto);
        customerRepository.save(customer);
        log.info("Customer ID -> {} date: {} updated", updateCustomerDto.getId(), new Date());
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers(int pageSize, int pageNumber) {
        Pageable paged = PageRequest.of(pageNumber, pageSize);
        return customerRepository.findAll(paged)
                .stream()
                .map(customerConverter::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new ServiceOperationException.NotFoundException("Customer not found"));

        User user = new User();
        user.setId(customer.getUser().getId());

        if (customer.getIsDeleted()) {
            throw new ServiceOperationException.AlreadyDeletedException("Customer has already been deleted");
        }
        if (cardService.checkCustomerHasCreditCardDebt(id)) {
            throw new ServiceOperationException.CanNotDeletedException("You can not delete because customer has credit" +
                    "card debit");
        }
        if (accountService.checkCustomerHasBalance(id)) {
            throw new ServiceOperationException.CanNotDeletedException(" you can not delete because customer has balance" +
                    "in her/his account");
        }
        log.info("Customer ID -> {} date: {} deleted", customer.getId(), new Date());
        customer.setIsDeleted(Boolean.TRUE);
        user.setIsBlocked(Boolean.TRUE);
        userService.saveUser(user);
        customerRepository.save(customer);
    }
}
