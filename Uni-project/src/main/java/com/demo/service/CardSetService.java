package com.demo.service;

import com.demo.model.CardSet;

public interface CardSetService  {

  CardSet saveCardSet(CardSet cardSet);

  CardSet getCardSetById(Long id) throws Exception;

  CardSet updateCardSet(CardSet cardSet) throws Exception;

  void deleteCardSetById(Long id);

  void addCardToCardSet(Long cardSetId, Long cardId) throws Exception;

  void removeCardFromCardSet(Long cardSetId, Long cardId)  throws Exception;
  
}
