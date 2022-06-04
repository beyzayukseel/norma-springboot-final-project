package com.tr.beyzanur.service;


import com.tr.beyzanur.dto.request.CreateCustomerDto;
import com.tr.beyzanur.dto.request.UpdateCustomerDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CustomerService {
    String createCustomer(CreateCustomerDto createCustomerDto);
    CustomerResponseDto getCustomerById(Long id);

    void updateCustomer(UpdateCustomerDto updateCustomerDto);

    List<CustomerResponseDto> getAllCustomers(int pageSize, int pageNumber);

    void deleteCustomer(Long id);
}
