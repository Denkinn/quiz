package domain;

import java.util.List;

public class Question{

    public Question(String topic, String content, int difficulty) {
        this.topic = topic;
        this.content = content;
        this.difficulty = difficulty;
    }

    private int id;
    private String topic;
    private String content;
    private int difficulty;
    private List<Response> responses;

    public String getTopic() {
        return topic;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
    public List<Response> getResponses() {
        return responses;
    }
}
