package uk.co.wh.sportsbook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.wh.sportsbook.model.ScoreBoards;
import uk.co.wh.sportsbook.repository.SportsBookRepository;

@Slf4j
@Service
public class SportsBookService {

    private SportsBookRepository sportsBookRepository;
    private RabbitTemplate rabbitTemplate;
    private Binding binding;

    @Autowired
    public SportsBookService(SportsBookRepository sportsBookRepository, RabbitTemplate rabbitTemplate, Binding binding){
        this.sportsBookRepository = sportsBookRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.binding = binding;
    }

    //Getting Match details using the method findById() of CrudRepository
    public ScoreBoards findMatchById(long id)
    {
        return sportsBookRepository.findById(id).get();
    }

    //Saving/Updating new match record by using the method save() of CrudRepository
    public Long saveOrUpdate(ScoreBoards scoreBoards){
        log.debug("Sending message to the queue.");
        sportsBookRepository.save(scoreBoards);
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), scoreBoards.toString());
        log.info("Message saved and broadcasted to message queue webhook with id:{}",scoreBoards.getId());
        return scoreBoards.getId();
    }
}
