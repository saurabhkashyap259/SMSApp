package com.mate.smsapp.model;

import android.net.Uri;

/**
 * Created by sasuke on 22/7/16.
 */
public class Conversation {

    Uri photoUri;
    String caontactName;
    String messageDate;
    String messageBody;
    String threadID;

    public Conversation() {
    }

    public Conversation(Uri photoUri, String caontactName, String messageDate, String messageBody, String threadID) {
        this.photoUri = photoUri;
        this.caontactName = caontactName;
        this.messageDate = messageDate;
        this.messageBody = messageBody;
        this.threadID = threadID;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public String getCaontactName() {
        return caontactName;
    }

    public void setCaontactName(String caontactName) {
        this.caontactName = caontactName;
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

    public String getThreadID() {
        return threadID;
    }

    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
}
