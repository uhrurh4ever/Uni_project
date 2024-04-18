package com.demo.service.impl;

import org.springframework.stereotype.Service;
import com.demo.model.Card;
import com.demo.service.CardService;
import com.demo.repository.CardRepository;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Service
public class CardServieceImpl implements CardService {

  private CardRepository cardRepository;

  public CardServieceImpl(CardRepository cardRepository){
    this.cardRepository = cardRepository;
  }

  @Override
  public Card saveCard(Card card) {
    return cardRepository.save(card);
  }

  @Override
  public Card getCardById(Long id) {
    return cardRepository.findById(id).get();
  }

  @Override
  public Card updateCard(Card card) {
    return cardRepository.save(card);
  }

  @Override
  public void deleteCardById(Long id) {
    cardRepository.deleteById(id);
  }
}
