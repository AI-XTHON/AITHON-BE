package com.eduai.resource.infrastructure;

import com.eduai.resource.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
