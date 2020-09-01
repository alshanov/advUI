package controller;


import java.awt.Graphics;
import javax.swing.*;

import model.Model;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public final class PhotoComponent extends JComponent implements Observer {
    public Image image;
    public boolean hidden;
    public Dimension size;
    public Model model;
    public Point startLinePoint;
    public Integer stringLength;
    public boolean mouseMoved;


    public PhotoComponent(String filepath , JLabel statusBar) {

    	model = new Model();

        model.addObserver(this);

        hidden = false;
       Color background = Color.lightGray;
        setBackground(background);
        startLinePoint = null;
        stringLength = 0;
        mouseMoved = false;

        try {
            image = ImageIO.read(new File(filepath));
            setImageSize();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    flipPhoto();
                }else if (e.getClickCount() == 1){
                    Point mouseClickPoint = e.getPoint();
                    if(mouseClickPoint.x < size.width && mouseClickPoint.y < size.height ){
                        startLinePoint = mouseClickPoint;
                    }
                    stringLength = 0;
                }
            }
            @Override
            public void mouseReleased(MouseEvent evt) {
                if(mouseMoved){
                    mouseMoved = false;
                    model.endCurrentStroke();
                    
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                evt.consume();
                Point mouseStart = evt.getPoint();
                if(mouseStart.x < size.width  && mouseStart.y < size.height ){
                    mouseMoved = true;
                    model.updateArrayPoint(mouseStart);
                }
            }

        });

        addKeyListener(new KeyAdapter() {
        	 Integer characterWidth = 9;
             Integer lineHeight = 12;
             Integer borderBufferWidth = 15;

            @Override
            public void keyTyped(KeyEvent evt) {
                char pressed = evt.getKeyChar();
                if(hidden && startLinePoint != null
                        && startLinePoint.x + borderBufferWidth < size.width) {

                        Point newLinePoint;
                        if(startLinePoint.x + (stringLength * characterWidth) + borderBufferWidth > size.width){
                            newLinePoint = new Point(startLinePoint.x, startLinePoint.y + lineHeight);
                            startLinePoint = newLinePoint;
                            stringLength = 0;
                        }
                        if(startLinePoint.y + borderBufferWidth < size.height){
                            model.updateTextHashMap(startLinePoint,Character.toString(pressed));
                        }
                }

            }
        });

    }


    private void setImageSize() {
        size = new Dimension(image.getWidth(null), image.getHeight(null));
        if(size.width > 1366 || size.height > 768){ //myscreensize
            image = image.getScaledInstance(1366, 768, 0);
            size = new Dimension(1366,768);
        }
        setPreferredSize(size);
        setSize(size);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphic2D = (Graphics2D) g;
        super.paintComponent(graphic2D);

        if(!hidden){
            graphic2D.drawImage(image, 0, 0, this);
        }else{
        	graphic2D.drawImage(image, 0, 0, this);
            ArrayList<ArrayList<Point>> strokes = model.getStrokesArray();
            HashMap<Point, String> annotations = model.getTextHashMap();


            annotations.entrySet().forEach((entry) -> {
                Point key = entry.getKey();
                String value = entry.getValue();
                if(key.x == startLinePoint.x && key.y == startLinePoint.y){
                    stringLength = value.length();
                }
                graphic2D.drawString(value, key.x, key.y);
            });

            for (int i = 0; i < strokes.size(); i++)
            {
                for (int j = 0; j < strokes.get(i).size() - 2; j++)
                {
                    Point poin1 = strokes.get(i).get(j);
                    Point point2 = strokes.get(i).get(j + 1);
                    graphic2D.drawLine(poin1.x, poin1.y, point2.x, point2.y);
                }
            }


        }
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    private void flipPhoto(){
        hidden = !hidden;
        repaint();
    }




}
