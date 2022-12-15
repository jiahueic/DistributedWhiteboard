package peer.commandhandler;

import exceptions.InvalidRequestException;
import message.Message;
import peer.PeerListener;


import java.util.ArrayList;

public class CommandHandlerFactory {
    private static CommandHandlerFactory commandHandlerFactory;

    public static CommandHandlerFactory getInstance(){
        if (commandHandlerFactory == null){
            commandHandlerFactory = new CommandHandlerFactory();
        }
        return commandHandlerFactory;
    }

    public CommandHandler createCommandHandler(PeerListener peerListener, Message message) throws InvalidRequestException {
        switch (message.getName()) {
            case "message.ShapeToDraw":
                return new ShapeToDrawHandler(peerListener, message);
            case "message.ChatToDisplay":
                return new ChatToDisplayHandler(peerListener, message);
            case "message.KickUserRequest":
                return new KickUserRequestHandler(peerListener, message);
            case "message.UpdatePeerList":
                return new UpdatePeerListHandler(peerListener, message);
            case "message.ClearWhiteboardRequest":
                return new ClearWhiteboardRequestHandler(peerListener, message);
            case "message.ShapeList":
                return new ShapeListHandler(peerListener, message);
            case "message.AcceptUserRequest":
                return new AcceptUserRequestHandler(peerListener, message);
            default:
                throw new InvalidRequestException();
        }
    }
}
