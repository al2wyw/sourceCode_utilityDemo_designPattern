package netty;

import com.google.common.util.concurrent.SettableFuture;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by win10 on 2018/8/12.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String DELIMITER = "$";

    private String uuid;

    private String msg;

    SettableFuture<String> answer = SettableFuture.create();

    public SettableFuture<String> getAnswer() {
        return answer;
    }

    public void setAnswer(SettableFuture<String> answer) {
        this.answer = answer;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString(){
        return uuid+DELIMITER+msg;
    }

    public void fromString(String s){
        String[] arr = StringUtils.split(s,DELIMITER);
        uuid = arr[0];
        msg = arr[1];
    }
}