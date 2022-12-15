package server.reqhandle;

import exceptions.InvalidRequestException;
import message.Message;
import server.Server;

import java.io.BufferedWriter;
import java.net.Socket;

public class RequestHandlerFactory {


    public RequestHandler getRequestHandler(Server server, Socket socket, Message message, BufferedWriter bufferedWriter) throws InvalidRequestException {
        switch (message.getName()) {
            case "message.CreateWhiteboardRequest":
                return new CreateWhiteboardRequestHandler(server, socket, message, bufferedWriter);
            case "message.JoinWhiteboardRequest":
                return new JoinWhiteboardRequestHandler(server, socket, message, bufferedWriter);
            case "message.ListenWhiteboardRequest":
                return new ListenWhiteboardRequestHandler(server, socket, message, bufferedWriter);
            // chat and shapes are sent in the same stream
            case "message.ShapeToDraw":
            case "message.ChatToDisplay":
                return new ShapeToDrawRequestHandler(server, socket, message, bufferedWriter);
            case "message.KickUserRequest":
                return new KickUserRequestHandler(server, socket, message, bufferedWriter);
            case "message.ClearWhiteboardRequest":
                return new ClearWhiteboardRequestHandler(server, socket, message, bufferedWriter);
            case "message.ShapeList":
                return new ShapeListHandler(server, socket, message, bufferedWriter);
            case "message.AcceptUserResponse":
                return new AcceptUserResponseHandler(server, socket, message, bufferedWriter);
            default:
                throw new InvalidRequestException();
        }
    }
}
