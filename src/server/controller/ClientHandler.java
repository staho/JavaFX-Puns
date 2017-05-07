package server.controller;

import client.model.Line;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by staho on 07.05.2017.
 */
public class ClientHandler extends Thread {
    private Server server;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
            while(true) {
                Object object = inputStream.readObject();
                if (object instanceof Line) {
                    server.broadcast(this, (Line) object);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
