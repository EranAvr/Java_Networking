package com.company;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;

public class ClientM {
    private static final int DEF_PORT = 5555;
    private static final String DEF_EXIT_CMD = "end";

    public static void main(String[] args) {
        ClientM client = new ClientM();
        LinkedList<String> log = new LinkedList<>();    // logger for client input
        try {
            String protocol = args.length>0 ? args[0] : "UDP";
            InetAddress dest_IP = args.length>1 ? InetAddress.getByName(args[1]) : InetAddress.getLocalHost();
            int dest_PORT = args.length>2 ? Integer.parseInt(args[2]) : DEF_PORT;

            System.out.println(">> Mirror Client is running.");
            System.out.println(">> Protocol: " + protocol + "\tDest IP: " + dest_IP + "\tDest PORT:" + dest_PORT);
            if (protocol.equals("UDP")) {
                client.udpInterface(log, dest_IP, dest_PORT);
            }
            else if (protocol.equals("TCP")) {
                client.tcpInterface(log, dest_IP, dest_PORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> End Mirror Client session");
        }
    }

    private void udpInterface(LinkedList<String> log, InetAddress dest_ip, int dest_port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DatagramSocket datagramSocket = new DatagramSocket();    // create Client socket for UDP communication
        while (true){
            String msg = scanner.nextLine();
            // log.add(msg);
            byte[] buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, dest_ip, dest_port);  // create packet
            datagramSocket.send(packet);   // send packet via DatagramSocket
            if (msg.equals(DEF_EXIT_CMD))
                break;
        }
        datagramSocket.close();
    }

    private void tcpInterface(LinkedList<String> log, InetAddress dest_ip, int dest_port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(dest_ip, dest_port);    // create Client socket for TCP communication
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   // attach output-stream to socket
        while (true){
            String msg = scanner.nextLine();
            // log.add(msg);
            if (msg.equals(DEF_EXIT_CMD))
                break;
            dos.writeUTF(msg);  // write utf-msg to DataOutputStream, and than through the socket output stream
            dos.flush();
        }
        dos.close();
        socket.close();
    }
}
