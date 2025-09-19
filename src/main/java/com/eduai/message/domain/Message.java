package com.eduai.message.domain;

import com.eduai.common.domain.BaseTimeEntity;
import com.eduai.studysession.domain.StudySession;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_session_id", nullable = false)
    private StudySession studySession;

    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    private String content;

    private String filePath;

    public static Message create(StudySession studySession, SenderType senderType, String content, String filePath) {
        return Message.builder()
                .studySession(studySession)
                .senderType(senderType)
                .content(content)
                .filePath(filePath)
                .build();
    }
}
