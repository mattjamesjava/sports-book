package uk.co.wh.sportsbook.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import uk.co.wh.sportsbook.model.ScoreBoards;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SportsBookRepositoryTest {

    @Autowired
    private SportsBookRepository sportsBookRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeeTest(){
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(1)
                .scoreTeamB(1)
                .build();
        sportsBookRepository.save(scoreBoards);
        Assertions.assertThat(scoreBoards.getId()).isGreaterThan(0);
        Assertions.assertThat(scoreBoards.getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    public void getEmployeeTest(){
        ScoreBoards scoreBoards = sportsBookRepository.findById(1L).get();
        Assertions.assertThat(scoreBoards.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void updateEmployeeTest() {

        ScoreBoards scoreBoards = sportsBookRepository.findById(1L).get();
        scoreBoards.setScoreTeamA(2);
//        scoreBoards.setScoreTeamB();
//        scoreBoards.setTeamA();
//        scoreBoards.setTeamB();
        ScoreBoards scoreBoardsUpdated = sportsBookRepository.save(scoreBoards);
        Assertions.assertThat(scoreBoardsUpdated.getScoreTeamA()).isEqualTo(2);
    }
}
