/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

import SubAnalisis.SimpleNode;
import SubAnalisis.SubAnalisis;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Dannek
 */
public class SubManejo {
    String BASE_USO="";
    String TABLA_aux="";
    String CAMPOS_aux="";
    String VALORES_aux="";
    String AMBITO="Global";
    String USER_ACTUAL="";
    
    Anlisis_XML LeerXML=new Anlisis_XML();
    boolean Ejecutar=true;
    
    SubManejo(){
        
    }
    
    String Manejar(String cadena ,String base){
        String Respuesta="";
        InputStream is=new ByteArrayInputStream(cadena.getBytes());
        SubAnalisis a=new SubAnalisis(is,"UTF-8");
        Manejo manejo=new Manejo();
        
        BASE_USO=base;
        
        try{
        SimpleNode raiz =a.Programa();
        
        Respuesta=Ejecuccion(raiz);
        
        System.out.println("Pruba");
            
        } catch (Exception ex) {
           System.out.println(ex.toString());
        }
        
        return Respuesta;
    }
    
    public boolean Existe(String sFichero){
        
         
        File fichero = new File(sFichero);
               
        return fichero.exists();
    } 
    
    boolean XML_Correcto(String path) throws FileNotFoundException{
            boolean Respuesta=false;
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            
             br.close();
             
            String everything = sb.toString();
            
            Respuesta=LeerXML.Responder(everything);
            
        }catch (Exception e) {
             e.printStackTrace();
             Respuesta=false;
            }
        
