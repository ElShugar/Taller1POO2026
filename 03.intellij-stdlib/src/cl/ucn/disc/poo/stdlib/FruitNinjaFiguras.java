package cl.ucn.disc.poo.stdlib;

// (c) 2026 Programacion Orientada al Objeto - Universidad Catolica del Norte.
import edu.princeton.cs.stdlib.StdAudio;
import edu.princeton.cs.stdlib.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * Taller N.º 1 - Fruit Ninja: Figuras.
 * Videojuego de reflejos y precision en el que el jugador debe seleccionar con el mouse
 * cuatro figuras geometricas (Triangulo, Cuadrado, Circulo y Rectangulo) que caen por la
 * pantalla, respetando el orden de una secuencia generada aleatoriamente en cada ronda.
 * @author Mario Gabriel Choque 27964257-0
 * @version 1.0
 */
public final class FruitNinjaFiguras {

    /** Ancho y alto del lienzo en pixeles. */
    public static final int TAMANO_LIENZO = 600;

    /** Cantidad total de figuras por ronda. */
    public static final int CANTIDAD_FIGURAS = 4;

    /** Cantidad total de rondas para ganar una partida. */
    public static final int TOTAL_RONDAS = 10;

    /** Identificadores numericos para cada tipo de figura. */
    public static final int FIGURA_TRIANGULO = 0;
    public static final int FIGURA_CUADRADO = 1;
    public static final int FIGURA_CIRCULO = 2;
    public static final int FIGURA_RECTANGULO = 3;

    /** Identificadores numericos para los estados del juego. */
    public static final int ESTADO_MENU = 0;
    public static final int ESTADO_JUEGO = 1;
    public static final int ESTADO_ESTADISTICAS = 2;
    public static final int ESTADO_GAME_OVER = 3;
    public static final int ESTADO_VICTORIA = 4;
    public static final int ESTADO_SALIR = 5;

    /** Puntaje otorgado al seleccionar correctamente una figura. */
    public static final int PUNTOS_POR_FIGURA = 10;

    /** Bono de puntaje otorgado al completar las 4 figuras de una ronda. */
    public static final int BONO_RONDA_COMPLETA = 50;

    /** Velocidad base inicial de caida en unidades por fotograma. */
    public static final double VELOCIDAD_INICIAL = 1.15;

    /** Incremento de velocidad por cada ronda superada. */
    public static final double INCREMENTO_VELOCIDAD = 0.22;

    // VARIABLES DE ESTADO DE LA PARTIDA ACTUAL

    /** Estado actual de la aplicacion (Menu, Juego, Estadisticas, Game Over o Victoria). */
    public static int estadoActual = ESTADO_MENU;

    /** Ronda actual de la partida (1 a 10). */
    public static int rondaActual = 1;

    /** Puntaje acumulado en la partida en curso. */
    public static int puntajeActual = 0;

    /** Velocidad de caida actual de las figuras en la ronda. */
    public static double velocidadActual = VELOCIDAD_INICIAL;

    /** Secuencia aleatoria de tipos de figuras que el jugador debe seguir en la ronda. */
    public static int[] secuenciaObjetivo = new int[CANTIDAD_FIGURAS];

    /** Indice (0 a 3) que indica cual figura de la secuencia corresponde seleccionar ahora. */
    public static int indiceObjetivoActual = 0;

    // PROPIEDADES DE LAS 4 FIGURAS

    /** Posicion horizontal (X) del centro de cada figura. */
    public static double[] figPosX = new double[CANTIDAD_FIGURAS];

    /** Posicion vertical (Y) del centro de cada figura. */
    public static double[] figPosY = new double[CANTIDAD_FIGURAS];

    /** Tamano base (radio o semi-lado) de cada figura. */
    public static double[] figTamano = new double[CANTIDAD_FIGURAS];

    /** Velocidad vertical de caida de cada figura. */
    public static double[] figVelocidad = new double[CANTIDAD_FIGURAS];

    /** Tipo de figura geometrica (0: Triangulo, 1: Cuadrado, 2: Circulo, 3: Rectangulo). */
    public static int[] figTipo = new int[CANTIDAD_FIGURAS];

    /** Estado de cada figura (true si sigue cayendo activa, false si ya fue seleccionada). */
    public static boolean[] figActiva = new boolean[CANTIDAD_FIGURAS];

    // VARIABLES DE ESTADISTICAS ACUMULADAS

    /** Cantidad total de partidas jugadas durante la ejecucion del programa. */
    public static int partidasJugadas = 0;

    /** Cantidad total de partidas ganadas (completando las 10 rondas). */
    public static int partidasGanadas = 0;

    /** Cantidad total de partidas perdidas. */
    public static int partidasPerdidas = 0;

