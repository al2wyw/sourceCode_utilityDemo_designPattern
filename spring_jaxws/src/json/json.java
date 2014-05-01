package json;
import net.sf.json.JSONObject;

public class json {
	public static String createJsonString(String key, Object value)
    {
        String jsonString = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        jsonString = jsonObject.toString();

        return jsonString;

    }
}
