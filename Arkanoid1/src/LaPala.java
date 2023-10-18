import java.awt.*;

public class LaPala {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color Color ;
    public LaPala(int XI, int YI, int AnchoPala, int AltoPala) {
    	Color = new Color(25,40, 52);
        x = XI; //Posicion X inicial
        y = YI;
        ancho = AnchoPala;
        alto = AltoPala;
    }

    public void MoverHaciaIzquierda() {
        if (x > 0) {
            x -= 20;
        }
    }

    public void MoverHaciaDerecha(int screenWidth) {
        if (x < screenWidth - ancho) {
            x += 20;
        }
    }

    public void Pintar(Graphics g) {
        g.setColor(Color);
        g.fillRect(x, y, ancho, alto);
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
    
    
    
    public int getLeftX() {
        return x;
    }

    public int getRightX() {
        return x + ancho;
    }
    
    
}
