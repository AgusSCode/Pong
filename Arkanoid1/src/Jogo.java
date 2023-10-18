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
	private static final long serialVersionUID = 2514333446986159279L; // evitar warning

	private Balon ball;
    private LaPala paddle;
    private Bloque bloque;
    private int score;
    private static int finalScore;
    private int TiempoTranscurridoSegundos = 0;
    private long startTime;
    private static boolean JuegoTermina = false;
    private static Timer timer;
    private int vidas = 3;
    private Image fondoImage;
    private static int Incremento = 0;
    private static final int IntervaloIncremento = 25; // Incrementar velocidad cada 25 segundos
    private int tiempoUltimoIncremento = 0;
    private int NumeroRandom;
    private Clip Choque;
    private Clip Muerte;
    private Clip Rebote;
    private Clip triste;
    private Clip MusicaFondo;
    private Bloque[] bloques;
    private int LargoBloques=0;
    private boolean todosBloquesEliminados;


    
    public Jogo() {
    	//objetos
    	
    	  String[] opciones = {"Fácil", "Medio", "Difícil"};
          int dificultad = JOptionPane.showOptionDialog(
              null,
              "Seleccione la dificultad:",
              "Dificultad",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              opciones,
              opciones[0]
          );
    	
        
          
          
          
          
          
          
    	
        ball = new Balon(400, 300, 15, 2, 2);
        paddle = new LaPala(350, 555, 100, 15);
        bloques = new Bloque[LargoBloques+(dificultad+1)*9]; 
        bloque = new Bloque(50, 20, 70, 20);
        
        int cantidadBloquesPorColor = 9;

        // Definir los colores para cada grupo de bloques
        Color[] colores = {Color.GREEN, Color.ORANGE, Color.BLUE};

        // Inicializar bloques con diferentes colores
        int indiceColor = 0;
        int AlturaBloque = 30;

        for (int i = 0; i < bloques.length; i++) {
        	if (i > 0 && i % cantidadBloquesPorColor == 0) {
                // Cambiar al siguiente color cuando se hayan creado la cantidad definida de bloques
                indiceColor++;         
            }
            if (dificultad == 2 && i >= 9 && i < 18) {
                AlturaBloque = 30; // Aumentar la altura de los bloques para el nivel medio
            } else if (dificultad == 3 && i >= 18) {
                AlturaBloque += 30; // Aumentar la altura de los bloques gradualmente para el nivel difícil
            }

            int indiceX = i % 9; // Calcular el índice de X para cada fila de bloques (módulo 9)
            int indiceY = i / 9; // Calcular el índice de Y para cada fila de bloques (división entera por 9)


            bloques[i] = new Bloque(indiceX * 80 + bloque.getX(), indiceY * 30 + bloque.getY() + AlturaBloque, bloque.getWidth(), 20);
            bloques[i].setColor(colores[indiceColor]); // Establecer el color del bloque
        }


   

  

   
         
            







        
        score = 0;
        startTime = System.currentTimeMillis();
        
        
        //controles
        JOptionPane.showMessageDialog(null, "Bienvenido a Pong\nControles:\nFlecha Izquierda ← \nFlecha Derecha → ");
        //timer 
        timer = new Timer(1, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //fondo de pantalla
        ImageIcon img = new ImageIcon("media/Space.png");
        Image Scaledimg = img.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon ScaledimgToIcon = new ImageIcon(Scaledimg);
        fondoImage = ScaledimgToIcon.getImage();
        //numero random para la cancion 
        Random random = new Random();
        NumeroRandom = random.nextInt(4);
        
       
     
        

           
    
        //musica/sonidos
        try {
        	
            File ArchivoSonidoChoque = new File("media/caida.wav");
            File ArchivoSonidoMuerte = new File("media/ahh.wav");
            File ArchivoSonidoRebotePared = new File("media/hit.wav");
            File ArchivoSonidoTriste = new File ("media/yoda.wav");
            File ArchivoSonidoMusicaFondo = new File("media/Fondo.wav"); 
            
            
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
    //   Graphics2D g2d = (Graphics2D) g;

        g.drawImage(fondoImage, 0, 0, getWidth(), getHeight(), this);
//"animacion"
        ball.Pintar(g);
        paddle.Pintar(g);
        for (Bloque bloque : bloques) {
            if (!bloque.isEliminado()) {
                bloque.Pintar(g);
            }
        }
        
        //textos
        g.setColor(Color.white);
        g.setFont(new Font("Tahoma", Font.PLAIN, 12));
        g.drawString("Score: " + score, 715, 25);

        int minutos = TiempoTranscurridoSegundos / 60;
        int segundos = TiempoTranscurridoSegundos % 60;
        String TiempoString = String.format("%02d:%02d", minutos, segundos);
        g.drawString("Time: " + TiempoString, 15, 25);

        String VidasString = String.format("%d", vidas);
        g.drawString("Lives: " + VidasString, 715, 45);
        
//juego termina
        if (JuegoTermina && vidas == 0) {
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.white);
            String FinalScoreString = "Final Score: " + finalScore;
            g.drawString(FinalScoreString, 325, 300);
        }
        
        
        
        if (todosBloquesEliminados==true) {
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.white);
            String Winner = "Congratulations!\n Final Score:" + finalScore;
            g.drawString(Winner, 325, 300);
            JuegoTermina = true;
            timer.stop();
        }
    }
    
    
    
    
    
    public void actionPerformed(ActionEvent e) {

    	ball.mover();
    		//bordes izquierda y derecha
        if (ball.getX() <= 0 || ball.getX() >= getWidth() - ball.Diametro()) {
            ball.InvertirVelocidadX();
           if (Rebote != null && TiempoTranscurridoSegundos>1) {
            	Rebote.setFramePosition(0);
            	Rebote.start();
            }
        }
        	//techo
        if (ball.getY() <= 0 || ball.getY() >= getHeight() - ball.Diametro()) {
            ball.InvertirVelocidadY();
             if (Rebote != null && TiempoTranscurridoSegundos>1) {
            	Rebote.setFramePosition(0);
           	Rebote.start();
            }
        }
        	// piso
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
                
                timer.stop();
            }
        }
        	//paleta
        
        
        
        
       if (ball.ChoqueConPaddle(paddle)) {
          
            score++;
            
            if (Choque != null) {
                Choque.setFramePosition(0); 
                Choque.start(); 
            }
            
        }
        
          
        
        
        
        
       if (ball.ChoqueConBloque(bloque)) {
    	    // Si hay colisión con el bloque, la inversión de la velocidad se maneja dentro del método ChoqueConBloque
    	    score++;

    	    if (Choque != null) {
    	        Choque.setFramePosition(0); 
    	        Choque.start(); 
    	    }
    	}

       if (ball.ChoqueConBloques(bloques)) {
           // Reproducir el sonido de choque con el bloque si es necesario
           if (Choque != null) {
               Choque.setFramePosition(0);
               Choque.start();
           }
       }
       
      
       boolean todosBloquesEliminados = true;
       for (Bloque bloque : bloques) {
           if (!bloque.isEliminado()) {
               todosBloquesEliminados = false;
               break;
           }
       }
       
       
       
       
       
       
       
       
        //pierde
        
        if (JuegoTermina && vidas == 0) {
            
            if (MusicaFondo != null && MusicaFondo.isRunning()) {
                MusicaFondo.stop();
            }
          
        } else {
       
            if (MusicaFondo != null && !MusicaFondo.isRunning()) {
            	MusicaFondo.start();
            }
         
        }
        
        if (todosBloquesEliminados) {
            timer.stop();
           
        
        }
        
        
if (JuegoTermina && vidas == 0) {
            
            if (MusicaFondo != null && MusicaFondo.isRunning()) {
                MusicaFondo.stop();
            }
          
        } 
        
        //contar tiempo 
        
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




