package gr.val.model;

public class QuestionImpl implements Question {

    private long topicId;
    private String body;
    private String answer;
    private int requestsNum;

    public QuestionImpl(long topicId, String body, String answer, int requestsNum) {
        this.topicId = topicId;
        this.body = body;
        this.answer = answer;
        this.requestsNum = requestsNum;
    }

    public QuestionImpl(String body, String answer) {
        this.body = body;
        this.answer = answer;
        this.requestsNum = 0;
    }

    @Override
    public long getTopicId() {
        return topicId;
    }

    @Override
    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int getRequestsNum() {
        return requestsNum;
    }

    @Override
    public void setRequestsNum(int requestsNum) {
        this.requestsNum = requestsNum;
    }

}
