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
public class Cubo {
    double colix[] = {-0.4, -0.4, 0.4, 0.4};
    double coliz[] = {-0.4, 0.4, 0.4, -0.4};
    static float escala = 0.4f;
    double positions[] = new double[3];
    private boolean destruido;
    
    Cubo(double x, double y, double z){
        positions[0] = x;
        positions[1] = y;
        positions[2] = z;
        destruido = false;
    }
    
    public void draw(GL2 gl, double ang, double transp){
        
        if(!destruido){
            gl.glPushMatrix();
                gl.glTranslated(positions[0], positions[1], positions[2]);
                gl.glPushMatrix();
                    gl.glRotated(ang, 1, 0, 0);
                    gl.glBegin(GL2.GL_QUADS);
                        float[] mat = { 1f, 1f, 1f, 1f };

                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat, 0);
                        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat, 0);
                        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 128);

                        gl.glColor3f(1f, 0f, 0f);

                        gl.glNormal3f(0f, 0f, 1f);
                        gl.glVertex3f(1.0f*escala, 1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(1.0f*escala, 1.0f*escala, 1.0f*escala);

                        gl.glColor3f(1f, 0f, 0f);

                        gl.glNormal3f(0f, 0f, -1f);
                        gl.glVertex3f(1.0f*escala, -1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(1.0f*escala, -1.0f*escala, -1.0f*escala);

                        gl.glColor3f(1f, 0f, 0f);
                        gl.glNormal3f(-1f, 0f, 0f);

                        gl.glVertex3f(1.0f*escala, 1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(1.0f*escala, -1.0f*escala, 1.0f*escala);

                        gl.glColor3f(1f, 1f, 0f);
                        gl.glNormal3f(1f, 0f, 0f);

                        gl.glVertex3f(1.0f*escala, -1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(1.0f*escala, 1.0f*escala, -1.0f*escala);

                        gl.glColor3f(1f, 0f, 1f);
                        gl.glNormal3f(0f, 1f, 0f);

                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, 1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(-1.0f*escala, -1.0f*escala, 1.0f*escala);

                        gl.glColor3f(0f, 1f, 1f);
                        gl.glNormal3f(0f, -1f, 0f);

                        gl.glVertex3f(1.0f*escala, 1.0f*escala, -1.0f*escala);
                        gl.glVertex3f(1.0f*escala, 1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(1.0f*escala, -1.0f*escala, 1.0f*escala);
                        gl.glVertex3f(1.0f*escala, -1.0f*escala, -1.0f*escala);
                    gl.glEnd();
                gl.glPopMatrix();

                ////Colisor////
                gl.glPushMatrix();
                    gl.glColor4d(0, 1, 0, transp);
                    gl.glBegin(GL2.GL_QUADS);
                        gl.glVertex3d(colix[0], 0, coliz[0]);
                        gl.glVertex3d(colix[1], 0, coliz[1]);
                        gl.glVertex3d(colix[2], 0, coliz[2]);
                        gl.glVertex3d(colix[3], 0, coliz[3]);
                    gl.glEnd();
                gl.glPopMatrix();
                /////////////////
            gl.glPopMatrix();
        }
    }
    
    public Ponto2V[] getColPontos(){
        Ponto2V vp[] = new Ponto2V[4];
        vp[0] = new Ponto2V(colix[0] + positions[0], coliz[0] + positions[2]);
        vp[1] = new Ponto2V(colix[1] + positions[0], coliz[1] + positions[2]);
        vp[2] = new Ponto2V(colix[2] + positions[0], coliz[2] + positions[2]);
        vp[3] = new Ponto2V(colix[3] + positions[0], coliz[3] + positions[2]);
        return vp;
    }
    
    public boolean colidiu(Ponto2V[] coli1){
        
        Ponto2V[] coli2 = this.getColPontos();
        
        if(!destruido){
            for(int i = 0; i < 4; i++){
                if(coli1[0].getX() < coli2[i].getX() && coli1[3].getX() > coli2[i].getX()){
                    if(coli1[0].getZ() < coli2[i].getZ() && coli1[1].getZ() > coli2[i].getZ()){
                        destruido = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isDestruido(){
        return destruido;
    }
}
