package co.edu.uptc.supplier.ui;

import co.edu.uptc.supplier.ui.view.MainView;

/**
 * Clase de arranque de la aplicación. Contiene el método {@code main}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.runApp();
    }
}
