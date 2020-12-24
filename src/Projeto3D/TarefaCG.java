/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projeto3D;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
/**
 *
 * @author Mago Negro
 */
public class TarefaCG {
    /**
     * @param args the command line arguments
     */
    
    private static GLWindow window = null;
    
    public static void init(){
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        
        window = GLWindow.create(caps);
        window.setSize(640, 640);
        window.setResizable(false);
        
        window.addGLEventListener(new EventListener());
        window.addKeyListener(new KeyInput());
        
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        
        window.setVisible(true);
        
        window.addWindowListener((WindowListener) (new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        }));
        
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        init();
        
    }
    
}
