package com.jefrido.cagnotte.repository;

import com.jefrido.cagnotte.domain.entity.Cagnotte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CagnotteRepository extends JpaRepository<Cagnotte, Long> {
}
