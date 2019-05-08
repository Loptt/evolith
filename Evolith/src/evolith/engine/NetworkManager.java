/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.entities.OrganismManager;
import evolith.entities.PredatorManager;
import evolith.entities.ResourceManager;
import evolith.helpers.Commons;
import evolith.helpers.Time;
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
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class NetworkManager implements Runnable, Commons {
    private DatagramSocket socket;      //Connection socket
    private DatagramPacket packet;      //Transimission packet
    private InetAddress address;        //Other player address
    private int port;                   //Other player port

    private boolean server;             //Server state
    private boolean otherExtinct;       //extinct state
    private boolean otherWon;           //win state
    private boolean otherDisconnect;    //Other player disconnected flag
    private boolean clientReady;        //client ready flag
    private boolean serverReady;        //server ready flag
    private boolean timeOut;            //timeout flag
    
    private boolean active;             //Active flag
    
    private OrganismManager otherorgs;  //Opponent organisms
    private ResourceManager resources;  //Resources
    private PredatorManager predators;  //Preadtors
    
    private Time time;                  //current time
    private int secSinceRecv;           //Seconds to timeout
    
    /**
     * NetworkManager constructor
     * @param isServer server state
     * @param otherorgs opponent organisms
     * @param resources this resources
     * @param predators this predators
     */
    public NetworkManager(boolean isServer, OrganismManager otherorgs, ResourceManager resources, PredatorManager predators) {
        this.otherorgs = otherorgs;
        this.resources = resources;
        this.predators = predators;
        server = isServer;
        active = false;
        otherExtinct = false;
        otherWon = false;
        otherDisconnect = false;
        timeOut = false;
        
        time = new Time();
        
        port = 0;
    }
    
    /**
     * Initialize client
     * @param address host address
     * @param port host port
     */
    public void initClient(String address, int port) {
        if (server) {
            //Not valid if instance is not a client
            return;
        }
        
        active = true;
        
        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();
        } catch(UnknownHostException | SocketException e) {
            System.out.println(e);
        }
    }
    
    /**
     * Initialize server
     */
    public void initServer() {
        try {  
            active = true;
            socket = new DatagramSocket(5000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * tick the time
     */
    public void tick() {
        time.tick();
        
        if (time.getSeconds() >= secSinceRecv + CONNECTION_TIMEOUT_SEC) {
            timeOut = true;
        }
    }
    
    /**
     *  Send organism data
     * @param orgs organisms to send
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
    
    /**
     * Send plant data
     * @param res plants to send
     */
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
    
    /**
     *  Send water data
     * @param res waters to send
     */
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
    
    /**
     * Send predators data
     * @param preds predators to send
     */
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
    
    /**
     * Send extinct flag
     */
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
    
    /**
     * Send won flag
     */
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
    
    /**
     * Send ready flag
     * @param client true if client
     */
    public void sendReady(boolean client) {
        if (port == 0) {
            //No address specified
            //beep beep bop
            return;
        }
        
        byte[] data = NetworkData.constructReady(client);
        
        packet = new DatagramPacket(data, NetworkData.getConstructedByteAmount(), address, port);
        
        try { 
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(NetworkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Receive data from the remote computer and process it
     */
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
                //Ready message
                case 7:
                    if (NetworkData.unsignByte(receivedData[1]) == 128) {
                        clientReady = true;
                    } else if (NetworkData.unsignByte(receivedData[1]) == 64) {
                        serverReady = true;
                    }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    /**
     * Close the connection
     */
    public void endConnection() {
        if (socket != null) {
            socket.close();
        }
        active = false;
    }
    
    /**
     * get clientReady state
     * @return clientReady
     */
    public boolean isClientReady() {
        return clientReady;
    }
    
    /**
     * get serverReady state
     * @return serverReady
     */
    public boolean isServerReady() {
        return serverReady;
    }

    /**
     * get other won
     * @return otherWon
     */
    public boolean isOtherWon() {
        return otherWon;
    }

    /**
     * get other extinct state
     * @return otherExtinct
     */
    public boolean isOtherExtinct() {
        return otherExtinct;
    }

    /**
     * get other disconnect
     * @return other disconnect
     */
    public boolean isOtherDisconnect() {
        return otherDisconnect;
    }

    /**
     * get timeout from remote
     * @return timeOut
     */
    public boolean isTimeOut() {
        return timeOut;
    }

    /**
     * Start network thread
     */
    @Override
    public void run() {
        while (active) {
            receiveData();
            secSinceRecv = (int) time.getSeconds();
        }
    }
}
