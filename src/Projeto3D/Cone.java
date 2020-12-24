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
public class Cone {
    
    private double colix[] = {-0.4, -0.4, 0.4, 0.4};
    private double coliz[] = {-0.4, 0.4, 0.4, -0.4};
    
    private double positions[] = new double[3];
    private double num_lines = 20;
    private double raio = 0.5f;
    private double scale = 1;
    private int tipo;
    private boolean destruido;
    
    Cone(double x, double y, double z, boolean aramado){
        
        positions[0] = x;
        positions[1] = y;
        positions[2] = z;
        destruido = false;
        this.tipo = aramado == true? GL2.GL_LINE_LOOP : GL2.GL_POLYGON;
    }
    
    void drawCone(GL2 gl, double tam, double transp){
        
        if(!destruido){
            double x = 0;
            double y = 0;
            double z = 0;
            
            float[] mat = { 1f, 1f, 1f, 1f };
            
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat, 0);
            gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat, 0);
            gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 128);

            gl.glPushMatrix();
                gl.glNormal3f(0f, 0f, 1f);
                gl.glTranslated(positions[0], positions[1], positions[2]);
                for(double j = 1; j > 0; j = j - tam){
                    y = y - tam;
                    scale = scale - j;
                    gl.glPushMatrix();
                        gl.glTranslated(0, 0.8, 0);
                        gl.glScaled(scale, 1, scale);

                        gl.glColor3d(1, 0.5, 0);
                        gl.glBegin(tipo);
                            for(int i = 0; i< num_lines; i++){ 
                                double angulo = i * 2 * Math.PI / num_lines;
                                gl.glVertex3d((x + (Math.cos(angulo) * raio)), y, (z + (Math.sin(angulo) * raio)));
                            }
                        gl.glEnd();

                        scale = 1;
                    gl.glPopMatrix();
                }

                gl.glPushMatrix();
                    //gl.glRotated(45, 0, 0, -1);
                    //gl.glRotated(PacMan.direction, 0, -1, 0);
                    gl.glColor4d(0, 1, 0, transp);
                    gl.glBegin(GL2.GL_QUADS);
                        gl.glVertex3d(colix[0], 0, coliz[0]);
                        gl.glVertex3d(colix[1], 0, coliz[1]);
                        gl.glVertex3d(colix[2], 0, coliz[2]);
                        gl.glVertex3d(colix[3], 0, coliz[3]);
                    gl.glEnd();
                gl.glPopMatrix();
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
    
    public boolean isAramado(){
        return this.tipo == GL2.GL_LINE_LOOP ? true : false;
    }
    
    public boolean isDestruido(){
        return destruido;
    }
}
