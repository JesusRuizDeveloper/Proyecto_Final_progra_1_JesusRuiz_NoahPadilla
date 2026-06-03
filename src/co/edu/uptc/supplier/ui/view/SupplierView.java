package co.edu.uptc.supplier.ui.view;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.supplier.domain.Supplier;
import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.ui.controller.SupplierController;

/**
 * Vista de administración de la entidad {@link Supplier}. Gestiona la
 * interacción por consola (entrada/salida) y delega la lógica en el
 * {@link SupplierController}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class SupplierView {

    /** Lector de entrada estándar. */
    private Scanner scanner;

    /** Controlador de proveedores. */
    private SupplierController controller;

    /**
     * Constructor por defecto. Inicializa el lector y el controlador.
     */
    public SupplierView() {
        this.scanner = new Scanner(System.in);
        this.controller = new SupplierController();
    }

    /**
     * Muestra el menú de operaciones CRUD para proveedores y procesa la opción
     * elegida hasta que el usuario decida volver.
     */
    public void menu() {
        int option = -1;
        do {
            StringBuilder menu = new StringBuilder();
            menu.append("\n----- MENÚ DE PROVEEDORES -----");
            menu.append("\n[1]. Crear proveedor");
            menu.append("\n[2]. Mostrar todos los proveedores");
            menu.append("\n[3]. Buscar proveedor por id");
            menu.append("\n[4]. Actualizar proveedor");
            menu.append("\n[5]. Eliminar proveedor");
            menu.append("\n[0]. Volver");
            menu.append("\nSeleccione una opción: ");
            System.out.println(menu.toString());

            String strOption = scanner.nextLine();
            if (!strOption.matches("^\\d$")) {
                System.out.println("Opción incorrecta.");
                continue;
            }
            option = Integer.parseInt(strOption);
            switch (option) {
                case 1 -> create();
                case 2 -> listAll();
                case 3 -> findById();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción incorrecta.");
            }
        } while (option != 0);
    }

    /**
     * Solicita los datos de un proveedor y solicita su creación al controlador.
     */
    private void create() {
        System.out.println("* Id (numérico): ");
        String id = scanner.nextLine();
        System.out.println("* Edad (numérico): ");
        String age = scanner.nextLine();
        System.out.println("* Nombre (solo letras): ");
        String name = scanner.nextLine();
        System.out.println("* ¿Activo? (true/false): ");
        String isActive = scanner.nextLine();
        System.out.println("* Comisión (decimal, ej: 10.5): ");
        String comission = scanner.nextLine();

        ResultDTO<Supplier> result = controller.addSupplier(id, age, name, isActive, comission);
        printResult(result);
    }

    /**
     * Muestra todos los proveedores registrados.
     */
    private void listAll() {
        List<Supplier> suppliers = controller.listSuppliers();
        System.out.println("\nLista de proveedores:");
        if (suppliers.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        suppliers.forEach(System.out::println);
    }

    /**
     * Solicita un id y muestra el proveedor correspondiente, si existe.
     */
    private void findById() {
        System.out.println("* Id del proveedor a buscar: ");
        String id = scanner.nextLine();
        ResultDTO<Supplier> result = controller.findById(id);
        if (!result.isSuccessful()) {
            printErrors(result);
            return;
        }
        /* Uso del operador ternario para mostrar el resultado */
        System.out.println(result.getData() != null
                ? result.getData() : "El proveedor no fue encontrado.");
    }

    /**
     * Solicita un id, muestra los valores actuales y permite actualizarlos.
     */
    private void update() {
        System.out.println("* Id del proveedor a actualizar: ");
        String id = scanner.nextLine();
        ResultDTO<Supplier> found = controller.findById(id);
        if (!found.isSuccessful()) {
            printErrors(found);
            return;
        }
        Supplier current = found.getData();
        System.out.println("Edad (" + current.getAge() + ") [enter para conservar]: ");
        String age = scanner.nextLine();
        System.out.println("Nombre (" + current.getName() + ") [enter para conservar]: ");
        String name = scanner.nextLine();
        System.out.println("Activo (" + current.isActive() + ") [enter para conservar]: ");
        String isActive = scanner.nextLine();
        System.out.println("Comisión (" + current.getComission() + ") [enter para conservar]: ");
        String comission = scanner.nextLine();

        ResultDTO<Supplier> result = controller.updateSupplier(id, age, name, isActive, comission);
        printResult(result);
    }

    /**
     * Solicita un id y solicita la eliminación del proveedor correspondiente.
     */
    private void delete() {
        System.out.println("* Id del proveedor a eliminar: ");
        String id = scanner.nextLine();
        ResultDTO<Supplier> result = controller.deleteSupplier(id);
        printResult(result);
    }

    /**
     * Imprime el resultado de una operación: el mensaje de éxito o la lista de
     * errores de validación.
     *
     * @param result resultado de la operación
     */
    private void printResult(ResultDTO<Supplier> result) {
        if (result.isSuccessful()) {
            System.out.println(result.getMessage());
        } else {
            printErrors(result);
        }
    }

    /**
     * Imprime la lista de errores de validación de un resultado.
     *
     * @param result resultado con los errores a mostrar
     */
    private void printErrors(ResultDTO<Supplier> result) {
        System.out.println("La operación no se completó por:");
        result.getListMessageError().forEach(System.out::println);
    }
}
