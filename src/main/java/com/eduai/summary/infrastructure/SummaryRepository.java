package com.eduai.summary.infrastructure;

import com.eduai.summary.domain.Summary;
import com.eduai.summary.domain.repository.SummaryQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary, Long>, SummaryQueryRepository {

    @Query("SELECT s FROM Summary s JOIN s.resource r JOIN r.user u WHERE u.email = :email ORDER BY s.createdAt DESC limit 1")
    Optional<Summary> getByEmail(String email);

    @Query("SELECT s FROM Summary s JOIN s.resource r JOIN r.user u WHERE u.email = :email ORDER BY s.createdAt DESC")
    List<Summary> getAllByEmail(String email);
}
