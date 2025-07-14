package com.rt.guardRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.userEntity.Users;
@Repository
public interface GuardRepository extends JpaRepository<Users, Integer>{

	boolean existsByEmailIgnoreCase(String email);

	//it is used to fetch all guard.
	Page<Users> findByRole(String role, Pageable pageable);
	
//it is used to check duplicate number.
	boolean existsByNumber(String number);

	//it is used to find specific guard record based on id for update.
	Optional<Users> findByIdAndRole(int id, String role);
	
// this is used to filter guard based on their status(Active or Inactive).
	Page<Users> findByRoleAndStatus(String role, String statusFilter, Pageable pageable);

	 @Query("SELECT u FROM Users u WHERE LOWER(u.fullname) LIKE LOWER(CONCAT('%', :fullname, '%')) AND g.role = :role")
	    List<Users> searchGuardsByNameAndRole(@Param("fullname") String fullname, @Param("role") String role);

}
