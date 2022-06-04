package com.tr.beyzanur.converter;


import com.tr.beyzanur.dto.request.CreateUserDto;
import com.tr.beyzanur.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convertToEntity(CreateUserDto dto) {
        User user = new User();
        if (dto != null) {
            BeanUtils.copyProperties(dto, user);
        }
        return user;
    }
}
