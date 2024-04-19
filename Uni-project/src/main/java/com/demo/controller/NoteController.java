package com.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.model.Note;
import com.demo.service.NoteService;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getAllNotes(Model model) {
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "notes"; 
    }

    @GetMapping("/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note-form"; 
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/notes"; 
    }

    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "note-details"; 
    }

    @GetMapping("/{id}/edit")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "note-form"; 
    }

    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note) {
        note.setId(id);
        noteService.updateNote(note);
        return "redirect:/notes/" + id; 
    }

    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes"; 
    }
}
