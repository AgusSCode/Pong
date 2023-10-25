public class Colisiones {
	private static final double FACTOR_ACELERACION = 1.2;
    public static void manejarColisionParedesLaterales(Balones pelota, int anchoPantalla) {
        if (pelota.getX() <= 0 || pelota.getX() >= anchoPantalla - pelota.Diametro()) {
            pelota.InvertirVelocidadX();
        }
    }

    public static void manejarColisionTecho(Balones pelota, int alturaPantalla) {
        if (pelota.getY() <= 0 || pelota.getY() >= alturaPantalla + pelota.Diametro()) {
            pelota.InvertirVelocidadY();
        }
    }

    public static void manejarColisionPiso(Balones pelota, int alturaPantalla) {
        
        if (pelota.getY() >=600|| pelota.getY() >= alturaPantalla - pelota.Diametro()) {
            pelota.InvertirVelocidadY();
          
        }}


    public static void manejarColisionEntrePelotas(Balones[] pelotas) {
        for (int i = 0; i < pelotas.length; i++) {
            for (int j = i + 1; j < pelotas.length; j++) {
                if (verificarColision(pelotas[i], pelotas[j])) {
                    // calcula nuevas velocidades de las pelotas despues del choque
                    calcularNuevasVelocidades(pelotas[i], pelotas[j]);

                    // aplica friccion a ambas pelotas
                    pelotas[i].aplicarFuerzas();
                    pelotas[j].aplicarFuerzas();
                }
            }
        }
    }

    private static void calcularNuevasVelocidades(Balones pelota1, Balones pelota2) {
        double diffVelocidadX = pelota1.getVelocidadX() - pelota2.getVelocidadX();
        double diffVelocidadY = pelota1.getVelocidadY() - pelota2.getVelocidadY();
//El coeficiente de restitución (e) puede calcularse como el cuociente negativo de la velocidad relativa después del choque a la velocidad relativa antes del choque.
        
        // calcula las nuevas velocidades con el coeficiente de restitución
        double nuevaVelocidadX1 = pelota2.getVelocidadX() + diffVelocidadX * pelota1.getCoeficienteRestitucion() * FACTOR_ACELERACION;
        double nuevaVelocidadY1 = pelota2.getVelocidadY() + diffVelocidadY * pelota1.getCoeficienteRestitucion() * FACTOR_ACELERACION;
        double nuevaVelocidadX2 = pelota1.getVelocidadX() - diffVelocidadX * pelota2.getCoeficienteRestitucion() * FACTOR_ACELERACION;
        double nuevaVelocidadY2 = pelota1.getVelocidadY() - diffVelocidadY * pelota2.getCoeficienteRestitucion() * FACTOR_ACELERACION;

      
        pelota1.setVelocidadX(nuevaVelocidadX1);
        pelota1.setVelocidadY(nuevaVelocidadY1);
        pelota2.setVelocidadX(nuevaVelocidadX2);
        pelota2.setVelocidadY(nuevaVelocidadY2);
    }




    private static boolean verificarColision(Balones pelota1, Balones pelota2) {
        double distancia = Math.sqrt(Math.pow(pelota2.getX() - pelota1.getX(), 2) + Math.pow(pelota2.getY() - pelota1.getY(), 2));
        double sumaRadios = pelota1.Diametro() / 2 + pelota2.Diametro() / 2;
        return distancia <= sumaRadios;
    }

    
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
}












