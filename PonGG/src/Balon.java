
import java.awt.*;

public class Balon {
    private int x;
    private int y;
    private int Diametro;
    private int XV; //velocidad X
    private int YV; //velocidad Y

    public Balon(int initialX, int initialY, int ballDiameter, int VelocidadXI, int VelocidadYI) { //Velocidad XI e YI es inicial
        x = initialX;
        y = initialY;
        Diametro = ballDiameter;
        XV = VelocidadXI;
        YV = VelocidadYI;
    }

    public void move() {
        x += XV; //x = x+ XV
        y += YV;
    }

    public void InvertirVelocidadX() {
    	XV = -XV;
    }

    public void InvertirVelocidadY() {
    	YV = -YV;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Diametro, Diametro);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return Diametro;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
