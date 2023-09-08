package DAL;

import domain.Question;
import domain.Response;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String connectionUrl = "jdbc:hsqldb:file:db/mydb";

    private static DaoQuestion dao = new DaoQuestion("jdbc:hsqldb:file:db/mydb");

    public static void main(String[] args) {


        // insert question
        Question question = new Question("Math", "What is 2+2", 1);
        List<Response> responses = new ArrayList<>();
        responses.add(new Response("4", true));
        responses.add(new Response("5", false));
        question.setResponses(responses);

//        List<Response> resps = dao.insertQuestion(question, 10).getResponses();
//        System.out.println(resps.get(0).getText());
//        System.out.println(resps.get(1).getText());

        // find question by topic
//        List<Question> questions = dao.findByTopic("Math");
//        System.out.println(questions.size());
//        System.out.println(questions.get(0).getContent());
//        System.out.println(questions.get(1).getContent());
//        System.out.println(questions.get(0).getResponses().get(1).getCorrect());

        // delete question
//        dao.deleteQuestion(20);
//
//        Question updatedQuestion = new Question("physics", "mass–energy equivalence formula", 4);
//        List<Response> updatedResponses = new ArrayList<>();
//        updatedResponses.add(new Response("E=mc^2", true));
//        updatedResponses.add(new Response("E=hf", false));
//        updatedQuestion.setResponses(updatedResponses);
//        dao.updateQuestion(updatedQuestion, 21);
//
//        Question questionFromDb = dao.findQuestionById(21);
//        List<Response> resps = questionFromDb.getResponses();
//        System.out.println(questionFromDb.getContent());
//        System.out.println(resps.get(0).getText());
//        System.out.println(resps.get(1).getText());


    }

}