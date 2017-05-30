package client.controller;

import com.sun.javaws.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import client.model.Line;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by staho on 23.04.2017.
 */
public class DrawingController {

    private Color color;
    private GraphicsContext graphicsContext;
    private double lineWidth;
    private List<Line> lineList;
    private Connection connection;
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private javafx.scene.canvas.Canvas canvas;
    @FXML
    private ColorPicker colorPicker;

    @FXML
    private void handleClear(){
        graphicsContext.clearRect(0,0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        double canvasHeight = graphicsContext.getCanvas().getHeight();
        double canvasWidth = graphicsContext.getCanvas().getWidth();

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(5);

        graphicsContext.fill();
        graphicsContext.strokeRect(
                0,
                0,
                canvasWidth,
                canvasHeight
        );

        graphicsContext.setFill(Color.RED);
        graphicsContext.setStroke(color);
        graphicsContext.clearRect(0,0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        graphicsContext.setLineWidth(lineWidth);
    }

    @FXML
    private void handleConnect(){
        connection = new Connection(this);
        mainApp.getPrimaryStage().setTitle("Kamilbury " + connection.getHostName());
    }

    @FXML
    private void handleRedraw(){
        for(Line line:lineList){
            drawLine(line);
        }
    }

    @FXML
    private void initialize(){
        lineList = new LinkedList<>();
        //
        lineWidth = 1;
        //
        colorPicker.setValue(Color.BLACK);
        graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);

        colorPicker.setOnAction(event -> {
            color = colorPicker.getValue();
            graphicsContext.setStroke(color);
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    lineList.add(new Line(lineWidth, fxToAwtColor(colorPicker.getValue())));
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(event.getX(), event.getY());
                    lineList.get(lineList.size()-1).addPoint(event.getX(), event.getY());
                    //System.out.println("Start from: x: " + event.getX() + " y: " + event.getY());
                    graphicsContext.stroke();
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    graphicsContext.lineTo(event.getX(), event.getY());
                    lineList.get(lineList.size()-1).addPoint(event.getX(), event.getY());
                    //System.out.println("x: " + event.getX() + " y: " + event.getY());
                    graphicsContext.stroke();

                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                event -> {
                    //System.out.println("Relased");
                    connection.sendLine(lineList.get(lineList.size()-1));
                });
    }



    public void drawLine(Line line){
        if(line != null) {
            graphicsContext.beginPath();
            graphicsContext.moveTo(line.getListOfPoints().get(0).getX(), line.getListOfPoints().get(0).getY());

            graphicsContext.setStroke(awtToFxColor(line.getLineColor()));

            graphicsContext.stroke();
            for (java.awt.geom.Point2D point : line.getListOfPoints()) {
                graphicsContext.lineTo(point.getX(), point.getY());
                graphicsContext.stroke();
            }
        }

    }

    private Color awtToFxColor(java.awt.Color color){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();
        double opacity = a / 255.0;

       Color color1 = Color.rgb(r, g, b, opacity);
       return color1;
    }
    private java.awt.Color fxToAwtColor(Color color){
        java.awt.Color color1 = new java.awt.Color( (float) color.getRed(),
                                                    (float) color.getGreen(),
                                                    (float) color.getBlue(),
                                                    (float) color.getOpacity());
        return color1;
    }

    private void initDraw(GraphicsContext gc){
        handleClear();
    }
        /*gc.clearRect(0,0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        double canvasHeight = gc.getCanvas().getHeight();
        double canvasWidth = gc.getCanvas().getWidth();

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,
                0,
                canvasWidth,
                canvasHeight
        );

        gc.setFill(Color.RED);
        gc.setStroke(color);
        //gc.clearRect(0,0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setLineWidth(lineWidth);
    }*/
}
