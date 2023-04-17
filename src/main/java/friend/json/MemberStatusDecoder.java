package friend.json;

import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MemberStatusDecoder implements Decoder.Text<MemberStatus> {

    private static Gson gson = new Gson();

    @Override
    public MemberStatus decode(String s) throws DecodeException {
        return gson.fromJson(s, MemberStatus.class);
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
