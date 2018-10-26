package priv.zxy.moonstep.kernel.bean;

public class Message {
    /**
     * 消息内容
     */
    private String msg;

    /**
     * 判断是否为自己发送
     * 1：代表是我发送的
     * 0：代表不是我发送的
     */
    private boolean isMeSend;

    public Message(String msg, boolean isMeSend){
        this.msg = msg;
        this.isMeSend = isMeSend;
    }

    public Message(){

    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setisMeSend(boolean isMeSend){
        this.isMeSend = isMeSend;
    }

    public boolean isMeSend(){
        return isMeSend;
    }
}
