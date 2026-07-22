package com.refresh_token.auth.service.impl;

import com.refresh_token.auth.repository.BlacklistedAccessTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistedAccessTokenCleanupService {

    private final BlacklistedAccessTokenRepository repository;

    @Transactional
    @Scheduled(cron = "0 */10 * * * *")
    public void cleanupExpiredTokens() {

        repository.deleteByExpiresAtBefore(Instant.now());

        log.info("Expired blacklisted access tokens cleaned.");
    }
}