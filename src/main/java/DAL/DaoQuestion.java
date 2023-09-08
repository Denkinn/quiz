package DAL;

import domain.Question;
import domain.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoQuestion {

    private final String connectionUrl;

    public DaoQuestion(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public Question insertQuestion(Question question, int quizId) {

        String sql = "insert into question (topic, content, difficulty, quiz_id) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"})) {

            ps.setString(1, question.getTopic());
            ps.setString(2, question.getContent());
            ps.setInt(3, question.getDifficulty());
            ps.setInt(4, quizId);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!rs.next()) {
                throw new RuntimeException("unexpected");
            }
            int questionId = rs.getInt("id");

            // insert responses too
            if (question.getResponses() != null) {
                insertResponses(questionId, question.getResponses(), connection);
            }

            Question newQuestion = new Question(question.getTopic(), question.getContent(), question.getDifficulty());
            newQuestion.setId(questionId);
            newQuestion.setResponses(question.getResponses());

            return newQuestion;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertResponses(int questionId, List<Response> responses, Connection conn) throws SQLException {
        String sql = "insert into response (text, correct, question_id) values (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (Response response : responses) {

            ps.setString(1, response.getText());
            ps.setBoolean(2, response.getCorrect());
            ps.setInt(3, questionId);

            ps.executeUpdate();
        }
    }

    public List<Question> findByTopic(String topic) {

        String sql = "select id, topic, content, difficulty from question where upper(topic) = upper(?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, topic);

            ResultSet rs = ps.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (rs.next()) {
                Question question = new Question(
                        rs.getString("topic"),
                        rs.getString("content"),
                        rs.getInt("difficulty")
                );
                question.setId(rs.getInt("id"));
                question.setResponses(getQuestionResponses(rs.getInt("id"), connection));
                questions.add(question);

            }

            return questions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteQuestion(int id) {

        String sql = "delete from question where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateQuestion(Question question, int id) {

        String sql = "update question set topic = ?, content = ?, difficulty = ? where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, question.getTopic());
            ps.setString(2, question.getContent());
            ps.setInt(3, question.getDifficulty());
            ps.setInt(4, id);

            ps.executeUpdate();
            updateQuestionResponses(id, question.getResponses(), connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateQuestionResponses(int questionId, List<Response> responses, Connection conn) throws SQLException {

        String sql = "delete from response where question_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, questionId);
        ps.executeUpdate();

        insertResponses(questionId, responses, conn);
    }

    private List<Response> getQuestionResponses(int questionId, Connection conn) throws SQLException {

        String sql = "select id, text, correct from response where question_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, questionId);
        ResultSet rs = ps.executeQuery();

        List<Response> responses = new ArrayList<>();
        while (rs.next()) {
            Response response = new Response(
                    rs.getString("text"),
                    rs.getBoolean("correct")
            );
            response.setId(rs.getInt("id"));
            responses.add(response);
        }
        return responses;

    }

    public Question findQuestionById(int questionId) {
        String sql = "select id, topic, content, difficulty from question where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, questionId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Question question = new Question(
                        rs.getString("topic"),
                        rs.getString("content"),
                        rs.getInt("difficulty")
                );
                question.setId(rs.getInt("id"));
                question.setResponses(getQuestionResponses(questionId, connection));

                return question;
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
