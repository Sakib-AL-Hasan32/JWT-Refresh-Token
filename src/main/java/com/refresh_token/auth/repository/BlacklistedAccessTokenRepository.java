package com.refresh_token.auth.repository;

import com.refresh_token.auth.entity.BlacklistedAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedAccessTokenRepository extends CrudRepository<BlacklistedAccessToken, Long> {
    boolean existsByToken(String tokenHash);
}
