package netty.client;

import com.google.protobuf.ByteString;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import netty.NamedThreadFactory;
import netty.codec.MyCodec;
import netty.codec.MyFrameReader;
import netty.proto.AntiFraud;
import netty.proto.Req;
import netty.proto.Res;
import netty.proto.Scintf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    private static final Logger logger = LoggerFactory.getLogger(Driver.class);

    private static Map<String, Integer> SrvType = new HashMap<>();

    static {
        SrvType.put("DescribeAntiFraud", 1300307);
        SrvType.put("DescribeMultipleLoansFinance", 1300566);
        SrvType.put("GetAntiFraud", 1300307);
        SrvType.put("GetMultipleLoans", 1300566);
        SrvType.put("GetAntiFraudInfo", 1300307); //朴道置信度
        SrvType.put("GetAntiFraudVip", 1300307); //朴道反欺诈VIP
        SrvType.put("GetModelBox", 1300307); //朴道模盒v2
        SrvType.put("GetInsuranceRisk", 1300594); //朴道贷中
        SrvType.put("GetAntiFraudVipLite", 1300307);
        SrvType.put("GetAntiFraudMaas", 1300307);
        SrvType.put("QueryAntiFraud", 1300307);
        SrvType.put("QueryAntiFraudVip", 1300307);
        SrvType.put("QueryMultipleLoans", 1300566);
        SrvType.put("QueryInsuranceRisk", 1300594);
    }

    public static void main(String[] args) throws Exception {

        int cons = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        String host = args.length > 1 ? args[1] : "";
        int port = args.length > 2 ? Integer.parseInt(args[2]) : 13649;
        String file = args.length > 3 ? args[3] : "";

        String scene = "test";

        NettyFrameReader.registerFrameReader(scene,new MyFrameReader());
        NettyCodec.registerMessageDecoder(scene, new MyCodec());
        NettyCodec.registerMessageEncoder(scene, new MyCodec());

        NettyClient client = new NettyClient();
        client.start(scene, cons);

        for (int i = 0; i < cons; i++) {
            if (!client.connect(host,port)){
                logger.error("connect failed");
            }
        }

        ThreadPoolExecutor pool = new ThreadPoolExecutor(200, 200, 60,
                TimeUnit.SECONDS, new SynchronousQueue<>(),
                new NamedThreadFactory("Client-Biz-Thread"));
        try (InputStream reader = Thread.currentThread().getContextClassLoader().getResourceAsStream(file)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(reader));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                String id = arr[0];
                String phone = arr[1];

                pool.submit(() -> {
                    Req req = new Req();
                    UUID uuid = UUID.randomUUID();
                    logger.info("sign:{}", uuid);
                    req.setReq(AntiFraud.AntifraudReq.newBuilder()
                            .setSignature(ByteString.copyFromUtf8(uuid.toString()))
                            .setIdNumber(ByteString.copyFromUtf8(id))
                            .setScene(2289)
                            .setPhoneCryptoType(3)
                            .setIdCryptoType(3)
                            .setIsTestRequest(1)
                            .build());
                    req.setHeader(Scintf.ScHeader.newBuilder()
                            .setPhoneNumber(ByteString.copyFromUtf8(phone))
                            .setAppid(SrvType.get("GetAntiFraud"))
                            .setType(1251001047) //appid
                            .setPostip(0)
                            .setPosttime((int)System.currentTimeMillis() / 1000)
                            .setTimeout(1000)
                            .build());
                    req.setPubPart(Scintf.PubPart.newBuilder().build());
                    try {
                        Res res = client.sendMessage(req);
                        logger.info("sign:{} code:{} found:{} score:{}", uuid, res.getRsp().getRetcode(), res.getRsp().getFound(), res.getRsp().getRiskScore());
                    } catch (Exception e) {
                        logger.error("sign:{}", uuid, e);
                    }
                });
            }
        }
    }
}
