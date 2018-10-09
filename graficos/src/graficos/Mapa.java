package graficos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * Clase que modela a un mapa
 * @author ¿?
 */
public class Mapa {
	/**
	 *  cuad es una matriz que representa el contenido de un mapa dela siguiente
	 * manera:
	 * 0 = vacio
	 * 1 = suelo
	 * 2 = pared
	 * 3 = tesoro
	 * 4 = polvo1
	 * 5 = polvo2
	 */
    public int cuad [][];
    /**
     * 	cuadC es una matriz que representa en donde se puede estar en un mapa de la
     * siguiente manera:
     * 0 = se puede estar
     * 1 = no se puede estar (hay pared)
     */
    public int cuadC [][];
  
    public int filas = 31;
    public int columnas = 31;
    public int limiteN = 4; // Limite de nacimiento
    public int limiteM = 3; // Limite de muerte
    
    /**
     * Constructor de la clase vacio
     */
    Mapa(){
    }
    
    /**
     * getter para el atributo cuadC @see cuadC
     * No se uso
     */
    public int[][] getCuad() {
        return cuad;
    }

    /**
     * setter para el atributo cuadC @see cuadC
     * No se uso
     */
    public void setCuad(int[][] cuad) {
        this.cuad = cuad;
    }
    
    /**
     * setter para el atributo cuadC @see cuadC
     */
    public void setCuad(){
        int[][] multi = new int[][]{
          { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
          { 0, 2, 2, 1, 1, 1, 1, 1, 0, 0 },
          { 0, 0, 0, 1, 1, 0, 0, 1, 0, 0 },
          { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
          { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }
        };
        this.cuad = multi;
    }
    
    /**
     * Metodo que crea una nueva cueva (en cuad)
     */
    public void generarCueva(){
        Random rand = new Random();
        int n = 0; /// Nunca se uso en esta funcion
        int[][] multi = new int[this.filas][this.columnas]; // Creando nueva matriz
        int prob1 = 20; // Se establece un numero para una probabilidad
    	
    	// Recorremos toda la matriz...
        for(int x=0; x<this.filas; x++){ 
            for(int y=0; y<this.columnas; y++){
            	// Genera un nuevo numero aleatorio
                if(rand.nextInt(100) < prob1) 
                    multi[x][y] = 2; //Si es menor que prob1, poner pared
                else{
                    multi[x][y] = 1; //De lo contrario, pon suelo
                }
                
            }
        }
        this.cuad = multi; //Actualizamos cuad   
    }

    /**
     * Basado en el juego de la vida, este metodo cuenta los vecinos "vivos" de una celda (de cuad)
     * @param map el mapa (cuad) que se esta usando
     * @param x la coordenada en x de la celda que se esta realizando el conteo
     * @param y la coordenada en y de la celda que se esta realizando el conteo
     * @return cuenta el numero de vecinos "vivos" (celdas donde hay pared) de la celda (x,y)
     */
	public int contarVecinosVivos(int[][] map, int x, int y){
	    int cuenta = 0; //Inicializamos conteo
	    //Recorremos toda la matriz
	    for(int i=-1; i<2; i++){
	        for(int j=-1; j<2; j++){
	            int vecino_x = x+i;
	            int vecino_y = y+j;

	            if(i == 0 && j == 0){
	            	/* Si caemos aqui, es porque el metodo se encuentra en la celda de interes,
	            		por obvias razones no se realiza nada, simplemente se omite */
	            }
	            
	            // De lo contrario, por medio de condicionales revisar si hay pared 
	            else if(vecino_x < 0 || vecino_y < 0 || 
	            	vecino_x >= map.length || vecino_y >= map[0].length){
	                cuenta = cuenta + 1;
	            }
	            else if(map[vecino_x][vecino_y] == 2){
	                cuenta = cuenta + 1;
	            }
	        }
	    }
	    return cuenta;
	}

	/**
	 * Basado en el juego de la vida, este es el metodo que crea el nuevo mapa de una forma mas organica.
	 * @param vMap el mapa "viejo" que sera modificado
	 * @return nMap el nuevo mapa
	 */
    public int[][] simularPaso(int[][] vMap){
        int[][] nMap = new int[this.filas][this.columnas]; // Nuevo mapa (matriz)
        //Recorremos toda la matriz...
        for(int x=0; x<vMap.length; x++){
            for(int y=0; y<vMap[0].length; y++){
                int nbs = contarVecinosVivos(vMap, x, y); // nbs (nuevos) guarda donde hay pared
                if(vMap[x][y] == 2){
                	// La celda actual es una pared
                    if(nbs < limiteM){ // Si los vecinos no exceden el limite de muerte
                        nMap[x][y] = 1; // Quita la pared (la celda muere por infrapoblacion)
                    }
                    else{
                        nMap[x][y] = 2; //De lo contrario, no hagas nada
                    }
                } 
                else{
                	// La celda actual no es pared
                    if(nbs > limiteN){ //Si los vecinos exceden el limite de nacimiento
                        nMap[x][y] = 2; //Pon pared (la celda revive)
                    }
                    else{
                        nMap[x][y] = 1; //De lo contrario, no hagas nada
                    }
                }
            }
        }
      return nMap;
	}
    
    
    /**
     * Metodo que permite adornar el mapa con polvo
     * @param map el mapa que sera adornado
     */
	public void ponerPolvo(int[][] map){
	    Random rand = new Random();
	    int piso = 1;
	    for (int x=0; x < this.filas; x++){
	        for (int y=0; y < this.columnas; y++){
	        if(map[x][y] == 1){
	             piso = rand.nextInt(100);  
	            if(piso < 100)
	                 this.cuad[x][y] = 1;
	            if(piso < 20)
	                 this.cuad[x][y] = 5;
	             if(piso < 10)
	                 this.cuad[x][y] = 4;
	            }
	        }
	    }
	}

	/**
	 * Metodo que coloca un cofre en el mapa
	 * @param mapa el mapa donde se colocara el cofre
	 */
	public void ponerTesoro(int[][] mapa){
	    int tesoroescondido = 5; // Que tan facil es que aparezca un cofre (5 o 6 es bueno)
	    // Se recorre la matriz...
	    for (int x=0; x < this.filas; x++){
	        for (int y=0; y < this.columnas; y++){
	            if(mapa[x][y] == 1 || mapa[x][y] == 4 || mapa[x][y] == 5){
	            	//En donde estamos no hay pared
	                int nbs = contarVecinosVivos(mapa, x, y); //Se cuentan paredes (vecinos vivos)
	                if(nbs >= tesoroescondido){ //Si es mayor que el valor establecido, se coloca
	                    this.cuad[x][y] = 3;
	                }
	            }
	        }
	    }
	}

	/**
	 * Metodo que actualiza la matriz de colisiones, es decir, en donde el jugador
	 * puede pasar o no.
	 * @param mapa el mapa (cuad) donde se hara el analisis de colisiones
	 */
	public void generarColisiones(int [][] mapa){
	    int[][] multi = new int[this.filas][this.columnas]; // Nueva matriz
	    // Se recorre la matriz...
	    for (int x=0; x < this.filas; x++){
	        for (int y=0; y < this.columnas; y++){
	            if(mapa[x][y] == 2 || mapa[x][y] == 3){
	            	//La celda es pared o cofre, el jugador no puede pasar por ahi
	                multi[x][y] = 1;
	            }else{
	            	//La celda es suelo o polvo, se puede pasar
	                multi[x][y] = 0;
	            }
	        }
	    }
	    this.cuadC = multi;
	}
   /**
    * Metodo que imprime en pantalla el mapa (cuad)
    * @param cuad el mapa que se imprime
    * @param grafico objeto para la impresion de imagenes
    *
    * Notas:
    * Se multiplica por 16 porque se estan manejando sprites de 16 bits
    */
   public void imprimirCuad(int cuad[][],GraphicsContext grafico){
        Image m1 = new Image("graficos/secuencia/tile2.png");
        Image m2 = new Image("graficos/secuencia/tile3.png");
        Image m3 = new Image("graficos/secuencia/tiletesoro.png");
        Image m4 = new Image("graficos/secuencia/tile4.png");
        Image m5 = new Image("graficos/secuencia/tile5.png");
        for(int x=0;x<this.filas;x++){
            for(int y=0;y<this.columnas;y++){
                if(cuad[x][y] == 1)
                    grafico.drawImage(m1,x*16,y*16);
                if(cuad[x][y] == 2)
                    grafico.drawImage(m2,x*16,y*16);
                if(cuad[x][y] == 3)
                    grafico.drawImage(m3,x*16,y*16);
                if(cuad[x][y] == 4)
                    grafico.drawImage(m4,x*16,y*16);
                if(cuad[x][y] == 5)
                    grafico.drawImage(m5,x*16,y*16);
            }
        }
        
   }    
}
