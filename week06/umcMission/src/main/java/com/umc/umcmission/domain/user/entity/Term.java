package com.umc.umcmission.domain.user.entity;

import com.umc.umcmission.domain.user.entity.mapping.UserTerm;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "term")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Term {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String termName;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private Boolean isOptional;

  @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
  private List<UserTerm> memberTerms = new ArrayList<>();
}
