package com.codyirving.timetotext;
import java.util.Date;

public class ScheduledTextObject {
    String textMessageContent = null;
    Date timeToText = null;
    String numberToText = null;
    public ScheduledTextObject() {}
    public Date getTimeToText() {
        return timeToText;
    }

    public void setTimeToText(Date timeToText) {
        this.timeToText = timeToText;
    }

    public String getTextMessageContent() {
        return textMessageContent;
    }

    public void setTextMessageContent(String textMessageContent) {
        this.textMessageContent = textMessageContent;
    }

    public String getNumberToText() {
        return numberToText;
    }

    public void setNumberToText(String numberToText) {
        this.numberToText = numberToText;
    }
}