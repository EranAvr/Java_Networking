/*
* TODO handle disconnections and re-connections.
* TODO handle multiple clients connecting to single server
* TODO handle bi-directional communication between server and clients
* */

package com.simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedList;

public class BiDiServer {
    private static final int DEF_PORT = 5555;
    private static final String DEF_EXIT_CMD = "end";

    public static void main(String[] args) {
        BiDiServer server = new BiDiServer();
        LinkedList<String> log = new LinkedList<>();
        try {
            String PROTOCOL = args.length>0 ? args[0] : "UDP";

            System.out.println(">> Server is running. Two-Way communication.");
            System.out.println(">> Server IP: " + InetAddress.getLocalHost());
            if (PROTOCOL.equals("UDP")) {
                server.udpInterface(log);
            }
            else if (PROTOCOL.equals("TCP")) {
                server.tcpInterface(log);
            }
        } catch (SocketException se) {
            se.printStackTrace();
            System.out.println("SocketException");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("IOException");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println(">> End Mirror Server session");
        }
    }

    private void udpInterface(LinkedList<String> log) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket(DEF_PORT);
        System.out.println(">> UDP channel is open");
        byte[] buffer;
        String msg = "";
        while ( !msg.equals(DEF_EXIT_CMD) ){
            buffer = new byte[100];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet); // receive msg from client
            msg = new String(Arrays.copyOfRange(buffer, 0, buffer.length-1));
            log.add(msg);   // save msg in log
            System.out.println(msg);    // print msg
            buffer = msg.getBytes();
            packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
            datagramSocket.send(packet);    // send msg back to client
        }
        datagramSocket.close();
    }

    private void tcpInterface(LinkedList<String> log) throws IOException{
        ServerSocket serverSocket = new ServerSocket(DEF_PORT);    // create Server socket for TCP communication
        Socket socket = serverSocket.accept();     // create Socket obj for communication-interface. Connect to Server.
        DataInputStream dis = new DataInputStream(socket.getInputStream());  // Attach input stream to socket
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());  // Attach output stream to socket
        String msg = "";
        System.out.println(">> TCP channel is open");
        while ( !(msg = dis.readUTF()).equals(DEF_EXIT_CMD) ){
            log.add(msg);
            System.out.println(">> Client: " + msg);
            dos.writeUTF(msg);
            System.out.println(">> Server: " + msg);
        }
        dis.close();
        socket.close();
        serverSocket.close();
    }
}
