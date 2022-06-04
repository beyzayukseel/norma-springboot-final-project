package com.tr.beyzanur.converter;

import com.tr.beyzanur.dto.request.CreateUserAddressDto;
import com.tr.beyzanur.dto.response.CustomerAddressResponseDto;
import com.tr.beyzanur.model.CustomerAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressConverter {

    public CustomerAddress convertToEntity(CustomerAddressResponseDto dto) {
        CustomerAddress customerAddress = new CustomerAddress();
        if (dto != null){
            BeanUtils.copyProperties(dto,customerAddress);
        }
        return customerAddress;
    }
}
