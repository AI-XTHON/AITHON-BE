package com.eduai.user.application;

import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.user.application.dto.UserInfoResponse;
import com.eduai.user.domain.User;
import com.eduai.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserInfoResponse(
                user.getName(),
                user.getName()
        );
    }
}
