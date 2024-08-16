package org.work.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.userservice.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
