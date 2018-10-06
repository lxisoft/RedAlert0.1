package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.Friends;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Friends entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

}
