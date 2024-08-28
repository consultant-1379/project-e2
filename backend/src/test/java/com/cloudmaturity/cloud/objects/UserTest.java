package com.cloudmaturity.cloud.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class UserTest {

    User user1, user2;

    @BeforeEach
    void setUp() {

        user1 = new User();
        user2 = new User("jik.ref@jimly.com");

        Map<String, Float> userMap = new HashMap<String, Float>();
        userMap.put("Culture", 2.0f);
        UserSubmission userSubmission = new UserSubmission(userMap);

        user2.addAnswer(userSubmission);
    }

    @Test
     void test_get_empty_user_email() {
        assertEquals(null, user1.getEmail(),
                "Getting Null User EmaIL should work");
    }


    @Test
     void test_get_user_email() {
        assertEquals("jik.ref@jimly.com", user2.getEmail(),
                "Getting User Email should work");
    }

    @Test
     void test_get_user_submission_pass() {
        assertEquals(2.0f, user2.getUserSubmissions().get(0).getAnswers().get("Culture"),
                "Getting Null UserSubmissions should work");
    }

    @Test
     void test_set_user_submission_pass() {
        Map<String, Float> userMap = new HashMap<>();
        userMap.put("Culture", 2.0f);

        UserSubmission userSubmission = new UserSubmission(userMap);
        userSubmission.setAnswers(userMap);

        assertEquals(userMap, userSubmission.getAnswers(), "Getting User Submissions works");
    }




}
