package peer;
import message.ChatToDisplay;
import message.ShapeToDraw;
import shape.*;
import shape.Point;
import shape.Rectangle;
import shape.Shape;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

// this is the panel
public class Painter extends JFrame implements ActionListener, ComponentListener{

    // interface methods MouseListener overridden when shapes are drawn
    // interface methods MouseMotionLister overridden for freehand drawing
    private int x = -10,y= -10;  // x and y location of our mouse (this is for freehand drawing)
    private int startX = -10, startY = -10; // starting x and y location for shapes
    private int endX= -10, endY = -10; // ending x and y location for shapes
    private Color col = Color.BLACK;
    public String act = "black";
    private boolean isMouseReleased = false;
    private int startDragX;
    private int startDragY;
    private int endDragX; // this is for drawing triangle (tracking last xpos of mouse drag)
    private int endDragY; // this is from drawing triangle (tracking last ypos of mouse drag)
    private int midX;
    private int midY;
    private Container c;
    private String textCaptured;
    private int componentCount;
    private JPanel secondJPanel;
    private boolean isEnteredPressed = false;
    String resultantLabelText = "<html>";
    JLabel chatLabel;
    private TextArea constructMessage;
    private JTable peerTable;
    private Peer peer;
    private JPanel p;
    //private JTextField fileEntry;
    private boolean isMousePressed = false;
    private String userToKick = null;
    ArrayList<Shape> tempShapeList = new ArrayList<Shape>();
    ArrayList<String> chatMessages = new ArrayList<String>();
    private PointCollection pointCollection = new PointCollection();
    private String tool = "draw";

