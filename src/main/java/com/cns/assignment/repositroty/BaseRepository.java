package com.cns.assignment.repositroty;

import com.cns.assignment.entity.AbstractBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends AbstractBaseEntity> extends JpaRepository<T, Long> {
}
