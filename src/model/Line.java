package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by staho on 07.05.2017.
 */
public class Line {
    private List<Point2D> listOfPoints;
    private double lineWidth;
    private Color lineColor;

    public void addPoint(double x, double y){
        listOfPoints.add(new Point2D(x, y));
        System.out.println("Point x:" + x + " y: " + y);
    }

    public Line(double lineWidth, Color lineColor) {
        listOfPoints = new LinkedList<>();
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
    }

    public List<Point2D> getListOfPoints(){
        return listOfPoints;
    }
}
