package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/**
 * Created by staho on 23.04.2017.
 */
public class DrawingController {

    private Color color;
    private GraphicsContext graphicsContext;
    private double lineWidth;

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
    private void initialize(){
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
                        graphicsContext.beginPath();
                        graphicsContext.moveTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        graphicsContext.lineTo(event.getX(), event.getY());
                        graphicsContext.stroke();
                    }
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
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
