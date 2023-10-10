import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jogo extends JPanel implements ActionListener, KeyListener {
    private Balon ball;
    private LaPala paddle;
    private int score;
    private static int finalScore;
    private int TiempoTranscurridoSegundos = 0;
    private long startTime;
    private static boolean JuegoTermina = false;
    private static Timer timer;
    private int vidas= 3;
    private Image fondoImage;
    

    public Jogo() {
        ball = new Balon(400, 300, 15, 2, 2);
        paddle = new LaPala(350, 555, 100, 15);
        score = 0;
        startTime = System.currentTimeMillis();

        timer = new Timer(1, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        ImageIcon img = new ImageIcon("img/Cielo.jpg");
        Image Scaledimg = img.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);  
        ImageIcon ScaledimgToIcon = new ImageIcon(Scaledimg); 
         fondoImage = ScaledimgToIcon.getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(fondoImage, 0, 0, getWidth(), getHeight(), this);
        
        ball.draw(g);
        paddle.draw(g);

        g.setColor(Color.white);
        g.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g.drawString("Score: " + score, 715, 25);

        int minutes = TiempoTranscurridoSegundos / 60;
        int seconds = TiempoTranscurridoSegundos % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        g.drawString("Tiempo: " + timeString, 15, 25);

        String vidasString = String.format("%d", vidas);
        g.drawString("Vidas: " + vidasString, 715, 45);
        
        if (JuegoTermina && vidas== 0) {
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.white);
            String mensaje = "Final Score: " + finalScore ;
            g.drawString(mensaje, 325, 300);
            
        }
    }


    public void actionPerformed(ActionEvent e) {
     
            ball.move();

            if (ball.getX() <= 0 || ball.getX() >= getWidth() - ball.getDiameter()) {
                ball.InvertirVelocidadX();
            }

            if (ball.getY() <= 0 || ball.getY() >= getHeight() - ball.getDiameter()) {
                ball.InvertirVelocidadY();
            }

            if (ball.getY() >= 600 - ball.getDiameter()) {
            	vidas = vidas -1;
            	ball.setX(400);
                ball.setY(300);
              
            	if(vidas ==0) {
              JuegoTermina = true;
                finalScore = (int) (score * (TiempoTranscurridoSegundos/5));
                System.out.println("Juego en pausa. Final Score: " + finalScore);
                timer.stop();}
            }

            if (ball.getY() >= paddle.getY() - ball.getDiameter() && ball.getX() >= paddle.getX()
                    && ball.getX() <= paddle.getX() + paddle.getWidth()) {
                ball.InvertirVelocidadY();
                score++;
            }

            long currentTime = System.currentTimeMillis();
            long elapsedTimeInMillis = currentTime - startTime;
            TiempoTranscurridoSegundos = (int) (elapsedTimeInMillis / 1000);
       // }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.moveLeft();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.moveRight(getWidth());
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
    	

        JFrame FramePrincipal = new JFrame("Pongi - Pingi");
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