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
public class Total_casos {
    
    //generar un codigo
    private String code;
    private String fecha;
    private String totalcasos;
    private String newcasos;
    private Pais pais;
    private Dataset ds;
    //fuente de datos

    public Total_casos(String code, String fecha, String totalcasos, String newcasos, Pais pais, Dataset ds) {
        this.code = code;
        this.fecha = fecha;
        this.totalcasos = totalcasos;
        this.newcasos = newcasos;
        this.pais = pais;
        this.ds = ds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotalcasos() {
        return totalcasos;
    }

    public void setTotalcasos(String totalcasos) {
        this.totalcasos = totalcasos;
    }

    public String getNewcasos() {
        return newcasos;
    }

    public void setNewcasos(String newcasos) {
        this.newcasos = newcasos;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Dataset getDs() {
        return ds;
    }

    public void setDs(Dataset ds) {
        this.ds = ds;
    }

    
    
}
