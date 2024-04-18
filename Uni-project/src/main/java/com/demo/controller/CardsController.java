package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.demo.service.CardService;
import com.demo.service.CardSetService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class CardsController {

  private CardSetService cardSetService;
  public CardsController (CardSetService cardSetService){
    super();
    this.cardSetService = cardSetService;
  }

  @GetMapping("/cards")
  public String cards(){
     return "cards";
  }

  



  


  
}
