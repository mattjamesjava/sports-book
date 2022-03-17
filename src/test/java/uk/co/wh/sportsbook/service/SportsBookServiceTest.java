package uk.co.wh.sportsbook.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import uk.co.wh.sportsbook.model.ScoreBoards;
import uk.co.wh.sportsbook.repository.SportsBookRepository;

@ExtendWith(MockitoExtension.class)
public class SportsBookServiceTest {
	@Mock
    private SportsBookRepository sportsBookRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private Binding binding;

    @InjectMocks
    private SportsBookService sportsBookService = new SportsBookService(sportsBookRepository, rabbitTemplate, binding);

    @Test
    public void save_Match_Success() {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .id(id)
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(1)
                .scoreTeamB(1)
                .build();
        when(sportsBookRepository.save(Mockito.any())).thenReturn(scoreBoards);
        Long savedMatchId = sportsBookService.saveOrUpdate(scoreBoards);
        assertThat(savedMatchId, is(scoreBoards.getId()));
        verify(sportsBookRepository).save(scoreBoards);    }

    @Test
    public void findById_should_Return_Match_ifFound() {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .id(id)
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(1)
                .scoreTeamB(1)
                .build();

        when(sportsBookRepository.findById(scoreBoards.getId())).thenReturn(Optional.of(scoreBoards));
        ScoreBoards expectedMatch = sportsBookService.findMatchById(scoreBoards.getId());
        assertThat(expectedMatch, is(scoreBoards));
        verify(sportsBookRepository).findById(scoreBoards.getId());
    }

    @Test()
    public void should_throw_exception_when_id_doesnt_exist() {
        Long id = 1L;
       
        when(sportsBookRepository.findById(Mockito.any())).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, ()->{
        	sportsBookService.findMatchById(id);
        });
    }

}
