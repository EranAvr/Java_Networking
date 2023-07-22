package com.simple;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class EchoServer {
    private static final int DEF_PORT = 5555;
    private static final int DEF_BUFFER_SIZE = 128;
    private static final UUID uuid = UUID.randomUUID();
    private static final String DEF_PROTOCOL = "TCP";
    private static final String DEF_EXIT_CMD = "end";

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        LinkedList<String> log = new LinkedList<>();
        String PROTOCOL = args.length>0 ? args[0] : DEF_PROTOCOL;
        try {
            System.out.println(">> Echo Server is running.");
            System.out.println(">> UUID: "+uuid+"\tProtocol: " + PROTOCOL + "\tServer IP: " + InetAddress.getLocalHost());
            if (PROTOCOL.equals("UDP")) {
                server.udpInterface(log);
            }
            else if (PROTOCOL.equals("TCP")) {
                server.tcpInterface(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> End Echo Server session");
        }
    }

    private void udpInterface(LinkedList<String> log) throws IOException{
        /*
         * While running, waiting for packets of data independently
         * */
        DatagramSocket datagramSocket = new DatagramSocket(DEF_PORT);
        byte[] buffer;
        String msg = "";
        while ( !msg.equals(DEF_EXIT_CMD) ){
            buffer = new byte[DEF_BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(packet);
            System.out.println("Client connected.");

            Date d = new Date();
            msg = new String(packet.getData(), 0, packet.getLength());
            log.add("date: " + d + " message: " + msg);   // save msg in log

            if (msg.equals("print log"))
                for (String l : log)
                    System.out.println(l);
        }
        datagramSocket.close();
    }

    private void tcpInterface(LinkedList<String> log) throws IOException{
        ServerSocket serverSocket = new ServerSocket(DEF_PORT);    // create Server socket for TCP communication

        while (true){
            Socket socket = serverSocket.accept();     // create Socket obj for communication-interface.
            System.out.println(">> Client connected.");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String msg = "";
            do {
                Date d = new Date();
                msg = reader.readLine();
                System.out.println("Client says: "+msg);
                log.add("date: " + d + " message: " + msg);   // save msg in log
                if (msg.equals("print log")){
                    System.out.println(">> Log:");
                    for (String l : log)
                        System.out.println(l);
                }
                writer.println(log.getLast());
            } while (!msg.equals(DEF_EXIT_CMD));
            socket.close();
            System.out.println(">> Client disconnected.");
        }
//        serverSocket.close();
    }
}
