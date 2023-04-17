package friend.json;

import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessageWrapper> {

    private static Gson gson = new Gson();

    @Override
    public ChatMessageWrapper decode(String s) throws DecodeException {
        return gson.fromJson(s, ChatMessageWrapper.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
