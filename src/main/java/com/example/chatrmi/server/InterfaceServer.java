package com.example.chatrmi.server;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import com.example.chatrmi.client.InterfaceClient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public interface InterfaceServer extends Remote{

    boolean checkLogin(String username, String password) throws RemoteException;

    void broadcastMessage(String message,List<String> list, String name) throws RemoteException;
    

    void broadcastMessage(ArrayList<Integer> inc, List<String> list, File file) throws RemoteException;


    void broadcastMessage(String name, Icon emoji, List<String> list) throws RemoteException;
    

    Vector<String> getListClientByName(String name) throws RemoteException;
    

    void addClient( InterfaceClient client) throws RemoteException;
    

    void blockClient(List<String> clients) throws RemoteException;


    void removeClient(List<String> clients) throws RemoteException;
    

    void removeClient(String clients) throws RemoteException;
    

    void reactiveClient(List<String> clients) throws RemoteException;
    

    boolean checkUsername(String username) throws RemoteException;
}