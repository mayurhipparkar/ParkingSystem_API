package com.rt.signUpAndLoginRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.userEntity.Users;

@Repository
public interface SignupRepositiry extends JpaRepository<Users, Integer> {

	Users findByEmailAndRole(String email, String role);

	boolean existsByRoleIgnoreCase(String string);

	boolean existsByEmailIgnoreCase(String email);


}
