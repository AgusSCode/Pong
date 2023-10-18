import java.awt.*;
import javax.swing.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;


public class Balon {
    private int x;
    private int y;
    private int Diametro;
    private int XV; // velocidad X
    private int YV; // velocidad Y
    private Color Dorado;

    public Balon(int initialX, int initialY, int ballDiameter, int VelocidadXI, int VelocidadYI) { // Velocidad XI e
                                                                                                // YI es inicial
        Dorado = new Color(255, 189, 25);
        x = initialX;
        y = initialY;
        Diametro = ballDiameter;
        XV = VelocidadXI;
        YV = VelocidadYI;
    }

    public void mover() {
        x += XV; // x = x+ XV
        y += YV;
    }

    public void InvertirVelocidadX() {
        XV = -XV;
    }

    public void InvertirVelocidadY() {
        YV = -YV;
    }

    public void Pintar(Graphics g) {
        g.setColor(Dorado);
        g.fillOval(x, y, Diametro, Diametro);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int Diametro() {
        return Diametro;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void Acelerar(int increment) {
        if (XV > 0) {
            XV += increment;
        } else {
        	
            XV -= increment;
        }

        if (YV > 0) {
            YV += increment;
        } else {
            YV -= increment;
        }
    }

    public boolean ChoqueConPaddleSuperior(LaPala paddle) {
        return y + Diametro >= paddle.getY() && x >= paddle.getX() && x <= paddle.getX() + paddle.getWidth();
    }
    
    
    public boolean ChoqueConPaddle(LaPala paddle) {
        // Verificar colisión con los bordes del bloque
        boolean colisionHorizontal = (x + Diametro >= paddle.getX() && x <= paddle.getX() + paddle.getWidth());
        boolean colisionVertical = (y + Diametro >= paddle.getY() && y <= paddle.getY() + paddle.getHeight());

        // Si hay colisión en ambos ejes (horizontal y vertical), hay una colisión total
        if (colisionHorizontal && colisionVertical) {
            // Determinar desde qué dirección se produjo la colisión
            int centroY = y + Diametro / 2;
            int diferenciaY = centroY - (paddle.getY() + paddle.getHeight() / 2);

            // Invertir la velocidad Y basándose en la posición relativa de la pelota respecto al centro del paddle
            // Si la diferencia en el eje Y es positiva, la pelota golpeó la parte inferior del paddle y debe ir hacia abajo
            // Si la diferencia en el eje Y es negativa, la pelota golpeó la parte superior del paddle y debe ir hacia arriba
            if (diferenciaY > 0) {
                YV = Math.abs(YV); // Invertir velocidad hacia abajo
            } else {
                YV = -Math.abs(YV); // Invertir velocidad hacia arriba
            }

            // Indicar que hubo una colisión
            return true;
        }

        // No hay colisión
        return false;
    }


    
   
    public boolean ChoqueConBloque(Bloque bloque) {
        // Verificar colisión con los bordes del bloque
        boolean colisionHorizontal = (x + Diametro >= bloque.getX() && x <= bloque.getX() + bloque.getWidth());
        boolean colisionVertical = (y + Diametro >= bloque.getY() && y <= bloque.getY() + bloque.getHeight());
        if (bloque.isEliminado()) {
            return false;
        }
        // Si hay colisión en ambos ejes (horizontal y vertical), hay una colisión total
        if (colisionHorizontal && colisionVertical) {
            // Determinar desde qué dirección se produjo la colisión
            int centroY = y + Diametro / 2;
            int diferenciaY = centroY - (bloque.getY() + bloque.getHeight() / 2);

            // Invertir la velocidad Y basándose en la posición relativa de la pelota respecto al centro del paddle
            // Si la diferencia en el eje Y es positiva, la pelota golpeó la parte inferior del paddle y debe ir hacia abajo
            // Si la diferencia en el eje Y es negativa, la pelota golpeó la parte superior del paddle y debe ir hacia arriba
            if (diferenciaY > 0) {
                YV = Math.abs(YV); // Invertir velocidad hacia abajo
            } else {
                YV = -Math.abs(YV); // Invertir velocidad hacia arriba
            }

            // Indicar que hubo una colisión
            return true;
        }

        // No hay colisión
        return false;
    }
    
    public boolean ChoqueConBloques(Bloque[] bloques) {
        for (Bloque bloque : bloques) {
            if (bloque != null && ChoqueConBloque(bloque)) {
                // Si hay colisión con un bloque, retornar true
            	 bloque.setEliminado(true);
                return true;
                
            }
        }
        // No hay colisión con ningún bloque
        return false;
    }
    

}
    

