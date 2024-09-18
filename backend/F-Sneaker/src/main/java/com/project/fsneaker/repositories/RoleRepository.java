package com.project.fsneaker.repositories;

import com.project.fsneaker.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
