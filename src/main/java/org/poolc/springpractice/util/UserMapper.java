package org.poolc.springpractice.util;

import org.mapstruct.*;
import org.poolc.springpractice.model.User;
import org.poolc.springpractice.payload.request.user.UserRequest;
import org.poolc.springpractice.payload.request.user.UserUpdateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildUserFromRequest(UserRequest userRequest, @MappingTarget User user);

}
