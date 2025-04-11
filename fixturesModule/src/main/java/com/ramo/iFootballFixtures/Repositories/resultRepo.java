package com.ramo.iFootballFixtures.Repositories;

import com.ramo.iFootballFixtures.Models.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface resultRepo extends JpaRepository<Result, Long> {
}
