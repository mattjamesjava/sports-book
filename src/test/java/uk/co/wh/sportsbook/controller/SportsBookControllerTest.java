package uk.co.wh.sportsbook.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.co.wh.sportsbook.model.ScoreBoards;
import uk.co.wh.sportsbook.service.SportsBookService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = SportsBookController.class)
public class SportsBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportsBookService sportsBookService;

    @Test
    public void create_scoreboard() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(1)
                .scoreTeamB(1)
                .build();

        when(sportsBookService.saveOrUpdate(any())).thenReturn(id);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sportbook/create")
                                .content(asJsonString(scoreBoards))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
        }

    @Test
    public void push_message_to_client_browser() throws Exception {
        Long id = 1L;
        String message = "Message Send to Queue";
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(1)
                .scoreTeamB(1)
                .build();

        when(sportsBookService.pushMessageToBrowser(any())).thenReturn(message);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sportbook/push")
                                .content(asJsonString(scoreBoards))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted())
                .andExpect(content().string("Message Send to Queue"));
    }

    @Test
    public void update_scoreboard() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .id(id)
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(2)
                .scoreTeamB(1)
                .build();

        when(sportsBookService.saveOrUpdate(scoreBoards)).thenReturn(id);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sportbook/update")
                                .content(asJsonString(scoreBoards))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());
    }
    

    @Test
    public void create_scoreboard_MethodArgumentNotValidException() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
							        		.teamA("Liverpool")
							                .teamB("Chelsea").build();
        when(sportsBookService.saveOrUpdate(scoreBoards)).thenReturn(id);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/sportbook/update")
                        .content(asJsonString(scoreBoards))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    public void match_not_found_noSuchElementException() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder().build();
        when(sportsBookService.findMatchById(id)).thenReturn(scoreBoards);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sportbook/id/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void general_Exception() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder().build();
        when(sportsBookService.findMatchById(id)).thenReturn(scoreBoards);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sportbook/id/null")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void match_find_by_id() throws Exception {
        Long id = 1L;
        ScoreBoards scoreBoards = ScoreBoards.builder()
                .id(id)
                .teamA("Liverpool")
                .teamB("Chelsea")
                .scoreTeamA(2)
                .scoreTeamB(1)
                .build();
        when(sportsBookService.findMatchById(id)).thenReturn(scoreBoards);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sportbook/id/1"))
                .andExpect(status().isOk())
                .andDo(log())
                .andExpect(content().json("{}, {}, {}, {}, {}"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
