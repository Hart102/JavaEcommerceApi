package com.Hart.shoppingCartApi.service.user;

import com.Hart.shoppingCartApi.dto.UserDto;
import com.Hart.shoppingCartApi.model.User;
import com.Hart.shoppingCartApi.request.CreateUserRequest;
import com.Hart.shoppingCartApi.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
