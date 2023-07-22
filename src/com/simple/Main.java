/*
* @TODO With any client-server system, you need to start with defining how the two should communicate:
            What infrastructure to use? Connected? Disconnected? Stateless? Stateful?
            What kind of communication method? Push? Pull? A bit of both?
            What calls should be available to each side?
            What are the latency requirements?
            What are the scalability requirements?
*
* @TODO write communication protocol and simple UI
* @TODO extract connection-data from the Socket obj
*
* About this module:
* A module contain simple client and mirror-server classes, and uses 'java.net' library's sockets.
* Our Client main() program can optionally take up to 3 arguments -
*   protocol (def=UDP), dest_ip (def=local_host), dest_port (def=DEF_PORT)
* According to the mentioned arguments, Client will send messages to Server through the constant PORT
* Our Server main() program could possibly take up to 1 argument - protocol (def=UDP)
* According to the mentioned arguments, Server will listen to the constant PORT
*
* Java Sockets:
* Socket - TCP/IP connection. Encapsulate client behaviour. Can handle both sides of the communication.
* ServerSocket - TCP/IP connection. Encapsulate server behaviour.
* DatagramSocket - UDP connection. Encapsulate both client and server behaviour.
*
* Well Known Ports: 0-1023, depend on superuser permission
* Registered Ports: 1024-49151, assigned by IANA standard, usually don't need special permission
*/

package com.simple;

import java.util.Stack;

public class Main {

    public static int size(Stack<Integer> s){
        int count = 0;
        if (s.isEmpty()){
            return 0;
        }
        int x = s.pop();
        count += size(s) + 1;
        s.push(x);
        return count;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.push(4);
        stack.push(6);
        stack.push(8);
        stack.push(10);
        System.out.println(size(stack));
    }
}
