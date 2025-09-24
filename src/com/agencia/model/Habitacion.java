package com.agencia.model;

public class Habitacion {
    private String numeroHabitacion;
    private int precioNoche;
    private boolean disponible;

    public Habitacion(String numeroHabitacion, int precioNoche) {
        this.numeroHabitacion = numeroHabitacion;
        this.precioNoche = precioNoche;
        this.disponible = true; // Por defecto, una habitación nueva está disponible
    }

    // Getters
    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public int getPrecioNoche() {
        return precioNoche;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // Setter para cambiar la disponibilidad
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Habitacion [Número=" + numeroHabitacion + ", Precio por Noche=" + precioNoche + ", Disponible=" + disponible + "]";
    }
}

