/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projeto3D;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import java.awt.Event;

/**
 *
 * @author Mago Negro
 */

public class KeyInput implements KeyListener{
   
    public static final int KEY_UP = 150;
    public static final int KEY_DOWN = 152;
    public static final int KEY_RIGHT = 151;
    public static final int KEY_LEFT = 149;
    
    public static double tx = 0;
    public static double ty;
    public static double tz = 0;
    public static double direction;
    
    
    public void keyPressed(KeyEvent event) {
        switch(event.getKeyCode()){
            case KEY_UP:
                tx += -0.1;
                direction = 180;
                break;
                
            case KEY_DOWN:
                tx += 0.1;
                direction = 0;
                break;
                
            case KEY_LEFT:
                tz += 0.1;
                direction = -90;
                break;
                
            case KEY_RIGHT:
                tz += -0.1;
                direction = 90;
                break;
        }
    }
    
    public void keyReleased(KeyEvent event) {
        
    }
    
}
