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
    
  
    private String code;
    private String fecha;
    private String totalcasos;
    private String newcasos;
    private Pais pais;
    private Dataset ds;
   
    private Total_Activos ta;
    private Total_muertes tm;
    private Total_hospitalizados th;
    private Total_Recuperados tr;
    
/*
    public Total_casos(String code, String fecha, String totalcasos, String newcasos, Pais pais, Dataset ds) {
        this.code = code;
        this.fecha = fecha;
        this.totalcasos = totalcasos;
        this.newcasos = newcasos;
        this.pais = pais;
        this.ds = ds;
    }
*/

    public Total_casos(String code, String fecha, String totalcasos, String newcasos, Pais pais, Dataset ds, Total_Activos ta, Total_muertes tm, Total_hospitalizados th, Total_Recuperados tr) {
        this.code = code;
        this.fecha = fecha;
        this.totalcasos = totalcasos;
        this.newcasos = newcasos;
        this.pais = pais;
        this.ds = ds;
        this.ta = ta;
        this.tm = tm;
        this.th = th;
        this.tr = tr;
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

    public Total_Activos getTa() {
        return ta;
    }

    public void setTa(Total_Activos ta) {
        this.ta = ta;
    }

    public Total_muertes getTm() {
        return tm;
    }

    public void setTm(Total_muertes tm) {
        this.tm = tm;
    }

    public Total_hospitalizados getTh() {
        return th;
    }

    public void setTh(Total_hospitalizados th) {
        this.th = th;
    }

    public Total_Recuperados getTr() {
        return tr;
    }

    public void setTr(Total_Recuperados tr) {
        this.tr = tr;
    }

    
    
}
