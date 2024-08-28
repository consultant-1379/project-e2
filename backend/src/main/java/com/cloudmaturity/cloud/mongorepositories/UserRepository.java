package com.cloudmaturity.cloud.mongorepositories;

import com.cloudmaturity.cloud.objects.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User getUserByEmail(String email);
    User getUserById(String id);

}
