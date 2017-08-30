/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

/**
 *
 * @author Dannek
 */

import Analizador_XML.Analizador_XML;
import Analizador_XML.ParseException;
import Analizador_XML.SimpleNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Anlisis_XML {
    
    public boolean Responder(String path){
        boolean Respuesta=false;
        
         InputStream is=new ByteArrayInputStream(path.getBytes());
        Analizador_XML a=new Analizador_XML(is,"UTF-8");
        try{
        SimpleNode raiz =a.Programa();

        Respuesta=Ejecuccion(raiz);
       
            
      
        } catch (Exception ex) {
           System.out.println(ex.toString());
           Respuesta=false;
        }
        
        return Respuesta;
    }
    
    public boolean Ejecuccion(SimpleNode raiz){
        boolean Respuesta=false;
        
        int id=raiz.id;
        switch(id){
            case 0://Programa
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);
            break;
            
            case 1://INicio
                
                try {
                    if (raiz.children.length > 1) {
                        int contador = 0;
                        while (contador < raiz.children.length) {
                            if (Ejecuccion((SimpleNode) raiz.children[contador])) {
                                contador++;
                                Respuesta = true;
                            } else {
                                Respuesta = false;
                                break;
                            }
                        }

                    } else {
                        Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                    }
                } catch (Exception ex) {
                    Respuesta = true;
                }
                
            break;
            
            case 2://Maestro
                
                if(raiz.children.length>1){
                int contador=0;
                 while(contador<raiz.children.length){
                     if( Ejecuccion((SimpleNode)raiz.children[contador])){
                         contador++;
                         Respuesta=true;
                     }else{
                         Respuesta=false;
                         break;
                     }
                 }   
                    
                    
                }else{
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);    
                }
                
                
            break;
            
            case 3: //DB
                Respuesta = Ejecuccion((SimpleNode)raiz.children[0])&& Ejecuccion((SimpleNode)raiz.children[1]);
                
            break;
            
            case 4://BASE
                if (raiz.children.length > 2) {
                    int contador = 0;
                    while (contador < raiz.children.length) {
                        if (Ejecuccion((SimpleNode) raiz.children[contador])) {
                            contador++;
                            Respuesta = true;
                        } else {
                            Respuesta = false;
                            break;
                        }
                    }

                } else Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) && Ejecuccion((SimpleNode) raiz.children[1]);
            break;
            
            case 5://tabB
                if (raiz.children.length > 3) {
                    int contador = 0;
                    while (contador < raiz.children.length) {
                        if (Ejecuccion((SimpleNode) raiz.children[contador])) {
                            contador++;
                            Respuesta = true;
                        } else {
                            Respuesta = false;
                            break;
                        }
                    }

                } else Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) && Ejecuccion((SimpleNode) raiz.children[1])&& Ejecuccion((SimpleNode) raiz.children[2]);
            break;
            
            case 6://procB
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;    
            
            case 7://obB
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;
            
            case 8: //ROWS
                Respuesta = ((SimpleNode) raiz.children[0]).name.equals(((SimpleNode) raiz.children[3]).name);
            break;        
            
            case 9://Metodos   
                    if (raiz.children.length > 2) {
                    int contador = 0;
                    while (contador < raiz.children.length) {
                        if (Ejecuccion((SimpleNode) raiz.children[contador])) {
                            contador++;
                            Respuesta = true;
                        } else {
                            Respuesta = false;
                            break;
                        }
                    }

                } else Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) && Ejecuccion((SimpleNode) raiz.children[1]);
            break;  
            
            case 10://Tipo Retorno   
                 Respuesta=true;       
            break;  
            
            case 11://Parametros   
                if(raiz.children.length>1){
                int contador=0;
                 while(contador<raiz.children.length){
                     if( Ejecuccion((SimpleNode)raiz.children[contador])){
                         contador++;
                         Respuesta=true;
                     }else{
                         Respuesta=false;
                         break;
                     }
                 }   
                    
                    
                }else{
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);    
                }   
            break;  
            
            case 12://Intrucciones   
                Respuesta=true;     
            break; 
            
            case 13://Retorno   
                Respuesta=true;
            break; 
                
            case 14://Objetos   
                 if(raiz.children.length>1){
                int contador=0;
                 while(contador<raiz.children.length){
                     if( Ejecuccion((SimpleNode)raiz.children[contador])){
                         contador++;
                         Respuesta=true;
                     }else{
                         Respuesta=false;
                         break;
                     }
                 }   
                    
                    
                }else{
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);    
                }        
            break; 
            
            case 15://Atributos objeto   
                  Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);           
            break; 
            
            case 16://tipo de dato   
                  Respuesta=true;
            break; 
                
            case 17://Atributos campo
                 Respuesta=true;        
            break; 
            
            case 18://Nombre   
                 Respuesta=true;           
            break; 
            
            case 19://Ruta   
                 Respuesta=true;           
            break; 
            
            case 20://Tabla   
                 if(raiz.children.length>1){
                int contador=0;
                 while(contador<raiz.children.length){
                     if( Ejecuccion((SimpleNode)raiz.children[contador])){
                         contador++;
                         Respuesta=true;
                     }else{
                         Respuesta=false;
                         break;
                     }
                 }   
                    
                    
                }else{
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);    
                }         
            break; 
            
            case 21://ROW   
                 Respuesta=true;       
            break; 
            
            case 22://r_atributo   
                 Respuesta=true;       
            break; 
            
            default:
                Respuesta=false;
            break;
        }
        
        
        return Respuesta;
    }
}