    public Painter(Peer peer) {
        this.peer = peer;
        // usual JFrame suspects
        setTitle(peer.getUsername() + ": " + peer.getClassName());
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // set up the layout
        JPanel rightRootPanel = new JPanel();
        rightRootPanel.setLayout(new BoxLayout(rightRootPanel,BoxLayout.X_AXIS));
        p = new JPanel();
        p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        p.setLayout(new FlowLayout());
        JButton text = new JButton("text");
        JButton rectangle = new JButton("rectangle");
        JButton circle = new JButton("circle");
        JButton triangle = new JButton("triangle");
        JButton line = new JButton("line");
        JButton red = new JButton("red");
        red.setBackground(Color.RED);
        JButton orange = new JButton("orange");
        orange.setBackground(Color.ORANGE);
        JButton yellow = new JButton("yellow");
        yellow.setBackground(Color.YELLOW);
        JButton blue = new JButton("blue");
        blue.setBackground(new Color(51,51,204));
        JButton black = new JButton("black");
        black.setBackground(Color.BLACK);
        JButton pink = new JButton("pink");
        pink.setBackground(Color.PINK);
        JButton green = new JButton("green");
        green.setBackground(Color.GREEN);
        JButton magenta = new JButton("magenta");
        magenta.setBackground(Color.MAGENTA);
        JButton cyan = new JButton("cyan");
        cyan.setBackground(Color.CYAN);
        JButton purple = new JButton("purple");
        purple.setBackground(new Color(191, 64, 191));
        JButton brown = new JButton("brown");
        brown.setBackground(new Color(139,69,19));
        JButton darkGrey = new JButton("darkGrey");
        darkGrey.setBackground(Color.DARK_GRAY);
        JButton lightGrey = new JButton("lightGrey");
        lightGrey.setBackground(Color.LIGHT_GRAY);
        JButton white = new JButton("white");
        JButton turquoise = new JButton("turquoise");
        turquoise.setBackground(new Color(64, 224, 208));
        white.setBackground(Color.WHITE);
        JButton bisque = new JButton("bisque");
        bisque.setBackground(new Color(255, 228, 196));
        JButton draw = new JButton("draw");
        p.add(draw);
        p.add(text);
        p.add(rectangle);
        p.add(triangle);
        p.add(circle);
        p.add(line);
        p.add(red);
        p.add(orange);
        p.add(yellow);
        p.add(blue);
        p.add(turquoise);
        p.add(black);
        p.add(pink);
        p.add(green);
        p.add(magenta);
        p.add(cyan);
        p.add(purple);
        p.add(brown);
        p.add(bisque);
        p.add(lightGrey);
        p.add(darkGrey);
        p.add(white);
        p.setVisible(true);
        rightRootPanel.add(p);
        p.setPreferredSize(new Dimension(600,1000));
        p.addComponentListener(this);
        String[] columnNames = {"Online Peer"};
        // This data should be replaced with actual peers later
        // convert arraylist to 2d array

        peerTable = new JTable();
        peerTable.setModel(new DefaultTableModel(null,columnNames));
        peerTable.setBounds(30, 40, 200, 300);
        // add data to the peerTable later
        JScrollPane rightP2 = new JScrollPane(peerTable); // this is the JPanel for the JTable
        rightP2.setPreferredSize(new Dimension(20,20));

        //rightRootPanel.add(rightP2,BorderLayout.SOUTH);
        JPanel chatPanel = new JPanel(new GridLayout(5,1));
        chatPanel.setPreferredSize(new Dimension(200,1000));
        JPanel filePanel = new JPanel(new FlowLayout());
        JButton newButton = new JButton("new");
        JButton openButton = new JButton("open");
        JButton save = new JButton("save");
        JButton saveAs = new JButton("saveAs");
        JButton close = new JButton("close");
        JButton kickUser = new JButton("kickUser");
        //fileEntry = new JTextField("Enter fileName");
        newButton.addActionListener(this);
        openButton.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        close.addActionListener(this);
        kickUser.addActionListener(this);
        filePanel.add(newButton);
        filePanel.add(openButton);
        filePanel.add(save);
        filePanel.add(saveAs);
        filePanel.add(close);
        //filePanel.add(fileEntry);
        filePanel.add(kickUser);
        chatPanel.add(filePanel);
        chatPanel.add(rightP2);
        chatLabel = new JLabel();
        chatPanel.add(chatLabel);
        JPanel sendChatPanel = new JPanel(new FlowLayout());
        constructMessage = new TextArea();
        constructMessage.setPreferredSize(new Dimension(200,50));
        JButton sendButton = new JButton("send");
        sendButton.addActionListener(this);
        sendChatPanel.add(constructMessage);
        sendChatPanel.add(sendButton);
        chatPanel.add(sendChatPanel);
        chatPanel.setPreferredSize(new Dimension(50,50));
        rightRootPanel.add(chatPanel);
        rightRootPanel.setVisible(true);

        revalidate();
        repaint();

        text.addActionListener(this);
        rectangle.addActionListener(this);
        triangle.addActionListener(this);
        line.addActionListener(this);
        circle.addActionListener(this);
        red.addActionListener(this);
        orange.addActionListener(this);
        turquoise.addActionListener(this);
        blue.addActionListener(this);
        yellow.addActionListener(this);
        black.addActionListener(this);
        white.addActionListener(this);
        darkGrey.addActionListener(this);
        lightGrey.addActionListener(this);
        pink.addActionListener(this);
        brown.addActionListener(this);
        green.addActionListener(this);
        magenta.addActionListener(this);
        bisque.addActionListener(this);
        purple.addActionListener(this);
        cyan.addActionListener(this);
        draw.addActionListener(this);
        JLabel instructions = new JLabel("Drag the mouse to draw",JLabel.RIGHT);
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(instructions,BorderLayout.SOUTH);
        //c.add(p,BorderLayout.EAST);
        c.add(rightRootPanel);
        // configure mouse
        MyMouse myMouse = new MyMouse();
        peerTable.addMouseListener(myMouse);
        p.addMouseListener(myMouse);
        p.addMouseMotionListener(myMouse);
        setVisible(true);


    }

    public JPanel getP() {
        return this.p;
    }

    public String[][] convert2dArray(ArrayList<String> peerList) {
        String[][] outputArray = null;
        if(peerList.size() != 0) {
            outputArray = new String[peerList.size()][1];
            for (int i = 0; i < peerList.size(); i++){
                outputArray[i][0] = peerList.get(i);
            }
        }

        return outputArray;
    }

