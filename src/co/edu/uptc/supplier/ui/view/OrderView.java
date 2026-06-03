package co.edu.uptc.supplier.ui.view;

import java.util.List;
import java.util.Scanner;

import co.edu.uptc.supplier.domain.Order;
import co.edu.uptc.supplier.dto.ResultDTO;
import co.edu.uptc.supplier.enums.ClientType;
import co.edu.uptc.supplier.ui.controller.OrderController;

/**
 * Vista de administración de la entidad {@link Order}. Gestiona la interacción
 * por consola y delega la lógica en el {@link OrderController}.
 *
 * @author Jesús Ruiz y Noah Padilla
 * @version 1.0
 */
public class OrderView {

    /** Lector de entrada estándar. */
    private Scanner scanner;

    /** Controlador de pedidos. */
    private OrderController controller;

    /**
     * Constructor por defecto. Inicializa el lector y el controlador.
     */
    public OrderView() {
        this.scanner = new Scanner(System.in);
        this.controller = new OrderController();
    }

    /**
     * Muestra el menú de operaciones CRUD para pedidos y procesa la opción
     * elegida hasta que el usuario decida volver.
     */
    public void menu() {
        int option = -1;
        do {
            StringBuilder menu = new StringBuilder();
            menu.append("\n----- MENÚ DE PEDIDOS -----");
            menu.append("\n[1]. Crear pedido");
            menu.append("\n[2]. Mostrar todos los pedidos");
            menu.append("\n[3]. Buscar pedido por id");
            menu.append("\n[4]. Actualizar pedido");
            menu.append("\n[5]. Eliminar pedido");
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
     * Solicita los datos de un pedido y solicita su creación al controlador.
     */
    private void create() {
        System.out.println("* Id del pedido (numérico): ");
        String idOrder = scanner.nextLine();
        System.out.println("* Tipo de cliente " + clientTypeOptions() + ": ");
        String clientType = scanner.nextLine();
        System.out.println("* Nombre del cliente (solo letras): ");
        String nameClient = scanner.nextLine();
        System.out.println("* Id del proveedor (numérico): ");
        String idSupplier = scanner.nextLine();
        System.out.println("* Dirección del cliente: ");
        String addressClient = scanner.nextLine();
        System.out.println("* Descripción del pedido: ");
        String order = scanner.nextLine();

        ResultDTO<Order> result = controller.addOrder(idOrder, clientType, nameClient,
                idSupplier, addressClient, order);
        printResult(result);
    }

    /**
     * Muestra todos los pedidos registrados.
     */
    private void listAll() {
        List<Order> orders = controller.listOrders();
        System.out.println("\nLista de pedidos:");
        if (orders.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        orders.forEach(System.out::println);
    }

    /**
     * Solicita un id y muestra el pedido correspondiente, si existe.
     */
    private void findById() {
        System.out.println("* Id del pedido a buscar: ");
        String idOrder = scanner.nextLine();
        ResultDTO<Order> result = controller.findById(idOrder);
        if (!result.isSuccessful()) {
            printErrors(result);
            return;
        }
        System.out.println(result.getData() != null
                ? result.getData() : "El pedido no fue encontrado.");
    }

    /**
     * Solicita un id, muestra los valores actuales y permite actualizarlos.
     */
    private void update() {
        System.out.println("* Id del pedido a actualizar: ");
        String idOrder = scanner.nextLine();
        ResultDTO<Order> found = controller.findById(idOrder);
        if (!found.isSuccessful()) {
            printErrors(found);
            return;
        }
        Order current = found.getData();
        System.out.println("Tipo de cliente (" + current.getClientType() + ") "
                + clientTypeOptions() + " [enter para conservar]: ");
        String clientType = scanner.nextLine();
        System.out.println("Nombre del cliente (" + current.getNameClient()
                + ") [enter para conservar]: ");
        String nameClient = scanner.nextLine();
        System.out.println("Id del proveedor (" + current.getIdSupplier()
                + ") [enter para conservar]: ");
        String idSupplier = scanner.nextLine();
        System.out.println("Dirección del cliente (" + current.getAddressClient()
                + ") [enter para conservar]: ");
        String addressClient = scanner.nextLine();
        System.out.println("Descripción del pedido (" + current.getOrder()
                + ") [enter para conservar]: ");
        String order = scanner.nextLine();

        ResultDTO<Order> result = controller.updateOrder(idOrder, clientType, nameClient,
                idSupplier, addressClient, order);
        printResult(result);
    }

    /**
     * Solicita un id y solicita la eliminación del pedido correspondiente.
     */
    private void delete() {
        System.out.println("* Id del pedido a eliminar: ");
        String idOrder = scanner.nextLine();
        ResultDTO<Order> result = controller.deleteOrder(idOrder);
        printResult(result);
    }

    /**
     * Construye una cadena con los valores válidos de {@link ClientType} para
     * orientar al usuario.
     *
     * @return texto con las opciones disponibles
     */
    private String clientTypeOptions() {
        StringBuilder sb = new StringBuilder("(");
        ClientType[] values = ClientType.values();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i].name());
            if (i < values.length - 1) {
                sb.append(" / ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Imprime el resultado de una operación: el mensaje de éxito o los errores.
     *
     * @param result resultado de la operación
     */
    private void printResult(ResultDTO<Order> result) {
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
    private void printErrors(ResultDTO<Order> result) {
        System.out.println("La operación no se completó por:");
        result.getListMessageError().forEach(System.out::println);
    }
}
