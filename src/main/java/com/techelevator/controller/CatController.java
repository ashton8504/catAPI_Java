package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
@RestController
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    //GET /api/cards
    @RequestMapping(path = "/api/cards", method = RequestMethod.GET)
    public List<CatCard> listAllCatCards(){
        return catCardDao.getCatCards();
    }

    //GET /api/cards/{id}
    @RequestMapping(path = "/api/cards/{id}", method = RequestMethod.GET)
    public CatCard get(@PathVariable int id){
        CatCard catCard = catCardDao.getCatCardById(id);
        if(catCard == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat Card Not Found");
        } else {
            return catCard;
        }
    }

    //GET /api/cards/random WE WILL GO BACK TO THIS ONE
    @RequestMapping(path = "/api/cards/random", method = RequestMethod.GET)
    public CatCard getRandom(){
        CatFact catFact = catFactService.getFact();
        CatPic catPic = catPicService.getPic();
        CatCard catCard = new CatCard();

        catCard.setImgUrl(catPic.getFile());
        catCard.setCatFact(catFact.getText());

        return catCard;
    }

    //POST /api/cards
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/cards", method = RequestMethod.POST)
    public CatCard addCatCard(@Valid @RequestBody CatCard catCard){
        return catCardDao.createCatCard(catCard);
    }
    //PUT /api/cards/{id}
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/cards/{id}", method = RequestMethod.PUT)
    public CatCard update(@Valid @RequestBody CatCard catCards, @PathVariable int id){
        catCards.setCatCardId(id);
        try{
            CatCard updatedCatCard = catCardDao.updateCatCard(catCards);
            return updatedCatCard;
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat Card Not Found");
        }
    }

    //DELETE /api/cards/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/api/cards/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }

}
