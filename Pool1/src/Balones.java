import java.awt.*;

public class Balones {
    private double Posix, Posiy,VelocidadX, VelocidadY,Diametro; 
    private Color Blanco;
    private double AceleracionX; 
    private double AceleracionY; 
    private double Friccion; 
    private double CoeficienteRestitucion;
    public Balones(double x, double y,double Diametrobalon ,double velocidadX, double velocidadY) {
        Posix = x;
        Posiy = y;
        VelocidadX = velocidadX;
        VelocidadY = velocidadY;
        Diametro = Diametrobalon;
       Blanco = new Color(255, 255, 255);
       AceleracionX = 0;
       AceleracionY = 0;
       Friccion = 0.99;
     
    }


    public void mover() {
        // mover la pelota 
    	Posix += VelocidadX;
    	Posiy += VelocidadY;
    }
    
    
    public void InvertirVelocidadX() {
    	 VelocidadX = - VelocidadX;
    }

    public void InvertirVelocidadY() {
    	VelocidadY = -VelocidadY;
    }
    
    
    public void aplicarFuerzas() {
        // aplica friccion
        VelocidadX *= Friccion;
        VelocidadY *= Friccion;

        // aplica aceleracion
        VelocidadX += AceleracionX;
        VelocidadY += AceleracionY;
    }
    public void Pintar(Graphics g) {
        // convertir las coordenadas y el diametro de tipo double a int usando casting
        int x = (int) Posix;
        int y = (int) Posiy;
        int diametro = (int) Diametro;
        g.setColor(Blanco);
        g.fillOval(x, y, diametro, diametro);
        aplicarFuerzas();
    }
    public double getCoeficienteRestitucion() {
        return CoeficienteRestitucion;
    }
    

    public double getX() {
        return Posix;
    }

    public double getY() {
        return Posiy;
    }

    public double Diametro() {
        return Diametro;
    }
    public void setVelocidadX(double velocidadX) {
        this.VelocidadX = velocidadX;
    }

   
    public void setVelocidadY(double velocidadY) {
        this.VelocidadY = velocidadY;
    }
    public double getVelocidadX() {
        return VelocidadX;
    }
   

    public double getVelocidadY() {
        return VelocidadY;
    }

    public void setX(int x) {
    	Posix = x;
    }

    public void setY(int y) {
    	Posiy = y;
    }
    public void setAceleracionX(double aceleracionX) {
        this.AceleracionX = aceleracionX;
    }

    
}