package com.simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class BiDiClient {
    private static final int DEF_PORT = 5555;
    private static final String DEF_EXIT_CMD = "end";

    public static void main(String[] args) {
        BiDiClient client = new BiDiClient();
        LinkedList<String> log = new LinkedList<>();    // logger for client input
        try {
            String protocol = args.length>0 ? args[0] : "UDP";
            InetAddress dest_IP = args.length>1 ? InetAddress.getByName(args[1]) : InetAddress.getLocalHost();
            int dest_PORT = args.length>2 ? Integer.parseInt(args[2]) : DEF_PORT;

            System.out.println(">> Client is running. Two-Way communication.");
            System.out.println(">> Dest IP: " + dest_IP + "\tDest PORT:" + dest_PORT);
            if (protocol.equals("UDP")) {
                client.udpInterface(log, dest_IP, dest_PORT);
            }
            else if (protocol.equals("TCP")) {
                client.tcpInterface(log, dest_IP, dest_PORT);
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
            System.out.println(">> End Client session");
        }
    }

    private void udpInterface(LinkedList<String> log, InetAddress dest_ip, int dest_port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">> Init UDP commun..");
        DatagramSocket datagramSocket = new DatagramSocket();    // create Client socket for UDP communication
        System.out.println(">> UDP channel is open");
        byte[] buffer;
        while (true){
            String msg = scanner.nextLine();    // enter client msg
            // log.add(msg);
            buffer = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, dest_ip, dest_port);  // create msg packet
            datagramSocket.send(packet);   // send packet via DatagramSocket
            if (msg.equals(DEF_EXIT_CMD))
                break;
            buffer = new byte[100];
            packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet); // receive response from server
            msg = new String(Arrays.copyOfRange(buffer, 0, buffer.length-1));
            System.out.println(msg);    // print msg
        }
        datagramSocket.close();
    }

    private void tcpInterface(LinkedList<String> log, InetAddress dest_ip, int dest_port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">> Init TCP commun..");
        Socket socket = new Socket(dest_ip, dest_port);    // create Client socket for TCP communication
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   // attach output-stream to socket
        DataInputStream dis = new DataInputStream(socket.getInputStream());     // attach input-stream to socket
        System.out.println(">> TCP channel is open");
        while (true){
            System.out.print(">> Client: ");
            String msg = scanner.nextLine();
            // log.add(msg);
            if (msg.equals(DEF_EXIT_CMD))
                break;
            dos.writeUTF(msg);  // write utf-msg to DataOutputStream, and than through the socket output stream
            msg = dis.readUTF();    // read utf-msg from DataInputStream, which connect to socket input stream
            System.out.println(">> Server: " + msg);

            dos.flush();
        }
        dis.close();
        dos.close();
        socket.close();
    }
}
