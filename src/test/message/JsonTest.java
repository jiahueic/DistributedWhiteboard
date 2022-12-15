package message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void objectToJson() throws JsonProcessingException {
        TestClass testClass;
        testClass = new TestClass("bob",123);
        String s = Json.objectToJson(testClass);
        assertEquals(s,"{\"name\":\"bob\",\"id\":123}");
    }

    @Test
    void JsonToObject() throws JsonProcessingException {
        String s = "{\"name\":\"bob\",\"id\":123}";
        TestClass testClass;
        testClass = Json.JsonToObject(s, TestClass.class);
        assertEquals(testClass.getName(),"bob");
        assertEquals(testClass.getId(),123);
    }

    @Test
    void JsonToObject1() throws JsonProcessingException, ClassNotFoundException {
        String s = "{\"name\":\"TestClass\",\"id\":123}";
        JsonNode jsonNode;
        TestClass testClass;
        jsonNode = objectMapper.readTree(s);
        String className = jsonNode.get("name").asText();
        assertEquals(className, "TestClass");
        Class tClass = Class.forName("message."+className);
        assertEquals(tClass, TestClass.class);
    }

    // testing deserialising welcome message
    @Test
    void JsonToObject2() throws JsonProcessingException, ClassNotFoundException {
        String s = "{\"name\":\"Welcome\"}";
        JsonNode jsonNode;
        jsonNode = objectMapper.readTree(s);
        String className = jsonNode.get("name").asText();
        assertEquals(className, "Welcome");
        Class tClass = Class.forName("message."+className);
        assertEquals(tClass, Welcome.class);
    }

    // testing object creation
    @Test
    void JsonToMessage() throws JsonProcessingException, ClassNotFoundException {
        String s = "{\"name\":\"message.Welcome\"}";
        Message message;
        message = Json.JsonToMessage(s);
        assertEquals(message.getName(),"message.Welcome");
    }

    // testing object creation for object that doesn't exist
    @Test
    void JsonToMessage1() throws JsonProcessingException, ClassNotFoundException {
        assertThrows(ClassNotFoundException.class, () -> {
                    String s = "{\"name\":\"Classthatdoesn'texist\"}";
                    Message message;
                    message = Json.JsonToMessage(s);
                });
    }

    // testing object creation
    @Test
    void JsonToMessage3() throws JsonProcessingException, ClassNotFoundException {
        String s = "{\"name\":\"message.CreateWhiteBoardReply\"}";
        Message message;
        message = Json.JsonToMessage(s);
        assertEquals(message.getName(),"message.CreateWhiteBoardReply");
    }

    @Test
    void canonicalName(){
        System.out.println(this.getClass().getCanonicalName());
    }

    // testing serialising and deserialising a more complex class
    @Test
    void jsonComplex() throws JsonProcessingException, ClassNotFoundException {
        ArrayList<TestClass> testClasses = new ArrayList<>();
        testClasses.add(new TestClass("bob", 123));
        testClasses.add(new TestClass("jane",321));
        String s = Json.objectToJson(new ComplexTestClass(testClasses));
        ComplexTestClass complexTestClass;
        complexTestClass = (ComplexTestClass) Json.JsonToMessage(s);
        assertEquals(complexTestClass.getTestClasses().toString(), testClasses.toString());
    }
}