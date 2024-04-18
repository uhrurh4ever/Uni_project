package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
}
