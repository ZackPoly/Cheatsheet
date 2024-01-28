package gr.val.controller;

import gr.val.model.Question;
import gr.val.model.Topic;
import gr.val.postgres.PostgresManager;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gr.val.service.QuestionService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class QuestionController {

    private static final String HOME = "index";
    private static final String SEARCH = "search";
    // view attributes
    private static final String TOPICS = "topics";
    private static final String QUESTION = "question";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PostgresManager postgresManager;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model, Locale locale) {

        try(Connection connection = postgresManager.getConnection()) {
            List<Topic> topics = questionService.getTopics(connection);

            model.addAttribute(QUESTION, null);
            model.addAttribute(TOPICS, topics);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return HOME;
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String search(@RequestParam Map<String,String> params, Model model, Locale locale) {

        String body = params.get("question");
        try(Connection connection = postgresManager.getConnection()) {
            Question question = questionService.getQuestion(body, connection);

            List<Topic> topics = questionService.getTopics(connection);

            model.addAttribute(QUESTION, question);
            model.addAttribute(TOPICS, topics);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return HOME;
    }

    @RequestMapping(value = {"/upload"}, method = RequestMethod.POST)
    public String upload(HttpServletRequest request, Model model, Locale locale) {

        try(Connection connection = postgresManager.getConnection()) {
            MultipartFile multipartFile = ((StandardMultipartHttpServletRequest) request).getFile("file");

            // Add a new line in case there isn't one already. Helps the Parser.
            //FileUtils.writeStringToFile(multipartFile.getResource().getFile(), "\n", Charset.defaultCharset());

            InputStream inputStream = multipartFile.getInputStream();
            questionService.uploadQuestions(inputStream, connection);

            List<Topic> topics = questionService.getTopics(connection);

            model.addAttribute(QUESTION, null);
            model.addAttribute(TOPICS, topics);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        return HOME;
    }

    @RequestMapping(value = {"/reset"}, method = RequestMethod.GET)
    public String reset(Model model, Locale locale) {

        try(Connection connection = postgresManager.getConnection()) {
            questionService.resetRequestsNums(connection);

            List<Topic> topics = questionService.getTopics(connection);

            model.addAttribute(QUESTION, null);
            model.addAttribute(TOPICS, topics);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return HOME;
    }
}
