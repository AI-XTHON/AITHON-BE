package com.eduai.quiz.infrastructure;

import com.eduai.quiz.domain.Question;
import com.eduai.summary.domain.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllBySummary(Summary summary);
}
