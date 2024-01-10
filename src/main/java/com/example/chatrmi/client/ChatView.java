package com.example.chatrmi.client;

import com.example.chatrmi.server.InterfaceServer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ChatView extends JFrame implements Runnable{
    private ChatClient client;
    private InterfaceServer server;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private Vector<String> listClients;
    private String name;
    private GroupLayout groupLayout;


    public ChatView(String name, InterfaceServer server) {
        initComponents();

        this.server = server;
        this.name = name;


        listConnect.setComponentPopupMenu(jPopupMenu1);


        this.setLocationRelativeTo(null);
        this.setTitle("Chat (" + name + ")");
        ImageIcon iconChat = new ImageIcon("img/chat.jpg");
        setIconImage(iconChat.getImage());

        groupLayout = new GroupLayout(jPanel1);
        jPanel1.setLayout(new GridLayout(100,1));
        jPanel1.setBorder(new EmptyBorder(5, 10, 10, 10));


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(new JFrame(),
                    "Are you sure you want to close this chat ?", "Close chat?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        server.removeClient(name);
                    } catch (RemoteException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    System.exit(0);
                }else{

                }
            }
        });

        //input msg
        inputMsg.setForeground(Color.GRAY);
        inputMsg.setText("Enter your Message ...");
        inputMsg.addFocusListener(new FocusListener() {
        @Override
         public void focusGained(FocusEvent e) {
            if (inputMsg.getText().equals("Enter your Message ...")) {
                inputMsg.setText("");
                inputMsg.setForeground(Color.BLACK);
            }
        }
        @Override
         public void focusLost(FocusEvent e) {
            if (inputMsg.getText().isEmpty()) {
                inputMsg.setForeground(Color.GRAY);
                inputMsg.setText("Enter your Message ...");
            }
        }
        });


        listClients = new Vector<>();
        listConnect.setListData(listClients);

        try{
            client = new ChatClient(name,server,inputMsg,listMessage,jPanel1);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }


        Timer minuteur = new Timer();
        TimerTask tache = new TimerTask() {
            @Override
            public void run() {
                try {
                    int[] indices = listConnect.getSelectedIndices();
                    model.clear();
                    listClients = server.getListClientByName(name);
                    int i=0;
                    while(i<listClients.size()){
                        model.addElement(listClients.get(i));
                        i++;
                    }
                    listConnect.setModel(model);
                    listConnect.setSelectedIndices(indices);
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        };
        minuteur.schedule(tache,0,20000);

        this.setVisible(true);
    }



    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        connectedListScroll = new javax.swing.JScrollPane();
        listConnect = new javax.swing.JList<>();
        inputMsg = new javax.swing.JTextPane();
        btnSend = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listMessage = new javax.swing.JTextPane();
        connectedLabel = new JLabel();
        refreshBtn = new javax.swing.JButton();
        uploadBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new JLabel();
        messageScroll = new JScrollPane();
        popupIcon = new JPopupMenu();
        iconPanel = new JPanel();
        btnIcon = new JButton();




        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        listConnect.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        listConnect.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listConnect.setToolTipText("");
        connectedListScroll.setViewportView(listConnect);

        inputMsg.setToolTipText("Enter your Message ...");
        inputMsg.setMargin(new java.awt.Insets(6, 0, 0, 16));
        inputMsg.getAccessibleContext().setAccessibleName("Enter your Message ...");
        StyledDocument doc = inputMsg.getStyledDocument();
        messageScroll.setViewportView(inputMsg);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Ẩn thanh cuộn dọc

//      listMessage.setRows(5);
//      listMessage.setColumns(20);


        listMessage.setEditable(false);
        listMessage.setFont(new java.awt.Font("Dialog", 1, 12));
        listMessage.setRequestFocusEnabled(false);
        StyledDocument docMess = listMessage.getStyledDocument();
        jScrollPane3.setViewportView(listMessage);

        connectedLabel.setFont(new java.awt.Font("Dialog", 0, 16));
        connectedLabel.setText("Connected Client List");

        refreshBtn.setText("Refresh");
        refreshBtn.setActionCommand("");
        refreshBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });


        btnSend.setIcon(new ImageIcon("img/send.png"));
        btnSend.setToolTipText("Send");
        btnSend.setBorderPainted(false);
        btnSend.setContentAreaFilled(false);
        btnSend.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        btnSend.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSend.setDefaultCapable(false);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });



        uploadBtn.setIcon(new ImageIcon("img/file-upload.png"));
        uploadBtn.setToolTipText("upload File");
        uploadBtn.setBorderPainted(false);
        uploadBtn.setContentAreaFilled(false);
        uploadBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        uploadBtn.setDefaultCapable(false);
        uploadBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadBtn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        uploadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadBtnActionPerformed(evt);
            }
        });


       btnIcon.setIcon(new ImageIcon("img/emoji.png"));
       btnIcon.setBorderPainted(false);
       btnIcon.setContentAreaFilled(false);
       btnIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
       btnIcon.setDefaultCapable(false);
       btnIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
       btnIcon.setMargin(new java.awt.Insets(0, 0, 0, 0));
       btnIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iconBtnActionPerformed(evt);
            }
        });

        this.iconPanel.setBackground(new Color(153, 153, 153));
        this.iconPanel.setMaximumSize(new Dimension(502, 349));
        this.iconPanel.setMinimumSize(new Dimension(502, 349));



        this.popupIcon.setBackground(new Color(255, 255, 255));
        this.popupIcon.setMaximumSize(new Dimension(504, 355));
        this.popupIcon.setMinimumSize(new Dimension(504, 355));
        this.popupIcon.setPreferredSize(new Dimension(504, 355));
        popupIcon.add(iconPanel);



        for (int i = 1; i <= 28; i++) {
            String emojiName = "emoji_green (" + i + ").png";

            // Load the emoji image
            ImageIcon emojiIcon = new ImageIcon("img/" + emojiName);

            // Create a JLabel with the emoji image
            JLabel emojiLabel = new JLabel(emojiIcon);

            emojiLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel clickedLabel = (JLabel) e.getSource();
                    Icon emojiIcon = clickedLabel.getIcon();
                    sendEmoji(emojiIcon);
                }
            });

            iconPanel.add(emojiLabel);
        }
        iconPanel.revalidate();
        iconPanel.repaint();



        jPanel1.setFont(new java.awt.Font("Dialog", 0, 14));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanel1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel1.setText("Shared Files");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jScrollPane4)
                                                                .addComponent(jScrollPane3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
                                                        .addGap(27, 27, 27))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(messageScroll, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(uploadBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnIcon, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                ))
                                        .addComponent(jLabel1))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(connectedLabel))
                                        .addComponent(connectedListScroll)
                                        .addComponent(refreshBtn, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                        .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(42, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(connectedLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(connectedListScroll, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshBtn))
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(uploadBtn, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnIcon,GroupLayout.PREFERRED_SIZE,43,GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(messageScroll, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void sendEmoji(Icon emojiIcon) {
        try {
            client.sendEmojiMessage(emojiIcon, listConnect.getSelectedValuesList());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    private void iconBtnActionPerformed(ActionEvent evt) {
        popupIcon.show(this.inputMsg, 5, -365);
    }


    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        if(!inputMsg.getText().equals("")){
            if(!inputMsg.getText().equals("Enter you Message ...")){
                client.sendMessage(listConnect.getSelectedValuesList());
                inputMsg.setText("");
            }else{
            JOptionPane.showMessageDialog(this,"Please insert something to set your message","Alert",JOptionPane.WARNING_MESSAGE);
        }
        }else{
            JOptionPane.showMessageDialog(this,"Please insert something to send your message","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }


    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {
        Thread thread = new Thread(this);
        thread.start();
    }




    private void uploadBtnActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            String[] extension = file.getName().split("\\.");

            if(extension[extension.length - 1].equals("txt")||
                extension[extension.length - 1].equals("java")||
                extension[extension.length - 1].equals("php")||
                extension[extension.length - 1].equals("c")||
                extension[extension.length - 1].equals("cpp")||
                extension[extension.length - 1].equals("xml")||
                extension[extension.length - 1].equals("exe")||
                extension[extension.length - 1].equals("png")||
                extension[extension.length - 1].equals("jpg")||
                extension[extension.length - 1].equals("jpeg")||
                extension[extension.length - 1].equals("pdf")||
                extension[extension.length - 1].equals("jar")||
                extension[extension.length - 1].equals("rar")||
                extension[extension.length - 1].equals("zip")||
                    extension[extension.length - 1].equals("docx")

            ){
                try {
                    ArrayList<Integer> inc;
                    try (FileInputStream in = new FileInputStream(file)) {
                        inc = new ArrayList<>();
                        int c=0;
                        while((c=in.read()) != -1) {
                            inc.add(c);
                        }
                        in.close();
                    }
                    System.out.println(file.getName());
                    server.broadcastMessage(inc, listClients,file);
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                JLabel jfile = new JLabel(file.getName() + " Uploaded ...");
                jPanel1.add(jfile);
                jPanel1.repaint();
                jPanel1.revalidate();
            }else{
                JOptionPane.showMessageDialog(this,"You can only upload file have an extension like: xml,exe,jpg,png,jpeg,pdf,c,cpp,jar,java,txt,php ","Alert",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private javax.swing.JButton btnSend;
    private javax.swing.JTextPane inputMsg;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton uploadBtn;
    private JLabel jLabel1;
    private JLabel connectedLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane connectedListScroll;
    private javax.swing.JScrollPane messageScroll;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listConnect;
    private javax.swing.JTextPane listMessage;
    private javax.swing.JPopupMenu popupIcon;
    private javax.swing.JButton btnIcon;
    private javax.swing.JPanel iconPanel;


    @Override
    public void run() {
        try {
            model.clear();
            listClients = server.getListClientByName(name);
            int i=0;
            while(i<listClients.size()){
                model.addElement(listClients.get(i));
                i++;
            }
            listConnect.setModel(model);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}