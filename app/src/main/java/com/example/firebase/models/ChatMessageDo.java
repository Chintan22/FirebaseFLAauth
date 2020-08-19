package com.example.firebase.models;

import java.io.Serializable;

public class ChatMessageDo implements Serializable, Comparable<ChatMessageDo> {

    public String messageText = "";
    public String fromUserId = "";
    public String messageUserName = "";
    public long messageTime;

    public ChatMessageDo(){}

    public ChatMessageDo(String messageText, String fromUserId, String messageUserName, long messageTime){
        this.messageText = messageText;
        this.fromUserId = fromUserId;
        this.messageUserName = messageUserName;
        this.messageTime = messageTime;
    }

    @Override
    public int compareTo(ChatMessageDo chatMessageDo) {
        return Long.valueOf(messageTime).compareTo(Long.valueOf(chatMessageDo.messageTime));
    }

}
