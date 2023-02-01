package com.ives.uesrservicepractice.util;

import com.ives.uesrservicepractice.dto.UserDto;
import com.ives.uesrservicepractice.entity.User;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static UserDto toDto(User user){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }

    public static User toEntity(UserDto dto){
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        return user;
    }
}
