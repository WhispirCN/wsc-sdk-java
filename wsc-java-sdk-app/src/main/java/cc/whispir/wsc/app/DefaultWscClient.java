package cc.whispir.wsc.app;


import cc.whispir.wsc.constants.WscConstants;
import cc.whispir.wsc.util.DateUtils;
import cc.whispir.wsc.util.HmacSha1Signature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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


    private String serverUrl;
    private String appId;
    private String appSecret;

    public DefaultWscClient(String serverUrl,String appId,String appSecret){
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public WscResponse execute(WscRequest request) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, URISyntaxException {


        String contentType ;
        String event;
        if (WscConstants.FORMAT_JSON.equals(WscConstants.FORMAT_JSON)) {
            ObjectMapper mapper = new ObjectMapper();

            event = mapper.writeValueAsString(request.getEvent());

            contentType = "application/json";

        } else {
            XStream xStream = new XStream(new DomDriver());
            event = xStream.toXML(request.getEvent());
            contentType = "application/xml";

        }

        String url = this.serverUrl.concat("/" + request.getName());
        List<NameValuePair> params = new ArrayList<NameValuePair>();


        String ts = DateUtils.formatUTC(DateUtils.RFC3339);
        UUID uuid = UUID.randomUUID();


        params.add(new BasicNameValuePair("signMethod", WscConstants.SIGN_TYPE_HMAC_SHA1));
        params.add(new BasicNameValuePair("signNonce", uuid.toString()));
        params.add(new BasicNameValuePair("signVer", "v1.0"));
        params.add(new BasicNameValuePair("ts", ts));


        if( request.isSecure()){
            params = sign(params);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost();
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        httpPost.setURI(new URI(url+"?"+ EntityUtils.toString(httpPost.getEntity())));

        StringEntity entity = new StringEntity(event);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", contentType);

        CloseableHttpResponse response = null;
        try {

            response = httpClient.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            response.close();
        }

        return new WscResponse();
    }

    private List<NameValuePair> sign(List<NameValuePair> params) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(this.appId).append("&");
        sb.append("POST").append("&");

        sb.append(URLEncodedUtils.format(params, WscConstants.CHARSET_UTF8));

        String sign = HmacSha1Signature.calculateRFC2104HMAC(sb.toString(), this.appSecret, WscConstants.CHARSET_UTF8);
        System.out.println(sb.toString());
        params.add(new BasicNameValuePair("sign", sign));
        return params;
    }

}
