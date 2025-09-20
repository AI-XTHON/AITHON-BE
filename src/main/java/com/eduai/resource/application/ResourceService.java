package com.eduai.resource.application;

import com.eduai.common.exception.BusinessException;
import com.eduai.common.exception.ErrorCode;
import com.eduai.resource.application.dto.CreateResourceRequest;
import com.eduai.resource.domain.Resource;
import com.eduai.resource.infrastructure.ResourceRepository;
import com.eduai.summary.application.dto.FastApiFullResponse;
import com.eduai.summary.domain.GlossaryTerm;
import com.eduai.quiz.domain.Question;
import com.eduai.quiz.domain.QuestionType;
import com.eduai.summary.domain.Slide;
import com.eduai.summary.domain.Summary;
import com.eduai.summary.infrastructure.SummaryRepository;
import com.eduai.user.domain.User;
import com.eduai.user.infrastructure.UserRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final Storage storage;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final SummaryRepository summaryRepository;

    private final WebClient.Builder webClientBuilder;

    private final String FASTAPI_SUMMARY_URL = "http://192.168.88.114:8000/summarize";

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Transactional
    public Long uploadResource(MultipartFile file, String email, CreateResourceRequest request) {
        // 1. 사전 조건 검증
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_IS_EMPTY);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        try {
            // 2. [책임 분리] GCS에 파일 업로드
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String storedFileName = createStoredFileName(originalFileName);
            String filePath = uploadFileToGCS(file, storedFileName);

            // 3. Resource 엔티티 생성 및 저장
            Resource resource = Resource.create(user, request.title(), storedFileName, filePath, file.getContentType());
            Resource savedResource = resourceRepository.saveAndFlush(resource);

            // 4. [책임 분리] FastAPI에 요약 요청 및 응답 받기
            FastApiFullResponse summaryDto = requestSummaryFromFastAPI(file);

            // 5. [책임 분리] 요약 데이터 변환 및 저장
            saveSummaryData(savedResource, summaryDto);

            return savedResource.getId();

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    /**
     * 책임 1: 파일을 GCS에 업로드하고 파일 경로를 반환
     */
    private String uploadFileToGCS(MultipartFile file, String storedFileName) throws IOException {
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, storedFileName).setContentType(file.getContentType()).build(),
                file.getInputStream()
        );
        return blobInfo.getMediaLink();
    }

    /**
     * 책임 2: FastAPI 서버에 요약 요청을 보내고 결과를 DTO로 받아옴
     */
    private FastApiFullResponse requestSummaryFromFastAPI(MultipartFile file) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(file.getBytes()))
                .header("Content-Disposition", "form-data; name=file; filename=" + file.getOriginalFilename());

        FastApiFullResponse response = webClientBuilder.build()
                .post().uri(FASTAPI_SUMMARY_URL)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve().bodyToMono(FastApiFullResponse.class).block();

        if (response == null) {
            throw new BusinessException(ErrorCode.SUMMARY_FAILED);
        }
        return response;
    }

    /**
     * 책임 3: DTO를 엔티티로 변환하고 DB에 저장
     */
    private void saveSummaryData(Resource resource, FastApiFullResponse responseDto) {
        Summary summary = Summary.builder().resource(resource)
                .filename(responseDto.filename())
                .pages(responseDto.pages())
                .model( responseDto.model())
                .summary5(responseDto.result().summary5())
                .furtherTopics(responseDto.result().further())
                .build();

        responseDto.result().slides().forEach(dto -> {
                    Slide slide = Slide.create(dto.title(), dto.one_liner(), dto.pages(), summary);
                    summary.addSlide(slide);
                }
        );
        responseDto.result().glossary().forEach(dto -> {
                    GlossaryTerm term = GlossaryTerm.create(dto.term(), dto.definition(), dto.pages(), summary);
                    summary.addGlossaryTerm(term);
                }
        );
        responseDto.result().questions().shortAnswer().forEach(dto -> {
                    Question question = Question.create(QuestionType.SHORT, dto.q(), dto.a(), dto.pages(), summary);
                    summary.addQuestion(question);
                }
        );
        responseDto.result().questions().longAnswer().forEach(dto -> {
                    Question question = Question.create(QuestionType.LONG, dto.q(), dto.a(), dto.pages(), summary);
                    summary.addQuestion(question);
                }
        );

        summaryRepository.save(summary);
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