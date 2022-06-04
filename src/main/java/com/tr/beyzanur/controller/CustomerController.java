package com.tr.beyzanur.controller;
import com.tr.beyzanur.dto.request.CreateCustomerDto;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.service.CustomerService;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.validator.implementation.EntityIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final EntityIdValidator entityIdValidator;

    @PostMapping()
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(createCustomerDto));
    }

    @GetMapping( "/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        entityIdValidator.validate(id, "customer");
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(int pageSize, int pageNumber){
        return ResponseEntity.ok(customerService.getAllCustomers(pageSize, pageNumber));
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable Long id) {
        entityIdValidator.validate(id, "customer");
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(new MessageResponse("Customer deleted successfully!"));
    }
}
