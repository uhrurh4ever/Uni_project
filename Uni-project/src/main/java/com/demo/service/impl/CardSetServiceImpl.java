package com.demo.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.demo.exceptions.CardNotFoundException;
import com.demo.exceptions.CardSetNotFoundException;
import com.demo.exceptions.UnauthorizedCardSetAccessException;
import com.demo.model.Card;
import com.demo.model.CardSet;
import com.demo.repository.CardSetRepository;
import com.demo.service.CardSetService;
import lombok.Getter;
import lombok.Setter;
import com.demo.repository.CardRepository;

@Getter
@Setter
public class CardSetServiceImpl implements CardSetService {

  private CardSetRepository cardSetRepository;
  private CardRepository cardRepository;
  private AuthenticationManager authenticationManager;

  public CardSetServiceImpl(CardSetRepository cardSetRepository, CardRepository cardRepository, AuthenticationManager authenticationManager) {
    this.cardSetRepository = cardSetRepository;
    this.cardRepository = cardRepository;
    this.authenticationManager = authenticationManager;
  }


  @Override
  public CardSet saveCardSet(CardSet cardSet) {
    return cardSetRepository.save(cardSet);
  }

  @Override
  public CardSet getCardSetById(Long id) throws CardSetNotFoundException {
    return cardSetRepository.findById(id)
    .orElseThrow(() -> new CardSetNotFoundException(id));
  }

  @Override
  public CardSet updateCardSet(CardSet cardSet) throws CardSetNotFoundException {
    CardSet existingCardSet = cardSetRepository.findById(cardSet.getId())
    .orElseThrow(() -> new CardSetNotFoundException(cardSet.getId()));
    return cardSetRepository.save(existingCardSet);
  }

  @Override
  public void deleteCardSetById(Long id) {
    cardSetRepository.deleteById(id);
  }
  @SuppressWarnings("unsed")
  @Override
  public void addCardToCardSet(Long cardSetId, Long cardId) throws CardNotFoundException, CardSetNotFoundException {
    CardSet cardSet = getCardSetById(cardSetId);
    Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(cardId));
  }

  @Override
  public void removeCardFromCardSet(Long cardSetId, Long cardId) throws UnauthorizedCardSetAccessException, CardSetNotFoundException, CardNotFoundException {
      CardSet cardSet = getCardSetById(cardSetId);
      Card card = cardRepository.findById(cardId)
              .orElseThrow(() -> new CardNotFoundException(cardId));
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (!authentication.getPrincipal().equals(cardSet.getUser())) {
          throw new UnauthorizedCardSetAccessException(cardSetId);
      }

      cardRepository.delete(card);
      cardSetRepository.save(cardSet);
  }
}
  
