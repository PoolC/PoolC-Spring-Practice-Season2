package org.poolc.springpractice.service;

import lombok.RequiredArgsConstructor;
import org.poolc.springpractice.exception.InvalidRequestException;
import org.poolc.springpractice.model.User;
import org.poolc.springpractice.payload.request.user.UserDeleteRequest;
import org.poolc.springpractice.payload.request.user.UserRequest;
import org.poolc.springpractice.payload.request.user.UserUpdateRequest;
import org.poolc.springpractice.payload.response.UserDto;
import org.poolc.springpractice.repository.UserRepository;
import org.poolc.springpractice.util.Message;
import org.poolc.springpractice.util.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public void saveUser(UserRequest userRequest) {
        User user = new User();
        userMapper.buildUserFromRequest(userRequest, user);
        // password 암호화해서 저장은 유정 인증 후에
        userRepository.save(user);
    }
    public void deleteUser(String username, UserDeleteRequest userDeleteRequest) throws InvalidRequestException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));
        userRepository.deleteById(user.getId());
    }
    public UserDto updateUser(String username, UserUpdateRequest userUpdateRequest) throws InvalidRequestException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));
        userMapper.updateUserFromRequest(userUpdateRequest, user);
        return userMapper.buildUserDtoFromUser(user);
    }

}
