package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.UserRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    @Query(value = "select distinct user_registration from UserRegistration user_registration left join fetch user_registration.emergencyContacts",
        countQuery = "select count(distinct user_registration) from UserRegistration user_registration")
    Page<UserRegistration> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_registration from UserRegistration user_registration left join fetch user_registration.emergencyContacts")
    List<UserRegistration> findAllWithEagerRelationships();

    @Query("select user_registration from UserRegistration user_registration left join fetch user_registration.emergencyContacts where user_registration.id =:id")
    Optional<UserRegistration> findOneWithEagerRelationships(@Param("id") Long id);

	void deleteById(Long id);

	Object findById(Long id);

}
