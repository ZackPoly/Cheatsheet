package gr.val.model;

public interface Topic {
    long getId();

    void setId(long id);

    String getTitle();

    void setTitle(String title);

    int getRequestsNum();

    void setRequestsNum(int requestsNum);
}
