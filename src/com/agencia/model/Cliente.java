package com.agencia.model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Cliente {
    private String idCliente;
    private String nombre;
    private ArrayList<Reserva> reservas;

    public Cliente(String idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.reservas = new ArrayList<>();
    }

    // Getters
    public String getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    // Método para agregar una reserva
    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    // Método para verificar si ya existe una reserva (regla de negocio)
    public boolean tieneReserva(Hotel hotel, Habitacion habitacion, LocalDate fecha) {
        for (Reserva reserva : reservas) {
            if (reserva.getHotel().getIdHotel().equals(hotel.getIdHotel()) && 
                reserva.getHabitacion().getNumeroHabitacion().equals(habitacion.getNumeroHabitacion()) && 
                reserva.getFecha().equals(fecha)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente [ID=" + idCliente + ", Nombre=" + nombre + "]";
    }
}
