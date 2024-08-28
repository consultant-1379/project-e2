package com.cloudmaturity.cloud.mongorepositories;

import com.cloudmaturity.cloud.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
 class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    User user1, user2;

    @BeforeEach
     void init(){
        user1 = new User("make.ref@jimly.com");
        user2 = new User("jik.ref@jimly.com");

        repository.deleteAll();
        repository.save(user1);
        repository.save(user2);
    }

    @Test
     void userPersisted(){
        List<User> items = (List<User>)repository.findAll();

        assertEquals(2, items.size());
        assertUserEquals(user1, items.get(0));
        assertUserEquals(user2, items.get(1));
    }

    private boolean assertUserEquals(User expected, User actual) {
        return expected.getEmail().equals(actual.getEmail());
    }
}
