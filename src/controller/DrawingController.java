package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Line;

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
    private void handleRedraw(){
        for(Line line:lineList){
            drawFromLineList(line);
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

        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                color = colorPicker.getValue();
                graphicsContext.setStroke(color);
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        lineList.add(new Line(lineWidth, colorPicker.getValue()));
                        graphicsContext.beginPath();
                        graphicsContext.moveTo(event.getX(), event.getY());
                        lineList.get(lineList.size()-1).addPoint(event.getX(), event.getY());
                        //System.out.println("Start from: x: " + event.getX() + " y: " + event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.lineTo(event.getX(), event.getY());
                        lineList.get(lineList.size()-1).addPoint(event.getX(), event.getY());
                        //System.out.println("x: " + event.getX() + " y: " + event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        //System.out.println("Relased");

                    }
                });
    }



    private void drawFromLineList(Line line){
        if(line != null) {
            graphicsContext.beginPath();
            graphicsContext.moveTo(line.getListOfPoints().get(0).getX(), line.getListOfPoints().get(0).getY());
            graphicsContext.stroke();
            for (Point2D point : line.getListOfPoints()) {
                graphicsContext.lineTo(point.getX(), point.getY());
                graphicsContext.stroke();
            }
        }

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
