
package com.nolookcoding.userservice.repository;

import com.nolookcoding.userservice.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    @Query("SELECT u from USER_TB as u WHERE u.name = :name AND u.phone = :phone AND u.email = :email")
    User findByUserInput(@Param("name") String name, @Param("phone") String phone, @Param("email") String email);
}
