package com.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "Note")
public class Note {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "note_id")
  private Long id;
  
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "topic_id")
  private Long topicId;

  private String name;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;
  
  private String text;
  private String tags;


}
