/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projeto3D;

import com.jogamp.opengl.GL2;

/**
 *
 * @author Mago Negro
 */
public class PacMan {
    
    public static double scale = 1;
    //Posição inicial dos coliders//
    double colix[] = {-0.8, -0.8, 0.8, 0.8};
    double coliz[] = {-0.8, 0.8, 0.8, -0.8};
    /////////////////////////////////
    double ang = 0;
    int Startframes = 90;
    int frames = Startframes;
    boolean doAnimation = false;
    
    
    public static double tx;
    public static double ty;
    public static double tz;
    public static double direction;
    
    double colors[] = new double[3];
    
    PacMan(){
        colors[0] = 0.7;
        colors[1] = 0.7;
        colors[2] = 0;
    }
    
    public void draw(GL2 gl, int lats, int longs, double div, double transp){
    
        gl.glPushMatrix();
            if(!doAnimation){
                tx = KeyInput.tx;
                ty = KeyInput.ty;
                tz = KeyInput.tz;
                direction = KeyInput.direction;
            }else{
                KeyInput.tx = tx;
                KeyInput.ty = ty;
                KeyInput.tz = tz;
                direction = 0;
            }
            
            gl.glTranslated(tx, ty, tz);
            gl.glRotated(direction, 0, 1, 0);
            gl.glScaled(scale, scale, scale);
            gl.glRotated(45, 0, 0, 1);
            
            if(doAnimation && frames > 0){
                animation(gl);
            }else{
                doAnimation = false;
            }
            
            ////////////// PacMan //////////////////
            gl.glColor3d(colors[0], colors[1], colors[2]);
            for(int i = 0; i <= lats; i++) {
                double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
                double z0  = Math.sin(lat0);
                double zr0 =  Math.cos(lat0);

                double lat1 = Math.PI * (-0.5 + (double) i / lats);
                double z1 = Math.sin(lat1);
                double zr1 = Math.cos(lat1);

                gl.glBegin(gl.GL_QUAD_STRIP);
                    for(int j = 0; j <= longs*div; j++) {
                        double lng = 2 * Math.PI * (double) (j - 1) / longs;
                        double x = Math.cos(lng);
                        double y = Math.sin(lng);

                        gl.glNormal3d(x * zr0, y * zr0, z0);
                        gl.glVertex3d(x * zr0, y * zr0, z0);
                        gl.glNormal3d(x * zr1, y * zr1, z1);
                        gl.glVertex3d(x * zr1, y * zr1, z1);
                    }
                gl.glEnd();
            }
            ////////////////////////////////
            
            ////////// Colisor /////////////
            gl.glPushMatrix();
                gl.glRotated(45, 0, 0, -1);
                gl.glRotated(PacMan.direction, 0, -1, 0);
                gl.glColor4d(0, 1, 0, transp);
                gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex3d(colix[0], 0, coliz[0]);
                    gl.glVertex3d(colix[1], 0, coliz[1]);
                    gl.glVertex3d(colix[2], 0, coliz[2]);
                    gl.glVertex3d(colix[3], 0, coliz[3]);
                gl.glEnd();
            gl.glPopMatrix();
            ////////////////////////////////
        gl.glPopMatrix();
    }
    
    public Ponto2V[] getColPontos(){
        Ponto2V vp[] = new Ponto2V[4];
        vp[0] = new Ponto2V(colix[0]*scale + KeyInput.tx, coliz[0]*scale + KeyInput.tz);
        vp[1] = new Ponto2V(colix[1]*scale + KeyInput.tx, coliz[1]*scale + KeyInput.tz);
        vp[2] = new Ponto2V(colix[2]*scale + KeyInput.tx, coliz[2]*scale + KeyInput.tz);
        vp[3] = new Ponto2V(colix[3]*scale + KeyInput.tx, coliz[3]*scale + KeyInput.tz);
        return vp;
    }
    
    public void activeAnim(){
        doAnimation = true;
        frames = Startframes;
        ang = 0;
        colors[0] = 0.25;
        colors[1] = 0.25;
        colors[2] = 0.45;
    }
    
    private void animation(GL2 gl){
        gl.glRotated(ang, 0, 0, 1);
        ang = ang + 4;
        frames--;
        colors[0] = colors[0] + 0.005;
        colors[1] = colors[1] + 0.005;
        colors[2] = colors[2] - 0.005;
    }
}
