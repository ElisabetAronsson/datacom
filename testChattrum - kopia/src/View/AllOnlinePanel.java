package View;

import Controller.Controller;
import Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AllOnlinePanel extends JPanel implements ListSelectionListener {
    private int width;
    private int height;
    private JButton friends;
    private JButton onlinePeople;
    private Controller controller;
    private JPanel panel = new JPanel();
    private Map<String,ImageIcon> users;
    private String[] nameList = new String[0];
    private ImageIcon[] imageIcons = new ImageIcon[0];
    private int[] positionsOnline = new int[0];
    private JList list;

    public AllOnlinePanel(int width, int height, Controller controller){
        this.setLayout(null);
        this.width = width;
        this.controller = controller;
        this.height = height;
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
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
        onlinePeople.setLocation(width / 2, 2);
        onlinePeople.addActionListener(l -> controller.updateBoolean(false));
        this.add(onlinePeople);

        users = createImageMap(nameList, imageIcons);
        list = new JList(nameList);
        list.setCellRenderer(new IconListRenderer());
        list.addListSelectionListener(l -> controller.askIfFriend(nameList[list.getSelectedIndex()]));
        this.add(list);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(200, 800));
        this.add(scroll);
        this.setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        list.getSelectedValue();
        System.out.println(list.getSelectedIndex());
        System.out.println(list.getSelectedValue());
    }

    public void addUsers(String[] userNames, ImageIcon[] iconImages, int[] positionOnline) {
        ///TODO: Lopa igenom positionsOnline, sätt indexen i arrayen till färgen grön
        panel.remove(list);
        this.nameList = userNames;
        this.imageIcons = iconImages;
        this.positionsOnline = positionOnline;
        list = new JList(nameList);
        panel.add(list);
        repaint();
        revalidate();
        }

public class IconListRenderer extends DefaultListCellRenderer {

    Font font = new Font("helvetica", Font.BOLD, 20);

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        label.setIcon(users.get((String) value));
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setFont(font);
        label.setVisible(true);
        return label;
    }
}

    private Map<String, ImageIcon> createImageMap(String[] username, ImageIcon[] imageIcons) {
        Map<String, ImageIcon> map = new HashMap<>();
        for(int i = 0; i < username.length; i++) {
            try {
                map.put(username[i],imageIcons[i]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}

