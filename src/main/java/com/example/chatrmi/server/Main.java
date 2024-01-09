package com.example.chatrmi.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Main extends javax.swing.JFrame {

    private ChatServer chatServer;

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        cmdStart = new JButton();
        cmdStop = new JButton();
        jScrollPane1 = new JScrollPane();
        txt = new JTextArea();
        lbStatus = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cmdStart.setBackground( new Color(102, 255, 102));
        cmdStart.setText("Start server");
        cmdStart.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmdStartActionPerformed(e);
            }
        });

        cmdStop.setBackground(new Color(255, 153, 153));
        cmdStop.setText("Stop Server");
        cmdStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStopActionPerformed(evt);
            }
        });

        txt.setEditable(false);
        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

//        lbStatus.setForeground(new Color(255, 51, 51));
//        lbStatus.setText("Server stop");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmdStart)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmdStop)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 332, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(cmdStart)
                                                .addComponent(cmdStop)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                                .addContainerGap())
        );


        pack();
        setLocationRelativeTo(null);


    }

    private void startServer() throws Exception {
        try {
            chatServer = new ChatServer();
            LocateRegistry.createRegistry(4321);
            Naming.rebind("rmi://localhost:4321/remote", chatServer);
            txt.setText("Server now starting");
            lbStatus.setForeground(new Color(0,255,0));
            lbStatus.setText("Server start");

        } catch (MalformedURLException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    private void stopServer() throws Exception {
            try {
                if(chatServer!=null){
                    Naming.unbind("rmi://localhost:4321/remote"); // delete register service
                    UnicastRemoteObject.unexportObject(chatServer, true); //stop service
                    txt.setText("Server stopped");
                    lbStatus.setForeground(new Color(255, 39, 39));
                    lbStatus.setText("Stop server");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void cmdStartActionPerformed(ActionEvent e) {
        try{
            startServer();
        }catch (Exception er){
            JOptionPane.showMessageDialog(this, er, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cmdStopActionPerformed(ActionEvent e) {
        try {
            stopServer();
        }catch (Exception er){
            JOptionPane.showMessageDialog(this, er, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    public static void main(String []args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }



    private javax.swing.JButton cmdStart;
    private javax.swing.JButton cmdStop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTextArea txt;
}