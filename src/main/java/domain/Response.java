package domain;

public class Response {

    public Response(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    private int id;
    private String text;
    private boolean correct;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }


    public boolean getCorrect() {
        return correct;
    }
}
