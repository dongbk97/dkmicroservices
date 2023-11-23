package dev.ngdangkiet.repository;

import dev.ngdangkiet.domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ngdangkiet
 * @since 11/22/2023
 */

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByTokenUUID(String tokenUUID);

    Optional<RefreshTokenEntity> findByUserId(Long userId);
}
