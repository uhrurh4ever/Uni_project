package com.demo.service.impl;

import org.springframework.stereotype.Service;
import com.demo.model.Card;
import com.demo.repository.CardRepository;
import com.demo.service.interfaces.CardService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class CardServiceImpl implements CardService {

    /**
     * Репозиторий для работы с сущностью Card
     */
    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Сохранение карточки в базе данных
     *
     * @param card Объект Card, представляющий сохраняемую карточку
     * @return Сохраненная карточка (объект Card)
     */
    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    /**
     * Получение карточки по идентификатору
     *
     * @param id Идентификатор карточки
     * @return Объект Card, представляющий найденную карточку,
     *         или null, если карточка с таким идентификатором не найдена
     */
    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).get();
    }

    /**
     * Обновление карточки в базе данных
     *
     * @param card Объект Card, содержащий обновленные данные карточки
     * @return Обновленная карточка (объект Card)
     */
    @Override
    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }

    /**
     * Удаление карточки из базы данных по идентификатору
     *
     * @param id Идентификатор карточки
     */
    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteById(id);
    }
}

