package cc.whispir.wsc.app;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by garen on 2018/1/22.
 */
public class DefaultWscClientTest {

    @Test
    public void execute() {
        //Url: "https://d-app.whispir.cc/a/app-027d30049900adf1/v1/events",
        //        AppId: "027d30049900adf1",
        //        AppSecret: "pULnwkHww9RuRFTcN9H3E9mAd0gcYBSmzAOqAYSEoBU=",
        WscAppClient wac = new DefaultWscClient("https://d-app.whispir.cc/a/app-027d30049900adf1/v1/events","027d30049900adf1","pULnwkHww9RuRFTcN9H3E9mAd0gcYBSmzAOqAYSEoBU=");

        WscRequest request = new WscRequest();
        Map<String,Object> event = new HashMap<String,Object>();

        event.put("to","garenwen@whispir.cc");
        event.put("content","hello world");

        request.setEvent(event);
        request.setName("foo");
        try {
            WscResponse response = wac.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


}