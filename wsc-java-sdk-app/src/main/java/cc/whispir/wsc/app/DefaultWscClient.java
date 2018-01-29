package cc.whispir.wsc.app;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * Created by garen on 2018/1/22.
 */
public class DefaultWscClient implements WscAppClient {

    private String methodHmacSha1 = "HMAC-SHA1";
    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private String serverUrl;
    private String appId;
    private String appSecret;
    private String format         = WscConstants.FORMAT_JSON;

    public DefaultWscClient(String serverUrl,String appId,String appSecret){
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public WscResponse execute(WscRequest request) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {


        String contentType ;
        String event = "";
        if (WscConstants.FORMAT_JSON.equals(this.format)) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                event = mapper.writeValueAsString(request.getEvent());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            contentType = "application/json";

        } else {
            XStream xStream = new XStream(new DomDriver());
            event = xStream.toXML(request.getEvent());
            contentType = "application/xml";

        }
        System.out.println("=====");
        String url = this.serverUrl.concat("/" + request.getName());
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        UUID uuid = UUID.randomUUID();

        java.util.Calendar cal = java.util.Calendar.getInstance();

        //System.out.println(event);


        params.add(new BasicNameValuePair("signMethod", methodHmacSha1));
        params.add(new BasicNameValuePair("signNonce", uuid.toString()));
        params.add(new BasicNameValuePair("signVer", "v1.0"));
        params.add(new BasicNameValuePair("ts", "2018-01-29T03:21:05Z"));
        //sdf.format(new Date(cal.getTimeInMillis()))

        String[] keys = new String[params.size()];
        for (int i = 0; i < params.size(); i++) {
            keys[i] = params.get(i).getName();
        }
        Arrays.sort(keys);
        StringBuffer sb = new StringBuffer();
        sb.append(this.appId).append("&");
        sb.append("POST").append("&");
        String encode = URLEncodedUtils.format(params, "UTF-8");


        sb.append(encode);
        //System.out.println(sb.toString());
        String sign = HmacSha1Signature.calculateRFC2104HMAC(sb.toString(), this.appSecret,ENCODING);
        //System.out.println(sign);
        params.add(new BasicNameValuePair("sign", sign));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //System.out.println(url);
        //HttpPost httpPost = new HttpPost(url);
        HttpPost httpPost = new HttpPost(url+"?signMethod=HMAC-SHA1&signNonce=274afaea-ebc1-40b4-b379-418eaad2ddd0&signVer=v1.0&ts=2018-01-29T03:21:05Z&sign=c8f079bfc729aa6d10fdfb2c5e2c9f8b3ec47db1");
        StringEntity entity = new StringEntity(event);
        httpPost.setEntity(entity);

        //httpPost.setEntity(new UrlEncodedFormEntity(params));


        System.out.println(EntityUtils.toString(httpPost.getEntity()));
        httpPost.setHeader("Content-type", contentType);

        CloseableHttpResponse response = null;
        try {

            response = httpClient.execute(httpPost);

            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            response.close();
        }

        httpPost.setHeader("User-Agent", "");
        //Map<String, Object> result = new HashMap<String, Object>();

        return new WscResponse();
    }

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception
    {
        byte[] data=encryptKey.getBytes(ENCODING);
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        //完成 Mac 操作
        return mac.doFinal(text);
    }

}
