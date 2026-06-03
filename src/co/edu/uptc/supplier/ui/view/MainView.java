package co.edu.uptc.supplier.ui.view;

import java.util.Scanner;

/**
 * Vista principal de la aplicación. Muestra el menú raíz que permite navegar a
 * la administración de cada una de las tres entidades: proveedores, empresas y
 * pedidos.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class MainView {

    /** Lector de entrada estándar. */
    private Scanner sc;

    /** Vista de administración de proveedores. */
    private SupplierView supplierView;

    /** Vista de administración de empresas. */
    private EnterpriseView enterpriseView;

    /** Vista de administración de pedidos. */
    private OrderView orderView;

    /**
     * Constructor por defecto. Inicializa el lector y las vistas hijas.
     */
    public MainView() {
        this.sc = new Scanner(System.in);
        this.supplierView = new SupplierView();
        this.enterpriseView = new EnterpriseView();
        this.orderView = new OrderView();
    }

    /**
     * Ejecuta el bucle principal de la aplicación mostrando el menú raíz hasta
     * que el usuario elija salir.
     */
    public void runApp() {
        StringBuilder menu = new StringBuilder();
        menu.append("\n===== SISTEMA DE GESTIÓN DE PROVEEDORES =====");
        menu.append("\n[1]. Administración de proveedores");
        menu.append("\n[2]. Administración de empresas");
        menu.append("\n[3]. Administración de pedidos");
        menu.append("\n[4]. Salir");
        menu.append("\nSeleccione una opción: ");

        boolean flag = true;
        do {
            System.out.println(menu.toString());
            String strOption = sc.nextLine();

            /* Valida que se ingrese un único dígito */
            if (!strOption.matches("^\\d$")) {
                System.out.println("Opción incorrecta. Ingrese un número del menú.");
                continue;
            }

            int op = Integer.parseInt(strOption);
            switch (op) {
                case 1 -> supplierView.menu();
                case 2 -> enterpriseView.menu();
                case 3 -> orderView.menu();
                case 4 -> {
                    flag = false;
                    System.out.println("¡Hasta pronto!");
                }
                default -> System.out.println("Opción incorrecta.");
            }
        } while (flag);
    }
}
