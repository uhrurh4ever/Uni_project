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
import com.demo.service.interfaces.NoteService;

@Controller
@RequestMapping("/notes")
public class NoteController {

    /**
     * Сервис для работы с заметками
     */
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Обработка GET запроса на "/notes" (получение всех заметок)
     *
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "notes.html"
     */
    @GetMapping
    public String getAllNotes(Model model) {
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "notes";
    }

    /**
     * Обработка GET запроса на "/notes/new" (форма создания новой заметки)
     *
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "note-form.html"
     */
    @GetMapping("/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note-form";
    }

    /**
     * Обработка POST запроса на "/notes" (сохранение новой заметки)
     *
     * @param note Объект Note из формы создания заметки
     * @return Перенаправление на "/notes" (список всех заметок)
     */
    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        noteService.saveNote(note);
        return "redirect:/notes";
    }

    /**
     * Обработка GET запроса на "/notes/{id}" (получение конкретной заметки по id)
     *
     * @param id Идентификатор заметки
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "note-details.html"
     */
    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "note-details"; 
    }

    /**
     * Обработка GET запроса на "/notes/{id}/edit" (форма редактирования заметки)
     *
     * @param id Идентификатор заметки
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "note-form.html"
     */
    @GetMapping("/{id}/edit")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "note-form";
    }

    /**
     * Обработка POST запроса на "/notes/{id}/update" (обновление заметки)
     *
     * @param id Идентификатор заметки
     * @param note Объект Note из формы редактирования заметки
     * @return Перенаправление на "/notes/{id}" (детали конкретной заметки)
     */
    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note) {
        note.setId(id); // В целях безопасности явно устанавливаем полученный id
        noteService.updateNote(note);
        return "redirect:/notes/" + id;
    }

    /**
     * Обработка GET запроса на "/notes/{id}/delete" (удаление заметки)
     *
     * @param id Идентификатор заметки
     * @return Перенаправление на "/notes" (список всех заметок)
     */
    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }
}

