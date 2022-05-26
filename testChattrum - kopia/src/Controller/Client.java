package Controller;

import Model.Buffer;
import Model.Message;
import Model.User;
import View.LoginPane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread{
    private User user;
    private Socket socket;
    private Controller controller;
    private ArrayList<User> allPeople;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Buffer<Object> unhandledObjects = new Buffer<>();


    public Client(String ipAdress, int port, User user) {
        try {
            socket = new Socket(ipAdress, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            this.user = user;
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            oos.writeObject(user);
            oos.flush();
            new InputCommunicator().start();
            new OutputCommunicator().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putMessageInBuffer(Message message){
        unhandledObjects.put(message);
    }

    public void updateOfUser(User user){
        unhandledObjects.put(user);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void sendChoosenFriend(String username){
     unhandledObjects.put(username);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public static String getPicture() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        File selectedFile = null;
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }
        return selectedFile.getAbsolutePath();
    }

    public class InputCommunicator extends Thread{
        public void run(){
            try {
                while (!Thread.interrupted()) {
                    Object temp = ois.readObject();
                    if (temp instanceof User[]) {
                         allPeople = new ArrayList<User>(List.of((User[]) temp));
                         user.setUsers(allPeople);
                        controller.updateList(allPeople);
                    } else if (temp instanceof Message){

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
            }
        }
    }

    public class OutputCommunicator extends Thread {
        public void run() {
            try {
                while (!Thread.interrupted()) {
                        Object unhandledObject = unhandledObjects.get();
                        oos.writeObject(unhandledObject);
                        oos.flush();
                }
            } catch (InterruptedException | IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        LoginPane loginPane = new LoginPane();
        String username = loginPane.usernamePopUp();
        System.out.println(username);
        ImageIcon imageIcon = new ImageIcon(getPicture());
        User user = new User(username, imageIcon, false, true);
        Client client = new Client("127.0.0.1", 1234, user);
        Controller controller = new Controller(client, user);
    }
}
