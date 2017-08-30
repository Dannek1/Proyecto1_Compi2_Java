/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

import java.io.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Dannek
 */
public class Handler {
    int contador = 0;
    String grafica="";
    boolean value = false;
    public Handler(){
        
    }
    
    public void agregarNumero(Analizador.SimpleNode nodo){
        nodo.numero = contador++;
        if(nodo.children != null){
            for (Analizador.Node children : nodo.children) {
                Analizador.SimpleNode hijo = (Analizador.SimpleNode) children;
                agregarNumero(hijo);
            }   
        }
    }
    
    public void iniciarGrafica(Analizador.SimpleNode nodo){
        grafica = "digraph g{\n" +
                    "rankdir = TB;\n" +
                    "node [shape = \"rectangle\", style = \"filled\", color = \"\"];\n";
        
        graficarArbol(nodo);
        
        grafica += "}";
    
        crearArchivo("C:\\Base_Compi2\\ArbolJJT.txt", grafica);
    }
    
    public void graficarArbol(Analizador.SimpleNode itemnodo){

        if (value == false){
            grafica += "\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[itemnodo.id] + itemnodo.numero + "\" [label=\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[itemnodo.id] + "\"];\n";        
            value = true;
        }

        if(itemnodo.children != null){

            for (Analizador.Node children : itemnodo.children){
                Analizador.SimpleNode temporal = (Analizador.SimpleNode) children;
                
                if (temporal.name != null)
                    grafica += "\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[temporal.id] + temporal.numero + "\" [label=\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[temporal.id] + " \\n " +  temporal.name + "\"];\n";
                else
                    grafica += "\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[temporal.id] + temporal.numero + "\" [label=\"" + Analizador.AnalizadorTreeConstants.jjtNodeName[temporal.id] + "\"];\n";

                grafica += Analizador.AnalizadorTreeConstants.jjtNodeName[itemnodo.id] + itemnodo.numero + "->" + Analizador.AnalizadorTreeConstants.jjtNodeName[temporal.id] + temporal.numero + ";\n";

                graficarArbol(temporal);

                //System.out.println("tipo: " + itemnodo.tipo + ", valor: " + itemnodo.valor + ", contador: " + itemnodo.contador);

            }

        }else{

            //System.out.println("tipo: " + itemnodo.tipo + ", valor: " + itemnodo.valor + ", contador: " + itemnodo.contador);

        }
    }
    
    public void crearArchivo(String nombre, String dato){
        File archivo = new File(nombre);
        try{
        FileWriter escritura = new FileWriter(archivo);
        escritura.write(dato);
        escritura.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void GenerarImagen(){
        
        try {
            String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
            String archivoEntrada = "C:\\Base_Compi2\\ArbolJJT.txt";
            String archivoSalida = "C:\\Base_Compi2\\Arbol.jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";
            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = archivoEntrada;
            cmd[3] = tOParam;
            cmd[4] = archivoSalida;

            Runtime rt = Runtime.getRuntime();

            rt.exec( cmd );                       
            /*
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new java.io.File("C:\\Users\\kevin3316\\Pictures\\Proyecto1\\Arbol.jpg"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al abrir imagen del arbol: " + e.toString());
            }
            */
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar imagen del arbol: " + e.toString());
        }
        
        
    }

}
