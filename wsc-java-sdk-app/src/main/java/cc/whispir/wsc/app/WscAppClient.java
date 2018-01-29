package cc.whispir.wsc.app;



import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Created by garen on 2018/1/22.
 */
public interface WscAppClient {

    public WscResponse execute(WscRequest request) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, URISyntaxException;


}
