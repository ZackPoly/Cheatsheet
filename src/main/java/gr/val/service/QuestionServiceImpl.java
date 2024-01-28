package gr.val.service;

import gr.val.model.Question;
import gr.val.model.Topic;
import gr.val.parser.QuestionParser;
import gr.val.util.ValUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.val.dao.QuestionDAO;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    @Override
    public List<Topic> getTopics(Connection connection) {
        return questionDAO.getAllTopics(connection);
    }

    @Override
    public Question getQuestion(String body, Connection connection) {
        Question question = questionDAO.getQuestion(ValUtils.finalizeString(body), connection);

        try {
            if (question != null) {
                questionDAO.updateQuestion(question, connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return question;
    }

    @Override
    public void uploadQuestions(InputStream inputStream, Connection connection) {
        Map<Topic, List<Question>> dataMap = QuestionParser.parseFile(inputStream);

        for (Topic topic : dataMap.keySet()) {
            long topicId = questionDAO.insertTopic(topic, connection);
            for (Question question : dataMap.get(topic)) {
                question.setTopicId(topicId);
                questionDAO.insertQuestion(question, connection);
            }
        }
    }

    @Override
    public void resetRequestsNums(Connection connection) {

        try {
            questionDAO.resetRequestsNums(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
