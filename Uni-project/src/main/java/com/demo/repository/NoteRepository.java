package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Note;
import com.demo.model.User;

public interface NoteRepository extends JpaRepository<Note,Long> {

  List<Note> findByUser(User user);
}
