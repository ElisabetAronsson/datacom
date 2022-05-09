package View;

import Controller.Server;
import Model.Buffer;
import Model.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class ServerGUI extends JFrame {
    private JList<Object> list;
    private JList printList;
    private ArrayList<Object> objectList = new ArrayList<Object>();
    private Buffer<String> messageDateSenderTextRecievers = new Buffer<>();
    private Buffer<ImageIcon> imageIcons;
    private panel panel;
    private Server server;

    public ServerGUI(Server server){
        JFrame frame = new JFrame("Server");
        panel = new panel();
        this.server = server;
        frame.setPreferredSize(new Dimension(500,700));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.add(this.panel);
    }

    public void putText(String messageText){
        messageDateSenderTextRecievers.put(messageText);
    }

    public void putImage(ImageIcon imageIcon){
        imageIcons.put(imageIcon);
    }

    private class panel extends JPanel{
        public panel(){
            this.setLayout(null);
            list = new JList<>();
            JScrollPane scroll = new JScrollPane(); // la till scroll bar
            scroll.setViewportView(list);
            scroll.setLocation(0, 23);
            scroll.setSize(900,900);
            this.add(scroll);
            try {
                objectList.add(messageDateSenderTextRecievers.get());
                objectList.add(imageIcons.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
