package com.agencia.service;

import com.agencia.model.Cliente;
import com.agencia.model.Habitacion;
import com.agencia.model.Hotel;
import com.agencia.model.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicioReservas {

    private final Map<String, Hotel> hoteles = new HashMap<>();
    private final Map<String, Cliente> clientes = new HashMap<>();

    public boolean registerHotel(String id, String nombre) {
        if (hoteles.containsKey(id.toUpperCase())) {
            return false; // ID ya existe
        }
        hoteles.put(id.toUpperCase(), new Hotel(id, nombre));
        return true;
    }

    public boolean registerCliente(String id, String nombre) {
        if (clientes.containsKey(id.toUpperCase())) {
            return false; // ID ya existe
        }
        clientes.put(id.toUpperCase(), new Cliente(id, nombre));
        return true;
    }

    public Hotel findHotelById(String id) {
        return hoteles.get(id.toUpperCase());
    }

    public Cliente findClienteById(String id) {
        return clientes.get(id.toUpperCase());
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
    
    public boolean releaseHabitacion(String idHotel, String numHabitacion) {
        Hotel hotel = findHotelById(idHotel);
        if (hotel != null) {
            Habitacion habitacion = hotel.buscarHabitacion(numHabitacion);
            if (habitacion != null && !habitacion.isDisponible()) {
                habitacion.setDisponible(true);
                // Opcional: remover la reserva de la lista del cliente si es necesario
                return true;
            }
        }
        return false;
    }

    public Map<String, Hotel> getHoteles() {
        return hoteles;
    }

    public ArrayList<Reserva> getReservasByCliente(String idCliente) {
        Cliente cliente = findClienteById(idCliente);
        return (cliente != null) ? cliente.getReservas() : null;
    }
}
