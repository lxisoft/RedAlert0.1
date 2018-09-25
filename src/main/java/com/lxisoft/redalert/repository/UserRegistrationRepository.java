package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.UserRegistration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the UserRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    @Query("select distinct user_registration from UserRegistration user_registration left join fetch user_registration.emergencyContacts")
    List<UserRegistration> findAllWithEagerRelationships();

    @Query("select user_registration from UserRegistration user_registration left join fetch user_registration.emergencyContacts where user_registration.id =:id")
    UserRegistration findOneWithEagerRelationships(@Param("id") Long id);

}
