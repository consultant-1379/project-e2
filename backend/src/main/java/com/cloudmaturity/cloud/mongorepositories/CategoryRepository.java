package com.cloudmaturity.cloud.mongorepositories;

import com.cloudmaturity.cloud.objects.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {}
