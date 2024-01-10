package com.example.chatrmi.client;

import com.example.chatrmi.server.InterfaceServer;
import com.example.chatrmi.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterView extends JFrame{
    private InterfaceServer server;
    private InterfaceClient clientServer;

    public RegisterView() {
        initComponents();
        this.setLocationRelativeTo(null);

        try {
            server = (InterfaceServer) Naming.lookup("rmi://localhost:4321/remote");
            clientServer = new ChatClient("YourClientName", server, new JTextArea(), new JTextPane(), new JPanel());
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }

   @SuppressWarnings("unchecked")
    private void initComponents() {
        registerBtn = new JButton();
        usernameLb = new JLabel();
        passwordLb = new JLabel();
        usernameField = new JTextField();
        passwordField = new JTextField();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log in");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        registerBtn.setFont(new Font("Dialog", 1,15));
        registerBtn.setText("Register");
        registerBtn.setToolTipText("");
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerBtn.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser(e);
            }
        }) ;

        usernameLb.setFont(new Font("Dialog", 1, 14));
        usernameLb.setText("Username");

        passwordLb.setFont(new Font("Dialog", 1, 14));
        passwordLb.setText("Password");

       usernameField.setColumns(20); // Số ký tự tối đa mà usernameField có thể chứa
       passwordField.setColumns(20);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(usernameLb)
                                                        .addComponent(passwordLb))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(usernameField)
                                                        .addComponent(passwordField)
                                                       ))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(124, 124, 124)
                                                .addComponent(registerBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameLb)
                                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLb)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addGap(20, 20, 20)
                                .addComponent(registerBtn)
                                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);


    }

    private void registerUser(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try{
            User user = new User(username, password);
            clientServer.registerUser(user);
            JOptionPane.showMessageDialog(this, "User register successfully");
            new LoginView();
        }catch (RemoteException ex){
            Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(this, "Error Registering user: "+ ex.getMessage());
        }
    }


    public static void main(String args[]){

        EventQueue.invokeLater(() -> {
            new RegisterView().setVisible(true);
        });
    }
    private JButton registerBtn;
    private JLabel usernameLb;
    private JLabel passwordLb;
    private JTextField usernameField;
    private JTextField passwordField;
}
