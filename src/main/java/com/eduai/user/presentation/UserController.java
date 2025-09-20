package com.eduai.user.presentation;

import com.eduai.auth.resolver.AuthUser;
import com.eduai.common.dto.ApiResult;
import com.eduai.user.application.UserService;
import com.eduai.user.application.dto.OnboardUserInfoRequest;
import com.eduai.user.application.dto.UpdateUserRequest;
import com.eduai.user.application.dto.UserInfoResponse;
import com.eduai.user.presentation.docs.UserApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping("/me")
    @Override
    public ResponseEntity<ApiResult<UserInfoResponse>> getMyInfo(@AuthUser String email) {
        UserInfoResponse response = userService.getUserInfo(email);
        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "현재 사용자 정보를 성공적으로 조회했습니다.", response));
    }

    @PostMapping("/onboard")
    @Override
    public ResponseEntity<ApiResult<Void>> onboardUser(@AuthUser String email, @RequestBody OnboardUserInfoRequest request) {
        userService.onboardUser(email, request);
        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "온보딩이 성공적으로 완료되었습니다.", null));
    }

    @PatchMapping("/me")
    @Override
    public ResponseEntity<ApiResult<Void>> updateUserInfo(@AuthUser String email, @RequestBody UpdateUserRequest request) {
        userService.updateUserInfo(email, request);
        return ResponseEntity.ok(ApiResult.success(HttpStatus.OK, "회원 정보가 성공적으로 수정되었습니다.", null));
    }
}
