package com.agencia.model;

import java.util.ArrayList;

public class Hotel {
    private String idHotel;
    private String nombre;
    private ArrayList<Habitacion> habitaciones;

    public Hotel(String idHotel, String nombre) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.habitaciones = new ArrayList<>();
    }

    // Getters
    public String getIdHotel() {
        return idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    // Método para agregar una habitación al hotel
    public void agregarHabitacion(Habitacion habitacion) {
        this.habitaciones.add(habitacion);
    }

    // Método para listar habitaciones disponibles
    public void listarHabitacionesDisponibles() {
        System.out.println("Habitaciones disponibles en " + nombre + ":");
        boolean encontradas = false;
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.isDisponible()) {
                System.out.println("- " + habitacion);
                encontradas = true;
            }
        }
        if (!encontradas) {
            System.out.println("No hay habitaciones disponibles en este momento.");
        }
    }
    
    public Habitacion buscarHabitacion(String numeroHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumeroHabitacion().equals(numeroHabitacion)) {
                return habitacion;
            }
        }
        return null; // Retorna null si no se encuentra la habitación
    }


    @Override
    public String toString() {
        return "Hotel [ID=" + idHotel + ", Nombre=" + nombre + ", Habitaciones=" + habitaciones.size() + "]";
    }
}
