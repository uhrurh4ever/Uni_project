package com.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.model.Note;
import com.demo.repository.NoteRepository;
import com.demo.service.NoteService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class NoteServiceImpl implements NoteService {

  private NoteRepository noteRepository;

  public NoteServiceImpl(NoteRepository noteRepository){
    this.noteRepository = noteRepository;
  }

  @Override
  public List <Note> getAllNotes() {
    return noteRepository.findAll();
    
  }

  @Override
  public Note saveNote(Note note) {
    return noteRepository.save(note);
  }

  @Override
  public Note getNoteById(Long id) {
    return noteRepository.findById(id).get();
  }

  @Override
  public Note updateNote(Note note) {
    return noteRepository.save(note);
  }

  @Override
  public void deleteNoteById(Long id) {
    noteRepository.deleteById(id);
  }

}
