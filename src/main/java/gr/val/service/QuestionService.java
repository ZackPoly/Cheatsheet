package gr.val.service;

import gr.val.model.Question;
import gr.val.model.Topic;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

public interface QuestionService {

    List<Topic> getTopics(Connection connection);

    Question getQuestion(String body, Connection connection);

    void uploadQuestions(InputStream inputStream, Connection connection);

    void resetRequestsNums(Connection connection);
}
