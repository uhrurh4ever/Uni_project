package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.CardSet;

public interface CardSetRepository extends JpaRepository<CardSet,Long> {
  
}
