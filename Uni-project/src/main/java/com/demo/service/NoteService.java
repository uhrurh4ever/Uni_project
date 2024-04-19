package com.demo.service;




import java.util.List;

import com.demo.model.Note;

public interface NoteService {

  List <Note> getAllNotes();
  
  Note saveNote(Note note);

  Note getNoteById(Long id);

  Note updateNote(Note note);

  void deleteNoteById(Long id);
}
