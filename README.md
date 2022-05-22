# Java_Networking
Java projects in networking, mainly based on java.net library

About this module:

A module contain simple client and mirror-server classes, and uses 'java.net' library's sockets.
Our Client main() program can optionally take up to 3 arguments -
  protocol (def=UDP), dest_ip (def=local_host), dest_port (def=DEF_PORT)
According to the mentioned arguments, Client will send messages to Server through the constant PORT
Our Server main() program could possibly take up to 1 argument - protocol (def=UDP)
According to the mentioned arguments, Server will listen to the constant PORT

Java Sockets:
Socket - TCP/IP connection. Encapsulate client behaviour. Can handle both sides of the communication.
ServerSocket - TCP/IP connection. Encapsulate server behaviour.
DatagramSocket - UDP connection. Encapsulate both client and server behaviour.

Well Known Ports: 0-1023, depend on superuser permission
Registered Ports: 1024-49151, assigned by IANA standard, usually don't need special permission
