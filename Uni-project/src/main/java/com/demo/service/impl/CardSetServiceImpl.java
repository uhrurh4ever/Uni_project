package com.demo.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.demo.exceptions.CardNotFoundException;
import com.demo.exceptions.CardSetNotFoundException;
import com.demo.model.Card;
import com.demo.model.CardSet;
import com.demo.repository.CardSetRepository;
import com.demo.service.interfaces.CardSetService;

import lombok.Getter;
import lombok.Setter;
import com.demo.repository.CardRepository;

@Getter
@Setter
@Service
public class CardSetServiceImpl implements CardSetService {

    /**
     * Репозиторий для работы с сущностью CardSet
     */
    private CardSetRepository cardSetRepository;

    /**
     * Репозиторий для работы с сущностью Card
     */
    private CardRepository cardRepository;

    public CardSetServiceImpl(CardSetRepository cardSetRepository, CardRepository cardRepository) {
        this.cardSetRepository = cardSetRepository;
        this.cardRepository = cardRepository;
    }

    /**
     * Получение всех наборов карточек
     *
     * @return Коллекция объектов CardSet, представляющая все наборы карточек в базе данных
     */
    @Override
    public Collection<CardSet> getAllCardSets() {
        return cardSetRepository.findAll();
    }

    /**
     * Сохранение набора карточек в базе данных
     *
     * @param cardSet Объект CardSet, представляющий сохраняемый набор карточек
     * @return Сохраненный набор карточек (объект CardSet)
     */
    @Override
    public CardSet saveCardSet(CardSet cardSet) {
        return cardSetRepository.save(cardSet);
    }

    /**
     * Получение набора карточек по идентификатору
     *
     * @param id Идентификатор набора карточек
     * @return Объект CardSet, представляющий найденный набор карточек,
     *         или выбрасывает CardSetNotFoundException, если набор с таким идентификатором не найден
     * @throws CardSetNotFoundException Ошибка, возникающая при поиске несуществующего набора карточек
     */
    @Override
    public CardSet getCardSetById(Long id) throws CardSetNotFoundException {
        return cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException(id));
    }

    /**
     * Обновление набора карточек в базе данных
     *
     * @param cardSet Объект CardSet, содержащий обновленные данные набора карточек
     * @return Обновленный набор карточек (объект CardSet)
     * @throws CardSetNotFoundException Ошибка, возникающая при попытке обновить несуществующий набор карточек
     */
    @Override
    public CardSet updateCardSet(CardSet cardSet) throws CardSetNotFoundException {
        CardSet existingCardSet = cardSetRepository.findById(cardSet.getId())
                .orElseThrow(() -> new CardSetNotFoundException(cardSet.getId()));
        return cardSetRepository.save(existingCardSet);
    }

    /**
     * Удаление набора карточек из базы данных по идентификатору
     *
     * @param id Идентификатор набора карточек
     */
    @Override
    public void deleteCardSetById(Long id) {
        cardSetRepository.deleteById(id);
    }

    /**
     * Добавление карточки в набор карточек
     *
     * @param cardSetId Идентификатор набора карточек
     * @param cardId Идентификатор карточки
     * @throws CardNotFoundException Ошибка, возникающая при попытке добавить несуществующую карточку
     * @throws CardSetNotFoundException Ошибка, возникающая при попытке добавить карточку в несуществующий набор карточек
     */
    @Override
    public void addCardToCardSet(Long cardSetId, Long cardId) throws CardNotFoundException, CardSetNotFoundException {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException(cardSetId));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        Collection<Card> cards = cardSet.getCards();
        cards.add(card);
        cardSet.setCards(cards);

        cardSetRepository.save(cardSet);
    }
    
        /**
     * Удаление карточки из набора карточек
     *
     * @param cardSetId Идентификатор набора карточек
     * @param cardId Идентификатор карточки
     * @throws CardNotFoundException Ошибка, возникающая при попытке удалить несуществующую карточку
     * @throws CardSetNotFoundException Ошибка, возникающая при попытке удалить карточку из несуществующего набора карточек
     */
    @Override
    public void removeCardFromCardSet(Long cardSetId, Long cardId) throws CardSetNotFoundException, CardNotFoundException {
      CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException(cardSetId));
      Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        cardRepository.delete(card);
        cardSetRepository.save(cardSet);
    }
  }