    public void displayPeerList(ArrayList<String> peerList){
        String[][] peerArray = convert2dArray(peerList);
        updateTable(peerArray);
    }

    public void updateTable(String[][] data) {
        String[] columnNames = {"Online Peer"};
        TableModel myModel = peerTable.getModel();
        peerTable.setModel(new DefaultTableModel(data,columnNames));
        peerTable.repaint();
    }
    // adding chat messages to display once it has been received
    public void addChatMessage(ChatToDisplay chatToDisplay){
        String userName = chatToDisplay.getUsername();
        String newMessage = chatToDisplay.getChatMessage();
        resultantLabelText += userName + ":" + newMessage + "<br>";
        chatLabel.setText(resultantLabelText);
    }



    public void actionPerformed(ActionEvent e){
        act = e.getActionCommand();
        if (act.equals("blue")) {
            col = new Color(51,51,204);
        }
        else if(act.equals("rectangle")) {
            tool = "rectangle";
        }
        else if(act.equals("draw")) {
            tool = "draw";
        }
        else if(act.equals("triangle")) {
            tool = "triangle";
        }

        else if(act.equals("kickUser")) {
            if(userToKick != null) {
                peer.kickUser(userToKick);
                userToKick = null;
            }
        }
        else if(act.equals("open")) {
            //String fileName = fileEntry.getText();
            peer.loadWhiteboard();
        }
        else if (act.equals("new")) {
            peer.clearWhiteboard();
        }
        else if(act.equals("save")) {
            //String fileName = fileEntry.getText();
            peer.saveWhiteboard();
        }
        else if(act.equals("saveAs")) {
            //String fileName = fileEntry.getText();
            peer.saveAsWhiteboard();
        }
        else if(act.equals("close")) {
            System.out.println("Close operation started");
            getPeer().closeWhiteboard();
        }
        else  if(act.equals("text")) {
            tool = "text";
            MyTextField myTextField = new MyTextField(16);
            JLabel textInst = new JLabel("Type text in text box");
            JLabel text2 = new JLabel("Next, press enter");
            JLabel text3 = new JLabel("Lastly, click on Canvas to insert text");
            myTextField.setMaximumSize(new Dimension(16,16));
            secondJPanel = new JPanel();
            secondJPanel.setMaximumSize(new Dimension(16,16));
            secondJPanel.setLayout(new GridLayout(12,1));
            secondJPanel.add(myTextField);
            secondJPanel.add(textInst);
            secondJPanel.add(text2);
            secondJPanel.add(text3);
            secondJPanel.setBackground(Color.LIGHT_GRAY);
            c.add(secondJPanel,BorderLayout.WEST);
            setVisible(true);
        }
        else if(act.equals("line")) {
            tool = "line";
        }
        else if (act.equals("circle")) {
            tool = "circle";
        }

        else if(act.equals(("cyan"))){
            col = Color.CYAN;
        }
        else if(act.equals(("darkGrey"))){
            col = Color.DARK_GRAY;
        }
        else if(act.equals(("lightGrey"))){
            col = Color.LIGHT_GRAY;
        }
        else if(act.equals(("orange"))){
            col = Color.ORANGE;
        }
        else if(act.equals(("magenta"))){
            col = Color.MAGENTA;
        }
        else if(act.equals(("green"))){
            col = Color.GREEN;
        }
        else if(act.equals(("pink"))){
            col = Color.PINK;
        }
        else if(act.equals(("purple"))){
            col = new Color(191, 64, 191);
        }
        else if(act.equals(("brown"))){
            col = new Color(139,69,19);
        }
        else if(act.equals(("bisque"))){
            col = new Color(255, 228, 196);
        }
        else if(act.equals(("red"))){
            col = Color.RED;
        }
        else if(act.equals(("white"))){
            col = Color.WHITE;
        }
        else if(act.equals("turquoise")){
            col = new Color(64, 224, 208);
        }
        else if (act.equals(("yellow"))){
            col = Color.YELLOW;
        }
        else if (act.equals("black")) {
            col = Color.BLACK;
        }
        else if(act.equals(("send"))) {
            String mess = constructMessage.getText();
            String userName=  peer.getUsername();
            ChatToDisplay chat = new ChatToDisplay(userName,mess);
            peer.getPeerNotifier().getMessagesToSend().add(chat);
        }

    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        Dimension dimension = componentEvent.getComponent().getSize();
        int newWidth = dimension.width*3/4;
        int newHeight = dimension.height*3/4;
        p.setPreferredSize(new Dimension(newWidth,newHeight));

    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    class MyTextField extends JTextField implements ActionListener{
        public MyTextField(int l){
            super(l);
            addActionListener(this);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // this is to capture text for textbox function in GUI
            textCaptured = getText();
            isEnteredPressed = true;
            isMousePressed = false;
            if(act.equals("text")) {
                shapify();
            }



        }
    }

    class MyMouse extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 1) { // to detect single click events
                if(e.getSource() instanceof JTable) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    userToKick = (String)peerTable.getValueAt(row,column);
                    System.out.println("User to kick:"+userToKick);
                }

            }
        }

        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            startDragX = e.getX();;
            startDragY = e.getY();
            endDragX = startDragX;
            endDragY = startDragY;
            isMousePressed = true;

        }
        public void mouseDragged(MouseEvent e){
            if(tool.equals("rectangle") || tool.equals("circle") || tool.equals("triangle") || tool.equals("line")){
//                    endX = e.getX();
//                    endY = e.getY();
                    endDragX = e.getX();
                    endDragY = e.getY();
            }
            else if(tool.equals("draw")){
                if (!getPeer().getDrawTimer().isStarted()){
                    getPeer().getDrawTimer().startTimer();
                    x = e.getX(); y = e.getY();
                    pointify();
                }
            }

            // repaint(); // will call on the paint method with a special parameter

        }
        public void pointify() {
            //System.out.println("Pointify is called");
            //System.out.println(col);
            pointCollection.addPoint(new Point(col,x,y));

        }
        public void mouseReleased(MouseEvent e){
            isMouseReleased = true;
            endX = e.getX();
            endY = e.getY();
            //int midX;
            if(endDragX > startX){
                midX = Math.abs(endDragX - startX)/2 + startX;
            }
            else{
                midX = Math.abs(startX - endDragX) / 2 + endDragX;
            }
            midY = e.getY();
            shapify();

        }
    }
    // parses the x and y coordinates to shapes that can be sent
    public void shapify() {

        Shape shape = null;

        if(tool.equals("rectangle") && isMouseReleased == true ) {
            shape = new Rectangle(col,startX,startY,endX,endY);
            isMouseReleased = false;
        }
        else if (tool.equals("triangle") && isMouseReleased == true){
            shape = new Triangle(col,startX,startY,midX,midY,endDragX,endDragY);
            isMouseReleased = false;
        }
        else if (tool.equals("line")&& isMouseReleased == true){
            shape = new Line(col,startX,startY,endX,endY);
            isMouseReleased = false;
        }
        else if(tool.equals("circle") && isMouseReleased == true) {
            shape = new Circle(col,startX,startY,endX,endY);
            isMouseReleased = false;
        }
        else if(tool.equals("draw") && isMouseReleased == true){
            //System.out.println("Point collection is here");
            shape = pointCollection;
            isMouseReleased = false;
        }
        else if(tool.equals("text") && isEnteredPressed == true && isMousePressed == true){
            if (textCaptured != ""){
                shape = new Text(col,textCaptured, startX, startY);
            }
            isEnteredPressed = false;
            isMousePressed = false;
        }
        if(shape != null) {
            //System.out.println("Line is packaged and sent");
            ShapeToDraw sh = new ShapeToDraw(shape);
            //System.out.println(sh.getShape());
            peer.getPeerNotifier().getMessagesToSend().add(sh);
            pointCollection = new PointCollection();
        }
    }

    public void paint(Graphics graphics){
        super.paint(graphics);

        synchronized (getPeer().getWhiteboardState()){
            Iterator i = getPeer().getWhiteboardState().iterator();
            while (i.hasNext()){
                Shape shape = (Shape)i.next();
                shape.draw(this);
            }
        }
    }
    public Peer getPeer() {
        return peer;
    }
}