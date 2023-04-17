package friend.json;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MemberStatusEncoder implements Encoder.Text<MemberStatus> {

    private static Gson gson = new Gson();

    @Override
    public String encode(MemberStatus memberStatus) throws EncodeException {
        return gson.toJson(memberStatus);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
