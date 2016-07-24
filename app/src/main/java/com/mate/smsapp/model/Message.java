package com.mate.smsapp.model;

import android.net.Uri;

/**
 * Created by sasuke on 23/7/16.
 */
public class Message {

    String messageID;
    Uri profileImage;
    String messageDate;
    String messageBody;
    String messageType;

    public Message() {
    }

    public Message(String messageID, Uri profileImage, String messageDate, String messageBody, String messageType) {
        this.messageID = messageID;
        this.profileImage = profileImage;
        this.messageDate = messageDate;
        this.messageBody = messageBody;
        this.messageType = messageType;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
