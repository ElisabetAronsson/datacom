package View;

import Controller.Controller;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileSystemView;

public class ChatPanel extends JPanel {
    private int width;
    private int height;
    private JTextField messageTextField;
    private JList<Object> list;
    private ArrayList<ImageIcon> imageArray = new ArrayList<ImageIcon>();
    private JTextArea chatArea;
    private JScrollPane scroll;
    private Border border;
    private Map<String, ImageIcon> imageMap;
    private JList printList;
    private Controller controller;
    private JPanel panel = new JPanel();
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<Object> objectList = new ArrayList<Object>();
    private String userName;

    public ChatPanel(int width, int height, String userName, Controller controller) {
        this.setLayout(null);
        this.controller = controller;
        this.userName = userName;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setLocation(200, 75);
        setUp(userName);
    }


    private void setUp(String userName) {
        list = new JList<>();
        JScrollPane scroll = new JScrollPane(); // la till scroll bar
        scroll.setViewportView(list);
        scroll.setLocation(0, 23);
        scroll.setSize(width - 40, height - 100);
        this.add(scroll);

        ImageIcon imageIcon = new ImageIcon("files/fotoicon.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);
        JButton selectPictureButton = new JButton();
        selectPictureButton.setContentAreaFilled(false);
        selectPictureButton.setBorderPainted(false);
        selectPictureButton.setIcon(imageIcon);
        selectPictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imagepath = getPicture();
                ImageIcon imageIcon = new ImageIcon(imagepath);
                Image image = imageIcon.getImage(); // transform it
                Image newimg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                imageIcon = new ImageIcon(newimg);
                selectPictureButton.setIcon(imageIcon);
                controller.setSelectedImage(imageIcon);

               /* String UserName = userName;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime now = LocalDateTime.now();
                String  messageToSend = ("<" + UserName + ":" + dtf.format(now) +">: ");
                objectList.add(messageToSend);
                objectList.add(imageIcon);
                imageArray.add(imageIcon);
                addImages(objectList.toArray()); */
            }

        });
        this.add(selectPictureButton);
        selectPictureButton.setLocation(5,height-60);
        selectPictureButton.setSize(25, 25);
        selectPictureButton.setAlignmentX(LEFT_ALIGNMENT);

        messageTextField = new JTextField("Aa");
        messageTextField.setSize(width-100, 35);
        messageTextField.setLocation(35, height-65);
        messageTextField.setFont(messageTextField.getFont().deriveFont(15.0F));
        messageTextField.setHorizontalAlignment(JLabel.LEFT);
        messageTextField.setBackground(new Color(236, 236, 236));
        messageTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        messageTextField.requestFocusInWindow();
        this.add(messageTextField);
        messageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               /* ImageIcon finalImage = new ImageIcon("files/fotoicon.png");
                ArrayList<User> temporaryTestArraylist = new ArrayList<>();
                temporaryTestArraylist.add(new User("Kris", finalImage));
                temporaryTestArraylist.add(new User("Amidala", finalImage));
                temporaryTestArraylist.add(new User("Sara", finalImage));
                */
                controller.setMessageText(messageTextField.getText());
                /*String UserName = userName;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime now = LocalDateTime.now();
                String message = messageTextField.getText();
                String  messageToSend = ("<" + UserName + ":" + dtf.format(now) +">: " +message);
                objectList.add(messageToSend);
                addImages(objectList.toArray()); */
            }
        });

        String[] nameList = {"Mario", "Luigi", "Bowser", "Koopa", "Princess"};
        imageMap = createImageMap(nameList);
        printList = new JList(nameList);
        printList.setCellRenderer(new IconListRenderer());

        scroll = new JScrollPane(printList);
        scroll.setPreferredSize(new Dimension(200, 800));
        panel.add(scroll);
        panel.setVisible(true);

        JButton sendButton = new JButton("s");
        sendButton.setEnabled(true);
        sendButton.setVisible(true);
        sendButton.setBackground(Color.red);
        sendButton.setSize(45, 30);
        sendButton.setLocation(width - 60, height-65);
        sendButton.addActionListener(l -> controller.connectWithMessage());
        this.add(sendButton);
    }

    public void clearChat() {
        chatArea.setText("");
    }
/*
    public String messageToSend(){
        String msg =
        return msg;
    }*/

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

    public Object[] textToView(){
        Object [] order = new Object[objectList.size()];
        for (int i = 0; i < order.length; i++) {
            order[i] = (objectList.get(i).toString());
        }
        return order;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void addText(Object[] text) {
        list.setListData(text);
    }

    public void addImages(Object[] icons) {
        list.setListData(icons);
    }

    public ImageIcon[] arrayListtoAarray() {
        ImageIcon [] icons = new ImageIcon[imageArray.size()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = (imageArray.get(i));
            //objectList.add(imageArray.get(i));
        }
        return icons;
    }

    public class IconListRenderer extends DefaultListCellRenderer {
        Font font = new Font("helvetica", Font.BOLD, 20);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }

    }

    public void valueChanged(ListSelectionEvent e) {
        printList.getSelectedValue();
        System.out.println(printList.getSelectedIndex());
        System.out.println(printList.getSelectedValue());
        //controller.setFriendName(String.valueOf(printList.getSelectedValue())); KOLLA PÅ DETTA!
        //controller.clearChat();

    }

    private Map<String, ImageIcon> createImageMap(String[] list) {
        Map<String, ImageIcon> map = new HashMap<>();
        //loop igenom hashmap
        try {
            map.put("client.getname", new ImageIcon(new ImageIcon("client.getUrl").getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;

    }

}

