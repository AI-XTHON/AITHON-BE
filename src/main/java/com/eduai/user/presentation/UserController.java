package com.eduai.user.presentation;

import com.eduai.common.dto.ApiResult;
import com.eduai.user.application.dto.UpdateUserRequest;
import com.eduai.user.presentation.docs.UserApiDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class UserController implements UserApiDocs {

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        return ResponseEntity.ok("Current user email: " + userDetails.getUsername());
    }

    @PutMapping
    @Override
    public ResponseEntity<ApiResult<Void>> updateMemberInfo(User user, UpdateUserRequest request) {
        return null;
    }
}
