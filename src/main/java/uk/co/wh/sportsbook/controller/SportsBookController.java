package uk.co.wh.sportsbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.wh.sportsbook.model.ScoreBoards;
import uk.co.wh.sportsbook.service.SportsBookService;
import javax.validation.Valid;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/sportbook")
public class SportsBookController {

    private SportsBookService sportsBookService;

    @Autowired
    public SportsBookController(SportsBookService sportsBookService){
        this.sportsBookService = sportsBookService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    private Long create(@RequestBody @Valid ScoreBoards scoreBoards){
        log.debug("create request: {}",scoreBoards.toString());
        return sportsBookService.saveOrUpdate(scoreBoards);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/update")
    private Long update(@RequestBody @Valid ScoreBoards scoreBoards){
        log.debug("update request: {}",scoreBoards.toString());
        return sportsBookService.saveOrUpdate(scoreBoards);
    }

    @GetMapping("/id/{id}")
    private ResponseEntity<ScoreBoards> find(@PathVariable @Valid long id){
        log.debug("findbyid request: {}",id);
        Optional<ScoreBoards> scoreBoardData = Optional.ofNullable(sportsBookService.findMatchById(id));
        if (scoreBoardData.isPresent()) {
            return new ResponseEntity<>(scoreBoardData.get(), HttpStatus.OK);
        } else {
        	throw new NoSuchElementException();
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/push")
    private String pushMessageToBrowser(@RequestBody @Valid ScoreBoards scoreBoards){
        log.debug("push request: {}",scoreBoards.toString());
        return sportsBookService.pushMessageToBrowser(scoreBoards);
    }
}
