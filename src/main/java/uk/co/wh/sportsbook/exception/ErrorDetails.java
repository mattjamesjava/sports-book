package uk.co.wh.sportsbook.exception;

import lombok.Getter;
import java.util.Date;

@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;

    public ErrorDetails(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }
}
