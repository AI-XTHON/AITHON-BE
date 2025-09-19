package com.eduai.user.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.user.application.UserService;
import com.eduai.user.application.dto.UpdateUserRequest;
import com.eduai.user.application.dto.UserInfoResponse;
import com.eduai.user.presentation.docs.UserApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping("/me")
    @Override
    public ResponseEntity<ApiResult<UserInfoResponse>> getMyInfo(@AuthUser Long userId) {
        UserInfoResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "현재 사용자 정보를 성공적으로 조회했습니다.", response));
    }

    @PatchMapping("/me")
    @Override
    public ResponseEntity<ApiResult<Void>> updateUserInfo(@AuthUser Long userId, UpdateUserRequest request) {
        return null;
    }
}
