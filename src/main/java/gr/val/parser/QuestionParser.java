package gr.val.parser;

import gr.val.model.Question;
import gr.val.model.QuestionImpl;
import gr.val.model.Topic;
import gr.val.model.TopicImpl;
import gr.val.util.ValUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionParser {

    private static final String TOPIC_PATTERN = "[\\d+][\\s]?[-]";
    private static final String QUESTION_PATTERN = "[\\d+]\\.";

    public static Map<Topic, List<Question>> parseFile(InputStream inputStream) {
        Map<Topic, List<Question>> dataMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            String prefix;

            Topic currentTopic = null;
            String currentQuestionBody = null;

            boolean readingTopic = false;
            boolean readingQuestion = false;
            boolean readingAnswer = false;
            String currentValue = "";

            while ((line = reader.readLine()) != null) {

                // Blank line means the full value of Topic Title or Answer is read
                if (line.trim().length() == 0) {
                    if (readingTopic) {
                        currentTopic = new TopicImpl(currentValue);
                        dataMap.put(currentTopic, new ArrayList<>());
                        readingTopic = false;
                    } else if (readingAnswer) {
                        Question question = new QuestionImpl(currentQuestionBody, currentValue);
                        dataMap.get(currentTopic).add(question);
                        readingAnswer = false;
                    }
                    currentValue = "";
                    continue;
                }

                if (readingQuestion && currentValue.endsWith(":")) {
                    currentQuestionBody = currentValue.substring(0, currentValue.length()-1);

                    currentValue = "";
                    readingQuestion = false;
                }

                prefix = getPrefix(TOPIC_PATTERN, line);
                if (prefix != null) {
                    readingTopic = true;
                    currentValue = ValUtils.finalizeString(prefix, line);

                    continue;
                }

                prefix = getPrefix(QUESTION_PATTERN, line);
                if (prefix != null) {
                    readingQuestion = true;
                    currentValue = ValUtils.finalizeString(prefix, line);

                    continue;
                }

                // Value is continued on the next line
                currentValue = currentValue + " " + ValUtils.finalizeString(line);
                if (!(readingTopic || readingQuestion)) {
                    readingAnswer = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }

    private static String getPrefix(String patternString, String line) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }
}
