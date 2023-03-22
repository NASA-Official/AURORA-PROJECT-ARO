package com.nassafy.core.respository;

import com.nassafy.core.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    int deleteByEmail(String email);
}
