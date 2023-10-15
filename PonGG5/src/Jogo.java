import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Jogo extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 2514333446986159279L;

	private Balon ball;
    private LaPala paddle;
    private int score;
    private static int finalScore;
    private int TiempoTranscurridoSegundos = 0;
    private long startTime;
    private static boolean JuegoTermina = false;
    private static Timer timer;
    private int vidas = 3;
    private Image fondoImage;
    private static int Incremento = 0;
    private static final int IntervaloIncremento = 5; // Incrementar velocidad cada 5 segundos
    private int tiempoUltimoIncremento = 0;
    private int NumeroRandom;
    private Clip Choque;
    private Clip Muerte;
    private Clip Rebote;
    private Clip triste;
    private Clip MusicaFondo;
    
    
    public Jogo() {
        ball = new Balon(400, 300, 15, 2, 2);
        paddle = new LaPala(350, 555, 100, 15);
        score = 0;
        startTime = System.currentTimeMillis();
        
        JOptionPane.showMessageDialog(null, "Bienvenido a Pong\nControles:\nFlecha Izquierda ← \nFlecha Derecha → ");
        
        timer = new Timer(1, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        ImageIcon img = new ImageIcon("media/Cielo.jpg");
        Image Scaledimg = img.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon ScaledimgToIcon = new ImageIcon(Scaledimg);
        fondoImage = ScaledimgToIcon.getImage();
        
        Random random = new Random();
        NumeroRandom = random.nextInt(4);
        
       
       
    
        
        try {
        	
            File ArchivoSonidoChoque = new File("media/caida.wav");
            File ArchivoSonidoMuerte = new File("media/oof.wav");
            File ArchivoSonidoRebotePared = new File("media/rebote.wav");
            File ArchivoSonidoTriste = new File ("media/triste.wav");
            File ArchivoSonidoMusicaFondo = new File("media/"+NumeroRandom+".wav");
            
            
            AudioInputStream SonidoChoque = AudioSystem.getAudioInputStream(ArchivoSonidoChoque);
            AudioInputStream SonidoMuerte = AudioSystem.getAudioInputStream(ArchivoSonidoMuerte);
            AudioInputStream SonidoRebotePared = AudioSystem.getAudioInputStream(ArchivoSonidoRebotePared);
            AudioInputStream SonidoTriste = AudioSystem.getAudioInputStream(ArchivoSonidoTriste);
            AudioInputStream SonidoMusicaFondo = AudioSystem.getAudioInputStream(ArchivoSonidoMusicaFondo);
            
            
            Choque = AudioSystem.getClip();
            Choque.open(SonidoChoque);

            Muerte = AudioSystem.getClip();
            Muerte.open(SonidoMuerte);
            
            Rebote = AudioSystem.getClip();
            Rebote.open(SonidoRebotePared);
            
            triste = AudioSystem.getClip();
            triste.open(SonidoTriste);
            
            MusicaFondo = AudioSystem.getClip();
            MusicaFondo.open(SonidoMusicaFondo);
            MusicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); 
        }
        
        
        

        
    }
    
    
    

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(fondoImage, 0, 0, getWidth(), getHeight(), this);

        ball.Pintar(g);
        paddle.Pintar(g);

        g.setColor(Color.white);
        g.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g.drawString("Score: " + score, 715, 25);

        int minutos = TiempoTranscurridoSegundos / 60;
        int segundos = TiempoTranscurridoSegundos % 60;
        String TiempoString = String.format("%02d:%02d", minutos, segundos);
        g.drawString("Time: " + TiempoString, 15, 25);

        String VidasString = String.format("%d", vidas);
        g.drawString("Lives: " + VidasString, 715, 45);

        if (JuegoTermina && vidas == 0) {
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.white);
            String FinalScoreString = "Final Score: " + finalScore;
            g.drawString(FinalScoreString, 325, 300);
        }
    }

    public void actionPerformed(ActionEvent e) {

    	ball.mover();

        if (ball.getX() <= 0 || ball.getX() >= getWidth() - ball.Diametro()) {
            ball.InvertirVelocidadX();
           if (Rebote != null && TiempoTranscurridoSegundos>1) {
            	Rebote.setFramePosition(0);
            	Rebote.start();
            }
        }

        if (ball.getY() <= 0 || ball.getY() >= getHeight() - ball.Diametro()) {
            ball.InvertirVelocidadY();
             if (Rebote != null && TiempoTranscurridoSegundos>1) {
            	Rebote.setFramePosition(0);
           	Rebote.start();
            }
        }

        if (ball.getY() >= 600 - ball.Diametro()) {
            vidas = vidas - 1;
            ball.setX(400);
            ball.setY(300);
            if (Muerte != null) {
            Muerte.setFramePosition(0); // Reiniciar el sonido al principio
            Muerte.start(); 
            }
            if (vidas == 0) {
            	if (triste != null) {
            		triste.setFramePosition(0); 
            		triste.start(); 
                }
                JuegoTermina = true;
                finalScore = (int) (score * (TiempoTranscurridoSegundos / 5));
                System.out.println("Juego en pausa. Final Score: " + finalScore);
                timer.stop();
            }
        }

        if (ball.ChoqueConPaddle(paddle)) {
            ball.InvertirVelocidadY();
            score++;
            
            if (Choque != null) {
                Choque.setFramePosition(0); 
                Choque.start(); 
            }
            
        }

        
        
        if (JuegoTermina && vidas == 0) {
            
            if (MusicaFondo != null && MusicaFondo.isRunning()) {
                MusicaFondo.stop();
            }
          
        } else {
       
            if (MusicaFondo != null && !MusicaFondo.isRunning()) {
            	MusicaFondo.start();
            }
         
        }
        
        
        
        
        
        
        long TiempoActual = System.currentTimeMillis();
        long TiempoMilisegundos = TiempoActual - startTime;
        TiempoTranscurridoSegundos = (int) (TiempoMilisegundos / 1000);
        
        if (TiempoTranscurridoSegundos >= tiempoUltimoIncremento + IntervaloIncremento) {
        	Incremento++; 
            ball.Acelerar(Incremento);
            tiempoUltimoIncremento = TiempoTranscurridoSegundos; 
        }
        
        
        
        
        
        

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddle.MoverHaciaIzquierda();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            paddle.MoverHaciaDerecha(getWidth());
        }
    }

    public void keyTyped(KeyEvent e) {
     
    }

    public void keyReleased(KeyEvent e) {
    
    }

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
