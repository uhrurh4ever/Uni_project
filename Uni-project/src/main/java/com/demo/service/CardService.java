package com.demo.service;

import com.demo.model.Card;

public interface CardService  {

  Card saveCard(Card card);

  Card getCardById(Long id);

  Card updateCard(Card card);

  void deleteCardById(Long id);

}
