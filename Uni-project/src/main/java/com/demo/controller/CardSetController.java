package com.demo.controller;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.exceptions.CardSetNotFoundException;
import com.demo.model.Card;
import com.demo.model.CardSet;
import com.demo.service.CardSetService;

@Controller
@RequestMapping("/card-sets")
public class CardSetController {

    private CardSetService cardSetService;

    public CardSetController(CardSetService cardSetService) {
        this.cardSetService = cardSetService;
    }

    @GetMapping
    public String getAllCardSets(Model model) {
        Collection<CardSet> cardSets = cardSetService.getAllCardSets();  
        model.addAttribute("cardSets", cardSets);
        return "card-sets";  
    }

    @GetMapping("/{id}")
    public String getCardSetById(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-details";  
    }

    @GetMapping("/new")
    public String showNewCardSetForm(Model model) {
        model.addAttribute("cardSet", new CardSet());  
        return "card-set-form";  
    }

    @PostMapping
    public String saveCardSet(@ModelAttribute CardSet cardSet) {
        cardSetService.saveCardSet(cardSet);
        return "redirect:/card-sets";  
    }

    @GetMapping("/{id}/edit")
    public String showEditCardSetForm(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-form";  
    }

    @PostMapping("/{id}/update")
    public String updateCardSet(@PathVariable Long id, @ModelAttribute CardSet cardSet) throws Exception {
        cardSet.setId(id);  
        cardSetService.updateCardSet(cardSet);
        return "redirect:/card-sets/" + id; 
    }

    @GetMapping("/{id}/delete")
    public String deleteCardSet(@PathVariable Long id) throws CardSetNotFoundException {
        cardSetService.deleteCardSetById(id);
        return "redirect:/card-sets";  
    }

   

    @GetMapping("/{cardSetId}/cards")
    public String getCardsInCardSet(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        Collection<Card> cards = cardSet.getCards();  
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("cards", cards);
        return "card-set-cards";  
    }

    @GetMapping("/{cardSetId}/cards/add")
    public String showAddCardToCardSetForm(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("card", new Card());  
        return "card-set-add-card-form";  
    }

    @PostMapping("/{cardSetId}/cards/add")
    public String addCardToCardSet(@PathVariable Long cardSetId, @ModelAttribute Card card) throws Exception {
        cardSetService.addCardToCardSet(cardSetId, card.getId());  
        return "redirect:/card-sets/" + cardSetId + "/cards";  
    }
  }
    

