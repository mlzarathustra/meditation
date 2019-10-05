package ui

import java.awt.*

public class ScreensaveStopper {

    public static void main(String[] args) throws Exception{
        System.out.println("screensave stopper");
        Robot hal = new Robot();
        while(true){
            hal.delay(1000 * 60);
            Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
            hal.mouseMove(mouseLoc.x, mouseLoc.y);
        }
    }

}