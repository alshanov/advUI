package model;

import java.awt.Point;
import java.util.*;

public class Model extends Observable {
    private HashMap<Point, String> textList;
    private ArrayList<Point> currentStroke;
    private ArrayList<ArrayList<Point>> strokeList;
    private Integer strokeId;

    public Model() {
       textList = new HashMap<Point, String>();
       strokeList = new ArrayList<ArrayList<Point>>();

       strokeId = 0;
       currentStroke = new ArrayList<Point>();
       strokeList.add(currentStroke);
    }




    public void updateArrayPoint(Point mouseLocation) {
        strokeList.get(strokeId).add(mouseLocation);
        notifyViews();
    }


    public ArrayList<ArrayList<Point>> getStrokesArray(){
        return strokeList;
    }


    public void updateTextHashMap(Point startPoint, String annotation) {
        if(textList.containsKey(startPoint)){
            String currentString = textList.get(startPoint);
            currentString = currentString + annotation;
            textList.put(startPoint,currentString);
        }else{
           textList.put(startPoint,annotation);
        }

        notifyViews();
    }


    public void removePreviousCharacter(Point startPoint){
        if(textList.containsKey(startPoint)){
            String currentString = textList.get(startPoint);
            if (currentString.length() > 0){
                currentString = currentString.substring(0, currentString.length() - 1);
                textList.put(startPoint,currentString);
            }
        }
        notifyViews();
    }


    public HashMap<Point, String> getTextHashMap(){
        return textList;
    }


    public void endCurrentStroke(){
        currentStroke = new ArrayList<Point>();
        strokeId = strokeId + 1;
        strokeList.add(currentStroke);
    }

    public void notifyViews() {
        setChanged();
        notifyObservers();
     }

}