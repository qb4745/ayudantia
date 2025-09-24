package com.agencia.service;

import com.agencia.model.Cliente;
import com.agencia.model.Habitacion;
import com.agencia.model.Hotel;
import com.agencia.model.Reserva;

import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;

public class ServicioReservas {

    // Cambiamos los HashMap por ArrayList
    private final List<Hotel> hoteles = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();

    /**
     * Busca un hotel por su ID. Ahora recorre la lista.
     * @param id El ID del hotel a buscar.
     * @return El objeto Hotel si se encuentra, o null si no.
     */
    public Hotel findHotelById(String id) {
        for (Hotel hotel : hoteles) {
            // Usamos equalsIgnoreCase para una búsqueda más flexible (ej. "H1" == "h1")
            if (hotel.getIdHotel().equalsIgnoreCase(id)) {
                return hotel;
            }
        }
        return null; // No se encontró el hotel
    }

    /**
     * Busca un cliente por su ID. Ahora recorre la lista.
     * @param id El ID del cliente a buscar.
     * @return El objeto Cliente si se encuentra, o null si no.
     */
    public Cliente findClienteById(String id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equalsIgnoreCase(id)) {
                return cliente;
            }
        }
        return null; // No se encontró el cliente
    }

    /**
     * Registra un nuevo hotel, verificando primero que el ID no exista.
     * @return true si el registro fue exitoso, false si el ID ya existía.
     */
    public boolean registerHotel(String id, String nombre) {
        // Verificamos si ya existe un hotel con ese ID antes de agregarlo
        if (findHotelById(id) != null) {
            return false; // Error: ID ya existe
        }
        hoteles.add(new Hotel(id, nombre));
        return true;
    }

    /**
     * Registra un nuevo cliente, verificando primero que el ID no exista.
     * @return true si el registro fue exitoso, false si el ID ya existía.
     */
    public boolean registerCliente(String id, String nombre) {
        if (findClienteById(id) != null) {
            return false; // Error: ID ya existe
        }
        clientes.add(new Cliente(id, nombre));
        return true;
    }

    public boolean addHabitacionToHotel(String idHotel, String numHabitacion, int precio) {
        Hotel hotel = findHotelById(idHotel);
        if (hotel != null && hotel.buscarHabitacion(numHabitacion) == null) {
            hotel.agregarHabitacion(new Habitacion(numHabitacion, precio));
            return true;
        }
        return false;
    }

    public String createReserva(String idCliente, String idHotel, String numHabitacion, int noches) {
        Cliente cliente = findClienteById(idCliente);
        if (cliente == null) return "ERROR_CLIENTE_NO_ENCONTRADO";

        Hotel hotel = findHotelById(idHotel);
        if (hotel == null) return "ERROR_HOTEL_NO_ENCONTRADO";

        Habitacion habitacion = hotel.buscarHabitacion(numHabitacion);
        if (habitacion == null) return "ERROR_HABITACION_NO_ENCONTRADA";
        if (!habitacion.isDisponible()) return "ERROR_HABITACION_NO_DISPONIBLE";

        LocalDate fechaReserva = LocalDate.now();
        if (cliente.tieneReserva(hotel, habitacion, fechaReserva)) {
            return "ERROR_RESERVA_DUPLICADA";
        }

        Reserva nuevaReserva = new Reserva(hotel, habitacion, cliente, fechaReserva, noches);
        cliente.agregarReserva(nuevaReserva);
        habitacion.setDisponible(false);
        return "RESERVA_CREADA_EXITOSAMENTE";
    }
    
    public boolean releaseHabitacion(String idCliente, String idHotel, String numHabitacion) {
        Hotel hotel = findHotelById(idHotel);
        if (hotel == null) return false;

        Habitacion habitacion = hotel.buscarHabitacion(numHabitacion);
        if (habitacion == null || habitacion.isDisponible()) return false;
        
        Cliente cliente = findClienteById(idCliente);
        if (cliente == null) return false;

        // Buscar y eliminar la reserva específica
        Reserva reservaAEliminar = null;
        for (Reserva reserva : cliente.getReservas()) {
            if (reserva.getHotel().getIdHotel().equalsIgnoreCase(idHotel) &&
                reserva.getHabitacion().getNumeroHabitacion().equals(numHabitacion)) {
                reservaAEliminar = reserva;
                break;
            }
        }

        if (reservaAEliminar != null) {
            cliente.getReservas().remove(reservaAEliminar);
            habitacion.setDisponible(true);
            return true;
        }

        return false; // No se encontró una reserva que coincida para ese cliente
    }

    // El método ahora devuelve una List en lugar de un Map
    public List<Hotel> getHoteles() {
        return hoteles;
    }

    public ArrayList<Reserva> getReservasByCliente(String idCliente) {
        Cliente cliente = findClienteById(idCliente);
        return (cliente != null) ? cliente.getReservas() : null;
    }
}