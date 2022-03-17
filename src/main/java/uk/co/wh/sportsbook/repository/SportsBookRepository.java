package uk.co.wh.sportsbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.wh.sportsbook.model.ScoreBoards;

@Repository
public interface SportsBookRepository extends JpaRepository<ScoreBoards, Long> {
}
