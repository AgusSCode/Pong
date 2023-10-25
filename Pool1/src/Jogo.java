import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class Jogo extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 9220938582858189572L;

	private Balones ball;

	private Balones[] pelotas;
	 private Image fondoImage;


    private double angulo; // angulo para la posición del cursor alrededor de la pelota
    private int radio; // Radio de la pelota blanca
    public Jogo() {
    	
       
       pelotas = new Balones[16]; 
       ball = new Balones(400, 300, 15, 0, 0);    
       pelotas[0] = new Balones(200, 300, 15, 0, 0); 
       radio = (int) (pelotas[0].Diametro() / 2);
       angulo = 0;
       calcularPosicionCursor();
       
       int diametro = 15; 
       int numFilasTriangulo = 5; 
       int posXInicialTriangulo = 600; 
       int posYInicialTriangulo = 300; 
       int espacioTriangulo = 15; // Espacio entre las pelotas
       int pelotaIndexTriangulo = 1; // empieza desde la 1 porque la 0 ya esta creada

       //  filas del triángulo
       for (int fila = 0; fila < numFilasTriangulo; fila++) {
           // Calcular la  Y de la fila 
           int posX = posXInicialTriangulo + (fila * (diametro + espacioTriangulo));

           // Calcular la  X inicial de la fila 
           int posYInicialFila = posYInicialTriangulo - (fila * (diametro / 2 + espacioTriangulo / 2));

           //  pelotas fila
           for (int columna = 0; columna <= fila; columna++) {
               // Calcular la  X de la pelota 
               int posY = posYInicialFila + (columna * (diametro + espacioTriangulo));

               // Crear la pelota 
               pelotas[pelotaIndexTriangulo] = new Balones(posX, posY, diametro, 0, 0);

               
               pelotaIndexTriangulo++;
           }
       }

       ImageIcon img = new ImageIcon("media/Fondo.png");
       Image Scaledimg = img.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
       ImageIcon ScaledimgToIcon = new ImageIcon(Scaledimg);
       fondoImage = ScaledimgToIcon.getImage();
        
       Timer timer = new Timer(1, this); // 1 milisegundos de intervalo
       timer.start();
       addKeyListener(this);
       setFocusable(true);
            }
            	
    
    private Point calcularPosicionCursor() {
        // calcula posición del cursor segun el ángulo y el radio de la pelota blanca
        int centroX = (int) (pelotas[0].getX() + radio); 
        int centroY = (int) (pelotas[0].getY() + radio); 
        int cursorX = centroX + (int) (radio * Math.cos(angulo));
        int cursorY = centroY + (int) (radio * Math.sin(angulo));
        return new Point(cursorX, cursorY);
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(fondoImage, 0, 0, getWidth(), getHeight(), this);
      
       for (Balones pelota : pelotas) {
            pelota.Pintar(g);
        }
          
   
       g.setColor(Color.RED);
       Point cursorPos = calcularPosicionCursor();
       int radioPunto = 5; // Radio del punto rojo
       g.fillOval(cursorPos.x - radioPunto / 2, cursorPos.y - radioPunto / 2, radioPunto, radioPunto);
   }
       
        

    
    public void actionPerformed(ActionEvent e) {

    	
    	ball.mover();
        Colisiones.manejarColisionParedesLaterales(ball, getWidth());
        Colisiones.manejarColisionTecho(ball, getHeight());
        Colisiones.manejarColisionPiso(ball, getHeight());

        for (int i = 0; i < pelotas.length; i++) {
            pelotas[i].mover();
            Colisiones.manejarColisionParedesLaterales(pelotas[i], getWidth());
            Colisiones.manejarColisionTecho(pelotas[i], getHeight());
            Colisiones.manejarColisionPiso(pelotas[i], getHeight());
        }
        
        Colisiones.manejarColisionEntrePelotas(pelotas);
      
        calcularPosicionCursor();
        repaint();
        
        
    }
    
  
 

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            angulo -= 0.1;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            angulo += 0.1;
        } else if (keyCode == KeyEvent.VK_ENTER) {
            // calcula  ángulo de impacto por el punto rojo (cursor)
            double anguloImpacto = calcularAnguloHaciaCursor();
            
            // calcula nueva velocidad de la pelota blanca 
            double nuevaVelocidad = 5; 
            double nuevaVelocidadX = nuevaVelocidad * Math.cos(anguloImpacto);
            double nuevaVelocidadY = nuevaVelocidad * Math.sin(anguloImpacto);
            
          
            pelotas[0].setVelocidadX(nuevaVelocidadX);
            pelotas[0].setVelocidadY(nuevaVelocidadY);
        }
        calcularPosicionCursor();
        repaint();
    }
    
    private double calcularAnguloHaciaCursor() {
        int centroX = (int) (pelotas[0].getX() + radio);
        int centroY = (int) (pelotas[0].getY() + radio); 
        int cursorX = (int) (pelotas[0].getX() + radio + radio * Math.cos(angulo)); 
        int cursorY = (int) (pelotas[0].getY() + radio + radio * Math.sin(angulo)); 
        return Math.atan2(cursorY - centroY, cursorX - centroX);
    }
    
  
    
    public void keyReleased(KeyEvent e) {
        
    	
    }
public void keyTyped(KeyEvent e) {
		
		
	}
   
    public static void main(String[] args) {
    	
        JFrame FramePrincipal = new JFrame("Pool manito");
        Jogo game = new Jogo();
        FramePrincipal.getContentPane().add(game);
        FramePrincipal.setSize(800, 600);
        FramePrincipal.setResizable(false);
        FramePrincipal.setLocationRelativeTo(null);
        FramePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FramePrincipal.setUndecorated(true);
     

        FramePrincipal.setVisible(true);
        FramePrincipal.setLayout(null);
    }















	
	
}




