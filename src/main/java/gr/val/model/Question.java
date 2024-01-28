package gr.val.model;

public interface Question {
    long getTopicId();

    void setTopicId(long id);

    String getBody();

    void setBody(String body);

    String getAnswer();

    void setAnswer(String answer);

    int getRequestsNum();

    void setRequestsNum(int requestsNum);
}
