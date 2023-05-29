package com.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: liyang
 * Date: 2023-05-29
 * Time: 11:30
 * Description:
 */
@Controller
@RequestMapping("perf")
public class PerfController {

    @RequestMapping(value="run", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String run(@RequestParam("count") int count, @RequestParam("sleep") int sleep) throws URISyntaxException, IOException {
        String name = Thread.currentThread().getName();
        long start = System.currentTimeMillis();
        int n = 0;
        double d = Double.MAX_VALUE;
        long l = 0;
        for (int i = 0; i < count; i++) {
            for(long b = 0; b < 100; b++) {
                d = d / 3;
                n += 2;
                l = n * 3;
            }
        }

        System.out.printf("%s: %d, %f, %d, cpu Eme: %d ms\n", name, n, d, l, System.currentTimeMillis() - start);

        ClientHttpResponse response = new SimpleClientHttpRequestFactory()
                .createRequest(new URI("http://localhost:8080/spring/perf/io?wait=" + sleep), HttpMethod.GET).execute();
        System.out.printf("%s: %s,  %d ms\n", name, response.getStatusCode().toString(), System.currentTimeMillis() - start);
        response.close();

        return "";
    }

    @RequestMapping(value="io", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String io(@RequestParam("wait") int sleepTime) throws InterruptedException {
        String name = Thread.currentThread().getName();
        long t1 = System.currentTimeMillis();
        Thread.sleep(sleepTime);
        long t2 = System.currentTimeMillis();
        System.out.printf("%s: Thread sleep: %d ms\n",name, t2 - t1);

        return "";
    }
}
