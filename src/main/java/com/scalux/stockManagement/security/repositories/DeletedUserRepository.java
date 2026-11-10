package com.scalux.stockManagement.security.repositories;

import com.scalux.stockManagement.security.models.entities.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}
