package cc.whispir.wsc.app;

import java.io.IOException;
import java.net.URISyntaxException;
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
        // Url: "https://app.wizardcloud.cn/a/app-033f5501e4000e4b/v1/events/whoru",
        // AppId: "033f5501e4000e4b",
        // AppSecret: "ZBWRGW2n97nn/5n7CeLxhdSV3/POSDlpRQ/cMVIGxhQ=",
        WscAppClient wac = new DefaultWscClient("https://app.wizardcloud.cn/a/app-033f5501e4000e4b/v1/events","033f5501e4000e4b","ZBWRGW2n97nn/5n7CeLxhdSV3/POSDlpRQ/cMVIGxhQ=");

        WscRequest request = new WscRequest();
//        Map<String,Object> event = new HashMap<String,Object>();
//
//        event.put("to","garenwen@whispir.cc");
//        event.put("content","hello world");
//
//        request.setEvent(event);
        request.setName("whoru");
        request.setSecure(true);
        try {
            WscResponse response = wac.execute(request);
            System.out.println(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}