package uk.co.wh.sportsbook;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SportsBookApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void applicationContextTest() {
        SportsBookApplication.main(new String[] {});
    }
}
