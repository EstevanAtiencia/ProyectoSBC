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
public class Total_muertes {
    
    
    private String Code;
    private String total_muertes;
    private String new_muertes;

    public Total_muertes(String Code, String total_muertes, String new_muertes) {
        this.Code = Code;
        this.total_muertes = total_muertes;
        this.new_muertes = new_muertes;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getTotal_muertes() {
        return total_muertes;
    }

    public void setTotal_muertes(String total_muertes) {
        this.total_muertes = total_muertes;
    }

    public String getNew_muertes() {
        return new_muertes;
    }

    public void setNew_muertes(String new_muertes) {
        this.new_muertes = new_muertes;
    }

   
    
}
