package com.mashedtomatoes.user;

import org.springframework.data.repository.CrudRepository;

public interface AudienceRepository extends CrudRepository<Audience, Long> {

    boolean existsByDisplayName(String displayName);
}
