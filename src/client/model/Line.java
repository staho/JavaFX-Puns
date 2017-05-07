package client.model;

import javafx.geometry.Point2D;

import java.awt.*;
import java.awt.image.ConvolveOp;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by staho on 07.05.2017.
 */
public class Line implements Serializable {
    private List<java.awt.geom.Point2D.Double> listOfPoints;
    private double lineWidth;
    private java.awt.Color lineColor;

    public void addPoint(double x, double y){
        listOfPoints.add(new java.awt.geom.Point2D.Double(x, y));
        //System.out.println("Point x:" + x + " y: " + y);
    }

    public Line(double lineWidth, java.awt.Color lineColor) {
        super();
        listOfPoints = new LinkedList<>();
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public java.awt.Color getLineColor() {
        return lineColor;
    }

    public List<java.awt.geom.Point2D.Double> getListOfPoints(){
        return listOfPoints;
    }
}
