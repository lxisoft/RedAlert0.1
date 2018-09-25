package com.lxisoft.redalert.repository;

import com.lxisoft.redalert.domain.Feed;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Feed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query("select distinct feed from Feed feed left join fetch feed.helpedFriends")
    List<Feed> findAllWithEagerRelationships();

    @Query("select feed from Feed feed left join fetch feed.helpedFriends where feed.id =:id")
    Feed findOneWithEagerRelationships(@Param("id") Long id);

}
