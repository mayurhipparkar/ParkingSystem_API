package com.rt.signUpAndLoginRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.signUpAndLoginDTO.ResponseLoginDTO;
import com.rt.userEntity.Users;
@Repository
public interface LoginRepository extends JpaRepository<Users, Integer> {
	
	 @Query("SELECT u.id AS id, u.fullname AS fullname, u.email AS email, u.role AS role, u.status AS status FROM Users u WHERE u.email = :email AND u.password = :password")
	    Optional<ResponseLoginDTO> loginUser(@Param("email") String email, @Param("password") String password);
	  
}
