package com.eduai.summary.infrastructure;

import com.eduai.summary.domain.Summary;
import com.eduai.summary.domain.repository.SummaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary, Long>, SummaryQueryRepository {

    Optional<Summary> findSummaryByResourceId(Long resourceId);
}
