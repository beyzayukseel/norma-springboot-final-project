package com.tr.beyzanur.controller;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CardResponseDto;
import com.tr.beyzanur.service.CardService;
import com.tr.beyzanur.util.constant.MessageResponse;
import com.tr.beyzanur.validator.implementation.EntityIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/api/v1/cards")
public class CardController {

    private final CardService cardService;
    private final EntityIdValidator entityIdValidator;

    @PostMapping()
    public ResponseEntity<?> createCard(@RequestBody CreateCardRequest createCardRequest) {
        cardService.demandCardCreation(createCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse( "Cart demand creation saved successfully!"));
    }

    @GetMapping("/customer-cards/{id}")
    public ResponseEntity<List<CardResponseDto>> getCardsByCustomerId(@PathVariable Long id) {
        entityIdValidator.validate(id, "card");
        return ResponseEntity.ok(cardService.getCardsByCustomerId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<CardResponseDto> approveAccountCreationDemand(
            @PathVariable Long id) {
        entityIdValidator.validate(id, "card");
        return ResponseEntity.ok(cardService.approveCardCreationDemand(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/deny")
    public ResponseEntity<CardResponseDto> denyAccountCreationDemand(
            @PathVariable Long id) {
        entityIdValidator.validate(id, "card");
        return ResponseEntity.ok(cardService.denyCardCreationDemand(id));
    }

    @PostMapping( "/addAccount/{accountId}/{cardId}")
    public ResponseEntity<?> addAccountToCart(@PathVariable Long accountId, @PathVariable Long cardId){
        entityIdValidator.validate(accountId, "account");
        entityIdValidator.validate(cardId, "card");
        cardService.addAccountToCard(accountId,cardId);
        return ResponseEntity.ok(new MessageResponse( "Account added successfully!"));
    }

    @DeleteMapping( "/deleteAccount/{accountId}/{cardId}")
    public ResponseEntity<?> removeAccountFromCart(@PathVariable Long accountId, @PathVariable Long cardId){
        entityIdValidator.validate(accountId, "account");
        entityIdValidator.validate(cardId, "card");
        return ResponseEntity.ok(new MessageResponse( "Account removed successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getAccountById(@PathVariable Long id) {
        entityIdValidator.validate(id, "account");
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAccount(@PathVariable Long id) {
        entityIdValidator.validate(id, "card");
        cardService.deleteCard(id);
        return ResponseEntity.ok(new MessageResponse( "card deleted successfully!"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteAccount(@PathVariable Long id) {
        entityIdValidator.validate(id, "card");
        cardService.hardDeleteCard(id);
        return ResponseEntity.ok(new MessageResponse( "card hard deleted successfully!"));
    }
}
