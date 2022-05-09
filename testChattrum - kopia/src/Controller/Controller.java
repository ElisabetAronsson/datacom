package Controller;
import Model.*;
import View.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private MainFrame mainFrame;
    private Message message;
    private Client client;
    private LoginPane loginPane;
    private User user;

    public Controller(Client client, User user) throws IOException {
        this.client = client;
        client.setController(this);
        mainFrame = new MainFrame(740, 550, this, user.getUserName(), user.getIconImage());
        loginPane = new LoginPane();
        this.user = user;
        message = new Message();
        message.setController(this);
    }

    public void openLogGUI(){

    }

    public void updateBoolean(Boolean bool) {
        mainFrame.updateBoolean(bool);
    }

    public void sendMessage(Message message){
        client.putMessageInBuffer(message);
    }

    public void updateList(ArrayList<User> allUsers){
       // mainFrame.getMainPanel().getFriendsPanel().clearPanel();
        //mainFrame.getMainPanel().getAllOnlinePanel().clearPanel();

        int amountOfFriends = 0;
        int amountOfNonFriends = 0;
        int friendsOnline = 0;
        int allOnline = 0;
        for(int i = 0; i < allUsers.size(); i++){
            if(allUsers.get(i).isFriend()){
                amountOfFriends++;
                if(allUsers.get(i).isOnline()){
                    friendsOnline++;
                }
            } else{
                amountOfNonFriends++;
                if(allUsers.get(i).isOnline()){
                    allOnline++;
                }
            }
        }
        String[] usernameFriends = new String[amountOfFriends];
        ImageIcon[] imageIconFriends = new ImageIcon[amountOfFriends];
        int[] positionOnlineFriend = new int[friendsOnline];

        String[] usernameAll = new String[amountOfNonFriends];
        ImageIcon[] imageIconsAll = new ImageIcon[amountOfNonFriends];
        int[] positionOnline = new int[allOnline];

        int friendIndex = 0;
        int allIndex = 0;
        for(int i = 0; i < allUsers.size(); i++){
            User user = allUsers.get(i);
            if (allUsers.get(i).isFriend()) {
                usernameFriends[friendIndex] = user.getUserName();
                imageIconFriends[friendIndex] = user.getIconImage();
                friendIndex++;
                if(allUsers.get(i).isOnline()){
                    positionOnlineFriend[i] = i;
                }
            } else {
                usernameAll[allIndex] = user.getUserName();
                imageIconsAll[allIndex] = user.getIconImage();
                allIndex++;
                if(allUsers.get(i).isOnline()){
                    positionOnline[i] = i;
                }
            }
        }
        mainFrame.getMainPanel().getAllOnlinePanel().addUsers(usernameAll, imageIconsAll, positionOnline);
        mainFrame.getMainPanel().getFriendsPanel().addUsers( usernameFriends ,imageIconFriends, positionOnlineFriend);
    }

    public void setSelectedImage(ImageIcon imageIcon){
        message.setImageIcon(imageIcon);
    }

    public void setMessageText (String messageText){
        message.setMessage(messageText);
    }

    public void askIfFriend(String userName){
        loginPane.giveOptions(userName, this);
    }

    public void addFriend(String username){
        user.getSpecificUser(username).setFriend(true);
        User user1 = user.getSpecificUser(username);
        client.updateOfUser(user1);
    }

    public void connectWithMessage(){
        message.sendThisMessage();
    }
 }








