/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author LOLO
 */
public class jugador {
   public int pos_x;
   public int pos_y;
    
    jugador(){
        this.pos_x = 12;
        this.pos_y = 12;
    }
    
    public void moverIzq(jugador p1,GraphicsContext grafico){
        this.pos_x = this.pos_x-1;
        p1.imprimirJugador(grafico);
        
    }
    
    public void moverDer(jugador p1,GraphicsContext grafico){
        this.pos_x = this.pos_x+1;
        p1.imprimirJugador(grafico);
        
    }
    
    public void moverArriba(jugador p1,GraphicsContext grafico){
        this.pos_y = this.pos_y-1;
        p1.imprimirJugador(grafico);
        
    }
    
    public void moverAbajo(jugador p1,GraphicsContext grafico){
        this.pos_y = this.pos_y+1;
        p1.imprimirJugador(grafico);
        
    }
    
    public void verificarS(int[][] collisionador){
        while(collisionador[this.pos_x][this.pos_y] == 1 ){
            this.pos_x = this.pos_x-1;
        }
    }
    
    public void imprimirJugador(GraphicsContext grafico){
        int x = this.pos_x;
        int y = this.pos_y;
        
        Image mp = new Image("graficos/secuencia/jugador1.png");
        grafico.drawImage(mp,x*16,y*16);
    }
    
}
