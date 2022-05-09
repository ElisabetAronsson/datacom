package View;

import Controller.Controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FriendsPanel extends JPanel implements ListSelectionListener {
    private int width;
    private int height;
    private JButton friends;
    private JButton onlinePeople;
    private Controller controller;
    private Map<String,ImageIcon> users = new HashMap<String, ImageIcon>();
    private JPanel panel = new JPanel();
    String[] nameList = new String[0];
    ImageIcon[] imageIcons = new ImageIcon[0];
    int[] positionsOnline = new int[0];
    private JList list;


    public FriendsPanel(int width, int height, Controller controller){
        this.setLayout(null);
        this.controller = controller;
        this.width = width;
        this.height = height;
        this.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        this.setSize(width, height);
        setLocation(0, 75);
        setUp();
        this.add(panel,BorderLayout.CENTER);
        panel.setSize(width - 20, height - 90);
        panel.setLocation(5,20);


    }

    private void setUp() {
        friends = new JButton("Vänner");
        friends.setEnabled(true);
        friends.setSize(width / 2, 25);
        friends.setLocation(0, 2);
        friends.addActionListener(l -> controller.updateBoolean(true));
        this.add(friends);

        onlinePeople = new JButton("Alla");
        onlinePeople.setEnabled(true);
        onlinePeople.setSize(width / 2, 25);
        onlinePeople.setLocation(width/2, 2);
        onlinePeople.addActionListener(l -> controller.updateBoolean(false));
        this.add(onlinePeople);


        users = createImageMap(nameList, imageIcons);
        list = new JList(nameList);
        list.setCellRenderer(new IconListRenderer());
        list.addListSelectionListener(l -> controller.askIfFriend(nameList[list.getSelectedIndex()]));
        panel.add(list);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(200, 800));
        panel.add(scroll);
        panel.setVisible(true);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        list.getSelectedValue();
        System.out.println(list.getSelectedIndex());
        System.out.println(list.getSelectedValue());
        //controller.setFriendName(String.valueOf(list.getSelectedValue()));
        //controller.clearChat();
    }

    public void addUsers(String[] usernameFriends, ImageIcon[] imageIconsFriends, int[] positionOnlineFriend) {
        ///TODO: Lopa igenom positionsOnline, sätt indexen i arrayen till färgen grön
        this.nameList = usernameFriends;
        this.imageIcons = imageIconsFriends;
        this.positionsOnline = positionOnlineFriend;
        list = new JList(nameList);
      //  list.setCellRenderer(new IconListRenderer());


       /* imageMap = createImageMap(usernameFriends, imageIconsFriends);
        list = new JList(usernameFriends);
        list.setCellRenderer(new IconListRenderer());

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(200, 800));
        panel.add(scroll);
        panel.setVisible(true);
        list.setVisible(true); */
    }

    public class IconListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvetica", Font.BOLD, 20);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(createImageMap(nameList, imageIcons).get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            label.setVisible(true);
            return label;
        }
    }

    private Map<String, ImageIcon> createImageMap(String[] username, ImageIcon[] imageIcons) {
        Map<String, ImageIcon> updatedUsers = new HashMap<>();
        for(int i = 0; i < username.length; i++) {
            try {
                updatedUsers.put(username[i], imageIcons[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return updatedUsers;
    }

   /* public String [] pizzaString() { // en array som senare används för att visa menyn i guit
        String [] menuUpdate = new String[products.size()];

        for (int i = 0; i < products.size(); i++) {
            menuUpdate[i] = products.get(i).toString();  // "kopierar" över allt från arrayen products till menuupte, som senare används i guit
        }
        return menuUpdate;
    }
    public Product getProductAt(int index) {
        if (index < products.size() && index >= 0) {
            return products.get(index);
        } else {
            return null;
        }
    }


    public int getListIndexL() {
        return menu.getSelectedIndex();
    } */
}
