package com.cloudmaturity.cloud.init;

import com.cloudmaturity.cloud.mongorepositories.CategoryRepository;
import com.cloudmaturity.cloud.mongorepositories.UserRepository;
import com.cloudmaturity.cloud.objects.Category;
import com.cloudmaturity.cloud.objects.Question;
import com.cloudmaturity.cloud.objects.User;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class SeedDb {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        List<Category> categories = createCategories();
            try{
                if(new HashSet<>(categories).containsAll(categoryRepository.findAll())){
                    System.out.println("Saving categories");
                    categories.forEach(category -> categoryRepository.save(category));
                }
            } catch (MongoWriteException e){
                System.out.println("Exception while trying to add Category: " + e.getMessage());
            }
        List<User> users = createUsers();
            try{
                if(new HashSet<>(users).containsAll(userRepository.findAll())){
                    users.forEach(user -> userRepository.save(user));
                }
            } catch(MongoWriteException e){
                System.out.println("Exception while trying to add Category: " + e.getMessage());
            }
    }

    public static List<User> createUsers(){
        List<User> users = new ArrayList<>();
        var user = new User("testUser@email.com");
        users.add(user);
        user = new User("anotherTestUser@email.com");
        users.add(user);
        user = new User("finalTestUser@email.com");
        users.add(user);
        return users;
    }

    public static List<Category> createCategories(){
        var proc = new String[] {"No Process", "Waterfall", "Agile", "Cloud Native", "Next"};
        List<Category> categoryList = new ArrayList<>();
        var answers = new String[] {
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
        var q = new Question("How do individuals in your organization interact, communicate, and work with each other?", answers, false);
        var leadQuestionAnswers =  new String[] {"Yes", "No"};
        var leadQuestion = new Question("Do you have a collaborative culture (e.g. big but not specific/highly detailed goals with no fixed delivery dates)?", leadQuestionAnswers, true);
        Map<String, String> values = new LinkedHashMap<>();
        values.put(proc[0], "Individualist");
        values.put(proc[1], "Predictive");
        values.put(proc[2], "Iterative");
        values.put(proc[3], "Collaborative");
        values.put(proc[4], "Experimental");
        var category = new Category( "Culture", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("" +
                "Is your product and design data-driven (that is they are rapidly prototyped, " +
                "deployed and evaluated based on real customer usage " +
                "before deciding on further development or retirement)?", leadQuestionAnswers, true);
        answers = new String[]{
                "We have product roadmaps spanning months or even " +
                        "years into the future. Our releases typically happen every " +
                        "six months to one year, sometimes even longer.",

                "There is pressure to deliver features, fast, and releases " +
                        "happen on a regular planned basis. (For example, 'We'll " +
                        "Feature X in two months, Feature Y in four months and " +
                        "Feature Z in six months’—with no deviation from the " +
                        "schedule).",

                "We release large sets of related features all at once as " +
                        "comprehensive updates.",

                "Our releases are usually small-scale iterative changes to " +
                        "existing features/services.."};
        q = new Question("How do decisions get made in your " +
                "organisation about new products to build, or services and features to offer — or " +
                "how to improve existing ones?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Arbitrary");
        values.put(proc[1], "Long-term plan");
        values.put(proc[2], "Feature driven");
        values.put(proc[3], "Data driven");
        values.put(proc[4], "All driven");
        category = new Category("Production/Service Design", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("" +
                "Are teams Devops teams that are self contained teams responsible " +
                "for all development and " +
                "deployment to production while production is monitored by SRE teams?", leadQuestionAnswers, true);
        answers = new String[]{
                "All decisions are made by managers, and teams must " +
                        "seek permission before changing any part of the project " +
                        "plan, no matter how small.",

                "Applications are developed as several large components, " +
                        "with one team per component fully and vertically " +
                        "responsible for the build.",

                "We have separate teams of specialists to handle different " +
                        "areas: design, architecture, security, testing, etc. When " +
                        "our team’s piece of a project is finished, we hand it off to " +
                        "the next team.",

                "Our teams are mixed: We have developers, QA/testing, " +
                        "someone with server experience, etc. all in one group. We " +
                        "don’t talk to other teams very much since our teams are " +
                        "meant to be self-sufficient and independent."};
        q = new Question("How do responsibilities, communication, and collaboration work between" +
                "teams in your organisation?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "No organization, single contributor");
        values.put(proc[1], "Hierarchy");
        values.put(proc[2], "Cross-functional teams");
        values.put(proc[3], "DevOps/SRE");
        values.put(proc[4], "Internal supply chains");
        category = new Category("Team", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("" +
                "Do you use research and experimentation techniques for large and complex problems " +
                "using lots of proof of concepts to compare options, " +
                "using Kanban to clarify the project then Agile " +
                "methods like Scrum once problem is well understood?", leadQuestionAnswers, true);
        answers = new String[]{
                "We do all our planning up front, and then hand off to " +
                        "teams for execution. Managers handle the collaboration " +
                        "and communication between our teams.",

                "A team will work on one small, defined project and deliver " +
                        "it in two to four weeks. If a new feature request comes in " +
                        "the middle of a delivery cycle, we may or may not be able " +
                        "to add it in.",

                "If a new feature request comes in the middle of a delivery " +
                        "cycle, we have to wait for the next cycle to plan for and " +
                        "incorporate it.",

                "If we can’t coordinate or fix an issue on the last day or two " +
                        "of a production cycle, we can’t ship—so when a bug or " +
                        "some other problem pops up it’s hard to do anything more " +
                        "than a quick fix. (Following up to address an issue in more " +
                        "depth requires a dedicated sprint so we can focus on it). "};
        q = new Question("How do you plan and then execute work?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Random");
        values.put(proc[1], "Waterfall");
        values.put(proc[2], "Agile(Scrum/Kanban)");
        values.put(proc[3], "Design Thinking + Agile + Lean");
        values.put(proc[4], "Distributed, self-organized");
        category = new Category("Process", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("" +
                "Do you have a microprocessor architecture built from independently deployable services?", leadQuestionAnswers, true);
        answers = new String[]{
                "Our system is very big. Few people understand the " +
                        "whole thing. We fear the domino effect: If you change " +
                        "something, you have to be very careful because it could " +
                        "break something else.",

                "Our application(s) is(are) divided into components, " +
                        "probably no more than five or six, communicating through " +
                        "networking.",

                "When we deliver, everything is delivered together, all " +
                        "ready on the same day and at a uniformly high level of " +
                        "quality.",

                "The scope of an app in development is defined by " +
                        "the deployment schedule. Each feature or piece of " +
                        "functionality is broken down into deliverable chunks that " +
                        "fit into the schedule."};
        q = new Question("What is the overall structure of your technology and systems?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Emerging from trial and error");
        values.put(proc[1], "Tightly coupled monolith");
        values.put(proc[2], "Client server");
        values.put(proc[3], "Microservices");
        values.put(proc[4], "Functions");
        category = new Category("Architecture", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("" +
                "Does your system collect metrics, alerts tracing and logging to provide a view of the running " +
                "system and try to keep itself alive through self healing if things begin to deteriorate?", leadQuestionAnswers, true);
        answers = new String[]{
                "We have some simple automation, like scripts, for alerting " +
                        "large-scale issues and outages in the field. We find out " +
                        "about many smaller problems from user reports..",

                "Our systems have full and continuous monitoring, and our " +
                        "Ops team spends lots of time checking on alerts. A lot of " +
                        "time, our system alerts turn out to be nothing.",

                "When problems arise, we have to open each server to " +
                        "understand what happened because we don’t have central " +
                        "logs or tracing. Then we fix it manually: someone from " +
                        "Operations logs into a production server and follows a " +
                        "preset procedure",

                "Some of our system update processes are fully automated " +
                        "and patches can be applied quickly—but a human still has " +
                        "to initialise the process."};
        q = new Question("How does your organisation deploy software and run it in production?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Respond to user complaints");
        values.put(proc[1], "Ad-hoc monitoring");
        values.put(proc[2], "Alerting");
        values.put(proc[3], "Full observability & self-healing");
        values.put(proc[4], "Preventive ML, AI");
        category = new Category("Maintenance and Operations", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("Do you deliver multiple times a day your releasable software?", leadQuestionAnswers, true);
        answers = new String[]{
                "We do ‘big bang’ releases that roll lots of changes into " +
                        "one new version, every six to 12 months. A lot of up-front " +
                        "planning goes into our next release before any actual " +
                        "development begins.",

                "Our delivery process includes some test automation " +
                        "and automated build, but outside of final integration. " +
                        "In an emergency, we can make manual updates to the " +
                        "production codebase.",

                "We don’t like to make changes to our production code, " +
                        "even emergency ones, because there are so many " +
                        "dependencies. Change is risky. Once we release a software " +
                        "version all changes have to wait for the next version roll out",

                "New functionality requests typically can be" +
                        "accommodated within a few weeks, if they are urgent."};
        q = new Question("How does software progress from your development teams to running live in production?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Irregular Releases");
        values.put(proc[1], "Periodic Releases");
        values.put(proc[2], "Continuous Integration");
        values.put(proc[3], "Continuous Delivery");
        values.put(proc[4], "Continuous Deployment");
        category = new Category("Delivery", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question("Do you run on Kubernetes?", leadQuestionAnswers, true);
        answers = new String[]{
                "Operations team is in charge of provisioning, period. You " +
                        "have to write a ticket to provision a machine—engineers " +
                        "can’t self-service.",

                "A machine can be provisioned (possibly even " +
                        "autoprovisioned) in hours, or maybe a day or two, and the " +
                        "process is fully automated by Ops.",

                "Developers write applications, and specify what they " +
                        "will need to run successfully in production (OS, libraries, " +
                        "dependent tools). The Ops team manually configures the " +
                        "production machines to meet the machine dependencies " +
                        "the Dev team specified.",

                "Provisioning is a mix of automation and manual work. " +
                        "Any task taking longer than a week to provision to VM" +
                        "breaks the production cycle, so is a nonstarter."};
        q = new Question("How does your organisation create and then control new infrastructure? How quickly can you deploy?", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Manual");
        values.put(proc[1], "Scripted");
        values.put(proc[2], "Config. management (Puppet/Chef/Ansible)");
        values.put(proc[3], "Orchestration (Kubernetes)");
        values.put(proc[4], "Serverless");
        category = new Category("Provisioning", leadQuestion, q, values);
        categoryList.add(category);

        leadQuestion = new Question(" Do you deploy your software in containers?", leadQuestionAnswers, true);
        answers = new String[]{
                "We have multiple physical servers in our own private " +
                        "data center (either on premises or co-located). If one of " +
                        "our servers goes down, we have to manually provision its " +
                        "replacement.",

                "We don’t use physical servers—we have VMs. We also " +
                        "have some instances in the cloud, which we manage " +
                        "manually.",

                "A data centre failure is just about the worst disaster we can imagine.",

                "Provisioning infrastructure is a mix of automation and " +
                        "manual work, so a new VM can take a couple of days to set up."};
        q = new Question("Your Infrastructure describes the physical servers or instances that your " +
                "production environment consists of: what they are, where they are, and how they are managed.", answers, false);
        values = new LinkedHashMap<>();
        values.put(proc[0], "Single server");
        values.put(proc[1], "Multiple servers");
        values.put(proc[2], "VMs (pets)");
        values.put(proc[3], "Containers/hybrid could (cattle)");
        values.put(proc[4], "Edge computing");
        category = new Category("Infrastructure", leadQuestion, q, values);
        categoryList.add(category);

        return categoryList;
    }


}



