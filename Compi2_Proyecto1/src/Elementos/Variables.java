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
public class Variables {
    Variable Cabeza;
    Variable Ultimo;
    Variable Aux;
    
    public Variables(){
        Cabeza=null;
        Ultimo=null;
        Aux=null;
    }
    
    public void Insertar(Variable nuevo){
        if(Cabeza==null){
            Cabeza=nuevo;
        }else if(Ultimo==null){
            Ultimo=nuevo;
            
            Cabeza.Siguiente=Ultimo;
            Ultimo.Anterior=Cabeza;
            
        }else{
            Aux=nuevo;
            
            Ultimo.Siguiente=Aux;
            Aux.Anterior=Ultimo;
            
            Ultimo=Aux;
        }
    }
    
    public Variable Buscar(String nombre){
        Variable Respuesta=null;
        Aux=Cabeza;
        boolean seguir=true;
        
        while(seguir){
            if(Aux.Nombre.equals(nombre)){
                Respuesta=Aux;
                seguir=false;
            }else{
                Aux=Aux.Siguiente;
            }
        }      
                
        return Respuesta;
    }
}
