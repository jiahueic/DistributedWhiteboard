package files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.Json;
import message.Message;
import message.ShapeList;
import shape.Shape;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private String filename;

    public FileHandler(String filename) {
        this.filename = filename;
    }

    public void writeWhiteBoardStateToFile(List<Shape> shapes) throws IOException {
        ShapeList shapeList = new ShapeList(shapes);
        String s = Json.objectToJson(shapeList);
        writeToFile(s);
    }

    public void writeToFile(String s) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, false));
        bufferedWriter.write(s);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public String fileToString() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String s = bufferedReader.readLine();
        bufferedReader.close();
        return s;
    }

    public List<Shape> fileToWhiteBoardState() throws IOException, ClassNotFoundException {
        String s = fileToString();
        Message message = Json.JsonToMessage(s);
        ShapeList shapeList = (ShapeList) message;
        return shapeList.getShapes();
    }
}
