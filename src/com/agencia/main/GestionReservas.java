package com.agencia.main;

import com.agencia.model.Habitacion;
import com.agencia.model.Hotel;
import com.agencia.model.Reserva;
import com.agencia.service.ServicioReservas;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionReservas {

    // --- Explicación de 'static final' para principiantes ---
    //
    // Usamos 'static' y 'final' para nuestras variables principales por dos razones muy importantes:
    //
    // 1. 'static': Esta palabra clave significa que la variable pertenece a la CLASE 'GestionReservas' en su conjunto,
    //    y no a una instancia particular de ella. En términos simples, asegura que solo exista UNA ÚNICA COPIA
    //    de 'servicio' y UNA ÚNICA COPIA de 'scanner' para toda nuestra aplicación.
    //    - Para 'servicio': Es crucial tener un único servicio que maneje todos nuestros datos (hoteles, clientes).
    //      Si creáramos un nuevo servicio cada vez, perderíamos toda la información guardada.
    //    - Para 'scanner': Abrir múltiples 'scanners' para leer de la consola puede causar problemas y es ineficiente.
    //      Con 'static', nos aseguramos de usar siempre el mismo para leer la entrada del usuario.
    //
    // 2. 'final': Esta palabra clave convierte la variable en una CONSTANTE. Una vez que le asignamos un valor
    //    (aquí mismo, al declararla), no podemos volver a asignarle un valor diferente en ningún otro lugar del código.
    //    Esto nos protege de errores accidentales, como que en alguna parte del programa se intente hacer
    //    'servicio = new ServicioReservas();' de nuevo, lo que borraría todos nuestros datos.
    //
    // En resumen: 'static final' nos da una única instancia compartida y constante de estos recursos importantes.
    private static final ServicioReservas servicio = new ServicioReservas();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1: registerHotel(); break;
                    case 2: registerCliente(); break;
                    case 3: addHabitacionToHotel(); break;
                    case 4: listHoteles(); break;
                    case 5: listHabitacionesDisponibles(); break;
                    case 6: createReserva(); break;
                    case 7: listReservasCliente(); break;
                    case 8: releaseHabitacion(); break;
                    case 9: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
                scanner.nextLine(); // Limpiar el buffer de entrada
                opcion = 0; // Resetear opción para continuar en el bucle
            }
            System.out.println(); // Espacio para mejorar la legibilidad
        } while (opcion != 9);
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("--- Menú de Gestión de Reservas ---");
        System.out.println("1. Registrar hotel");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Agregar habitación a un hotel");
        System.out.println("4. Listar hoteles");
        System.out.println("5. Listar habitaciones disponibles de un hotel");
        System.out.println("6. Crear reserva");
        System.out.println("7. Listar reservas de un cliente");
        System.out.println("8. Liberar habitación");
        System.out.println("9. Salir");
    }

    private static void registerHotel() {
        System.out.print("Ingrese ID del hotel: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre del hotel: ");
        String nombre = scanner.nextLine();
        if (servicio.registerHotel(id, nombre)) {
            System.out.println("¡Hotel registrado exitosamente!");
        } else {
            System.out.println("Error: Ya existe un hotel con ese ID.");
        }
    }

    private static void registerCliente() {
        System.out.print("Ingrese ID del cliente: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre del cliente: ");
        String nombre = scanner.nextLine();
        if (servicio.registerCliente(id, nombre)) {
            System.out.println("¡Cliente registrado exitosamente!");
        } else {
            System.out.println("Error: Ya existe un cliente con ese ID.");
        }
    }

    private static void addHabitacionToHotel() {
        System.out.print("Ingrese ID del hotel para agregar habitación: ");
        String idHotel = scanner.nextLine();
        if (servicio.findHotelById(idHotel) == null) {
            System.out.println("Error: Hotel no encontrado.");
            return;
        }
        System.out.print("Ingrese número de la habitación: ");
        String numHabitacion = scanner.nextLine();
        try {
            System.out.print("Ingrese precio por noche: ");
            int precio = scanner.nextInt();
            scanner.nextLine();
            if (servicio.addHabitacionToHotel(idHotel, numHabitacion, precio)) {
                System.out.println("¡Habitación agregada exitosamente!");
            } else {
                System.out.println("Error: La habitación ya existe en este hotel.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: El precio debe ser un número entero.");
            scanner.nextLine();
        }
    }

    private static void listHoteles() {
        System.out.println("--- Listado de Hoteles ---");
        var hoteles = servicio.getHoteles();
        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados.");
        } else {
            hoteles.forEach(System.out::println);
        }
    }

    private static void listHabitacionesDisponibles() {
        System.out.print("Ingrese ID del hotel: ");
        String idHotel = scanner.nextLine();
        Hotel hotel = servicio.findHotelById(idHotel);
        if (hotel != null) {
            hotel.listarHabitacionesDisponibles();
        } else {
            System.out.println("Error: Hotel no encontrado.");
        }
    }

    private static void createReserva() {
        System.out.print("Ingrese ID del cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese ID del hotel: ");
        String idHotel = scanner.nextLine();
        System.out.print("Ingrese número de habitación: ");
        String numHabitacion = scanner.nextLine();
        try {
            System.out.print("Ingrese cantidad de noches: ");
            int noches = scanner.nextInt();
            scanner.nextLine();
            String resultado = servicio.createReserva(idCliente, idHotel, numHabitacion, noches);
            switch (resultado) {
                case "RESERVA_CREADA_EXITOSAMENTE": System.out.println("¡Reserva creada exitosamente!"); break;
                case "ERROR_CLIENTE_NO_ENCONTRADO": System.out.println("Error: Cliente no encontrado."); break;
                case "ERROR_HOTEL_NO_ENCONTRADO": System.out.println("Error: Hotel no encontrado."); break;
                case "ERROR_HABITACION_NO_ENCONTRADA": System.out.println("Error: Habitación no encontrada."); break;
                case "ERROR_HABITACION_NO_DISPONIBLE": System.out.println("Error: La habitación no está disponible."); break;
                case "ERROR_RESERVA_DUPLICADA": System.out.println("Error: El cliente ya tiene una reserva para esta habitación hoy."); break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: El número de noches debe ser un número entero.");
            scanner.nextLine();
        }
    }

    private static void listReservasCliente() {
        System.out.print("Ingrese ID del cliente: ");
        String idCliente = scanner.nextLine();
        ArrayList<Reserva> reservas = servicio.getReservasByCliente(idCliente);
        if (reservas == null) {
            System.out.println("Error: Cliente no encontrado.");
        } else if (reservas.isEmpty()) {
            System.out.println("El cliente no tiene reservas.");
        } else {
            System.out.println("--- Reservas de " + servicio.findClienteById(idCliente).getNombre() + " ---");
            reservas.forEach(System.out::println);
        }
    }

    private static void releaseHabitacion() {
        System.out.print("Ingrese ID del cliente que libera la habitación: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese ID del hotel: ");
        String idHotel = scanner.nextLine();
        System.out.print("Ingrese número de habitación a liberar: ");
        String numHabitacion = scanner.nextLine();
        
        if (servicio.releaseHabitacion(idCliente, idHotel, numHabitacion)) {
            System.out.println("¡Habitación liberada y reserva eliminada exitosamente!");
        } else {
            System.out.println("Error: No se pudo liberar la habitación. Verifique los datos (cliente, hotel, habitación) o el estado de la habitación.");
        }
    }
}