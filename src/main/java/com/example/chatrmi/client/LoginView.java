package com.example.chatrmi.client;



import com.example.chatrmi.server.InterfaceServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginView extends JFrame {
    private InterfaceServer server;

    public LoginView() {
        initComponents();
        this.setLocationRelativeTo(null);
        ImageIcon iconLogin = new ImageIcon("img/login.png");
        setIconImage(iconLogin.getImage());

        try {
            server = (InterfaceServer) Naming.lookup("rmi://localhost:4321/remote");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        loginBtn = new JButton();
        usernameLb = new JLabel();
        passwordLb = new JLabel();
        usernameTextField = new JTextField();
        passwordTextField = new JTextField();
        jComboBox1 = new JComboBox<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log in");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setResizable(false);

        loginBtn.setFont(new Font("Dialog", 1, 15)); // NOI18N
        loginBtn.setText("Log in");
        loginBtn.setToolTipText("");
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                login(evt);
            }
        });

        usernameLb.setFont(new Font("Dialog", 1, 14)); // NOI18N
        usernameLb.setText("Username");

        passwordLb.setFont(new Font("Dialog", 1, 14)); // NOI18N
        passwordLb.setText("Password");

        usernameTextField.setPreferredSize(new Dimension(200, 27));
        passwordTextField.setPreferredSize(new Dimension(200, 27));

        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jComboBox1.setFont(new Font("Dialog", 0, 14)); // NOI18N
        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[] { "Simple User", "Administrator" }));

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
                                                        .addComponent(jComboBox1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(usernameTextField)
                                                        .addComponent(passwordTextField)
                                                )
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(124, 124, 124)
                                                .addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameLb)
                                        .addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLb)
                                        .addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                                .addComponent(loginBtn)
                                .addGap(20, 20, 20))
        );


        pack();
        setLocationRelativeTo(null);
    }

    private void login(ActionEvent evt) {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if (!username.equals("") && !username.contains(" ") && !password.equals("") && !password.contains(" ")) {
                if (server.checkLogin(username, password)) {
                    JOptionPane.showMessageDialog(this, "User login successfully");
                    openChatView(username);

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid username or password", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            } else {

                JOptionPane.showMessageDialog(new JFrame(), "Invalid username", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Error! A RemoteException occurred in the server.\n\nTry to:\n- Restart the server\n- Change the port", "Alert", JOptionPane.WARNING_MESSAGE);
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void openChatView(String username) {
        SwingUtilities.invokeLater(() -> {
            new ChatView(username, (String) jComboBox1.getSelectedItem(), server);
            this.dispose();
        });
    }

    private void jTextField1ActionPerformed(ActionEvent evt) {

    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }


    private JButton loginBtn;
    private JComboBox<String> jComboBox1;
    private JLabel usernameLb;
    private JLabel passwordLb;
    private JTextField usernameTextField;
    private JTextField passwordTextField;

}
