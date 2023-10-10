import java.awt.*;

public class LaPala {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color Marron;
    public LaPala(int XI, int YI, int AnchoPala, int AltoPala) {
    	Marron = new Color(53,27, 17);
        x = XI; //Posicion X inicial
        y = YI;
        width = AnchoPala;
        height = AltoPala;
    }

    public void moveLeft() {
        if (x > 0) {
            x -= 20;
        }
    }

    public void moveRight(int screenWidth) {
        if (x < screenWidth - width) {
            x += 20;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Marron);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    
    
    
    
}