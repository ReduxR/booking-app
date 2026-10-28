package com.reduxr.repository;

import com.reduxr.model.RoleName;
import com.reduxr.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRolesName(RoleName roleName);

    boolean existsByEmailAndRolesName(String email, RoleName roleName);
}