        return Respuesta;
    }
    
     String CrearBase(String Nombre) {
        String Respuesta ="";
        if (Existe("C:\\Base_Compi2\\BD\\" + Nombre + ".usac") == false) {
            
            
            
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                // root element

                Element rootElement = doc.createElement("Base");
                doc.appendChild(rootElement);

                TransformerFactory factoria = TransformerFactory.newInstance();
                Transformer transformer = factoria.newTransformer();

                Source fuente = new DOMSource(doc);

                File file = new File("C:\\Base_Compi2\\BD\\" + Nombre + ".usac");
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                Result result = new StreamResult(pw);

                transformer.transform(fuente, result);

                InsetarBase_Maestro(Nombre);
                BASE_USO=Nombre;
                CrearProArch(Nombre+"_PRO");
                CrearObjetoArch(Nombre+"_OBJ");
                BASE_USO="";
                Respuesta="Base de Datos: "+Nombre+" creada";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Ya Esiste la Base de Datos:"+Nombre);
            Respuesta="Ya Esiste la Base de Datos:"+Nombre;
        }
        return Respuesta;
    }
     
    void CrearObjetoArch(String Nombre) {
        if (Existe("C:\\Base_Compi2\\BD\\" + Nombre + ".usac") == false) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                // root element

                Element rootElement = doc.createElement("Objeto");
                doc.appendChild(rootElement);

                TransformerFactory factoria = TransformerFactory.newInstance();
                Transformer transformer = factoria.newTransformer();

                Source fuente = new DOMSource(doc);

                File file = new File("C:\\Base_Compi2\\BD\\" + Nombre + ".usac");
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                Result result = new StreamResult(pw);

                transformer.transform(fuente, result);
                
                InsertarObjeto_Base(Nombre,BASE_USO);
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Ya Esiste la Base de Datos:"+Nombre);
        }

    }
    
    void CrearProArch(String Nombre) {
        if (Existe("C:\\Base_Compi2\\BD\\" + Nombre + ".usac") == false) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                // root element

                Element rootElement = doc.createElement("Metodo");
                doc.appendChild(rootElement);

                TransformerFactory factoria = TransformerFactory.newInstance();
                Transformer transformer = factoria.newTransformer();

                Source fuente = new DOMSource(doc);

                File file = new File("C:\\Base_Compi2\\BD\\" + Nombre + ".usac");
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                Result result = new StreamResult(pw);

                transformer.transform(fuente, result);
                
                InsertarPRo_Base(Nombre,BASE_USO);
                

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Ya Esiste la Base de Datos:"+Nombre);
        }

    } 
     
    void InsetarBase_Maestro(String Base) throws FileNotFoundException{
        
        if(XML_Correcto("C:/Base_Compi2/Maestro.usac")){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/Maestro.usac"));

                NodeList items = doc.getElementsByTagName("Maestro");
                Element element = (Element) items.item(0);

                Element DB = doc.createElement("DB");
                element.appendChild(DB);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode("\""+Base+"\""));
                DB.appendChild(nombre);

                Element path = doc.createElement("path");
                path.appendChild(doc.createTextNode("\"C:\\Base_Compi2\\BD\\" + Base + ".usac\""));
                DB.appendChild(path);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("C:/Base_Compi2/Maestro.usac"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.print("EL Archivo Usac Contiene Errores");
        }
        
    }
    
    void InsertarObjeto_Base(String Objeto,String Base){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));

            NodeList items = doc.getElementsByTagName("Base");
            Element element= (Element) items.item(0);  

            Element Objetos = doc.createElement("Object");
            element.appendChild(Objetos);

            
            Element path = doc.createElement("path");
            path.appendChild(doc.createTextNode("\"C:\\Base_Compi2\\BD\\" + Objeto + ".usac\""));
            Objetos.appendChild(path);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void InsertarPRo_Base(String Objeto,String Base){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));

            NodeList items = doc.getElementsByTagName("Base");
            Element element= (Element) items.item(0);  

            Element Objetos = doc.createElement("Procedure");
            element.appendChild(Objetos);

            
            Element path = doc.createElement("path");
            path.appendChild(doc.createTextNode("\"C:\\Base_Compi2\\BD\\" + Objeto + ".usac\""));
            Objetos.appendChild(path);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String CrearTabla(String Nombre){
        String Respuesta="";
        if (Existe("C:\\Base_Compi2\\BD\\" + Nombre + ".usac") == false) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement("Tabla");
                doc.appendChild(rootElement);
                // root element
                /*Element rootElement = doc.createElement("Row");
                  doc.appendChild(rootElement);
                
                    Element nombre=doc.createElement("nombre");
                    nombre.appendChild(doc.createTextNode(Nombre));
                    rootElement.appendChild(nombre);
        
                 */

                TransformerFactory factoria = TransformerFactory.newInstance();
                Transformer transformer = factoria.newTransformer();

                Source fuente = new DOMSource(doc);

                File file = new File("C:\\Base_Compi2\\BD\\" + Nombre + ".usac");
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);

                Result result = new StreamResult(pw);

                transformer.transform(fuente, result);
                
                Respuesta="Se ha creado la Tabla:" + Nombre+ " En la Base:"+BASE_USO;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ya Esiste Un Archivo con el Nombre de:" + Nombre);
            Respuesta="Ya Esiste Un Archivo con el Nombre de:" + Nombre;
        }
       return Respuesta;
    }
    
    void InsertarTabla_Base(String Tabla,String Campos,String Base) throws FileNotFoundException{
        if (XML_Correcto("C:/Base_Compi2/BD/" + Base + ".usac")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));

                NodeList items = doc.getElementsByTagName("Base");
                Element element = (Element) items.item(0);

                Element Tablas = doc.createElement("Tabla");
                element.appendChild(Tablas);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode("\""+Tabla+"\""));
                Tablas.appendChild(nombre);

                Element path = doc.createElement("path");
                path.appendChild(doc.createTextNode("\"C:\\Base_Compi2\\BD\\" + Tabla + ".usac\""));
                Tablas.appendChild(path);

                Element Filas = doc.createElement("rows");
                Tablas.appendChild(Filas);

                String campo[] = Campos.split(",");

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
                Source input = new DOMSource(doc);
                transformer.transform(input, output);

                int x;

                for (x = 0; x < campo.length; x++) {
                    String[] dato = campo[x].split(" ");

                    AñadirCampo_BAse_Tabla(Base, dato, Tabla);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("EL Archivo Usac Contiene Errores");
        }
       
    }
    
    void AñadirCampo_BAse_Tabla(String Base,String[] Campo,String Tabla){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
            Element elementoTabla;

            NodeList items = doc.getElementsByTagName("Tabla");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(0);

                if (element2.getTextContent().equals("\""+Tabla+"\"")) {
                    NodeList subitem2 = element.getElementsByTagName("rows");
                    
                    elementoTabla = (Element) subitem2.item(0);

                    if (Campo.length >= 3) {
                        
                        
                        String Atributos="";
                        for(int y=2;y<Campo.length;y++){
                            if(Campo[y].equals("No")){
                                Atributos+="No Nulo;";
                                y++;
                            }else if(Campo[y].equals("Llave_Foranea")){
                                if(Buscar_Atibuto_primario(Campo[y+1],Base)){
                                  Atributos+=Campo[y]+" "+Campo[y+1]+";";
                                }
                                y++;
                            }else{
                                Atributos+=Campo[y]+";";
                            }
                        }
                        
                        Element tipo = doc.createElement(Campo[0]);
                        tipo.appendChild(doc.createTextNode("\""+Campo[1]+"\""));
                        tipo.setAttribute("Atributos", Atributos);
                        elementoTabla.appendChild(tipo);
                    }else{
                        Element tipo = doc.createElement(Campo[0]);
                        tipo.appendChild(doc.createTextNode("\""+Campo[1]+"\""));
                        elementoTabla.appendChild(tipo);
                    }

                    ix = items.getLength();
                } else {
                    ix++;
                }

            }
            
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);  
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    boolean Buscar_Atibuto_primario(String Tabla,String Base){
        boolean respuesta = false;
        String mensaje="";
        if (Existe("C:\\Base_Compi2\\BD\\" + Base + ".usac")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
                boolean encontrado=false;
                Element elemento;
                NodeList basel = doc.getElementsByTagName("Base");
                Element base = (Element) basel.item(0);
                NodeList items = base.getElementsByTagName("Tabla");
                int x=0;
                while(x<items.getLength()){
                    
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("nombre");
                    Element nombre=(Element) items2.item(0);
                    if (nombre.getTextContent().equals(Tabla)) {
                        encontrado=true;
                        break;
                    }else{
                        mensaje="NO Existe la Tabla:"+Tabla+" En la Base de Datos:"+Base;
                        x++;
                    }
                    
                }
                
                if(encontrado){
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("rows");
                    Element row = (Element) items2.item(0);
                    NodeList campos = row.getChildNodes();
                    for (int ix = 0; ix < campos.getLength(); ix++) {
                        Element element = (Element) campos.item(ix);
                        // elejir un elemento especifico por algun atributo
                        String atribs=element.getAttribute("Atributos").toString();
                        String[] atrib=atribs.split(";");
                        for(int ixx=0;ixx<atrib.length;ixx++){
                            if(atrib[ixx].equals("Llave_Primaria")){
                                ixx=atrib.length;
                                respuesta=true;
                                ix=campos.getLength();
                                mensaje="Llave Primaria Encontrada";
                            }else{
                                respuesta=false;
                                mensaje="La Tabla:"+Tabla+" NO tiene Llave Primaria";
                            }
                        }
                        
                    }
                    
                }else{
                    respuesta=encontrado;
                }


            } catch (Exception e) {

            }
        } else {
            respuesta = false;
        }
        System.out.println(mensaje);
        return respuesta;
    }
    
    void Insertar_Obj(String Objeto,String Archivo,String Parametros){
       try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            

            NodeList base = doc.getElementsByTagName("Objeto");
            Element element= (Element) base.item(0);  

            Element Objeto_e = doc.createElement("Obj");
            element.appendChild(Objeto_e);
            
            Element nombre=doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode("\""+Objeto+"\""));
            Objeto_e.appendChild(nombre);
            
            Element att = doc.createElement("attr");
            Objeto_e.appendChild(att);
            
            

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);  
            
            
            if (!Parametros.equals("Default")) {
               String campo[] = Parametros.split(",");

               int x;

               for (x = 0; x < campo.length; x++) {
                   String[] dato = campo[x].split(" ");

                   AñadirCampo_Objeto(Archivo, dato, Objeto);

               }
           }


        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    void AñadirCampo_Objeto(String archivo, String[] dato, String Objeto){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
            Element elementoTabla;

            NodeList items = doc.getElementsByTagName("Obj");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(0);

                if (element2.getTextContent().equals("\""+Objeto+"\"")) {
                    NodeList subitem2 = element.getElementsByTagName("attr");
                    
                    elementoTabla = (Element) subitem2.item(0);

                    
                        Element tipo = doc.createElement(dato[0]);
                        tipo.appendChild(doc.createTextNode("\""+dato[1]+"\""));
                        elementoTabla.appendChild(tipo);
                    

                    ix = items.getLength();
                } else {
                    ix++;
                }

            }
            
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
          Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
          Source input = new DOMSource(doc);
          transformer.transform(input, output);  
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void Insertar_PROC(String Procedimiento,String Archivo,String Parametros,String Sentencias){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();      
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            

            NodeList base = doc.getElementsByTagName("Metodo");
            Element element= (Element) base.item(0);  

            Element proc_e = doc.createElement("Proc");
            element.appendChild(proc_e);
            
            Element nombre=doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode("\""+Procedimiento+"\""));
            proc_e.appendChild(nombre);
            
            Element att = doc.createElement("params");
            proc_e.appendChild(att);
            
            

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);  
            
            
            if (!Parametros.equals("Default")) {
               String campo[] = Parametros.split(",");

               int x;

               for (x = 0; x < campo.length; x++) {
                   String[] dato = campo[x].split(" ");

                   AñadirCampo_Funcion(Archivo, dato, Procedimiento);

               }
           }
            
            Añadir_Sentencias_Pro(Archivo,Sentencias,Procedimiento);    
          


        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    void AñadirCampo_Funcion(String archivo, String[] dato, String funcion){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
            Element elementoTabla;

            NodeList items = doc.getElementsByTagName("Proc");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(0);

                if (element2.getTextContent().equals("\""+funcion+"\"")) {
                    NodeList subitem2 = element.getElementsByTagName("params");
                    
                    elementoTabla = (Element) subitem2.item(0);

                    
                        Element tipo = doc.createElement(dato[0]);
                        tipo.appendChild(doc.createTextNode("\""+dato[1]+"\""));
                        elementoTabla.appendChild(tipo);
                    

                    ix = items.getLength();
                } else {
                    ix++;
                }

            }
            
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
          Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
          Source input = new DOMSource(doc);
          transformer.transform(input, output);  
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void Añadir_Sentencias_Pro(String archivo,String Sentencias, String funcion){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));

            NodeList items = doc.getElementsByTagName("Proc");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(0);

                if (element2.getTextContent().equals("\""+funcion+"\"")) {

                    Element sentencias = doc.createElement("src");
                    sentencias.appendChild(doc.createTextNode("\""+Sentencias+"\""));
                    element.appendChild(sentencias);
                    
                    

                    ix = items.getLength();
                } else {
                    ix++;
                }

            }
            
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
          Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
          Source input = new DOMSource(doc);
          transformer.transform(input, output);  
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void Insertar_FUN(String funcion,String Archivo,String Parametros,String TipoR,String Sentencias){
       try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();      
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            

            NodeList base = doc.getElementsByTagName("Metodo");
            Element element= (Element) base.item(0);  

            Element proc_e = doc.createElement("Proc");
            element.appendChild(proc_e);
            
            Element nombre=doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode("\""+funcion+"\""));
            proc_e.appendChild(nombre);
            
            Element att = doc.createElement("params");
            proc_e.appendChild(att);
            
            

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Archivo + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);  
            
            
            if (!Parametros.equals("Default")) {
               String campo[] = Parametros.split(",");

               int x;

               for (x = 0; x < campo.length; x++) {
                   String[] dato = campo[x].split(" ");

                   AñadirCampo_Funcion(Archivo, dato, funcion);

               }
           }
            
          Añadir_Tipo_Sentencias_Funcion(Archivo,TipoR,Sentencias,funcion);


        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    void Añadir_Tipo_Sentencias_Funcion(String archivo, String Tdato,String Sentencias, String funcion){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
            Element elementoTabla;

            NodeList items = doc.getElementsByTagName("Proc");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(0);

                if (element2.getTextContent().equals("\""+funcion+"\"")) {
                    Element tipo = doc.createElement("tipo_retorno");
                    tipo.appendChild(doc.createTextNode("\""+Tdato+"\""));
                    element.appendChild(tipo);
                    
                    String[] sens=Sentencias.split("RETORNO");
                    
                    Element sentencias = doc.createElement("src");
                    sentencias.appendChild(doc.createTextNode("\""+sens[0]+"\""));
                    element.appendChild(sentencias);
                    
                    Element retorno=doc.createElement("return");
                    retorno.appendChild(doc.createTextNode("\""+sens[1]+"\""));
                    element.appendChild(retorno);

                    ix = items.getLength();
                } else {
                    ix++;
                }

            }
            
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
          Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + archivo + ".usac"));
          Source input = new DOMSource(doc);
          transformer.transform(input, output);  
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    String Buscar_autoincrementable(String Tabla,String Base){
        String respuesta="";
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
                boolean encontrado=false;
                
                NodeList basel = doc.getElementsByTagName("Base");
                Element base = (Element) basel.item(0);
                NodeList items = base.getElementsByTagName("Tabla");
                int x=0;
                while(x<items.getLength()){
                    
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("nombre");
                    Element nombre=(Element) items2.item(0);
                    if (nombre.getTextContent().equals(Tabla)) {
                        encontrado=true;
                        break;
                    }else{
                        System.out.println("NO Existe la Tabla:"+Tabla+" En la Base de Datos:"+Base);
                        x++;
                    }
                    
                }
                
                if(encontrado){
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("rows");
                    Element row = (Element) items2.item(0);
                    NodeList campos = row.getChildNodes();
                    for (int ix = 0; ix < campos.getLength(); ix++) {
                        Element element = (Element) campos.item(ix);
                        // elejir un elemento especifico por algun atributo
                        String atribs=element.getAttribute("Atributos").toString();
                        String[] atrib=atribs.split(";");
                        for(int ixx=0;ixx<atrib.length;ixx++){
                            if(atrib[ixx].equals("Autoincrementable")){
                                respuesta=element.getTextContent();
                            }else{
                                
                                
                            }
                        }
                        
                    }
                    
                }else{
                    
                }


            } catch (Exception e) {

            }
        
        return respuesta;
    }
    
    String Buscar_Nulo(String Tabla,String Base){
        String respuesta="";
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
                boolean encontrado=false;
                
                NodeList basel = doc.getElementsByTagName("Base");
                Element base = (Element) basel.item(0);
                NodeList items = base.getElementsByTagName("Tabla");
                int x=0;
                while(x<items.getLength()){
                    
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("nombre");
                    Element nombre=(Element) items2.item(0);
                    if (nombre.getTextContent().equals(Tabla)) {
                        encontrado=true;
                        break;
                    }else{
                        System.out.println("NO Existe la Tabla:"+Tabla+" En la Base de Datos:"+Base);
                        x++;
                    }
                    
                }
                
                if(encontrado){
                    Element tablaE=(Element) items.item(x);
                    NodeList items2 = tablaE.getElementsByTagName("rows");
                    Element row = (Element) items2.item(0);
                    NodeList campos = row.getChildNodes();
                    for (int ix = 0; ix < campos.getLength(); ix++) {
                        Element element = (Element) campos.item(ix);
                        // elejir un elemento especifico por algun atributo
                        String atribs=element.getAttribute("Atributos").toString();
                        String[] atrib=atribs.split(";");
                        for(int ixx=0;ixx<atrib.length;ixx++){
                            if(atrib[ixx].equals("Nulo")){
                                respuesta+=element.getTextContent()+",";
                            }else{
                                
                                
                            }
                        }
                        
                    }
                    
                }else{
                    
                }


            } catch (Exception e) {

            }
        
        return respuesta;
    }
    
    void Insertar_Tabla(String Dato,String Tabla,String Base){
        
        String[] Datos=Dato.split(",");
         
        String Campo=CamposT_Base(Base,Tabla);
        
        Campo=EliminaCaracteres(Campo);
        
        String[] Campos=Campo.split(";");
        int posauto=-1;
        String posnull="";
        
        if(Campos.length!=Datos.length){
            String auto=Buscar_autoincrementable(Tabla,Base);
            String nulos=Buscar_Nulo(Tabla,Base);
            String[] nulo=nulos.split(",");
            
            for(int x=0;x<Campos.length;x++){
                if(auto.equals(Campos[x])){
                    posauto=x;
                }
                
                for(int y = 0; y < nulo.length; y++) {
                    if (nulo[y].equals(Campos[x])) {
                        posnull =x+"," ;
                    }
                }
                
            }
            
            
            if(posauto!=-1){
                if(isNumeric(Datos[posauto])){
                    
                }
            }
            
            
        }
        
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));

            NodeList items = doc.getElementsByTagName("Tabla");
            Element element = (Element) items.item(0);
          
            
            Element Row=doc.createElement("Row");
            element.appendChild(Row);
            
           
            
            
           
            
            Element[] elementos=new Element[Campos.length];
            
            for(int x=0;x<elementos.length;x++){
                elementos[x]=doc.createElement(Campos[x]);
                
                if(Datos[x].charAt(0)=='"'){
                    int fin=Datos[x].length()-1;
                    elementos[x].appendChild(doc.createTextNode(Datos[x].substring(1,fin)));
                }else{
                    elementos[x].appendChild(doc.createTextNode(Datos[x]));
                }
                
                Row.appendChild(elementos[x]);
            }

                  

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
            
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String CamposT_Base(String Base,String Tabla){
        String respuesta="";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + ".usac"));
            
            NodeList items = doc.getElementsByTagName("Tabla");
            int ix = 0;
            while (ix < items.getLength()) {
                Element element = (Element) items.item(ix);
                NodeList subitem = element.getElementsByTagName("nombre");
                Element element2 = (Element) subitem.item(ix);

                if (element2.getTextContent().equals("\""+Tabla+"\"")) {
                    NodeList subitem2 = element.getElementsByTagName("rows");
                    Element element3 = (Element) subitem2.item(0);
                    
                    NodeList subitem3=element3.getChildNodes();
                    
                    for(int y=0;y<subitem3.getLength();y++){
                        
                        respuesta+=subitem3.item(y).getTextContent()+";";
                    }

                    ix = items.getLength();
                } else {
                    ix++;
                }
            }


        } catch (Exception e) {
            respuesta=e.toString();
            
        }
        
        
        return respuesta;
    }
    
    public String EliminaCaracteres(String s_cadena) {
        String nueva_cadena = "";
        Character caracter = null;
        boolean valido = true;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
     sólo los caracteres que no estén en la cadena s_caracteres */
        for (int i = 0; i < s_cadena.length(); i++) {
            valido = true;

            caracter = '\"';

            if (s_cadena.charAt(i) == caracter) {
                valido = false;
                
            }

            if (valido) {
                nueva_cadena += s_cadena.charAt(i);
            }
        }

        return nueva_cadena;
    }
    
    public String EliminaEspacios(String s_cadena) {
        String nueva_cadena = "";
        Character caracter = null;
        boolean valido = true;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
     sólo los caracteres que no estén en la cadena s_caracteres */
        for (int i = 0; i < s_cadena.length(); i++) {
            valido = true;

            caracter = ' ';

            if (s_cadena.charAt(i) == caracter) {
                valido = false;
                
            }

            if (valido) {
                nueva_cadena += s_cadena.charAt(i);
            }
        }

        return nueva_cadena;
    }
    
    String Ejecuccion(SimpleNode raiz) throws FileNotFoundException{
        String Respuesta="";
        
        int id=raiz.id;
        switch(id){
            
            case 0://Programa
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;    
            
            case 1://Inicio
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;
            
            case 2://Sentencia
               Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;
                
            case 3://USAR   
                String mensaje = "";
                if (Ejecutar) {
                    System.out.println(((SimpleNode) raiz.children[0]).name);
                    if (Existe("C:\\Base_Compi2\\BD\\" + ((SimpleNode) raiz.children[1]).name + ".usac")) {
                        BASE_USO = ((SimpleNode) raiz.children[1]).name;
                        System.out.println("Se Utilizará La Base de Datos:" + ((SimpleNode) raiz.children[1]).name);
                        mensaje = "Se Utilizará La Base de Datos:" + ((SimpleNode) raiz.children[1]).name;
                        
                    } else {
                        System.out.println("La Base de Datos:" + ((SimpleNode) raiz.children[1]).name + " No Existe");
                        mensaje = "La Base de Datos:" + ((SimpleNode) raiz.children[1]).name + " No Existe";
                    }
                } else {
                   mensaje = "USAR $" + ((SimpleNode) raiz.children[1]).name;     
                }

                Respuesta = mensaje;
                break; 
                
            case 6://Crear
                System.out.println(((SimpleNode) raiz.children[0]).name);
                Respuesta="CREAR@ "+ Ejecuccion((SimpleNode) raiz.children[1]);
                break;  
                
           case 7://Opciones Crear
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;  
                
           case 8://Crear Base
                System.out.println(((SimpleNode) raiz.children[0]).name);
                if(Ejecutar){
                    Respuesta=CrearBase(((SimpleNode) raiz.children[0]).name);

                }else{
                    Respuesta="BASE_DATOS "+((SimpleNode) raiz.children[0]).name+"\n";
                }
                
                break;   
                
            case 9://Crear Tabla
                
                if (Ejecutar) {
                    if (BASE_USO.equals("")) {
                        System.out.println("No Hay ninguna Base en Uso");
                    } else {
                        System.out.println(((SimpleNode) raiz.children[0]).name);
                        String nombreTabla = ((SimpleNode) raiz.children[0]).name;
                        Respuesta = CrearTabla(nombreTabla);
                        String Campos = Ejecuccion((SimpleNode) raiz.children[1]);
                        InsertarTabla_Base(nombreTabla, Campos, BASE_USO);
                    }
                    
                }else{
                    Respuesta="TABLA "+((SimpleNode) raiz.children[0]).name+"("+Ejecuccion((SimpleNode) raiz.children[1])+");\n";
                }
                
                break;
                
            case 10://Campos Tabla
                if (raiz.children.length > 1) {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                    for (int x = 1; x < raiz.children.length; x++) {
                        Respuesta += "," + Ejecuccion((SimpleNode) raiz.children[x]);
                    }

                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                break;  
                
            case 11://Campos Tabla Prima
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;   
                
            case 12://Campo Tabla
                if (raiz.children.length > 2) {
                    Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;

                    for (int x = 2; x < raiz.children.length; x++) {
                        Respuesta += " " + ((SimpleNode) raiz.children[x]).name;
                    }

                } else {
                    Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;
                }

                break;
                
            case 14://Crear Objeto
                
                if (Ejecutar) {
                    if (BASE_USO.equals("")) {
                        System.out.println("No Hay ninguna Base en Uso");
                    } else {
                        System.out.println(((SimpleNode) raiz.children[0]).name);
                        String nombreObjeto = ((SimpleNode) raiz.children[0]).name;

                        String Param_Objet = Ejecuccion((SimpleNode) raiz.children[1]);

                        Insertar_Obj(nombreObjeto,BASE_USO+"_OBJ",Param_Objet);
                    }
                    Respuesta = "Exito";
                } else {
                    Respuesta="OBJETO "+((SimpleNode) raiz.children[0]).name+"("+Ejecuccion((SimpleNode) raiz.children[1])+");\n";
                }
                
                break;
            
            case 15://Parametros
                if (raiz.children.length > 1) {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) ;

                    for (int x = 1; x < raiz.children.length; x++) {
                        Respuesta += "," +Ejecuccion((SimpleNode) raiz.children[x]);
                    }

                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                break; 
                
            case 16://Parametro
           
                Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;
                
                break; 
                
            case 17://Crear Procedimiento
                if (BASE_USO.equals("")) {
                    System.out.println("No Hay ninguna Base en Uso");
                } else {
                    System.out.println(((SimpleNode) raiz.children[0]).name);
                    String nombrePRO = ((SimpleNode) raiz.children[0]).name;
                    Ejecutar=false;
                    if (raiz.children.length > 2) {
                    String Param_Objet = Ejecuccion((SimpleNode) raiz.children[1]);
                    String sentencias= Ejecuccion((SimpleNode) raiz.children[2]);
                    System.out.println(((SimpleNode) raiz.children[1]).name);
                    Insertar_PROC(nombrePRO,BASE_USO+"_PRO",Param_Objet,sentencias);
                    }else{
                    String sentencias= Ejecuccion((SimpleNode) raiz.children[1]);    
                    System.out.println(((SimpleNode) raiz.children[0]).name);
                    Insertar_PROC(nombrePRO,BASE_USO+"_PRO","Default",sentencias);
                    }
                    Ejecutar=true;
                    
                }
                Respuesta="Exito";
                break;
                
            case 18://Sub Sentencia
                if (raiz.children.length > 1) {
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    for (int x = 2; x < raiz.children.length; x++) {
                    Respuesta += "\n" +Ejecuccion((SimpleNode) raiz.children[0]);
                    }
                    
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            
            break;  
            
            case 19://Crear Funcion
                
                if (BASE_USO.equals("")) {
                    System.out.println("No Hay ninguna Base en Uso");
                } else {
                    System.out.println(((SimpleNode) raiz.children[0]).name);
                    String nombreFun = ((SimpleNode) raiz.children[0]).name;
                    
                    if (raiz.children.length > 3) {
                        
                        String Param= Ejecuccion((SimpleNode) raiz.children[1]);
                        String tipoR=((SimpleNode) raiz.children[2]).name;
                        
                        Ejecutar=false;
                        String sentencias=Ejecuccion((SimpleNode) raiz.children[3]);
                        Ejecutar=true;
                        
                        Insertar_FUN(nombreFun,BASE_USO+"_PRO",Param,tipoR,sentencias);
                        
                        
                    } else {
                        String tipoR=((SimpleNode) raiz.children[1]).name;
                        
                        Ejecutar=false;
                        String sentencias=Ejecuccion((SimpleNode) raiz.children[2]);
                        Ejecutar=true;
                        
                        Insertar_FUN(nombreFun,BASE_USO+"_PRO","Default",tipoR,sentencias);
                    }

                    //Insertar_Obj(nombreObjeto,BASE_USO+"_)
                }
                Respuesta = "Exito";

                
            break; 
            
            case 20://Sentencias Retorno
               if (raiz.children.length > 2){
                   for(int x=0;x<raiz.children.length-1;x++){
                       Respuesta+=Ejecuccion((SimpleNode) raiz.children[x])+"\n";
                   }
                   Respuesta+="RETORNO "+Ejecuccion((SimpleNode) raiz.children[raiz.children.length-1]);
                   
               }else{
                   Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+"RETORNO "+Ejecuccion((SimpleNode) raiz.children[1]);
               } 
                
            break;
            
            
            case 21://Retorno
                Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) ;
                
            break;
            
            case 22://Crear Usuario
                if(Ejecutar){
                String base_temp=BASE_USO;
                BASE_USO="UsuariosBD";
                String usuario=((SimpleNode) raiz.children[0]).name;
                String pass=Ejecuccion((SimpleNode) raiz.children[1]) ;
                Insertar_Tabla("\""+usuario+"\",\""+usuario+"\","+pass+"\"","Tabla_Usuarios",BASE_USO);
                BASE_USO=base_temp; 
                }else{
                   Respuesta="USUARIO "+((SimpleNode) raiz.children[0]).name+" COLOCAR password ="+Ejecuccion((SimpleNode) raiz.children[1]); 
                }
                
                
            break;
            
            case 23://Imprimir
                if(Ejecutar){
                
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]); 

                }else{
                   Respuesta="IMPRIMIR ("+Ejecuccion((SimpleNode) raiz.children[0])+");";
                }
                
                
            break;
        }
        
        return Respuesta;
    }
    
    
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    private static boolean isDouble(String cadena){
	try {
		Double.parseDouble(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
}


