package com.demo.exceptions;

public class UnauthorizedCardSetAccessException extends Exception {

  private Long cardSetId;

  public UnauthorizedCardSetAccessException(Long cardSetId) {
      super("Card not found with ID: " + cardSetId);
      this.cardSetId = cardSetId;
  }

  public Long getCardId() {
      return cardSetId;
  }
}
