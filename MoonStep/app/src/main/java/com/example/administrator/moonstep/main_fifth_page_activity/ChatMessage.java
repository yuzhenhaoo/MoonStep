package com.example.administrator.moonstep.main_fifth_page_activity;

public class ChatMessage {
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息类型：
     * 0代表文本
     * 1代表图片
     */
    private int type;

    /**
     * 判断是否为自己发送
     * 1：代表是我发送的
     * 0：代表不是我发送的
     */
    private boolean isMeSend;

    public ChatMessage(String msg, int type, boolean isMeSend){
        this.msg = msg;
        this.type = type;
        this.isMeSend = isMeSend;
    }

    public ChatMessage(){

    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public void setisMeSend(boolean isMeSend){
        this.isMeSend = isMeSend;
    }

    public boolean isMeSend(){
        return isMeSend;
    }
}
