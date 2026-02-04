package netty.client;

import com.google.common.util.concurrent.SettableFuture;

public class MessageWrapper<Req, Res> {

    private final Req req;

    private final SettableFuture<Res> res;

    public MessageWrapper(Req req, SettableFuture<Res> res) {
        this.req = req;
        this.res = res;
    }

    public Req getReq() {
        return req;
    }

    public SettableFuture<Res> getRes() {
        return res;
    }
}
