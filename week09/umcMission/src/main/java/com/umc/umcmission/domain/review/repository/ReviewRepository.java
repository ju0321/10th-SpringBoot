package com.umc.umcmission.domain.review.repository;

import com.umc.umcmission.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByStoreId(Long storeId);

    // Slice 기반 - ID 순 (내림차순)
    Slice<Review> findReviewsByUserIdOrderByIdDesc(Long userId, Pageable pageable);
    Slice<Review> findReviewsByUserIdAndIdLessThanOrderByIdDesc(Long userId, Long id, Pageable pageable);

    // Slice 기반 - 별점 순 (내림차순), 동점이면 ID 내림차순
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId ORDER BY r.rating DESC, r.id DESC")
    Slice<Review> findReviewsByUserIdOrderByRatingDesc(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND (r.rating < :lastRating OR (r.rating = :lastRating AND r.id < :lastId)) ORDER BY r.rating DESC, r.id DESC")
    Slice<Review> findReviewsByUserIdWithRatingCursor(@Param("userId") Long userId, @Param("lastRating") Integer lastRating, @Param("lastId") Long lastId, Pageable pageable);
}