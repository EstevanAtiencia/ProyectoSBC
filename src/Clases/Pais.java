/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Estevan
 */
public class Pais {
 
    
    private String iso_code;
    private String nombre;
    private String latitud;
    private String longitud;
    private String poblacion;
    private String per_capita;
    private Continente con;

    public Pais(String iso_code, String nombre, String latitud, String longitud, String poblacion, String per_capita, Continente con) {
        this.iso_code = iso_code;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.poblacion = poblacion;
        this.per_capita = per_capita;
        this.con = con;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getPer_capita() {
        return per_capita;
    }

    public void setPer_capita(String per_capita) {
        this.per_capita = per_capita;
    }

    public Continente getCon() {
        return con;
    }

    public void setCon(Continente con) {
        this.con = con;
    }
    
    
    
    
    
}
