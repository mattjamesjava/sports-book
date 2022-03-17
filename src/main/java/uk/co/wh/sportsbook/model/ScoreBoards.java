package uk.co.wh.sportsbook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Sports_Book")
public class ScoreBoards {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Team name A cannot be empty")
    @Column(name = "Team_A")
    @JsonProperty("teamA")
    private String teamA;

    @NotNull(message = "Team name B cannot be empty")
    @Column(name = "Team_B")
    @JsonProperty("teamB")
    private String teamB;

    @Min(value = 0, message = "Team A score should only contain numbers")
    @Column(name = "Score_Team_A")
    @NotNull(message = "Team A score cannot be null")
    @JsonProperty("scoreTeamA")
    private Integer scoreTeamA;

    @Min(value = 0, message = "Team B score should only contain numbers")
    @Column(name = "Score_Team_B")
    @NotNull(message = "Team B score cannot be null")
    @JsonProperty("scoreTeamB")
    private Integer scoreTeamB;
}
