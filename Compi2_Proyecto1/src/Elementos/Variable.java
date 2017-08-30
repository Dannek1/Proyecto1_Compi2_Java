/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elementos;

/**
 *
 * @author Dannek
 */
public class Variable {
    String Nombre;
    private String Tipo;
    private String Valor;
    private String Ambito;
    
    Variable Siguiente;
    Variable Anterior;
    
    public Variable(String N,String t,String am){
        Nombre=N;
        Tipo=t;
        Ambito=am;
        
        Siguiente=null;
        Anterior=null;
    }

    /**
     * @return the Valor
     */
    public String getValor() {
        return Valor;
    }

    /**
     * @param Valor the Valor to set
     */
    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the Ambito
     */
    public String getAmbito() {
        return Ambito;
    }

    /**
     * @param Ambito the Ambito to set
     */
    public void setAmbito(String Ambito) {
        this.Ambito = Ambito;
    }
    
}
