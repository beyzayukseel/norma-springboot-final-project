package com.tr.beyzanur.controller;

import com.tr.beyzanur.dto.request.AccountRequestDto;
import com.tr.beyzanur.dto.response.AccountResponseDto;
import com.tr.beyzanur.service.AccountService;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.validator.implementation.EntityIdValidator;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final EntityIdValidator validator;

    @PostMapping()
    public ResponseEntity<MessageResponse> createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        accountService.demandAccountCreation(accountRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse( "Account created successfully!"));
    }

    @GetMapping("/user-accounts/{id}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByCustomerId(@PathVariable Long id) {
        validator.validate(id,"account");
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<AccountResponseDto> approveAccountCreationDemand(
            @PathVariable Long id) {
        validator.validate(id,"account");
        return ResponseEntity.ok(accountService.approveAccountCreationDemand(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/deny")
    public ResponseEntity<AccountResponseDto> denyAccountCreationDemand(
            @PathVariable Long id) {
        validator.validate(id,"account");
        return ResponseEntity.ok(accountService.denyAccountCreationDemand(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {
        validator.validate(id,"account");
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        validator.validate(id,"account");
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new MessageResponse("Account deleted successfully!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> hardDeleteAccount(@PathVariable Long id) {
        validator.validate(id,"account");
        accountService.hardDeleteAccount(id);
        return ResponseEntity.ok(new MessageResponse("Account hard deleted successfully!"));
    }
}
