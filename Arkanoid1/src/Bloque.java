import java.awt.Color;
import java.awt.Graphics;


public class Bloque {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color Color;



    public Bloque(int X, int Y, int AnchoBloque, int AltoBloque) {
    
        x = X; 
        y = Y;
        ancho = AnchoBloque;
        alto = AltoBloque;
        eliminado = false;
        Color = java.awt.Color.WHITE;

    }

    
 

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return ancho;
    }
    
    public int getHeight() {
        return alto;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    
    
    private boolean eliminado;

   
    public boolean isEliminado() { // devuelve true or false dependiendo si hubo colision o no
        return eliminado;
    }

    public void setEliminado(boolean eliminado) { // devuelve el booleano eliminado, luego en la clase balon se pone como true. Es decir, se elimino un bloque
        this.eliminado = eliminado;
    }
    
    
    
    
    
    public void setColor(Color color) {
        Color = color;
    }

    
    public void Pintar(Graphics g) {
        // Dibujar el bloque con el color base     
        g.setColor(Color);
        g.fillRect(x, y, ancho, alto);

        // Agregar un destello en la esquina superior izquierda (gradiente radial blanco-transparente)
        int radio = 10; // Tamaño del destello
        int transparencia = 4; // Nivel de transparencia del destello (0: totalmente transparente, 255: totalmente opaco)
        Color colorDestello = new Color(255, 255, 255, transparencia);
       
        // Dibujar el gradiente radial (destello) en la esquina superior izquierda
        for (int i = 0; i <= radio; i++) {
            int alpha = transparencia - i * (transparencia / radio);
            Color colorGradiente = new Color(colorDestello.getRed(), colorDestello.getGreen(), colorDestello.getBlue(), alpha);
            g.setColor(colorGradiente);
            g.fillRoundRect(x - i, y - i, ancho + i * 2, alto + i * 2, ancho, alto);
           
        }
       
    }
    

    
    
}