/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

import Analizador_Paquetes.Analizador_Paquetes;
import Analizador_Paquetes.ParseException;
import Analizador_Paquetes.SimpleNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author Dannek
 */
public class Analisis_Paquete {
    
    public String Responder(String path){
        String Respuesta="";
        
        InputStream is=new ByteArrayInputStream(path.getBytes());
        Analizador_Paquetes a=new Analizador_Paquetes(is);
        try{
        SimpleNode raiz =a.Programa();

        Respuesta=Ejecuccion(raiz);
       
            
      
        } catch (Exception ex) {
           System.out.println(ex.toString());
           Respuesta="ERROR";
        }
        
        return Respuesta;
    }
    public String Ejecuccion(SimpleNode raiz){
        String Respuesta="";      
        
        int id=raiz.id;
        
        switch(id){
            case 0://Programa
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);
            break;
            
            case 1://inicio
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);
            break;
            
            case 2://Login
                Respuesta="login#"+Ejecuccion((SimpleNode)raiz.children[1]);
            break;
            
            case 3://validar
                Respuesta=Ejecuccion((SimpleNode)raiz.children[1]);
            break;
            
            case 4://login s
                Respuesta=((SimpleNode)raiz.children[0]).name;
            break;
                
            case 7://paquete
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);
            break;
            
            case 8://tipo paquete
                Respuesta=Ejecuccion((SimpleNode)raiz.children[0]);
            break;
            
            case 9://usql
                Respuesta="usql#"+((SimpleNode)raiz.children[0]).name;
            break;
            
            case 10://reporte
                Respuesta="reporte#"+((SimpleNode)raiz.children[0]).name;
            break;
            
            case 11://
                Respuesta=((SimpleNode)raiz.children[0]).name+"#algo";
            break;
            
            default:
                Respuesta="";
            break;
        }
        
        return Respuesta;
    }
}
