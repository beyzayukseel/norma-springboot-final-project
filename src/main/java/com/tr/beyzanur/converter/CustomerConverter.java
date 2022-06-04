package com.tr.beyzanur.converter;

import com.tr.beyzanur.dto.request.CreateCustomerDto;
import com.tr.beyzanur.dto.request.UpdateCustomerDto;
import com.tr.beyzanur.dto.response.CustomerAddressResponseDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.Account;
import com.tr.beyzanur.model.Customer;
import com.tr.beyzanur.model.CustomerAddress;
import com.tr.beyzanur.util.generator.NumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerConverter {

    private final UserConverter userConverter;

    public Customer convertToEntity(CreateCustomerDto dto) {
        Customer customer = new Customer();
        customer.setCustomerNumber(NumberGenerator.accountNumberGenerator());
        customer.setIsDeleted(Boolean.FALSE);
        customer.setUser((userConverter.convertToEntity(dto.getCreateUserDto())));


        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(customer);

        if (dto.getCustomerAddressResponseDto() != null){
            BeanUtils.copyProperties(dto.getCustomerAddressResponseDto(),customerAddress);
        }

        customer.setCustomerAddress(customerAddress);
        return customer;
    }

    public CustomerResponseDto convertToDto(Customer customer){
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setCustomerNumber(customer.getCustomerNumber());
        customerResponseDto.setIsDeleted(customer.getIsDeleted());
        customerResponseDto.setEmail(customer.getEmail());

        CustomerAddressResponseDto customerAddressResponseDto = new CustomerAddressResponseDto();

        if (customer.getCustomerAddress() != null){
            BeanUtils.copyProperties(customer.getCustomerAddress(),customerAddressResponseDto);
        }
        customerResponseDto.setCustomerAddressResponseDto(customerAddressResponseDto);
        return customerResponseDto;
    }

    public Customer convertToEntity(UpdateCustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCustomerNumber(dto.getCustomerNumber());
        customer.setIsDeleted(dto.getIsDeleted());
        customer.setUser((userConverter.convertToEntity(dto.getCreateUserDto())));

        CustomerAddress customerAddress = new CustomerAddress();
        if (dto.getCustomerAddressResponseDto() != null){
            BeanUtils.copyProperties(dto.getCustomerAddressResponseDto(),customerAddress);
        }

        customer.setCustomerAddress(customerAddress);
        return customer;
    }


    public Customer convertToEntity(CustomerResponseDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCustomerNumber(dto.getCustomerNumber());
        customer.setIsDeleted(dto.getIsDeleted());
        customer.setEmail(dto.getEmail());

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(customer);
        if (dto.getCustomerAddressResponseDto() != null){
            BeanUtils.copyProperties(dto.getCustomerAddressResponseDto(),customerAddress);
        }

        customer.setCustomerAddress(customerAddress);
        return customer;
    }
}
