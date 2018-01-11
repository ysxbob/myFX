package org.fx.utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MouseUtils {
    public static Robot robot;

    static {
        try {
            robot = new Robot();
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    public static void setRobot(Robot robot){
        robot = robot;
    }
    public static void clickTarget(int x,int y){
        robot.mouseMove(x,y);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }

   public static void clikTarget(int x,int y,int numer){
       for (int i = 0; i < numer; i++) {
           clickTarget(x,y);
           try{
               if(i+1<numer){
                   Thread.sleep(5);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }
   }


}
