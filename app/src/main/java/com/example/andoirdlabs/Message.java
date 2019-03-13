package com.example.andoirdlabs;

public class Message {
    private String message;
    private int Direction; // 1: send;  2: receive
    private long msg_id;
    boolean isSent;
    boolean isReceived;

    public Message() {
        this(null, -1,-1);
    }

    public Message(String message, int messageDirection,long id) {
        this.setContent(message);
        this.setMessageDirection(messageDirection);
        this.setMsg_id(id);

    }

    public String getContent() {
        return message;
    }

    private void setContent(String content) {
        this.message = content;
    }

    public int getMessageDirection() {
        return Direction;
    }

    private void setMessageDirection(int messageDirection) {
        this.Direction = messageDirection;
    }
    private void setMsg_id(long id){this.msg_id=id;}
    public long getMsg_id(){return msg_id;}


    @Override
    public String toString() {
        return this.getContent();
    }
}
