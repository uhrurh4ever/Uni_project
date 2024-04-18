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

    // Methods for handling card set operations

    @GetMapping
    public String getAllCardSets(Model model) {
        Collection<CardSet> cardSets = cardSetService.getAllCardSets();  // Assuming getAllCardSets is implemented
        model.addAttribute("cardSets", cardSets);
        return "card-sets";  // Replace with your Thymeleaf template name
    }

    @GetMapping("/{id}")
    public String getCardSetById(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-details";  // Replace with your Thymeleaf template name
    }

    @GetMapping("/new")
    public String showNewCardSetForm(Model model) {
        model.addAttribute("cardSet", new CardSet());  // Create empty object for form binding
        return "card-set-form";  // Replace with your Thymeleaf template name
    }

    @PostMapping
    public String saveCardSet(@ModelAttribute CardSet cardSet) {
        cardSetService.saveCardSet(cardSet);
        return "redirect:/card-sets";  // Redirect after saving
    }

    @GetMapping("/{id}/edit")
    public String showEditCardSetForm(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-form";  // Replace with your Thymeleaf template name (can be the same as new form)
    }

    @PostMapping("/{id}/update")
    public String updateCardSet(@PathVariable Long id, @ModelAttribute CardSet cardSet) throws Exception {
        cardSet.setId(id);  // Set the ID manually in case it's missing in the form data
        cardSetService.updateCardSet(cardSet);
        return "redirect:/card-sets/" + id;  // Redirect after updating
    }

    @GetMapping("/{id}/delete")
    public String deleteCardSet(@PathVariable Long id) throws CardSetNotFoundException {
        cardSetService.deleteCardSetById(id);
        return "redirect:/card-sets";  // Redirect after deleting
    }

    // Methods for handling card management within card sets (optional)

    @GetMapping("/{cardSetId}/cards")
    public String getCardsInCardSet(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        // Logic to retrieve cards associated with the card set
        Collection<Card> cards = cardSet.getCards();  // Assuming a getter method for cards exists
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("cards", cards);
        return "card-set-cards";  // Replace with your Thymeleaf template name
    }

    @GetMapping("/{cardSetId}/cards/add")
    public String showAddCardToCardSetForm(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("card", new Card());  // Create empty object for form binding
        return "card-set-add-card-form";  // Replace with your Thymeleaf template name
    }

    @PostMapping("/{cardSetId}/cards/add")
    public String addCardToCardSet(@PathVariable Long cardSetId, @ModelAttribute Card card) throws Exception {
        cardSetService.addCardToCardSet(cardSetId, card.getId());  // Assuming card ID is retrieved elsewhere
        return "redirect:/card-sets/" + cardSetId + "/cards";  // Redirect after adding card
    }
  }
    

