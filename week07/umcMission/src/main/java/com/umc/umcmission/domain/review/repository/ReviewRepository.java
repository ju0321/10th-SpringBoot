package com.umc.umcmission.domain.review.repository;

import com.umc.umcmission.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByStoreId(Long storeId);

    // 커서 기반 페이지네이션 - ID 순 (오름차순)
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId ORDER BY r.id ASC")
    List<Review> findByUserIdOrderById(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.id > :lastId ORDER BY r.id ASC")
    List<Review> findByUserIdWithIdCursor(@Param("userId") Long userId, @Param("lastId") Long lastId, Pageable pageable);

    // 커서 기반 페이지네이션 - 별점 순 (내림차순), 같은 별점이면 ID 오름차순
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId ORDER BY r.rating DESC, r.id ASC")
    List<Review> findByUserIdOrderByRating(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND (r.rating < :lastRating OR (r.rating = :lastRating AND r.id > :lastId)) ORDER BY r.rating DESC, r.id ASC")
    List<Review> findByUserIdWithRatingCursor(@Param("userId") Long userId, @Param("lastRating") Integer lastRating, @Param("lastId") Long lastId, Pageable pageable);
}