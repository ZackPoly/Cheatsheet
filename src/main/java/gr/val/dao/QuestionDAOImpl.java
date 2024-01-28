package gr.val.dao;

import gr.val.model.Question;
import gr.val.model.QuestionImpl;
import gr.val.model.Topic;
import gr.val.model.TopicImpl;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDAOImpl implements QuestionDAO {

    private static String GET_QUESTION = "SELECT * FROM \"public\".\"QUESTION\" " +
            "WHERE UPPER(\"BODY\") LIKE UPPER('%1$s')";
    private static String GET_ALL_QUESTIONS = "SELECT * FROM \"public\".\"QUESTION\" " +
            "ORDER BY \"REQUESTS_NUM\" DESC";
    private static String GET_ALL_TOPICS = "SELECT * FROM \"public\".\"TOPIC\" " +
            "ORDER BY \"REQUESTS_NUM\" DESC LIMIT 5";
    private static String INSERT_TOPIC = "INSERT INTO \"public\".\"TOPIC\" " +
            "(\"TITLE\") VALUES ('%1$s')";
    private static String INSERT_QUESTION = "INSERT INTO \"public\".\"QUESTION\" " +
            "(\"BODY\",\"ANSWER\",\"TOPIC_ID\") VALUES ('%1$s','%2$s',%3$s)";
    private static String TOPIC_REQUESTED = "UPDATE \"public\".\"TOPIC\" " +
            " SET \"REQUESTS_NUM\" = \"REQUESTS_NUM\" + 1 WHERE \"ID\" = %1$s";
    private static String QUESTION_REQUESTED = "UPDATE \"public\".\"QUESTION\" " +
            " SET \"REQUESTS_NUM\" = \"REQUESTS_NUM\" + 1 WHERE UPPER(\"BODY\") LIKE UPPER('%1$s')";
    private static String RESET_TOPICS = "UPDATE \"public\".\"TOPIC\" " +
            " SET \"REQUESTS_NUM\" = 0 WHERE \"ID\" > 0";
    private static String RESET_QUESTIONS = "UPDATE \"public\".\"QUESTION\" " +
            " SET \"REQUESTS_NUM\" = 0 WHERE \"TOPIC_ID\" > 0";



    @Override
    public Question getQuestion(String body, Connection connection) {
        String query = String.format(GET_QUESTION, body);
        Question question = null;

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                long topicId = rs.getLong("TOPIC_ID");
                int requestsNum = rs.getInt("REQUESTS_NUM");
                String answer = rs.getString("ANSWER");

                question = new QuestionImpl(topicId, body, answer, requestsNum);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return question;
    }

    @Override
    public List<Question> getAllQuestions(Connection connection) {
        List<Question> questions = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_QUESTIONS);
            while (rs.next()) {
                long topicId = rs.getLong("TOPIC_ID");
                String body = rs.getString("BODY");
                String answer = rs.getString("ANSWER");
                int requestsNum = rs.getInt("REQUESTS_NUM");

                questions.add(new QuestionImpl(topicId, body, answer, requestsNum));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    @Override
    public List<Topic> getAllTopics(Connection connection) {
        List<Topic> topics = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_ALL_TOPICS);
            while (rs.next()) {
                long id = rs.getLong("ID");
                String title = rs.getString("TITLE");
                int requestsNum = rs.getInt("REQUESTS_NUM");

                topics.add(new TopicImpl(id, title, requestsNum));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topics;
    }

    @Override
    public long insertTopic(Topic topic, Connection connection) {

        try (Statement stmt = connection.createStatement()) {

            String query = String.format(INSERT_TOPIC, topic.getTitle());
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong("ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertQuestion(Question question, Connection connection) {

        try (Statement stmt = connection.createStatement()) {
            String query = String.format(INSERT_QUESTION, question.getBody(), question.getAnswer(), question.getTopicId());
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateQuestion(Question question, Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        try (Statement stmt = connection.createStatement()) {

            String query = String.format(QUESTION_REQUESTED, question.getBody());
            stmt.addBatch(query);

            query = String.format(TOPIC_REQUESTED, question.getTopicId());
            stmt.addBatch(query);

            int[] updateCount = stmt.executeBatch();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void resetRequestsNums(Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        try (Statement stmt = connection.createStatement()) {

            String query = String.format(RESET_TOPICS);
            stmt.addBatch(query);

            query = String.format(RESET_QUESTIONS);
            stmt.addBatch(query);

            int[] updateCount = stmt.executeBatch();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
