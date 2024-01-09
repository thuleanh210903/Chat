package com.example.chatrmi.client;

import com.example.chatrmi.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface InterfaceClient extends Remote{

    void registerUser(User user) throws RemoteException;

    void retrieveMessage(String message) throws RemoteException;
    

    void retrieveMessage(String filename,ArrayList<Integer> inc) throws RemoteException;
    

    void sendMessage(List<String> list) throws RemoteException;
    

    String getName()throws RemoteException;
    

    void closeChat(String message) throws RemoteException;
    

    void openChat() throws RemoteException;
}
