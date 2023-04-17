package com.stp.springsecurityjwtdbcontainer.Repositories;


import com.stp.springsecurityjwtdbcontainer.DAO.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
       select t from Token t inner join Enduser u
            on t.enduser.enduserId = u.enduserId
            where u.enduserId = :enduserid and (t.expired = false or t.revoked = false)
      """)
    List<Token> findAllValidTokenByUser(@Param("enduserid") Integer id);

    Optional<Token> findByToken(String token);
}