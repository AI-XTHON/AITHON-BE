package com.eduai.resource.application;

import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.resource.application.dto.CreateResourceRequest;
import com.eduai.resource.domain.Resource;
import com.eduai.resource.infrastructure.ResourceRepository;
import com.eduai.user.domain.User;
import com.eduai.user.infrastructure.UserRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final Storage storage;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Transactional
    public Long uploadResource(MultipartFile file, String email, CreateResourceRequest request) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_IS_EMPTY);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        String storedFileName = createStoredFileName(originalFileName);

        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, storedFileName)
                            .setContentType(file.getContentType())
                            .build(),
                    file.getInputStream()
            );

            String filePath = blobInfo.getMediaLink();

            Resource resource = Resource.create(
                    user,
                    request.title(),
                    storedFileName,
                    filePath,
                    file.getContentType()
            );

            return resourceRepository.save(resource).getId();

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private String createStoredFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}