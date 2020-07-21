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
public class Total_Recuperados {
    
    private String code;
    private String total_recuperados;

    public Total_Recuperados(String code, String total_recuperados) {
        this.code = code;
        this.total_recuperados = total_recuperados;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal_recuperados() {
        return total_recuperados;
    }

    public void setTotal_recuperados(String total_recuperados) {
        this.total_recuperados = total_recuperados;
    }

    
    
}
