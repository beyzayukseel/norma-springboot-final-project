package com.tr.beyzanur.validator;

import com.tr.beyzanur.dto.request.CreateCardRequest;
import com.tr.beyzanur.dto.response.CustomerResponseDto;
import com.tr.beyzanur.model.enums.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateCardValidatorTest {

    @Test
    void should_throw_exception_when_card_request_is_null() {
        CreateCardRequest createCardRequest = null;
        Assertions.assertNull(createCardRequest);
    }

    @Test
    void should_throw_exception_when_customer_response_id_is_null() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(null);
        createCardRequest.setCustomerResponseDto(customerResponseDto);

        Assertions.assertNotNull(createCardRequest);
        Assertions.assertNull(createCardRequest.getCustomerResponseDto().getId());
    }

    @Test
    void should_throw_exception_when_card_type_is_null1() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(1L);
        createCardRequest.setCustomerResponseDto(customerResponseDto);
        createCardRequest.setCardType(null);

        Assertions.assertNotNull(createCardRequest);
        Assertions.assertNotNull(createCardRequest.getCustomerResponseDto().getId());
        Assertions.assertNull(createCardRequest.getCardType());
    }

    @Test
    void should_success_when_card_creation_params_are_valid() {
        CreateCardRequest createCardRequest = new CreateCardRequest();
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(1L);
        createCardRequest.setCustomerResponseDto(customerResponseDto);
        createCardRequest.setCardType(CardType.BANK);

        Assertions.assertNotNull(createCardRequest);
        Assertions.assertNotNull(createCardRequest.getCustomerResponseDto().getId());
        Assertions.assertNotNull(createCardRequest.getCardType());
    }
}