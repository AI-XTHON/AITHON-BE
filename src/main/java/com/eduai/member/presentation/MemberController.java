package com.eduai.member.presentation;

import com.eduai.common.dto.ApiResult;
import com.eduai.member.application.dto.UpdateMemberRequest;
import com.eduai.member.presentation.docs.MemberApiDocs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController implements MemberApiDocs {

    @PutMapping
    @Override
    public ResponseEntity<ApiResult<Void>> updateMemberInfo(User user, UpdateMemberRequest request) {
        return null;
    }
}
