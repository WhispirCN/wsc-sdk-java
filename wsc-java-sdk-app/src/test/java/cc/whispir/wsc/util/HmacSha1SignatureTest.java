package cc.whispir.wsc.util;

import cc.whispir.wsc.constants.WscConstants;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by garen on 2018/1/29.
 */
public class HmacSha1SignatureTest {
    @Test
    public void calculateRFC2104HMAC() throws Exception {
        String sign = HmacSha1Signature.calculateRFC2104HMAC("A", "pULnwkHww9RuRFTcN9H3E9mAd0gcYBSmzAOqAYSEoBU=", WscConstants.CHARSET_UTF8);
        System.out.println(sign);
    }

}