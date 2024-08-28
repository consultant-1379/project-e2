package com.cloudmaturity.cloud.mongorepositories;

import com.cloudmaturity.cloud.objects.Category;
import com.cloudmaturity.cloud.objects.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
 class CategoryRepositoryTest {

    @Autowired
    CategoryRepository repository;

    Category cart1, cart2;

    @BeforeEach
        void init() {

        String[] answers = {
                "Project managers coordinate between all the different " +
                        "teams working on the same project, and the teams have " +
                        "highly specialised responsibilities.",

                "Our development teams focus on achieving small, " +
                        "defined objectives quickly and then moving immediately " +
                        "to the next one.",

                "A lot of up-front planning goes into documenting each " +
                        "step of a project before it even begins. ",
                "Each team contain a mix of members whose different " +
                        "areas of expertise cover the full spectrum of skills needed " +
                        "for crafting a releasable increment."};
        Question q = new Question("How do individuals in your organization interact, communicate, and work with each other?", answers, false);
        String[] leadQuestionAnswers =  {"Yes", "No"};
        Question leadQuestion = new Question("Do you have a collaborative culture (e.g. big but not specific/highly detailed goals with no fixed delivery dates)?", leadQuestionAnswers, true);
        Map<String, String> values = new LinkedHashMap<>();
        values.put("No process", "Individualist");
        values.put("Waterfall", "Predictive");
        values.put("Agile", "Iterative");
        values.put("Cloud native", "Collaborative");
        values.put("Next", "Experimental");

        cart1 = new Category("Culture", leadQuestion, q, values);
        cart2 = new Category("Product/Design", leadQuestion, q, values);
        //cart3 = new Category("ProductC", "", "");

        repository.deleteAll();
        repository.save(cart1);
        repository.save(cart2);
    }

    @Test
        void categoryPersisted(){

        List<Category> items = (List<Category>)repository.findAll();

        assertEquals(2, items.size());
        assertCategoryEquals(cart1, items.get(0));
        assertCategoryEquals(cart2, items.get(1));
    }

    private boolean assertCategoryEquals(Category expected, Category actual) {
        return expected.getName().equals(actual.getName()) &&
                expected.getLeadQuestion() == actual.getLeadQuestion();
    }


}
