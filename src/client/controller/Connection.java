package client.controller;

import client.model.Line;
import javafx.application.Platform;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by staho on 07.05.2017.
 */
public class Connection{
    private Socket clientSocket;
    private final String ipAddress = "localhost";
    private final int PORT = 4822;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private DrawingController drawingController1;

    public Connection(DrawingController drawingController){
        this.drawingController1 = drawingController;
        InetAddress host = null;
        try {
            host = InetAddress.getByName(ipAddress);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(host != null){
            try {
                clientSocket = new Socket(host, PORT);
                clientSocket.setReuseAddress(true);
                System.out.println("Connected to: " + host.getHostName() + ":" + host.getHostAddress());

                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(clientSocket.getInputStream());

                Thread thread = new Thread(() -> {
                    try {
                        while (true){
                            Object object = inputStream.readObject();
                            if(object instanceof Line){
                                Line line = (Line) object;

                                drawingController1.drawLine(line);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void sendLine(Line line){
        try {
            outputStream.writeObject(line);
            outputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
