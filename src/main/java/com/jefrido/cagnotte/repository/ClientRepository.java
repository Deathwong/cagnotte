package com.jefrido.cagnotte.repository;

import com.jefrido.cagnotte.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
