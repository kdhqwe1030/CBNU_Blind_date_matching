package Chatting;

import java.io.Serializable;

enum Info {
    JOIN, EXIT, SEND // 기능 분류
}

class InfoDTO implements Serializable {
    private String nickName;
    private String message;
    private Info command;
    private String url;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Info getCommand() {
        return command;
    }

    public void setCommand(Info command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setURL(String url){this.url = url;}
    public String getURL(){return url;}
}