/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.entities.OrganismManager;
import evolith.entities.PredatorManager;
import evolith.entities.ResourceManager;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charles
 */
public class NetworkManager implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private InetAddress address;
    private int port;

    private boolean server;
    private boolean otherExtinct;
    private boolean otherWon;
    private boolean otherDisconnect;
    
    private OrganismManager otherorgs;
    private ResourceManager resources;
    private PredatorManager predators;
    
    public NetworkManager(boolean isServer, OrganismManager otherorgs, ResourceManager resources, PredatorManager predators) {
        this.otherorgs = otherorgs;
        this.resources = resources;
        this.predators = predators;
        server = isServer;
        
        otherExtinct = false;
        otherWon = false;
        otherDisconnect = false;
        
        port = 0;
    }
    
    public void initClient(String address, int port) {
        if (server) {
            //Not valid if instance is not a client
            return;
        }
        
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();
        } catch(UnknownHostException | SocketException e) {
            System.out.println(e);
        }
    }
    
    public void initServer() {
        try {  
            socket = new DatagramSocket(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *
     * @param orgs
     * @param data
     */
    public void sendData(OrganismManager orgs) {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructData(orgs);
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendDataPlants(ResourceManager res) {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructDataPlants(res);
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendDataWaters(ResourceManager res) {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructDataWaters(res);
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendDataPreds(PredatorManager preds) {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructDataPreds(preds);
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendDataExtinct() {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructDataExtinct();
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendDataWin() {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructDataWin();
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void receiveData() {
        try {
            //If port is 0, address and port have not been specified
            //So define them
            
            byte[] receivedData = new byte[/*(int) ObjectSizeFetcher.getObjectSize(receivedData)*/ 2304];
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.receive(packet);
            
            if (port == 0) {
                port = packet.getPort();
                address = packet.getAddress();
            }
            
            //The first byte on each packet defines its type
            switch (receivedData[0]) {
                //Organism packet
                case 1:
                    NetworkData.parseBytes(otherorgs, receivedData);
                    break;
                //Plants packet
                case 2:
                    NetworkData.parseBytesPlants(resources, receivedData, server);
                    break;
                //Water packet
                case 3:
                    NetworkData.parseBytesWaters(resources, receivedData, server);
                    break;
                //Predator packet    
                case 4:
                    NetworkData.parseBytesPreds(predators, receivedData);
                    break;
                //Extinct message
                case 5:
                    otherExtinct = true;
                    break;
                //Won message
                case 6:
                    otherWon = true;
                    break;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean isOtherWon() {
        return otherWon;
    }

    public boolean isOtherExtinct() {
        return otherExtinct;
    }

    public boolean isOtherDisconnect() {
        return otherDisconnect;
    }

    @Override
    public void run() {
        while (true) {
            receiveData();
        }
    }
}
