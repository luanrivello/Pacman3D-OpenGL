/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projeto3D;

import com.jogamp.graph.geom.Vertex;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import java.awt.Dimension;
import java.nio.DoubleBuffer;

import sun.audio.*;
import  java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class EventListener implements GLEventListener{
    
    private GLU glu = new GLU();
    private float posicaoZ = -15.0f;
    private float posicaoX = 0f;
    private float posicaoY = 0f;
    private boolean a = false;
    private boolean b = false;
    private float rotation;
    
    //float escala = 0.4f;
    int ang = 0;
    double div = 3.2;
    double transpColisors = 0;
    
    //Objetos
    PacMan pac = new PacMan();
    Cubo c1 = new Cubo(-3, 0, 5);
    Cubo c2 = new Cubo(3, 0, -2);
    Cubo c3 = new Cubo(-3, 0, 3);
    Cubo c4 = new Cubo(3, 0, -6);
    Cone co1 = new Cone(2, 0, 2, true);
    Cone co2 = new Cone(-2, 0, -2, false);
    Cone co3 = new Cone(4, 0, 4, true);
    Cone co4 = new Cone(4, 0, -4, false);

    long startTime = System.currentTimeMillis();
    int frames = 0;
    
    MediaPlayer as;
    private final AudioClip CAT = new AudioClip(this.getClass().getResource("/Cat_flashbang.mp3").toString());
    private final AudioClip MarioGrande = new AudioClip(this.getClass().getResource("/MarioGrande.mp3").toString());
    private final AudioClip MarioGrito = new AudioClip(this.getClass().getResource("/MarioGrito.mp3").toString());
    private final AudioClip MarioMamamia = new AudioClip(this.getClass().getResource("/MamaMia.mp3").toString());
    private final AudioClip MusicaFundo = new AudioClip(this.getClass().getResource("/MusicaFundo.mp3").toString());
    
    public EventListener() {
        
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        
        long currentTime = System.currentTimeMillis();
        //System.out.println(currentTime);
        
        if(!MusicaFundo.isPlaying()){
           MusicaFundo.play(0.2); 
        }
        
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable( GL2.GL_BLEND );
        
        
        gl.glTranslated(0, 0, -10);
        gl.glRotated(-90, 0, 1, 0);
        gl.glRotated(-45, 0, 0, 1);
        ang++;
        
        ///////////////Desenha Pac/////////////////
        div = (div + 0.1) % 1;
        pac.draw(gl, 50, 50, (3.2 + div)/4, transpColisors);
        ////////////////////////////////
        
        c1.draw(gl, ang, transpColisors);
        c2.draw(gl, ang, transpColisors);
        c3.draw(gl, ang, transpColisors);
        c4.draw(gl, ang, transpColisors);
        co1.drawCone(gl, 0.1f, transpColisors);
        co2.drawCone(gl, 0.01f, transpColisors);
        co3.drawCone(gl, 0.1f, transpColisors);
        co4.drawCone(gl, 0.01f, transpColisors);
        
        VerificaColisoes();
        
        frames++;
        
        if(currentTime - startTime > 1000){
            System.out.println("frames: " + frames);
            frames = 0;
            startTime = currentTime;
        }
        
    }
    
    @Override
    public void dispose(GLAutoDrawable drawable) {
            // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {
            final GL2 gl = drawable.getGL().getGL2();
            gl.glShadeModel(GL2.GL_SMOOTH);
            gl.glClearColor(0f, 0f, 0f, 0f);
            gl.glClearDepth(1f);
            gl.glEnable(GL2.GL_DEPTH_TEST);

            float[] luz = { 0.2f, 0.2f, 0.2f, 1f };
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(GL2.GL_LIGHT0);
            gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, luz, 0);
            float[] luz1 = { 0.1f, 0.1f, 0.1f, 1f };
            float[] luz2 = { 0.8f, 0.8f, 0.8f, 1f };
            float[] luz3 = { 1f, 1f, 1f, 1f };
            float[] luz4 = { 0f, 0f, 1f, 1f };
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luz1, 0);
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, luz2, 0);
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, luz3, 0);
            gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, luz4, 0);

            gl.glEnable(GL2.GL_COLOR_MATERIAL);

            gl.glDepthFunc(GL2.GL_LEQUAL);
            gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            final GL2 gl = drawable.getGL().getGL2();
            if (height <= 0)
                    height = 1;
            final float h = (float) width / (float) height;
            gl.glViewport(0, 0, width, height);
            gl.glMatrixMode(GL2.GL_PROJECTION);
            gl.glLoadIdentity();
            glu.gluPerspective(100.0f, h, 1, 60.0);
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();
    }

    public void VerificaColisoes() {
        if(c1.colidiu(pac.getColPontos())&& c1.isDestruido()){
            pac.activeAnim();
            CAT.play();
        }
        if(c2.colidiu(pac.getColPontos())&& c2.isDestruido()){
            pac.activeAnim();
            CAT.play();
        }
        if(c3.colidiu(pac.getColPontos())&& c3.isDestruido()){
            pac.activeAnim();
            MarioGrito.play(0.5);;
        }
        if(c4.colidiu(pac.getColPontos())&& c4.isDestruido()){
            pac.activeAnim();
            MarioGrito.play(0.5);
        }
        if(co1.colidiu(pac.getColPontos()) && co1.isDestruido()){
            PacMan.scale = PacMan.scale + (co1.isAramado() ? -0.25 : 0.25);
            if(co1.isAramado()){
                MarioMamamia.play();
            }else{
                MarioGrande.play();
            }
        }
        if(co2.colidiu(pac.getColPontos())&& co2.isDestruido()){
            PacMan.scale = PacMan.scale + (co2.isAramado() ? -0.25 : 0.25);
            if(co2.isAramado()){
                MarioMamamia.play();
            }else{
                MarioGrande.play();
            }
        }
        if(co3.colidiu(pac.getColPontos())&& co3.isDestruido()){
            PacMan.scale = PacMan.scale + (co1.isAramado() ? -0.25 : 0.25);
            if(co3.isAramado()){
                MarioMamamia.play();
            }else{
                MarioGrande.play();
            }
        }
        if(co4.colidiu(pac.getColPontos())&& co4.isDestruido()){
            PacMan.scale = PacMan.scale + (co2.isAramado() ? -0.25 : 0.25);
            if(co4.isAramado()){
                MarioMamamia.play();
            }else{
                MarioGrande.play();
            }
        }
    }
}
