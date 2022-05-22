package com.company;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedList;

public class ServerM {
    private static final int DEF_PORT = 5555;
    private static final String DEF_EXIT_CMD = "end";

    public static void main(String[] args) {
        ServerM server = new ServerM();
        LinkedList<String> log = new LinkedList<>();
        try {
            String PROTOCOL = args.length>0 ? args[0] : "UDP";

            System.out.println(">> Mirror Server is running.");
            System.out.println(">> Protocol: " + PROTOCOL + "\tServer IP: " + InetAddress.getLocalHost());
            if (PROTOCOL.equals("UDP")) {
                server.udpInterface(log);
            }
            else if (PROTOCOL.equals("TCP")) {
                server.tcpInterface(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> End Mirror Server session");
        }
    }

    private void udpInterface(LinkedList<String> log) throws IOException{
        DatagramSocket datagramSocket = new DatagramSocket(DEF_PORT);
        byte[] buffer = new byte[100];
        String msg = "";
        while ( !msg.equals(DEF_EXIT_CMD) ){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);
            msg = new String(Arrays.copyOfRange(buffer, 0, buffer.length-1));
            log.add(msg);   // save msg in log
            System.out.println(msg);    // mirroring
            if (msg.equals("prnt log"))
                for (String l : log)
                    System.out.println(l);
            buffer = new byte[100];
        }
        datagramSocket.close();
    }

    private void tcpInterface(LinkedList<String> log) throws IOException{
        ServerSocket serverSocket = new ServerSocket(DEF_PORT);    // create Server socket for TCP communication
        Socket socket = serverSocket.accept();     // create Socket obj for communication-interface. Connect to Server.
        DataInputStream dis = new DataInputStream(socket.getInputStream());  // Attach input stream to socket
        String msg = "";
        while ( !(msg = dis.readUTF()).equals(DEF_EXIT_CMD) ){
            log.add(msg);
            System.out.println(msg);
            if (msg.equals("prnt log"))
                for (String l : log)
                    System.out.println(l);
        }
        dis.close();
        socket.close();
        serverSocket.close();
    }
}
