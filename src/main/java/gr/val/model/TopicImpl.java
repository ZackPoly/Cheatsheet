package gr.val.model;

public class TopicImpl implements Topic {

    private long id;
    private String title;
    private int requestsNum;

    public TopicImpl(long id, String title, int requestsNum) {
        this.id = id;
        this.title = title;
        this.requestsNum = requestsNum;
    }

    public TopicImpl(String title) {
        this.title = title;
        this.requestsNum = 0;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
