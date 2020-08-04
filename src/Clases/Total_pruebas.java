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
public class Total_pruebas {
    private String code;
    private String total_pruebas;
    private String new_pruebas;

    public Total_pruebas(String code, String total_pruebas, String new_pruebas) {
        this.code = code;
        this.total_pruebas = total_pruebas;
        this.new_pruebas = new_pruebas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal_pruebas() {
        return total_pruebas;
    }

    public void setTotal_pruebas(String total_pruebas) {
        this.total_pruebas = total_pruebas;
    }

    public String getNew_pruebas() {
        return new_pruebas;
    }

    public void setNew_pruebas(String new_pruebas) {
        this.new_pruebas = new_pruebas;
    }
    
    
    
}