    /** Mejor puntaje historico registrado en la sesion. */
    public static int mejorPuntaje = 0;

    /** Suma de todos los puntajes obtenidos para calcular el puntaje promedio. */
    public static int sumaPuntajes = 0;

    /** Mayor ronda alcanzada entre todas las partidas jugadas. */
    public static int mayorRondaAlcanzada = 0;

    /** Cantidad total de clics acertados sobre la figura objetivo correcta. */
    public static int totalAciertos = 0;

    /** Cantidad total de clics realizados durante el juego para calcular el % de aciertos. */
    public static int totalIntentosClic = 0;

    /** Controla si el boton del mouse ya estaba presionado en el fotograma anterior. */
    public static boolean mouseEstabaPresionado = false;

    /**
     * Metodo principal (Main) que inicializa la ventana grafica y ejecuta el ciclo
     * principal de actualizacion del videojuego.
     *
     * @param args .
     */
    public static void main(String[] args) {
        // Configurar el lienzo de StdDraw con al menos 500x500 pixeles
        inicializarLienzo();

        // Ciclo principal de ejecucion mientras el jugador no elija "Salir"
        while (estadoActual != ESTADO_SALIR) {
            // Detectar si ocurrio un nuevo clic del mouse en este fotograma
            boolean clicActual = StdDraw.isMousePressed();
            boolean nuevoClic = clicActual && !mouseEstabaPresionado;
            mouseEstabaPresionado = clicActual;

            // Obtener las coordenadas actuales del mouse
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

            // Ejecutar la logica y el dibujo segun el estado actual del juego
            if (estadoActual == ESTADO_MENU) {
                actualizarMenuPrincipal(nuevoClic, mouseX, mouseY);
            } else if (estadoActual == ESTADO_JUEGO) {
                actualizarJuego(nuevoClic, mouseX, mouseY);
            } else if (estadoActual == ESTADO_ESTADISTICAS) {
                actualizarEstadisticas(nuevoClic, mouseX, mouseY);
            } else if (estadoActual == ESTADO_GAME_OVER) {
                actualizarPantallaResultado(nuevoClic, mouseX, mouseY, false);
            } else if (estadoActual == ESTADO_VICTORIA) {
                actualizarPantallaResultado(nuevoClic, mouseX, mouseY, true);
            }

            // Mostrar el fotograma renderizado y hacer una breve pausa (~50 FPS)
            StdDraw.show();
            StdDraw.pause(20);
        }

        // Cerrar el programa limpiamente al salir del menu
        System.exit(0);
    }

    /**
     * Configura el tamano de la ventana grafica, la escala de coordenadas (0 a 600)
     * y activa el doble buffering para evitar parpadeos en la animacion.
     */
    public static void inicializarLienzo() {
        StdDraw.setCanvasSize(TAMANO_LIENZO, TAMANO_LIENZO);
        StdDraw.setXscale(0, TAMANO_LIENZO);
        StdDraw.setYscale(0, TAMANO_LIENZO);
        StdDraw.enableDoubleBuffering();
    }

    // LOGICA DE INICIALIZACION DE PARTIDA, RONDAS, SECUENCIA Y VELOCIDAD

    /**
     * Inicia una nueva partida desde la ronda 1, reiniciando el puntaje actual
     * y la velocidad inicial de caida.
     */
    public static void iniciarNuevaPartida() {
        rondaActual = 1;
        puntajeActual = 0;
        velocidadActual = calcularVelocidadPorRonda(rondaActual);
        iniciarRonda();
        estadoActual = ESTADO_JUEGO;
    }

