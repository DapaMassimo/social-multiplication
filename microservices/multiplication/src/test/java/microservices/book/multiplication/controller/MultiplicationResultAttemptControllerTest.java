package microservices.book.multiplication.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.service.impl.MultiplicationServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultiplicationResultAttemptControllerTest {

    @MockBean
    MultiplicationServiceImpl multiplicationService;

    @MockBean
    MultiplicationResultAttemptRepository multiplicationResultAttemptRepository;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<MultiplicationResultAttempt> jsonResponse;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

    private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;

    @BeforeAll
    void initAll(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception{
        genericParameterizedTest(true);
    }

    @Test
    public void postResultReturnNotCorrect() throws Exception{
        genericParameterizedTest(true);
    }

    void genericParameterizedTest(final Boolean correct) throws Exception{
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(correct);
        User user = new User("Smith");
        Multiplication multiplication = new Multiplication(50,70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, false);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/results").contentType(MediaType.APPLICATION_JSON).
                        content(jsonResult.write(attempt).getJson())).
                andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new MultiplicationResultAttempt(attempt.getUser(),
                        attempt.getMultiplication(),
                        attempt.getResultAttempt(),
                        correct)).getJson());
    }

    @Test
    public void getUserStats() throws Exception {
        // given
        User user = new User("john_doe");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500,true);
        List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUsers("john_doe")).willReturn(recentAttempts);


        // when
        MockHttpServletResponse response = mvc.perform(
                get("/results").param("alias", "john_doe")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());
    }

    @Test
    public void getResultByIdTest() throws Exception{
        // given
        User user = new User("john_doe");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
        given(multiplicationService.getMultiplicationResultAttemptById(4L)).willReturn(attempt);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/results/4")).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResultAttempt.write(attempt).getJson());
    }
}
