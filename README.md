# Java_Networking
Java projects in networking, based mainly on java.net library

Java Sockets:
  Socket - TCP/IP connection. Encapsulate client behaviour. Can handle both sides of the communication.
  ServerSocket - TCP/IP connection. Encapsulate server behaviour.
  DatagramSocket - UDP connection. Encapsulate both client and server behaviour.

Well Known Ports: 0-1023, depend on superuser permission
Registered Ports: 1024-49151, assigned by IANA standard, usually don't need special permission

Mirror Client-Server module:
  This module contain simple client and mirror-server classes, and uses 'java.net' library's sockets.
  Our ClientM main() program can optionally take up to 3 arguments -
    protocol (def=UDP), dest_ip (def=local_host), dest_port (def=DEF_PORT)
  According to the mentioned arguments, Client will send messages to Server through the constant PORT
  Our ServerM main() program could possibly take up to 1 argument - protocol (def=UDP)
  According to the mentioned arguments, Server will listen to the constant PORT
