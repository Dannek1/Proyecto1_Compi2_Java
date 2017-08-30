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
    
    public void Eliminar(String Nombre,String Ambito){
        Aux=Cabeza;
        
        boolean seguir=true;
        
        while(seguir){
            if(Aux.getAmbito().equals(Ambito)&&Aux.Nombre.equals(Nombre)){
                
                if(Aux==Cabeza){
                    Cabeza.Siguiente.Anterior=null;
                    Cabeza=Cabeza.Siguiente;
                    
                }else if(Aux==Ultimo){
                    Ultimo.Anterior.Siguiente=null;
                    Ultimo=Ultimo.Anterior;
                    
                }else{
                    Aux.Anterior.Siguiente=Aux.Siguiente;
                    Aux.Siguiente.Anterior=Aux.Anterior;
                }
                
                seguir=false;
            }else{
                if(Aux.Siguiente!=null){
                    Aux=Aux.Siguiente;
                }else{
                    seguir=false;
                }
            } 
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
                if(Aux.Siguiente!=null){
                    Aux=Aux.Siguiente;
                }else{
                    seguir=false;
                    Respuesta=null;
                }
                
            }
        }      
                
        return Respuesta;
    }
}
