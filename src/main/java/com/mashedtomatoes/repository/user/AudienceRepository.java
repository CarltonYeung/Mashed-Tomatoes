package com.mashedtomatoes.repository.user;

import com.mashedtomatoes.model.user.Audience;
import org.springframework.data.repository.CrudRepository;

public interface AudienceRepository extends CrudRepository<Audience, Long> {

    public boolean existsByDisplayName(String displayName);
}
