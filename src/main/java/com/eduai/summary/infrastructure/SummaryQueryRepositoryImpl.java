package com.eduai.summary.infrastructure;

import com.eduai.summary.domain.Summary;
import com.eduai.summary.domain.repository.SummaryQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.eduai.summary.domain.QSummary.summary;

@Repository
@RequiredArgsConstructor
public class SummaryQueryRepositoryImpl implements SummaryQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
