package com.example.t13;

public class Persona {
    private String nombre;
    private char genero;

    public Persona(String Nombre, char gnero)
    {
        this.nombre = Nombre;
        this.genero = gnero;
    }
    public String getNombre()
    {
        return nombre;
    }
    public char getGenero()
    {
        return genero;
    }

}
