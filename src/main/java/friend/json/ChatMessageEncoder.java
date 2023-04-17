package friend.json;

import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<Object> {

    private static Gson gson = new Gson();

    @Override
    public String encode(Object data) throws EncodeException {
        if (data instanceof ChatMessageWrapper) {
            return gson.toJson(data);
        } else if (data instanceof Exception) {
            return gson.toJson(new ChatMessageWrapper("error", data));
        } else {
            throw new EncodeException(data, "Something wrong the data for encoding");
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
