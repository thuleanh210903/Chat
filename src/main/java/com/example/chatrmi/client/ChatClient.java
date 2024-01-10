package com.example.chatrmi.client;

import com.example.chatrmi.model.User;
import com.example.chatrmi.server.InterfaceServer;
import com.example.chatrmi.controller.ConnectDatabase;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class ChatClient extends UnicastRemoteObject implements InterfaceClient{
    private final InterfaceServer server;
    private final String name;
    private final JTextPane input;
    private final JTextPane output;
    private final JPanel jpanel;
    

    public ChatClient(String name , InterfaceServer server, JTextPane jtext1, JTextPane jtext2, JPanel jpanel) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
        this.jpanel = jpanel;
        server.addClient(this);
    }



    @Override
    public void registerUser(User user) throws RemoteException {
        ConnectDatabase.registerUser(user.getUserName(), user.getPassword());
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
    }


    @Override
    public void retrieveMessage(File file, ArrayList<Integer> inc) throws RemoteException {
        System.out.println(file);
        JLabel label = new JLabel("<HTML><U><font size=\"4\" color=\"#365899\">" + file.getName() + "</font></U></HTML>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Mở hộp thoại JFileChooser để người dùng chọn vị trí lưu file
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath()+".png";
                    try {
                        byte[] data = new byte[inc.size()];
                        for (int i = 0; i < inc.size(); i++) {
                            data[i] = inc.get(i).byteValue();
                        }
                        // Ghi dữ liệu từ mảng byte[] vào file
                        FileOutputStream fos = new FileOutputStream(filePath);
                        fos.write(data);
                        fos.close();
                        JOptionPane.showMessageDialog(new JFrame(), "Your file has been saved at: " + filePath, "File Saved", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
        });
        jpanel.add(label);
        jpanel.repaint();
        jpanel.revalidate();
    }




    @Override
    public void sendMessage(List<String> list) {
        try {
            server.broadcastMessage(name + " : " + input.getText(),list, name);
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }


    @Override
    public void sendEmojiMessage(Icon emoji, List<String> list) throws RemoteException {
        try{
            server.broadcastMessage(name + ": ", emoji,list);
        }catch (RemoteException ex){
            System.out.println("Error " +ex.getMessage());
        }

    }

    public void retrieveMessage(String name, Icon emoji) throws RemoteException {
        StyledDocument doc = output.getStyledDocument();
        try {
            int len = doc.getLength(); // Lưu độ dài ban đầu của document
            doc.insertString(len, name + ": ", null); // Chèn tên vào trước tin nhắn
            len = doc.getLength(); // Cập nhật độ dài mới của document sau khi chèn tên

            // Chèn emoji vào cuối tin nhắn
            Style style = doc.addStyle("icon", null);
            StyleConstants.setIcon(style, emoji);
            doc.insertString(len, " ", style);

            // Chèn ký tự xuống dòng để ngăn tin nhắn tiếp theo bị ghi đè lên
            doc.insertString(doc.getLength(), "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }




    @Override
    public String getName() {
        return name;
    }


    @Override
    public void closeChat(String message) throws RemoteException {
        input.setEditable(false);
        input.setEnabled(false);
        JOptionPane.showMessageDialog(new JFrame(),message,"Alert",JOptionPane.WARNING_MESSAGE); 
    }


    @Override
    public void openChat() throws RemoteException {
        input.setEditable(true);
        input.setEnabled(true);    
    }
}