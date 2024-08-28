package com.cloudmaturity.cloud.controllers;

import com.cloudmaturity.cloud.mongorepositories.UserRepository;
import com.cloudmaturity.cloud.objects.Category;
import com.cloudmaturity.cloud.objects.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class ObjectDataRestControllerTest {

    @MockBean
    private UserRepository repo;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private ObjectDataRestController userController;


    @org.junit.jupiter.api.BeforeEach
     void init(){
        User first_applicant = new User("a.king@ericsson.com");
        when(repo.findById("a.king@ericsson.com")).thenReturn(Optional.of(first_applicant));

        repo = Mockito.mock(UserRepository.class);
    }



    @Test
     void test_get_category_works_properly() throws Exception {
        ResponseEntity<Collection<Category>> responseEntity = this.restTemplate.exchange(
                "/db/getCategories/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Category>>() {}
        );
        //Collection<Category> categoryCollection = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(2, categoryCollection.size());
    }

    @Test
     void test_get_user_by_email() throws Exception {
        User first_applicant = new User("a.king@ericsson.com");

        when(repo.findById("a.king@ericsson.com")).thenReturn(Optional.of(first_applicant));

        ResponseEntity<User> responseEntity = this.restTemplate.exchange(
                "/db/getUserByEmail/a.king@ericsson.com",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {}
        );
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());


    }

    @Test
     void test_get_user_by_Id() throws Exception {
        User first_applicant = new User("a.king@ericsson.com");

        when(repo.findById(first_applicant.getId())).thenReturn(Optional.of(first_applicant));

        ResponseEntity<User> responseEntity = this.restTemplate.exchange(
                "/db/getUserById/" + first_applicant.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {}
        );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());


    }

    @Test
     void test_get_user_answers() throws Exception {
        User first_applicant = new User("a.king@ericsson.com");

        when(repo.findById("a.king@ericsson.com")).thenReturn(Optional.of(first_applicant));

        ResponseEntity<User> responseEntity = this.restTemplate.exchange(
                "/db/getUserAnswers/" + first_applicant.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {}
        );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());


    }

    @Test
     void test_create_users_works_properly() throws Exception {
        User user = new User("mike.ross@gmail.com");
        when(repo.save(any(User.class))).thenReturn(user);

        ResponseEntity<User> responseEntity = this.restTemplate.postForEntity(
                "/db/createUser/",
                user,
                User.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
     void test_post_users_submission_works_properly() throws Exception {
        User user = new User("mike.ross@gmail.com");
        when(repo.save(any(User.class))).thenReturn(user);

        ResponseEntity<User> responseEntity = this.restTemplate.postForEntity(
                "/db/submitUserAnswers/" + user.getId(),
                user,
                User.class);

        //System.out.println(responseBody.getUserSubmissions());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());


    }
}
