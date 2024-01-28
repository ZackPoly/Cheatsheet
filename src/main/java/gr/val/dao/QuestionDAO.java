package gr.val.dao;

import gr.val.model.Question;
import gr.val.model.Topic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO {
    Question getQuestion(String body, Connection connection);

    List<Question> getAllQuestions(Connection connection);

    List<Topic> getAllTopics(Connection connection);

    long insertTopic(Topic topic, Connection connection);

    void insertQuestion(Question question, Connection connection);

    void updateQuestion(Question question, Connection connection) throws SQLException;

    void resetRequestsNums(Connection connection) throws SQLException;
}
