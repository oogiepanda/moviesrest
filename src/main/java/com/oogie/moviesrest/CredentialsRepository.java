package com.oogie.moviesrest;

import com.oogie.moviesrest.model.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
}
