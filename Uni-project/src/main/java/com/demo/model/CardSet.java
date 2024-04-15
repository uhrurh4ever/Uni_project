package com.demo.model;

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
@Table(name = "Card_set")
public class CardSet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "set_id")
  private Long id;

  @Column(name="user_id")
  private Long userId;

  @Column(name = "topic_id")
  private Long topicId;
  
  private String name;
  private String descrition;

  
}
