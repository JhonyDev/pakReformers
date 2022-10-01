package com.app.pakreformers.models;

public class MessagePojo extends Super {
    String MessageId;
    String driveId;
    String driveTitle;
    String messageText;
    String authorId;
    String authorName;

    public MessagePojo() {
    }

    public MessagePojo(String messageId, String driveId, String driveTitle, String messageText, String authorId, String authorName) {
        MessageId = messageId;
        this.driveId = driveId;
        this.driveTitle = driveTitle;
        this.messageText = messageText;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getDriveTitle() {
        return driveTitle;
    }

    public void setDriveTitle(String driveTitle) {
        this.driveTitle = driveTitle;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
