package com.stp.springsecurityjwtdbcontainer.Repositories;



import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.DAO.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EnduserRepository extends JpaRepository<Enduser,Integer> {

    Enduser findByEnduserId(Integer id);

    Optional<Enduser> findByUsername(String username);

    Optional<Enduser> findByEmail(String email);


    @Modifying
    @Query("update Enduser e set e.role = :role where e.username = :username")
    void updateRoleByUsername(@Param("username") String username, @Param("role") Role role);


}
