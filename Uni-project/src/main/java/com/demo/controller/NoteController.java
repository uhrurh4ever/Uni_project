package com.demo.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.exceptions.AccessDeniedException;
import com.demo.model.Note;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.service.interfaces.NoteService;
import com.demo.service.interfaces.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserRepository userRepository;
    private UserService userService;


    public NoteController(NoteService noteService,UserService userService, UserRepository userRepository) {
        this.noteService = noteService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAllNotes(Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
    String email = userDetails.getUsername();
    User user = userRepository.findByEmail(email);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }
    List<Note> notes = noteService.getNotesByUser(user);
    model.addAttribute("notes", notes);
    model.addAttribute("username", email);
    return "notes";
    }

    @GetMapping("/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note-form";
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        // Получаем аутентификацию из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Получаем UserDetails для текущего пользователя
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());

        // Получаем email текущего пользователя из UserDetails
        String email = userDetails.getUsername();

        // Находим пользователя по email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Устанавливаем пользователя для заметки и сохраняем ее
        note.setUser(user);
        noteService.saveNote(note);

        // Перенаправляем на страницу с заметками
        return "redirect:/notes";
    }

    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) throws AccessDeniedException {
    Note note = noteService.getNoteById(id);
    
    // Получаем текущего пользователя
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
    String email = userDetails.getUsername();
    User user = userRepository.findByEmail(email);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    // Проверяем, что заметка принадлежит текущему пользователю
    if (!note.getUser().equals(user)) {
        throw new AccessDeniedException("You do not have permission to access this note");
    }

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