    /**
     * Prepara una nueva ronda generando una secuencia aleatoria sin repeticiones
     * y posicionando las cuatro figuras en la parte superior de la pantalla.
     */
    public static void iniciarRonda() {
        // Reiniciar el progreso de la secuencia al primer elemento (indice 0)
        indiceObjetivoActual = 0;

        // Actualizar la velocidad de caida segun el numero de ronda actual
        velocidadActual = calcularVelocidadPorRonda(rondaActual);

        // 1. Generar la secuencia aleatoria de objetivos sin repetir ninguna figura
        generarSecuenciaAleatoria(secuenciaObjetivo);

        // 2. Generar un orden aleatorio para los carriles horizontales (X)
        int[] ordenCarriles = new int[CANTIDAD_FIGURAS];
        generarSecuenciaAleatoria(ordenCarriles);

        // Posiciones X fijas de los 4 carriles bien separados en el lienzo de 600px
        double[] columnasX = {95.0, 230.0, 370.0, 505.0};

        // 3. Configurar cada una de las 4 figuras usando su orden en la secuencia
        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            int tipoDeEstaFigura = secuenciaObjetivo[i];
            figTipo[i] = tipoDeEstaFigura;
            figPosX[i] = columnasX[ordenCarriles[i]];

            // Escalonar levemente la altura inicial Y segun el orden en la secuencia:
            // asi la primera figura objetivo siempre va un poco mas adelante que las
            // siguientes, evitando situaciones injustas o imposibles al caer al fondo.
            figPosY[i] = 480.0 + (i * 25.0);

            figTamano[i] = 32.0;
            figVelocidad[i] = velocidadActual;
            figActiva[i] = true;
        }
    }

    /**
     * Calcula la velocidad de caida de las figuras en funcion de la ronda actual,
     * aumentando progresivamente la dificultad sin volver el juego imposible.
     *
     * @param ronda numero de la ronda actual (1 a 10).
     * @return velocidad de caida correspondiente a la ronda.
     */
    public static double calcularVelocidadPorRonda(int ronda) {
        return VELOCIDAD_INICIAL + (ronda - 1) * INCREMENTO_VELOCIDAD;
    }

    /**
     * Llena un arreglo de 4 enteros con los valores {0, 1, 2, 3} desordenados
     * al azar sin repetir ningun elemento (algoritmo de mezcla Fisher-Yates).
     *
     * @param arreglo arreglo de tamano 4 donde se guardara la secuencia aleatoria.
     */
    public static void generarSecuenciaAleatoria(int[] arreglo) {
        // Inicializar con los 4 tipos: 0, 1, 2, 3
        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            arreglo[i] = i;
        }
        // Intercambiar cada posicion con otra elegida al azar
        for (int i = CANTIDAD_FIGURAS - 1; i > 0; i--) {
            int indiceAzar = (int) (Math.random() * (i + 1));
            int temporal = arreglo[i];
            arreglo[i] = arreglo[indiceAzar];
            arreglo[indiceAzar] = temporal;
        }
    }

    // ACTUALIZACION DEL JUEGO PRINCIPAL Y MOVIMIENTO DE FIGURAS

    /**
     * Actualiza un fotograma del estado de juego: procesa clics del jugador,
     * desplaza las figuras hacia abajo, verifica colisiones con el borde inferior
     * y dibuja la interfaz grafica de la partida.
     *
     * @param nuevoClic indica si el jugador hizo clic en este fotograma.
     * @param mouseX    coordenada X del clic del mouse.
     * @param mouseY    coordenada Y del clic del mouse.
     */
    public static void actualizarJuego(boolean nuevoClic, double mouseX, double mouseY) {
        // 1. Procesar el clic del jugador si presiono el boton del mouse
        if (nuevoClic) {
            procesarClicEnJuego(mouseX, mouseY);
            // Si el clic causo Victoria o Game Over, salir de inmediato de la actualizacion
            if (estadoActual != ESTADO_JUEGO) {
                return;
            }
        }

        // 2. Actualizar la posicion vertical (caida continua) de cada figura activa
        int tipoEsperado = secuenciaObjetivo[indiceObjetivoActual];

        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            if (figActiva[i]) {
                // Desplazar la figura hacia abajo segun su velocidad
                figPosY[i] = figPosY[i] - figVelocidad[i];

                // Verificar si el borde inferior de la figura toco el fondo de la pantalla (Y <= 0)
                if (figPosY[i] - figTamano[i] <= 0.0) {
                    if (figTipo[i] == tipoEsperado) {
                        // Condicion d): si la figura que debia seleccionarse llega al fondo, pierde
                        registrarFinDePartida(false);
                        return;
                    } else {
                        // Condicion d): las figuras que aun no corresponden al objetivo actual
                        // pueden continuar cayendo sin penalizacion (reaparecen arriba para que
                        // sigan disponibles cuando llegue su turno en la secuencia).
                        figPosY[i] = 515.0;
                    }
                }
            }
        }

        // 3. Dibujar el fondo tematico, las figuras activas y la barra superior de informacion
        dibujarFondoTematicoJuego();
        dibujarFigurasActivas();
        dibujarBarraInformacion();
    }

    /**
     * Verifica si el clic del jugador ocurrio dentro de alguna de las figuras activas
     * y aplica las reglas de puntaje, avance de ronda o derrota inmediata.
     *
     * @param mouseX coordenada X del clic del mouse.
     * @param mouseY coordenada Y del clic del mouse.
     */
    public static void procesarClicEnJuego(double mouseX, double mouseY) {
        // Contabilizar el clic dentro de la partida para el porcentaje de aciertos
        totalIntentosClic++;

        // Reproducir un sonido aleatorio al hacer clic (criterio de Creatividad)
        reproducirSonidoClickAleatorio();

        // Buscar si el clic cayo matematicamente dentro de alguna figura activa
        int indiceFiguraSeleccionada = -1;
        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            if (figActiva[i]) {
                boolean clicDentro = verificarClicEnFigura(
                        mouseX, mouseY, figPosX[i], figPosY[i], figTamano[i], figTipo[i]
                );
                if (clicDentro) {
                    indiceFiguraSeleccionada = i;
                    break;
                }
            }
        }

        // Si el jugador hizo clic en el fondo vacio (fuera de toda figura), no selecciona figura
        if (indiceFiguraSeleccionada == -1) {
            return;
        }

        // Obtener el tipo de la figura seleccionada y compararlo con la figura esperada
        int tipoSeleccionado = figTipo[indiceFiguraSeleccionada];
        int tipoEsperado = secuenciaObjetivo[indiceObjetivoActual];

        if (tipoSeleccionado == tipoEsperado) {
            // Seleccion correcta: desactivar figura, sumar 10 puntos y avanzar en la secuencia
            figActiva[indiceFiguraSeleccionada] = false;
            puntajeActual += PUNTOS_POR_FIGURA;
            totalAciertos++;
            indiceObjetivoActual++;

            // Actualizar el mejor puntaje historico en tiempo real si se supera
            if (puntajeActual > mejorPuntaje) {
                mejorPuntaje = puntajeActual;
            }

            // Verificar si se completaron las 4 figuras de la ronda actual
            if (indiceObjetivoActual >= CANTIDAD_FIGURAS) {
                // Otorgar los 50 puntos adicionales por completar la ronda
                puntajeActual += BONO_RONDA_COMPLETA;
                if (puntajeActual > mejorPuntaje) {
                    mejorPuntaje = puntajeActual;
                }

                // Condicion f): si completo la ronda 10, gana la partida
                if (rondaActual >= TOTAL_RONDAS) {
                    registrarFinDePartida(true);
                } else {
                    // Condicion e): incrementar ronda, aumentar velocidad y nueva secuencia
                    rondaActual++;
                    iniciarRonda();
                    // Evitar situaciones injustas si la primera figura de la nueva ronda
                    // estuviese baja (iniciarRonda ya las ubica arriba en Y >= 480)
                }
            } else {
                // Evitar situacion injusta: si la siguiente figura objetivo estaba muy cerca
                // del borde inferior cuando le toca su turno, darle margen de altura
                for (int j = 0; j < CANTIDAD_FIGURAS; j++) {
                    if (figActiva[j] && figTipo[j] == secuenciaObjetivo[indiceObjetivoActual]) {
                        if (figPosY[j] < 160.0) {
                            figPosY[j] = 480.0;
                        }
                    }
                }
            }
        } else {
            // Condicion c): selecciono una figura que NO corresponde al orden -> pierde de inmediato
            registrarFinDePartida(false);
        }
    }

    /**
     * Registra las estadisticas acumuladas al terminar una partida (por victoria o derrota)
     * y cambia el estado del juego a la pantalla correspondiente.
     *
     * @param esVictoria true si el jugador completo las 10 rondas, false si perdio.
     */
    public static void registrarFinDePartida(boolean esVictoria) {
        partidasJugadas++;
        sumaPuntajes += puntajeActual;

        if (puntajeActual > mejorPuntaje) {
            mejorPuntaje = puntajeActual;
        }
        if (rondaActual > mayorRondaAlcanzada) {
            mayorRondaAlcanzada = rondaActual;
        }

        if (esVictoria) {
            partidasGanadas++;
            estadoActual = ESTADO_VICTORIA;
            // Criterio de Creatividad: al ganar, escoge una cancion aleatoria
            reproducirCancionVictoriaAleatoria();
        } else {
            partidasPerdidas++;
            estadoActual = ESTADO_GAME_OVER;
        }
    }


    // DETECCION MATEMATICA DE CLIC EN CADA TIPO DE FIGURA

    /**
     * Determina matematicamente si el punto (mx, my) del clic del mouse se encuentra
     * dentro del area de una figura geometrica segun su tipo.
     *
     * @param mx     coordenada X del mouse.
     * @param my     coordenada Y del mouse.
     * @param cx     coordenada X del centro de la figura.
     * @param cy     coordenada Y del centro de la figura.
     * @param tamano radio o semi-lado base de la figura.
     * @param tipo   tipo de figura (Triangulo, Cuadrado, Circulo o Rectangulo).
     * @return true si el clic ocurrio dentro de la figura, false en caso contrario.
     */
    public static boolean verificarClicEnFigura(
            double mx, double my, double cx, double cy, double tamano, int tipo) {

        if (tipo == FIGURA_CIRCULO) {
            // Ecuacion del circulo: distancia al centro <= radio
            double dx = mx - cx;
            double dy = my - cy;
            double distancia = Math.sqrt(dx * dx + dy * dy);
            return distancia <= tamano;

        } else if (tipo == FIGURA_CUADRADO) {
            // Cuadrado centrado en (cx, cy) con semi-lado igual a tamano
            return Math.abs(mx - cx) <= tamano && Math.abs(my - cy) <= tamano;

        } else if (tipo == FIGURA_RECTANGULO) {
            // Rectangulo centrado en (cx, cy) con semi-ancho = tamano * 1.35 y semi-alto = tamano * 0.75
            double semiAncho = tamano * 1.35;
            double semiAlto = tamano * 0.75;
            return Math.abs(mx - cx) <= semiAncho && Math.abs(my - cy) <= semiAlto;

        } else if (tipo == FIGURA_TRIANGULO) {
            // Triangulo isosceles con base en (cy - tamano) y punta superior en (cy + tamano).
            // Primero verificamos que Y este entre la base y la punta superior:
            if (my < cy - tamano || my > cy + tamano) {
                return false;
            }
            // A medida que 'my' sube hacia la punta (cy + tamano), el semi-ancho permitido
            // disminuye linealmente desde 'tamano' (en la base) hasta 0 (en la punta):
            double semiAnchoPermitido = ((cy + tamano) - my) / 2.0;
            return Math.abs(mx - cx) <= semiAnchoPermitido;
        }

        return false;
    }

    /**
     * Verifica si el clic del mouse ocurrio dentro de un boton rectangular de la interfaz.
     *
     * @param mx        coordenada X del mouse.
     * @param my        coordenada Y del mouse.
     * @param centroX   coordenada X del centro del boton.
     * @param centroY   coordenada Y del centro del boton.
     * @param semiAncho mitad del ancho del boton.
     * @param semiAlto  mitad del alto del boton.
     * @return true si el mouse esta dentro del boton, false en caso contrario.
     */
    public static boolean verificarClicEnBoton(
            double mx, double my, double centroX, double centroY, double semiAncho, double semiAlto) {
        return Math.abs(mx - centroX) <= semiAncho && Math.abs(my - centroY) <= semiAlto;
    }

    // FUNCIONES DE DIBUJO DE LA INTERFAZ GRAFICA (GUI) Y FIGURAS


    /**
     * Dibuja un fondo tematico inspirado en un tablero de madera de dojo ("Fruit Ninja")
     * utilizando colores calidos y lineas simples de StdDraw.
     */
    public static void dibujarFondoTematicoJuego() {
        // Color crema/madera clara de fondo (como en la Figura 2 del enunciado)
        StdDraw.clear(new Color(245, 245, 228));

        // Lineas horizontales suaves que simulan tablones de madera en el dojo
        StdDraw.setPenColor(new Color(232, 228, 205));
        StdDraw.setPenRadius(0.003);
        for (int y = 60; y < 520; y += 75) {
            StdDraw.line(0, y, TAMANO_LIENZO, y);
        }

        // Borde decorativo inferior que marca la zona limite de caida
        StdDraw.setPenColor(new Color(210, 80, 80));
        StdDraw.setPenRadius(0.005);
        StdDraw.line(0, 4, TAMANO_LIENZO, 4);
        StdDraw.setPenRadius();
    }

    /**
     * Recorre las 4 figuras de la ronda y dibuja en pantalla aquellas que continuan activas.
     */
    public static void dibujarFigurasActivas() {
        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            if (figActiva[i]) {
                dibujarFiguraGeometrica(figPosX[i], figPosY[i], figTamano[i], figTipo[i]);
            }
        }
    }

    /**
     * Dibuja una figura geometrica especifica en las coordenadas (x, y) con su color distintivo.
     *
     * @param x      posicion X del centro de la figura.
     * @param y      posicion Y del centro de la figura.
     * @param tamano radio o semi-lado de la figura.
     * @param tipo   tipo de figura (Triangulo, Cuadrado, Circulo o Rectangulo).
     */
    public static void dibujarFiguraGeometrica(double x, double y, double tamano, int tipo) {
        if (tipo == FIGURA_TRIANGULO) {
            // Triangulo color Naranja (como en la Figura 2 del enunciado)
            StdDraw.setPenColor(new Color(235, 110, 45));
            double[] vx = {x - tamano, x + tamano, x};
            double[] vy = {y - tamano, y - tamano, y + tamano};
            StdDraw.filledPolygon(vx, vy);

        } else if (tipo == FIGURA_CUADRADO) {
            // Cuadrado color Verde
            StdDraw.setPenColor(new Color(75, 175, 105));
            StdDraw.filledSquare(x, y, tamano);

        } else if (tipo == FIGURA_CIRCULO) {
            // Circulo color Azul
            StdDraw.setPenColor(new Color(65, 145, 225));
            StdDraw.filledCircle(x, y, tamano);

        } else if (tipo == FIGURA_RECTANGULO) {
            // Rectangulo color Rosa/Magenta
            StdDraw.setPenColor(new Color(220, 70, 115));
            StdDraw.filledRectangle(x, y, tamano * 1.35, tamano * 0.75);
        }
    }

    /**
     * Dibuja la barra superior de informacion durante la partida: puntaje actual,
     * ronda actual, velocidad actual, figura objetivo y progreso de la secuencia.
     */
    public static void dibujarBarraInformacion() {
        // Franja superior oscura para puntaje, ronda y velocidad (Figura 2)
        StdDraw.setPenColor(new Color(35, 35, 35));
        StdDraw.filledRectangle(300, 580, 300, 20);

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));
        StdDraw.textLeft(15, 578, "Puntaje: " + puntajeActual);
        StdDraw.text(280, 578, "Ronda: " + rondaActual + " / " + TOTAL_RONDAS);

        // Calcular el multiplicador de velocidad relativo para mostrar (ej: x1.0, x1.2)
        double factorVelocidad = Math.round((velocidadActual / VELOCIDAD_INICIAL) * 10.0) / 10.0;
        StdDraw.textRight(585, 578, "Velocidad: x" + factorVelocidad);

        // Segunda franja informativa con el OBJETIVO actual y el progreso de la secuencia
        StdDraw.setPenColor(new Color(235, 232, 210));
        StdDraw.filledRectangle(300, 542, 300, 18);

        String nombreObjetivo = obtenerNombreFigura(secuenciaObjetivo[indiceObjetivoActual]);
        StdDraw.setPenColor(new Color(40, 40, 40));
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 14));
        StdDraw.textLeft(15, 540, "OBJETIVO: " + nombreObjetivo);

        // Mostrar el orden completo de la secuencia y el progreso (ej: 1/4)
        String textoProgreso = "Progreso: " + indiceObjetivoActual + "/4   Secuencia: ";
        for (int i = 0; i < CANTIDAD_FIGURAS; i++) {
            if (i == indiceObjetivoActual) {
                textoProgreso += "[" + obtenerNombreCortoFigura(secuenciaObjetivo[i]) + "] ";
            } else {
                textoProgreso += obtenerNombreCortoFigura(secuenciaObjetivo[i]) + " ";
            }
        }
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.textRight(585, 540, textoProgreso);
    }

    /**
     * Retorna el nombre completo en mayusculas de un tipo de figura.
     *
     * @param tipo identificador numerico de la figura (0 a 3).
     * @return nombre de la figura ("TRIANGULO", "CUADRADO", "CIRCULO" o "RECTANGULO").
     */
    public static String obtenerNombreFigura(int tipo) {
        if (tipo == FIGURA_TRIANGULO) {
            return "TRIANGULO";
        } else if (tipo == FIGURA_CUADRADO) {
            return "CUADRADO";
        } else if (tipo == FIGURA_CIRCULO) {
            return "CIRCULO";
        } else {
            return "RECTANGULO";
        }
    }

    /**
     * Retorna una abreviatura corta de 3 letras para mostrar la secuencia en pantalla.
     *
     * @param tipo identificador numerico de la figura (0 a 3).
     * @return abreviatura de la figura ("TRI", "CUA", "CIR" o "REC").
     */
    public static String obtenerNombreCortoFigura(int tipo) {
        if (tipo == FIGURA_TRIANGULO) {
            return "TRI";
        } else if (tipo == FIGURA_CUADRADO) {
            return "CUA";
        } else if (tipo == FIGURA_CIRCULO) {
            return "CIR";
        } else {
            return "REC";
        }
    }


    // MENU PRINCIPAL, ESTADISTICAS Y PANTALLAS DE RESULTADO FINAL


    /**
     * Dibuja y controla las interacciones del Menu Principal (Figura 3 del enunciado).
     *
     * @param nuevoClic indica si hubo un clic del mouse en este fotograma.
     * @param mouseX    posicion X del mouse.
     * @param mouseY    posicion Y del mouse.
     */
    public static void actualizarMenuPrincipal(boolean nuevoClic, double mouseX, double mouseY) {
        StdDraw.clear(Color.WHITE);

        // Titulo y subtitulo del menu principal
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 20));
        StdDraw.text(300, 490, "FRUIT NINJA: FIGURAS");

        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));
        StdDraw.text(300, 460, "Haz clic sobre la figura correcta en el orden indicado");

        // Dibujar los 3 botones del menu principal (centrados en X = 300)
        dibujarBotonMenu(300, 365, 115, 24, "1. Iniciar Juego", mouseX, mouseY);
        dibujarBotonMenu(300, 305, 115, 24, "2. Estadisticas", mouseX, mouseY);
        dibujarBotonMenu(300, 245, 115, 24, "3. Salir", mouseX, mouseY);

        // Pie de pagina con el nombre del jugador (como en la Figura 3)
        StdDraw.setPenColor(Color.DARK_GRAY);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 13));
        StdDraw.text(300, 100, "Jugador: Mario Gabriel");

        // Verificar si el usuario hizo clic en alguno de los 3 botones
        if (nuevoClic) {
            if (verificarClicEnBoton(mouseX, mouseY, 300, 365, 115, 24)) {
                reproducirSonidoClickAleatorio();
                iniciarNuevaPartida();
            } else if (verificarClicEnBoton(mouseX, mouseY, 300, 305, 115, 24)) {
                reproducirSonidoClickAleatorio();
                estadoActual = ESTADO_ESTADISTICAS;
            } else if (verificarClicEnBoton(mouseX, mouseY, 300, 245, 115, 24)) {
                reproducirSonidoClickAleatorio();
                estadoActual = ESTADO_SALIR;
            }
        }
    }

    /**
     * Dibuja y controla la pantalla de Estadisticas Acumuladas con todos los indicadores
     * solicitados en la condicion h) del enunciado.
     *
     * @param nuevoClic indica si hubo un clic del mouse en este fotograma.
     * @param mouseX    posicion X del mouse.
     * @param mouseY    posicion Y del mouse.
     */
    public static void actualizarEstadisticas(boolean nuevoClic, double mouseX, double mouseY) {
        StdDraw.clear(new Color(248, 248, 242));

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 20));
        StdDraw.text(300, 510, "ESTADISTICAS ACUMULADAS");

        // Calcular el puntaje promedio y el porcentaje de aciertos evitando division por cero
        double puntajePromedio = 0.0;
        if (partidasJugadas > 0) {
            puntajePromedio = Math.round((sumaPuntajes * 10.0) / partidasJugadas) / 10.0;
        }

        double porcentajeAciertos = 0.0;
        if (totalIntentosClic > 0) {
            porcentajeAciertos = Math.round((totalAciertos * 1000.0) / totalIntentosClic) / 10.0;
        }

        // Mostrar cada estadistica solicitada en la letra h)
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 15));
        StdDraw.text(300, 435, "Partidas jugadas: " + partidasJugadas);
        StdDraw.text(300, 395, "Partidas ganadas: " + partidasGanadas);
        StdDraw.text(300, 355, "Partidas perdidas: " + partidasPerdidas);
        StdDraw.text(300, 315, "Mejor puntaje: " + mejorPuntaje);
        StdDraw.text(300, 275, "Puntaje promedio: " + puntajePromedio);
        StdDraw.text(300, 235, "Mayor ronda alcanzada: " + mayorRondaAlcanzada);
        StdDraw.text(300, 195, "Porcentaje de aciertos: " + porcentajeAciertos + " %");

        // Boton para regresar al menu principal
        dibujarBotonMenu(300, 110, 120, 24, "Volver al menu", mouseX, mouseY);

        if (nuevoClic && verificarClicEnBoton(mouseX, mouseY, 300, 110, 120, 24)) {
            reproducirSonidoClickAleatorio();
            estadoActual = ESTADO_MENU;
        }
    }

    /**
     * Dibuja y controla la pantalla de resultado final (Game Over o Victoria),
     * mostrando puntaje obtenido, ronda alcanzada, mejor puntaje historico y botones
     * para jugar de nuevo o volver al menu (Seccion 5 y Figura 4).
     *
     * @param nuevoClic  indica si hubo un clic del mouse en este fotograma.
     * @param mouseX     posicion X del mouse.
     * @param mouseY     posicion Y del mouse.
     * @param esVictoria true si el resultado fue Victoria, false si fue Derrota (Game Over).
     */
    public static void actualizarPantallaResultado(
            boolean nuevoClic, double mouseX, double mouseY, boolean esVictoria) {

        // Fondo oscuro como en la Figura 4 del enunciado
        StdDraw.clear(new Color(22, 22, 22));

        // Titulo indicando claramente si fue Victoria o Derrota
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 22));
        if (esVictoria) {
            StdDraw.setPenColor(new Color(80, 220, 100));
            StdDraw.text(300, 430, "¡VICTORIA! PARTIDA COMPLETADA");
        } else {
            StdDraw.setPenColor(new Color(220, 50, 50));
            StdDraw.text(300, 430, "GAME OVER");
        }

        // Mostrar puntaje obtenido, ronda alcanzada y mejor puntaje historico
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 15));
        StdDraw.text(300, 365, "Puntaje obtenido: " + puntajeActual);
        StdDraw.text(300, 335, "Ronda alcanzada: " + rondaActual);
        StdDraw.text(300, 305, "Mejor puntaje actual: " + mejorPuntaje);

        // Botones "Jugar de nuevo" y "Volver al menu" (Figura 4)
        dibujarBotonMenu(300, 215, 115, 22, "Jugar de nuevo", mouseX, mouseY);
        dibujarBotonMenu(300, 155, 115, 22, "Volver al menu", mouseX, mouseY);

        if (nuevoClic) {
            if (verificarClicEnBoton(mouseX, mouseY, 300, 215, 115, 22)) {
                reproducirSonidoClickAleatorio();
                iniciarNuevaPartida();
            } else if (verificarClicEnBoton(mouseX, mouseY, 300, 155, 115, 22)) {
                reproducirSonidoClickAleatorio();
                estadoActual = ESTADO_MENU;
            }
        }
    }

    /**
     * Dibuja un boton rectangular interactivo con borde negro y efecto visual al pasar el mouse.
     *
     * @param cx        coordenada X del centro del boton.
     * @param cy        coordenada Y del centro del boton.
     * @param semiAncho mitad del ancho del boton.
     * @param semiAlto  mitad del alto del boton.
     * @param texto     etiqueta de texto mostrada dentro del boton.
     * @param mouseX    coordenada X actual del mouse.
     * @param mouseY    coordenada Y actual del mouse.
     */
    public static void dibujarBotonMenu(
            double cx, double cy, double semiAncho, double semiAlto,
            String texto, double mouseX, double mouseY) {

        boolean mouseEncima = verificarClicEnBoton(mouseX, mouseY, cx, cy, semiAncho, semiAlto);
        if (mouseEncima) {
            StdDraw.setPenColor(new Color(175, 175, 175));
        } else {
            StdDraw.setPenColor(new Color(200, 200, 200));
        }
        StdDraw.filledRectangle(cx, cy, semiAncho, semiAlto);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.rectangle(cx, cy, semiAncho, semiAlto);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 14));
        StdDraw.text(cx, cy - 4, texto);
    }
    /**
     * Escoge y reproduce un sonido aleatorio cada vez que el jugador hace clic
     */
    /**
     * Escoge y reproduce un sonido aleatorio cada vez que el jugador hace clic,
     * protegiendo la ejecucion si Linux no tiene linea de audio ALSA activa.
     */

    //para la reproduccion de sonido no me funciono en linux y no me dio tiempo de solucionarlo lo unico que pude aser es en caso de fallar es que continue el uego sin sonido

    public static void reproducirSonidoClickAleatorio() {
        String[] sonidosClick = {
                "resources/pipebang.wav",
                "resources/pipebang.wav",
                "resources/pipebang.wav"
        };
        int indiceAzar = (int) (Math.random() * sonidosClick.length);

        try {
            StdAudio.playInBackground(sonidosClick[indiceAzar]);
        } catch (Throwable errorAudio) {
            // Ignora el error de tarjeta de sonido en Linux/Flatpak para no cerrar el juego
        }
    }

    /**
     * Escoge y reproduce una cancion aleatoria cuando el jugador gana la partida.
     */
    public static void reproducirCancionVictoriaAleatoria() {
        String[] cancionesVictoria = {
                "resources/pipebang.wav",
                "resources/pipebang.wav",
                "resources/pipebang.wav"
        };
        int indiceCancion = (int) (Math.random() * cancionesVictoria.length);

        try {
            StdAudio.playInBackground(cancionesVictoria[indiceCancion]);
        } catch (Throwable errorAudio) {
            // Ignora el error de tarjeta de sonido en Linux/Flatpak para no cerrar el juego
        }
    }

    /**
     * Genera un arreglo de muestras de audio (onda sinusoidal) para una frecuencia
     * y duracion determinadas, compatible con StdAudio.play().
     *
     * @param frecuencia frecuencia de la nota en Hertz (Hz).
     * @param segundos   duracion del sonido en segundos.
     * @return arreglo de muestras de audio entre -1.0 y 1.0.
     */
    public static double[] generarTono(double frecuencia, double segundos) {
        int frecuenciaMuestreo = 44100;
        int totalMuestras = (int) (frecuenciaMuestreo * segundos);
        double[] muestras = new double[totalMuestras];
        for (int i = 0; i < totalMuestras; i++) {
            muestras[i] = 0.35 * Math.sin(2.0 * Math.PI * i * frecuencia / frecuenciaMuestreo);
        }
        return muestras;
    }
}