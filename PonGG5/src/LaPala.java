import java.awt.*;

public class LaPala {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Color Marron;
    public LaPala(int XI, int YI, int AnchoPala, int AltoPala) {
    	Marron = new Color(53,27, 17);
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
        g.setColor(Marron);
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
    
    
    
    
}
