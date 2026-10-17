// (c) 2026 Programacion Orientada al Objeto.
package cl.ucn.disc.poo.stdlib;

import edu.princeton.cs.stdlib.StdDraw;

import java.awt.*;

/**
 * Clase que dibuja un lienzo lleno de puntos al azar.
 */
public final class PuntosAzar {

    /**
     * The Main.
     */
    public static void main(String[] args) {
        // escala de dibujo
        double min = -10.0;
        double max = 10.0;

        // definicion de la escala
        StdDraw.setXscale(min, max);
        StdDraw.setYscale(min, max);

        // habilitar el doble buffering
        StdDraw.enableDoubleBuffering();

        // ciclo de dibujo
        for (int i = 0; i < 10000000; i++) {

            // definicion del color del lapiz
            // en los numeros pares
            if (i % 2 == 0) {
                StdDraw.setPenColor(Color.RED);
            } else {
                StdDraw.setPenColor(Color.BLUE);
            }

            // calculo de un (x, y) al azar
            double x = min + (max - min) * Math.random();
            double y = min + (max - min) * Math.random();

            // dibujo un circulo pintado
            StdDraw.filledCircle(x, y, 1);

            // espera antes de continuar
            StdDraw.pause(250);
            // muestra el lienzo
            StdDraw.show();

        }

    }

}