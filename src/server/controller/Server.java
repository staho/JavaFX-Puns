package server.controller;

import client.model.Line;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by staho on 07.05.2017.
 */
public class Server implements Runnable {
    private final static int PORT = 4822;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientSet;


    @Override
    public void run(){
        while (true){
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected: " + clientSocket.getRemoteSocketAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientSet.add(clientHandler);
                clientHandler.start();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private Server(){
        try{
            serverSocket = new ServerSocket(PORT);
            serverSocket.setReuseAddress(true);
            clientSet = Collections.synchronizedSet(new HashSet<ClientHandler>());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void broadcast(ClientHandler clientHandlerSource, Line line) throws Exception {
        for(ClientHandler client: clientSet){
            if(client != clientHandlerSource && !client.getSocket().isClosed()){
                ObjectOutputStream outputStream = client.getOutputStream();
                outputStream.writeObject(line);
                outputStream.flush();
            }
        }
    }
    public static void main(String[] args){
        new Server().run();
    }
}
