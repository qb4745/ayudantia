package com.agencia.model;

import java.time.LocalDate;

public class Reserva {
    private Hotel hotel;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate fecha;
    private int noches;

    public Reserva(Hotel hotel, Habitacion habitacion, Cliente cliente, LocalDate fecha, int noches) {
        this.hotel = hotel;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fecha = fecha;
        this.noches = noches;
    }

    // Getters
    public Hotel getHotel() {
        return hotel;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getNoches() {
        return noches;
    }

    @Override
    public String toString() {
        return "Reserva [Hotel=" + hotel.getNombre() + ", Habitación=" + habitacion.getNumeroHabitacion() + 
               ", Fecha=" + fecha + ", Noches=" + noches + "]";
    }
}
