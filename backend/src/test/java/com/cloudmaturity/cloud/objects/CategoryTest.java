package com.cloudmaturity.cloud.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class CategoryTest {

    Category category, cart1;

    @BeforeEach
    void setUp() {

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

        category = new Category();
    }

    @Test
     void test_get_empty_lead_question() {
        assertEquals(null, category.getLeadQuestion(),
                "Getting Null Lead Questions should work");
    }

    @Test
     void test_get_lead_question() {
        assertEquals("Do you have a collaborative culture (e.g. big but not specific/highly detailed goals with no fixed delivery dates)?",
                cart1.getLeadQuestion().getText(),
                "Getting Lead Questions should work");
    }

    @Test
     void test_get_name() {
        assertEquals("Culture", cart1.getName(),
                "Getting Name works");
    }

    @Test
     void test_get_values(){
        assertEquals("Predictive", cart1.getValues().get("Waterfall"),
                "Getting Values works");
        assertEquals("Iterative", cart1.getValues().get("Agile"),
                "Getting Values works");
    }
}


