package com.eduai.user.application;

import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.user.application.dto.UpdateUserRequest;
import com.eduai.user.application.dto.UserInfoResponse;
import com.eduai.user.domain.User;
import com.eduai.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserInfoResponse(
                user.getName(),
                user.getName()
        );
    }

    @Transactional
    public void updateUserInfo(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.updateUserInfo(request.job(), request.ageGroup(), request.purpose());
    }
}
