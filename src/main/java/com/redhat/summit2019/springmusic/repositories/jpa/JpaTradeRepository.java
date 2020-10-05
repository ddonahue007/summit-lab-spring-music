package com.redhat.summit2019.springmusic.repositories.jpa;

import com.redhat.summit2019.springmusic.domain.Trade;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!mongodb & !redis")
public interface JpaTradeRepository extends JpaRepository<Trade, String> {
}