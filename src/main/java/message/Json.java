package message;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

public class Json {
    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T JsonToObject(String s, Class<T> tClass) throws JsonProcessingException {
        return objectMapper.readValue(s, tClass);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /*
        returns a message from a json string
         */
    public static Message JsonToMessage(String s) throws JsonProcessingException, ClassNotFoundException, ClassCastException {
        Message message;
        JsonNode jsonNode;
        jsonNode = objectMapper.readTree(s);
        String className = jsonNode.get("name").asText();
        Class tClass = Class.forName(className);
        message = (Message)JsonToObject(s, tClass);
        return message;
    }
}
