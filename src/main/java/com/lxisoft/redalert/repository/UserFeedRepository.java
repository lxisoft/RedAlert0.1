package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.UserFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserFeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserFeedRepository extends JpaRepository<UserFeed, Long> {

    @Query(value = "select distinct user_feed from UserFeed user_feed left join fetch user_feed.helpedFriends",
        countQuery = "select count(distinct user_feed) from UserFeed user_feed")
    Page<UserFeed> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_feed from UserFeed user_feed left join fetch user_feed.helpedFriends")
    List<UserFeed> findAllWithEagerRelationships();

    @Query("select user_feed from UserFeed user_feed left join fetch user_feed.helpedFriends where user_feed.id =:id")
    Optional<UserFeed> findOneWithEagerRelationships(@Param("id") Long id);

}
