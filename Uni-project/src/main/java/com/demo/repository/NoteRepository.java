package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Note;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
