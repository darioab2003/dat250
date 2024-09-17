package com.example.demo;

public class VoteOption {
    private String id;  // Asegúrate de incluir el ID si es necesario
    private String caption;
    private int presentationOrder;
    private Poll poll;

    public VoteOption() {}

    // Getter y setter para el ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(int presentationOrder) {
        this.presentationOrder = presentationOrder;
    }
}
