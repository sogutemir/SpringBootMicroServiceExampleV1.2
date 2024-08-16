package org.work.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.accountservice.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
