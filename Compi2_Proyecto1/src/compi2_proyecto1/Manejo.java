/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

import Analizador.Analizador;
import Analizador.ParseException;
import Analizador.SimpleNode;
import comunicacion.Datos;
import org.apache.thrift.TException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.NodeList;

import Elementos.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.w3c.dom.Node;
/**
 *
 * @author Dannek
 */
public class Manejo implements Datos.Iface {

    String BASE_USO="";
    String TABLA_aux="";
    String CAMPOS_aux="";
    String VALORES_aux="";
    String AMBITO="Global";
    String USER_ACTUAL="";
    int hacer=0;
    boolean Ejecutar=true;
    boolean Ejecutar_Swithc=false;
    boolean Detener=false;
    String EXP_SWITCH="";
    String Historial="";
    
    Anlisis_XML LeerXML=new Anlisis_XML();
    Analisis_Paquete LeerPaquete=new Analisis_Paquete();
    Variables variables=new Variables();
    
    @Override
    public String Paquete(String cadena) throws TException {
        String Lectura="";
        String Respuesta="";
        String instruciones=LeerPaquete.Responder(cadena);
        String[] sentencias=instruciones.split("#");
        
        if (sentencias[0].equals("\"fin\"")) {
                USER_ACTUAL = "";
                BASE_USO = "";
                
        } else if (!instruciones.equals("ERROR")) {
            InputStream is = new ByteArrayInputStream(sentencias[1].getBytes());
            Date f_historial=new Date();
            DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
            Historial +=hourdateFormat.format(f_historial)+";"+sentencias[1]+";";
            Analizador a = new Analizador(is, "UTF-8");

            try {
                SimpleNode raiz = a.Programa();

                Handler h = new Handler();
                h.agregarNumero(raiz);
                h.iniciarGrafica(raiz);
                h.GenerarImagen();
                System.out.println("Pruba");

                if (sentencias[0].equals("login")) {
                    Lectura = Ejecuccion(raiz);

                    if (!Lectura.equals("")) {
                        Lectura = Lectura.substring(0, Lectura.length() - 1);

                        Respuesta = "[ \n \"validar\": 1500 ,\n \"login\":[\n";

                        String[] Resultados = Lectura.split(";");

                        int x = 0;

                        while (x < Resultados.length) {

                            if (Resultados[x].contains("usuario")) {
                                String[] temp = Resultados[x].split(",");

                                Respuesta += "\"usuario\":\"" + temp[1] + "\",\n";
                                USER_ACTUAL = temp[1];
                            }

                            if (Resultados[x].contains("nombre")) {
                                String[] temp = Resultados[x].split(",");

                                Respuesta += "\"nombre\":\"" + temp[1] + "\",\n";
                            }
                            x++;

                        }

                        Respuesta += "\"login\":true \n ] \n ]";

                    } else {
                        //Prueba comit
                        Respuesta = "[ \n \"validar\": 1500 ,\n \"login\":[\n";
                        Respuesta += "\"login\":false \n ] \n ]";
                    }

                }else if(sentencias[0].equals("usql")) {
                    Lectura = Ejecuccion(raiz); 
                    if (!Lectura.equals("")) {
                        
                        try{
                           String[] leer=Lectura.split("@");
                           if(leer[0].equals("CREAR")){
                            Historial+=leer[1]+";n";    
                            Respuesta ="[\n \"paquete\" : \"usql\",\"mensaje\" : \""+leer[1]+"\" \n]";
                            }     
                        }catch(Exception x){
                            
                        }
                            
                        
                    }else{
                        Respuesta ="[\n \"paquete\" : \"usql\",]";
                    }
                        
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                Respuesta = "ERRO en paquete";
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Manejo.class.getName()).log(Level.SEVERE, null, ex);
                Respuesta = "ERRO en paquete";
            }
        } else {
            Respuesta = "ERRO en paquete";
        }
        
        
        
        return Respuesta;
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
    
    public void CrearMaestro(){
        
        if(Existe("C:\\Base_Compi2\\Maestro.usac")==false){
            try {
            File f = new File("C:\\Base_Compi2\\");    
            boolean bool = f.mkdir();
                
            DocumentBuilderFactory dbFactory =DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =  dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
             // root element    
            Element rootElement = doc.createElement("Maestro");
            doc.appendChild(rootElement);  
             
            TransformerFactory factoria=TransformerFactory.newInstance();
            Transformer transformer= factoria.newTransformer();

            Source fuente=new DOMSource(doc);    

            File file=new File("C:\\Base_Compi2\\Maestro.usac");
            FileWriter fw=new FileWriter(file);
            PrintWriter pw=new PrintWriter(fw);

            Result result=new StreamResult(pw);

            transformer.transform(fuente, result);
            
            File x = new File("C:\\Base_Compi2\\BD");    
            boolean j = x.mkdir();
            
            BASE_USO="UsuariosBD";
            CrearBase("UsuariosBD");
            CrearTabla("Tabla_Usuarios");
            InsertarTabla_Base("Tabla_Usuarios","TEXT Usuario No Nulo,TEXT Nombre No Nulo,TEXT password No Nulo","UsuariosBD");
            Insertar_Tabla("\"admin\",\"admin\",\"admin\"","Tabla_Usuarios","UsuariosBD");
            BASE_USO="";
            
            } catch (Exception e) {
             e.printStackTrace();
            }
        }
        
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
    
    public boolean Existe(String sFichero){
        
         
        File fichero = new File(sFichero);
               
        return fichero.exists();
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
    
    int Ultimo_incrementable(String Tabla, String Base, String Campo){
        int Respuesta =0;
        
        
        return Respuesta;
    }
    
    String Path_tabla(String Tabla,String Base){
        String Respuesta="";
        
        return Respuesta;
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
    
    String Selecionar(String Campos, String Tabla,String Condiciones,String Orden){
        String Respuesta="";
        
        if(!Condiciones.equals("")){
         Condiciones=EliminaCaracteres(Condiciones);
         Condiciones=EliminaEspacios(Condiciones);   
        }
        
        
        if(Tabla.equals("usuarios")){
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/Tabla_Usuarios.usac"));
                
                NodeList items = doc.getElementsByTagName("Row");
                String Valores="";
                if(Campos.equals("*")){
                   
                    for(int x=0;x<items.getLength();x++){
                       Element element = (Element) items.item(x); 
                       NodeList elementos=element.getChildNodes();
                       
                       for(int y=0;y<elementos.getLength();y++){
                           Element temp=(Element)elementos.item(y);
                           
                           Valores+=temp.getTagName()+","+temp.getTextContent()+";";
                       }
                       
                       Valores+="#";
                    } 
                    
                    Valores=Valores.toLowerCase();
                    
                    String[] Registros = Valores.split("#");

                    if (!Condiciones.equals("")) {
                        String[] Condicion = Condiciones.split("&&");

                        String cond = "";

                        for (int x = 0; x < Condicion.length; x++) {
                            String[] temp = Condicion[x].split("==");
                            cond += temp[0] + "," + temp[1] + ";";
                        }
                        String[] term = cond.split(";");

                        boolean resultado = false;

                        for (int x = 0; x < Registros.length; x++) {

                            int y = 0;

                            while (y < term.length) {
                                if (Registros[x].contains(term[y])) {
                                    resultado = true;
                                    y++;
                                } else {
                                    resultado = false;
                                    break;
                                }
                            }

                            if (resultado) {
                                Respuesta += Registros[x] + "#";
                            }
                        }
                    }else{
                    Respuesta=Valores;    
                    }
                    

                }else{
                    
                    String busqueda="";
                    for(int x=0;x<items.getLength();x++){
                       Element element = (Element) items.item(x); 
                       NodeList elementos=element.getChildNodes();
                       
                       for(int y=0;y<elementos.getLength();y++){
                           Element temp=(Element)elementos.item(y);
                           
                           Valores+=temp.getTagName()+","+temp.getTextContent()+";";
                       }
                       
                       Valores+="#";
                    } 
                    
                    Valores=Valores.toLowerCase();
                    
                    String[] Registros = Valores.split("#");

                    if (!Condiciones.equals("")) {
                        String[] Condicion = Condiciones.split("&&");

                        String cond = "";

                        for (int x = 0; x < Condicion.length; x++) {
                            String[] temp = Condicion[x].split("==");
                            cond += temp[0] + "," + temp[1] + ";";
                        }
                        String[] term = cond.split(";");

                        boolean resultado = false;

                        for (int x = 0; x < Registros.length; x++) {

                            int y = 0;

                            while (y < term.length) {
                                if (Registros[x].contains(term[y])) {
                                    resultado = true;
                                    y++;
                                } else {
                                    resultado = false;
                                    break;
                                }
                            }

                            if (resultado) {
                                busqueda += Registros[x] + "#";
                            }
                        }
                    }else{
                    busqueda=Valores;    
                    }
                    
                    String[] Campo=Campos.split(",");
                    
                    String[] filtro=busqueda.split("#");
                    
                    for(int x=0;x<filtro.length;x++){
                        String[] reg=filtro[x].split(";");
                        
                        for(int y=0;y<reg.length;y++){
                            
                            for(int z=0;z<Campo.length;z++){
                                if(reg[y].contains(Campo[z])){
                                    Respuesta+=reg[y]+";";
                                    
                                }
                            }
                        }
                        
                        Respuesta+="#";
                        
                    }
                    
                    
                    
                    
                }                           

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }else{
            if (BASE_USO.equals("")) {
                        System.out.println("No Hay ninguna Base en Uso");
                    } else {
                
            }
        }
        
        return Respuesta;
    }
    
    void Declarar(String nombres,String Ambito,String Valor,String tipo){
        
        String[] nombre=nombres.split(",");
        
        for(int x=0;x<nombre.length;x++){
            Variable temp=new Variable(nombre[x],tipo,Ambito);
            variables.Insertar(temp);
            Asignacion(nombre[x],Valor);
        }
    }
    
    void Asignacion(String Variable,String Valor){
        
        Variable temp=variables.Buscar(Variable);
        
        switch (temp.getTipo()){
            
            case "TEXT":
                if(Valor.equals("")){
                    temp.setValor(Valor);
                }else if(Valor.charAt(0)=='\"' ){
                    temp.setValor(Valor);
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;
            
            case "INTEGER": 
                if(isNumeric(Valor)){
                    temp.setValor(Valor);
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;
            
            case "DOUBLE": 
                if(isDouble(Valor)){
                    temp.setValor(Valor);
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;
            
            case "BOOL": 
                if(Valor.equals("verdadero")||Valor.equals("1")||Valor.equals("falso")||Valor.equals("0")){
                    if(Valor.equals("verdadero")||Valor.equals("1")){
                        temp.setValor("true");
                    }else{
                        temp.setValor("false");
                    }
                    
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;
            
            case "DATE":
                if(Valor.matches("[1-3]*[0-9][-][0-1][0-9][-][1-9][0-9][0-9][0-9]")){
                    temp.setValor(Valor);
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;  
            
            case "DATETIME":
                if(Valor.matches("[1-3]*[0-9][-][0-1][0-9][-][1-9][0-9][0-9][0-9][\" ][0-2]*[0-9][:][0-5][0-9][:][0-5][0-9]")){
                    temp.setValor(Valor);
                }else{
                    System.out.println("Error: Tipos Incompatibles");
                }
            break;   
        }
        
    }
    
    boolean Existefuncion(String funcion, String Base){
        boolean respuesta=false;
        if (Existe("C:\\Base_Compi2\\BD\\" + Base + "_PRO.usac")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + "_PRO.usac"));

                NodeList items = doc.getElementsByTagName("Proc");

                int ix = 0;
                while (ix < items.getLength()) {
                    Element element = (Element) items.item(ix);
                    NodeList subitem = element.getElementsByTagName("nombre");
                    Element element2 = (Element) subitem.item(0);

                    if (element2.getTextContent().equals("\"" + funcion + "\"")) {
                        respuesta = true;
                        break;
                    }else{
                        ix++;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            respuesta = false;
        }  
        
        return respuesta;
    }
    
    String Ejecutar_Proc(String funcion, String Base){
        String Respuesta="";
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Base + "_PRO.usac"));

                NodeList items = doc.getElementsByTagName("Proc");

                int ix = 0;
                while (ix < items.getLength()) {
                    Element element = (Element) items.item(ix);
                    NodeList subitem = element.getElementsByTagName("nombre");
                    Element element2 = (Element) subitem.item(0);

                    if (element2.getTextContent().equals("\"" + funcion + "\"")) {
                        NodeList sentencias = element.getElementsByTagName("src");
                        Respuesta = sentencias.item(0).getTextContent();
                        break;
                    }else{
                        ix++;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        
        return Respuesta;
    }
    
    boolean Es_Variable(String Nombre, String Ambito){
        boolean Respuesta=false;
        
        Variable temp=variables.Buscar(Nombre);
        
        if(temp==null){
            Respuesta=false;
        }else{
            
            if(temp.getAmbito().equals(Ambito)||temp.getAmbito().equals("Global")){
                Respuesta=true;
            }else{
                Respuesta=false;
            }            
        }
        
        return Respuesta;
    }
    
    String Actualiza_Todo(String Tabla,String campos, String Datos){
        String respuesta="";   
        
        Datos=EliminaCaracteres(Datos);
        
        String[] campo=campos.split(",");
        String[] dato=Datos.split(",");
        
            try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));

            NodeList items = doc.getElementsByTagName("Tabla");
            Element element = (Element) items.item(0);
            
            NodeList filas = element.getElementsByTagName("Row");
            
                for (int x = 0; x < filas.getLength(); x++) {
                    Element elemento = (Element) filas.item(x);
                    NodeList elementos = elemento.getChildNodes();

                    for (int z = 0; z < elementos.getLength(); z++) {
                        Node temp = elementos.item(z);

                        for (int y = 0; y < campo.length; y++) {

                            if (temp.getNodeName().equals(campo[y])) {
                                temp.setTextContent(dato[y]);
                            }
                        }

                    }

                }
            
                  

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
            
          

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }
    
    String Actualiza_Selec(String Tabla,String camposs,String Datos,String camposc,String datosc){
        String respuesta="";   
        
        Datos=EliminaCaracteres(Datos);
        datosc=EliminaCaracteres(datosc);
        
        String[] campo=camposs.split(",");
        String[] dato=Datos.split(",");
        
        String[] campoc=camposc.split(",");
        String[] datoc=datosc.split(",");
        
            try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));

            NodeList items = doc.getElementsByTagName("Tabla");
            Element element = (Element) items.item(0);
            
            NodeList filas = element.getElementsByTagName("Row");
            
            for(int x=0;x<filas.getLength();x++){
                boolean cumple=false;
                Node temp=filas.item(x);
                
                for(int y=0;y<campo.length;y++){
                    
                    if(temp.getNodeName().equals(campoc[y])){
                        if(temp.getTextContent().equals(datoc[y])){
                            cumple=true;
                        }
                    }
                }
                
                
                if(cumple){
                    for (int y = 0; y < campo.length; y++) {

                            if (temp.getNodeName().equals(campo[y])) {
                                temp.setTextContent(dato[y]);
                            }
                        }
                }
                
            }
            
                  

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + Tabla + ".usac"));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
            
          

        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }
   
    String Eliminar(String tipo, String objeto){
        String Respuesta="";
        
        switch (tipo){
            
            case "TABLA":
                if (Existe("C:\\Base_Compi2\\BD\\" + objeto + ".usac")) {
                   File file=new File("C:\\Base_Compi2\\BD\\" + objeto + ".usac");
                   file.delete();
                   Respuesta="Tabla: "+objeto+" Eliminada";
               }else{
                   Respuesta="NO existe ninguna Tabla con el nombre: "+objeto;
               } 
            break;    
            
            case "OBJETO":
                if(!BASE_USO.equals("")){
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(new File("C:/Base_Compi2/BD/" + BASE_USO + "_OBJ.usac"));

                        NodeList items = doc.getElementsByTagName("Objeto");
                        Element element = (Element) items.item(0);

                        NodeList objetos = element.getElementsByTagName("OBJ");
                        for(int x=0;x<objetos.getLength();x++){
                            Element elemento=(Element) objetos.item(x);
                            NodeList datos=elemento.getElementsByTagName("nombre");
                            Node nombre=datos.item(0);
                            if(nombre.getTextContent().equals("\""+objeto+"\"")){
                                elemento.getParentNode().removeChild(elemento);
                                Respuesta="Objeto:" +objeto+" eliminado";
                                break;
                            }
                            
                            if(x==(objetos.getLength()-1)){
                                Respuesta="El Objeto:"+objeto+" no Existe";
                            }
                        }
                                               

                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        Result output = new StreamResult(new File("C:/Base_Compi2/BD/" + BASE_USO + "_OBJ.usac"));
                        Source input = new DOMSource(doc);
                        transformer.transform(input, output);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Respuesta ="No hay ninguna base en uso";
                }
            break;
            
            case "BASE_DATOS":
               if (Existe("C:\\Base_Compi2\\BD\\" + objeto + ".usac")) {
                   File file=new File("C:\\Base_Compi2\\BD\\" + objeto + ".usac");
                   file.delete();
                   file=new File("C:\\Base_Compi2\\BD\\" + objeto + "_OBJ.usac");
                   file.delete();
                   file=new File("C:\\Base_Compi2\\BD\\" + objeto + "_PRO.usac");
                   file.delete();
                   Respuesta="Base de Datos: "+objeto+" Eliminada";
               }else{
                   Respuesta="NO existe ninguna base de dato con el nombre: "+objeto;
               } 
            break;
            
            case "USUARIO":
                try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document doc = builder.parse(new File("C:/Base_Compi2/BD/Tabla_Usuarios.usac"));

                        NodeList items = doc.getElementsByTagName("Tabla");
                        Element element = (Element) items.item(0);

                        NodeList objetos = element.getElementsByTagName("Row");
                        for(int x=0;x<objetos.getLength();x++){
                            Element elemento=(Element) objetos.item(x);
                            NodeList datos=elemento.getElementsByTagName("Usuario");
                            Node nombre=datos.item(0);
                            if(nombre.getTextContent().equals(objeto)){
                                elemento.getParentNode().removeChild(elemento);
                                Respuesta="Usuario:" +objeto+" eliminado";
                                break;
                            }
                            
                            if(x==(objetos.getLength()-1)){
                                Respuesta="El Objeto:"+objeto+" no Existe";
                            }
                        }
                                               

                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        Result output = new StreamResult(new File("C:/Base_Compi2/BD/Tabla_Usuarios.usac"));
                        Source input = new DOMSource(doc);
                        transformer.transform(input, output);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            break;
            
        }
        return Respuesta;
    }
    
    public String Ejecuccion(SimpleNode raiz) throws FileNotFoundException{
        String Respuesta="";
        int id=raiz.id;
        switch(id){
            
            case 0://Programa

                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;
            
            case 1://Inicio
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;
                
            case 2://Sentencias
                
                if(raiz.children.length>1){
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                Respuesta+=Ejecuccion((SimpleNode) raiz.children[1]);
                }else{
                 Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }

                break; 
                
            case 3://Sentencia
               Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;
                
            case 4://USAR   
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
                
            case 7://Crear
                System.out.println(((SimpleNode) raiz.children[0]).name);
                Respuesta="CREAR@ "+ Ejecuccion((SimpleNode) raiz.children[1]);
                break;   
                
            case 8://Opciones Crear
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;   
                
            case 9://Crear Base
                System.out.println(((SimpleNode) raiz.children[0]).name);
                if(Ejecutar){
                    Respuesta=CrearBase(((SimpleNode) raiz.children[0]).name);

                }else{
                    Respuesta="BASE_DATOS "+((SimpleNode) raiz.children[0]).name+"\n";
                }
                
                break;   
                
            case 10://Crear Tabla
                
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
                
            case 11://Campos Tabla
                if (raiz.children.length > 1) {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                    for (int x = 1; x < raiz.children.length; x++) {
                        Respuesta += "," + Ejecuccion((SimpleNode) raiz.children[x]);
                    }

                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                break;

            case 12://Campos Tabla Prima
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                break;   
                
            case 13://Campo Tabla
                if (raiz.children.length > 2) {
                    Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;

                    for (int x = 2; x < raiz.children.length; x++) {
                        Respuesta += " " + ((SimpleNode) raiz.children[x]).name;
                    }

                } else {
                    Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;
                }

                break;  
            
            case 15://Crear Objeto
                
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
                
             case 16://Parametros
                if (raiz.children.length > 1) {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) ;

                    for (int x = 1; x < raiz.children.length; x++) {
                        Respuesta += "," +Ejecuccion((SimpleNode) raiz.children[x]);
                    }

                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                break;     
            
            case 17://Parametro
           
                Respuesta = ((SimpleNode) raiz.children[0]).name + " " + ((SimpleNode) raiz.children[1]).name;
                
                break;
                
            case 18://Crear Procedimiento
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
                
            case 19://Sub Sentencia
                if (raiz.children.length > 1) {
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    for (int x = 2; x < raiz.children.length; x++) {
                    Respuesta += "\n" +Ejecuccion((SimpleNode) raiz.children[0]);
                    }
                    
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            
            break;    
            
            case 20://Crear Funcion
                
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
            
            case 21://Sentencias Retorno
               if (raiz.children.length > 2){
                   for(int x=0;x<raiz.children.length-1;x++){
                       Respuesta+=Ejecuccion((SimpleNode) raiz.children[x])+"\n";
                   }
                   Respuesta+="RETORNO "+Ejecuccion((SimpleNode) raiz.children[raiz.children.length-1]);
                   
               }else{
                   Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+"RETORNO "+Ejecuccion((SimpleNode) raiz.children[1]);
               } 
                
            break;
            
            case 22://Retorno
                Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) ;
                
            break;
            
            case 23://Crear Usuario
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
            
            case 24://Imprimir
                if(Ejecutar){
                
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]); 

                }else{
                   Respuesta="IMPRIMIR ("+Ejecuccion((SimpleNode) raiz.children[0])+");";
                }
                
                
            break;
            
            case 25://Insertar
                if(Ejecutar){
                
                TABLA_aux=((SimpleNode) raiz.children[0]).name;
                Ejecuccion((SimpleNode) raiz.children[1]); 
                

                }else{
                   Respuesta="INSERTAR EN TABLA "+((SimpleNode) raiz.children[0]).name+"("+Ejecuccion((SimpleNode) raiz.children[1]);
                }
                
                
            break;
                
            case 26://Tipo Ins
                if (raiz.children.length > 2) {
                    if(Ejecutar){
                        if (BASE_USO.equals("")) {
                        System.out.println("No Hay ninguna Base en Uso");
                        } else {
                        String Campos=Ejecuccion((SimpleNode) raiz.children[0]);
                        String Valores=Ejecuccion((SimpleNode) raiz.children[1]);   
                        System.out.println("Control");
                        
                        }
                        
                    }else{
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+") VALORES ("+Ejecuccion((SimpleNode) raiz.children[1])+");";
                    }
                }else{
                    if(Ejecutar){
                        if (BASE_USO.equals("")) {
                        System.out.println("No Hay ninguna Base en Uso");
                        } else {
                            String Valores=Ejecuccion((SimpleNode) raiz.children[0]);
                            Insertar_Tabla(Valores,TABLA_aux,BASE_USO);
                        }
                        TABLA_aux="";
                    }else{
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+");";
                    }
                }
                
                
            break;
            
            case 27://Campos
                if (raiz.children.length > 1) {
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+","+Ejecuccion((SimpleNode) raiz.children[1]);
                }else{
                  Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);  
                }
            break;
            
            case 28://Campo
                Respuesta=((SimpleNode) raiz.children[0]).name;
            
            break;
            
            case 29://Campo
                if (raiz.children.length > 1) {
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+","+Ejecuccion((SimpleNode) raiz.children[1]);
                }else{
                  Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);  
                }
                
            
            break;
            
            case 30://Actualizar
                
                if(Ejecutar){
                    TABLA_aux=((SimpleNode) raiz.children[0]).name;
                    String CAMPOS_aux=Ejecuccion((SimpleNode) raiz.children[1]);
                    String VALORES_aux=Ejecuccion((SimpleNode) raiz.children[2]);
                    //Condicion
                    if (raiz.children.length == 5) {
                        String condiciones=Ejecuccion((SimpleNode) raiz.children[3]);
                        System.out.println("prueba");
                    }else{
                        Actualiza_Todo(TABLA_aux,CAMPOS_aux,VALORES_aux);
                        //Ejecutar
                        
                    }
                }else{
                    if (raiz.children.length > 4) {
                        Respuesta="ACTUALIZAR TABLA"+((SimpleNode) raiz.children[0]).name+"("+Ejecuccion((SimpleNode) raiz.children[1])+") VALORES ("+Ejecuccion((SimpleNode) raiz.children[2])+")DONDE "+";";
                    }else{
                        Respuesta="ACTUALIZAR TABLA"+((SimpleNode) raiz.children[0]).name+"("+Ejecuccion((SimpleNode) raiz.children[1])+") VALORES ("+Ejecuccion((SimpleNode) raiz.children[2])+");";
                    }
                   
                }
            break;
            
            case 31://Condicionar
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;
            
            case 32://Borrar
                String Tabla=((SimpleNode) raiz.children[0]).name;;
                String condicion=Ejecuccion((SimpleNode) raiz.children[1]);
                //Borrar(Tabla,condicion);
            break;
            
            case 33: //Seleccionar
                if(Ejecutar){
                   String campos=Ejecuccion((SimpleNode) raiz.children[0]);
                   String tabla_busqueda=((SimpleNode) raiz.children[1]).name;
                   
                   if(raiz.children.length==5){
                   
                       
                   }else if (raiz.children.length == 4) {
                     String Condiciones=Ejecuccion((SimpleNode) raiz.children[2]);
                     System.out.println("Control");
                     
                     Respuesta=Selecionar(campos,tabla_busqueda,Condiciones,"");
                    }else{
                        Respuesta=Selecionar(campos,tabla_busqueda,"","");
                    }
                }else{
                    
                }
            break;
            
            case 34://campos Seleccion
                try {
                    
                    if(raiz.children.length==1){
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Respuesta="*";
                }
            break;
             
            case 35://Ordenamiento
                Respuesta=((SimpleNode) raiz.children[0]).name+";"+Ejecuccion((SimpleNode) raiz.children[1]);
            break;
            
            case 36:
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;
            
            case 37://Otorgar
                String usuario_Otor=((SimpleNode) raiz.children[0]).name;
                String Base_Otor=((SimpleNode) raiz.children[1]).name;
                String Objet_Otor=Ejecuccion((SimpleNode) raiz.children[2]);
                //Otorar_Permiso()
            break;
            
            case 38://Objeto Otorgar
                try {
                    
                    if(raiz.children.length==1){
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Respuesta="*";
                }
            break;
            
            case 39://Denegar
                String usuario_Deneg=((SimpleNode) raiz.children[0]).name;
                String Base_Deneg=((SimpleNode) raiz.children[1]).name;
                String Objet_Deneg=Ejecuccion((SimpleNode) raiz.children[2]);
                //DenegarPermisos
            break;
            
            case 40://back
                String Tipo_back=Ejecuccion((SimpleNode) raiz.children[0]);
                String Base_back=((SimpleNode) raiz.children[1]).name;
                String Archivo_back=((SimpleNode) raiz.children[2]).name;
                
            break;
            
            case 41://tipo back
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;
            
            case 42://restaurar
                String Tipo_restaurar=Ejecuccion((SimpleNode) raiz.children[0]);
                String Archivo_rest=((SimpleNode) raiz.children[1]).name;
            break;
            
            case 43://alterar
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
            break;
            
            case 44://alterado
                String alterado=((SimpleNode) raiz.children[0]).name;
                
            break;
            
            case 47://eliminar
                String obj_elim=Ejecuccion((SimpleNode) raiz.children[0]);   
                String eliminado=((SimpleNode) raiz.children[1]).name;      
                
                Eliminar(obj_elim,eliminado);
            break;
            
            case 48://objetos_eliminar
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;    
            
            case 49://Declarar
                if (Ejecutar) {
                    String variables = Ejecuccion((SimpleNode) raiz.children[0]);
                    String Tipo = ((SimpleNode) raiz.children[1]).name;
                    if (raiz.children.length == 4) {
                        String Valor = Ejecuccion((SimpleNode) raiz.children[2]);
                        Declarar(variables, AMBITO, Valor, Tipo);
                    } else {
                        Declarar(variables, AMBITO, "", Tipo);
                    }
                } else {
                    if (raiz.children.length == 4) {
                        Respuesta="DECLARAR "+Ejecuccion((SimpleNode) raiz.children[0])+" "+((SimpleNode) raiz.children[1]).name +"="+Ejecuccion((SimpleNode) raiz.children[2])+";";
                    }else{
                        Respuesta="DECLARAR "+Ejecuccion((SimpleNode) raiz.children[0])+" "+((SimpleNode) raiz.children[1]).name+";";
                    }
                    
                }

            break;
            
            case 50://Lista Variables
                if(raiz.children.length==2){
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    Respuesta+=","+Ejecuccion((SimpleNode) raiz.children[1]);
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 51://Variable
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;
            
            case 52://Asignacion declaracion
                if(raiz.children.length==2){
                    //Ejecutar Funcion
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 53://Asignacion
                if(Ejecutar){
                    String varAsig=((SimpleNode) raiz.children[0]).name;
                    String Valor= Ejecuccion((SimpleNode) raiz.children[1]);
                    Asignacion(varAsig,Valor);
                    Respuesta="Exito";
                }else{
                    Respuesta=((SimpleNode) raiz.children[0]).name+" ="+Ejecuccion((SimpleNode) raiz.children[1])+";";
                }
            break;
            
            case 54://if
                if (Ejecutar) {
                    String condicionIF = Ejecuccion((SimpleNode) raiz.children[0]);

                    if (condicionIF.equals("true")) {
                        Respuesta = Ejecuccion((SimpleNode) raiz.children[1]);
                    }
                    if (raiz.children.length == 3) {
                        Respuesta = Ejecuccion((SimpleNode) raiz.children[2]);
                    }
                } else {
                   Respuesta="Si ("+ Ejecuccion((SimpleNode) raiz.children[0])+"){"+Ejecuccion((SimpleNode) raiz.children[1])+"}";     
                   if (raiz.children.length == 3) {
                        Respuesta+= Ejecuccion((SimpleNode) raiz.children[2]);
                    }
                }
                
                
            break;
            
            case 55://Else
                if(Ejecutar){
                 Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);   
                }else{
                    Respuesta= "SINO{"+Ejecuccion((SimpleNode) raiz.children[0])+"}";
                }
                 
            break;
            
            case 56://Switch
                if (Ejecutar) {
                    EXP_SWITCH = Ejecuccion((SimpleNode) raiz.children[0]);
                    AMBITO = "swith";
                    String r2 = Ejecuccion((SimpleNode) raiz.children[1]);

                    if (raiz.children.length == 3) {
                        if (Detener == false) {
                            Ejecuccion((SimpleNode) raiz.children[2]);
                        }

                    }
                    
                    Detener=true;
                    
                }else{
                    
                    if (raiz.children.length == 3) {
                        Respuesta="SELECCIONA("+Ejecuccion((SimpleNode) raiz.children[0])+"){"+Ejecuccion((SimpleNode) raiz.children[1])+"\n"+Ejecuccion((SimpleNode) raiz.children[2])+"}";
                    }else{
                        Respuesta="SELECCIONA("+Ejecuccion((SimpleNode) raiz.children[0])+"){"+Ejecuccion((SimpleNode) raiz.children[1])+"}";
                    }
                    
                }
              
                
                
            break;
            
            case 57://Casos
                
                if(raiz.children.length==2){
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                    if(Detener==false){
                    Respuesta+=Ejecuccion((SimpleNode) raiz.children[1]);
                    }
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 58://Caso
                if(Ejecutar){
                    Variable temp1=variables.Buscar(EXP_SWITCH);
                    String caso=Ejecuccion((SimpleNode) raiz.children[0]);

                    if(temp1.getValor().equals(caso) ||Ejecutar_Swithc){
                        Ejecutar_Swithc=true;
                        String c=Ejecuccion((SimpleNode) raiz.children[1]);
                        int asig=Integer.parseInt(temp1.getValor());
                        asig++;
                        Asignacion(EXP_SWITCH,String.valueOf(asig));
                        if(c.equals("Detener")){
                            Detener=true;
                        }
                    }
                }else{
                   Respuesta="caso" +Ejecuccion((SimpleNode) raiz.children[0])+":"+Ejecuccion((SimpleNode) raiz.children[1]);  
                }
                
                    
                
            break;
            
            case 59://Setencias Switch
                if (Ejecutar) {
                    
                    try {
                        if (((SimpleNode) raiz.children[0]).name.equals("DETENER")) {
                            Respuesta = "Detener";
                        }
                    } catch (Exception ex) {
                        if (raiz.children.length == 2) {
                            Ejecuccion((SimpleNode) raiz.children[0]);
                            Respuesta = Ejecuccion((SimpleNode) raiz.children[1]);
                        } else {
                            Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                        }
                    }
   
                    
                     
                } else {
                    
                    try{
                        if (((SimpleNode) raiz.children[0]).name.equals("DETENER")) {
                        Respuesta = "Detener;";
                        }
                    } catch (Exception ex) {
                        if (raiz.children.length == 2) {
                            Respuesta = Ejecuccion((SimpleNode) raiz.children[0])+" "+Ejecuccion((SimpleNode) raiz.children[1]);
                        } else {
                            Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                        }
                    }
                     
                }
                
            break;
            
            case 60://Defecto
                if(Ejecutar){
                   Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);     
                }else{
                   Respuesta = "DEFECTO:" +Ejecuccion((SimpleNode) raiz.children[0]);     
                }
                
            break;
            
            
            case 61://FOR
                if(Ejecutar){
                    String Ambtiotemp=AMBITO;
                AMBITO="for";
                
                String varFor=Ejecuccion((SimpleNode) raiz.children[0]); 
                String logicaFor=Ejecuccion((SimpleNode) raiz.children[2]); 
                String operadorF=Ejecuccion((SimpleNode) raiz.children[4]);
                
                while(logicaFor.equals("true") && Detener==false){
                    String r=Ejecuccion((SimpleNode) raiz.children[5]);
                    
                    if(r.equals("Detener")){
                        Detener=true;
                    }else if(operadorF.equals("++")){
                        Variable temp=variables.Buscar(varFor);
                        int temporal=Integer.parseInt(temp.getValor())+1;
                        Asignacion(varFor,String.valueOf(temporal));
                        
                    }else if(operadorF.equals("--")){
                        Variable temp=variables.Buscar(varFor);
                        int temporal=Integer.parseInt(temp.getValor())-1;
                        Asignacion(varFor,String.valueOf(temporal));
                    }
                    logicaFor=Ejecuccion((SimpleNode) raiz.children[2]); 
                }
                
                Detener=false;
                variables.Eliminar(varFor, AMBITO);
                
                AMBITO=Ambtiotemp;
                
                }else{
                    Respuesta="Para ("+Ejecuccion((SimpleNode) raiz.children[0])+";"+Ejecuccion((SimpleNode) raiz.children[2])+";"+Ejecuccion((SimpleNode) raiz.children[4])+"){"+Ejecuccion((SimpleNode) raiz.children[5])+"}";
                }

            break;
            
            case 62://Declaracion For
                if (Ejecutar) {
                    String idF = ((SimpleNode) raiz.children[0]).name;
                    String tipoF = "INTEGER";

                    String Valor = Ejecuccion((SimpleNode) raiz.children[1]);
                    Declarar(idF, AMBITO, Valor, tipoF);

                    Respuesta = idF;
                }else{
                    Respuesta="Declarar "+((SimpleNode) raiz.children[0]).name+" INTEGER ="+Ejecuccion((SimpleNode) raiz.children[1]);
                }
                
                
                
            break;
            
            case 63://Operador FOr
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;    
            
            case 64://WHILE
                if(Ejecutar){
                    String logicaWhile=Ejecuccion((SimpleNode) raiz.children[0]); 
                
                while(logicaWhile.equals("true") && Detener==false){
                    String r=Ejecuccion((SimpleNode) raiz.children[1]);
                    
                    if(r.equals("Detener")){
                        Detener=true;
                    }
                }
                }else{
                   Respuesta="Mientras ("+Ejecuccion((SimpleNode) raiz.children[0])+"){"+Ejecuccion((SimpleNode) raiz.children[1])+"}";
                }
                
                
                
            break;
                
            case 65://Fecha
                String varFecha=((SimpleNode) raiz.children[0]).name;
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String valor=dateFormat.format(date);
                
                Asignacion(varFecha,valor);
            break;
            
            case 66://FechaHora
                String varFechaHora=((SimpleNode) raiz.children[0]).name;
                Date date2 = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                String valorHora=hourdateFormat.format(date2);
                
                Asignacion(varFechaHora,valorHora);
            break;
            
            case 67://Contar
                String seleccion=Ejecuccion((SimpleNode) raiz.children[0]);
                
                String[] contable=seleccion.split("#");
                
                Respuesta=String.valueOf(contable.length);
                
            break;
            
            case 69://Logica 
             
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
           
                
            break;    
            
            case 70://Logica OR
                if (raiz.children.length > 1)   {
                  if(Ejecutar){
                    String op1=Ejecuccion((SimpleNode) raiz.children[0]);
                    String op2=Ejecuccion((SimpleNode) raiz.children[2]);
                    
                    if(op1.equals("true")|| op2.equals("true")){
                       Respuesta="true"; 
                    }else{
                        Respuesta="false"; 
                    }
                  }  
                }else{
                 Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);    
                }
            
            break;
            
            case 71://Logica AND
                if (raiz.children.length == 3) {

                    if (((SimpleNode) raiz.children[0]).name.equals("(")) {
                        Respuesta = Ejecuccion((SimpleNode) raiz.children[1]);
                    } else{
                        if (Ejecutar) {
                            String op1 = Ejecuccion((SimpleNode) raiz.children[0]);
                            String op2 = Ejecuccion((SimpleNode) raiz.children[2]);

                            if (op1.equals("true") && op2.equals("true")) {
                                Respuesta = "true";
                            } else {
                                Respuesta = "false";
                            }
                        }
                    }

                }else if (raiz.children.length == 2){
                    String op1 = Ejecuccion((SimpleNode) raiz.children[1]);
                    
                    if(op1.equals("true")){
                         Respuesta = "false";
                    }else if(op1.equals("false")){
                         Respuesta = "true";
                    }
                    
                }else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 72://Relacional
                if (raiz.children.length > 1) {
                    if (Ejecutar) {
                        String op1 = Ejecuccion((SimpleNode) raiz.children[0]);
                        String[] componentes = Ejecuccion((SimpleNode) raiz.children[1]).split(";");
                        String operacion=componentes[0];
                        String op2=componentes[1];
                        
                        Respuesta=relacional(op1,op2,operacion);
                    }else{
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+" "+Ejecuccion((SimpleNode) raiz.children[1]);
                    }
                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 73://Relacional prima
                if (raiz.children.length > 2)   {
                   if (Ejecutar) {
                       
                      Respuesta =((SimpleNode) raiz.children[0]).name+";"; 
                      String op1 = Ejecuccion((SimpleNode) raiz.children[1]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[2]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                      Respuesta+= relacional(op1,op2,componentes[0]);
                      
                   }else{  
                        Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1])+ Ejecuccion((SimpleNode) raiz.children[2]); 
                   }
                }else{
                     if (Ejecutar) {
                         Respuesta =((SimpleNode) raiz.children[0]).name +";"+ Ejecuccion((SimpleNode) raiz.children[1]); 
                     }else{
                     Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1]); 
                     }                 
                }
                 
            break;
            
            case 74://sub Relacional
                if (raiz.children.length > 3)   {
                    
                   Respuesta= Ejecuccion((SimpleNode) raiz.children[1]); 
                }else{
                   Respuesta= Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 75://aritmetica
                
                   if (raiz.children.length > 1) {
                    if (Ejecutar) {
                        String op1 = Ejecuccion((SimpleNode) raiz.children[0]);
                        String comp = Ejecuccion((SimpleNode) raiz.children[1]);
                        String[] componentes = comp.split(";");
                        String op2 = componentes[1];

                        Respuesta = Suma_REsta(op1, op2, componentes[0]);
                    } else {
                        Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) + Ejecuccion((SimpleNode) raiz.children[1]);
                    }
                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                // <editor-fold>
            break;
            
            case 76://aritmetica_prima
                 if (raiz.children.length > 2)   {
                   if (Ejecutar) {
                       
                      Respuesta =((SimpleNode) raiz.children[0]).name+";"; 
                      String op1 = Ejecuccion((SimpleNode) raiz.children[1]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[2]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                      Respuesta+= Suma_REsta(op1,op2,componentes[0]);
                      
                   }else{  
                        Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1])+ Ejecuccion((SimpleNode) raiz.children[2]); 
                   }
                }else{
                     if (Ejecutar) {
                         Respuesta =((SimpleNode) raiz.children[0]).name +";"+ Ejecuccion((SimpleNode) raiz.children[1]); 
                     }else{
                     Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1]); 
                     }
                   
                }
            break;
            
            case 77://multi_div
                 if (raiz.children.length > 1)   {
                   if (Ejecutar) {
                      String op1 = Ejecuccion((SimpleNode) raiz.children[0]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[1]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                       Respuesta= Multi_DIv(op1,op2,componentes[0]);
                   }else{
                       Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+Ejecuccion((SimpleNode) raiz.children[1]);
                   }  
                }else{
                   Respuesta = Ejecuccion((SimpleNode) raiz.children[0]); 
                }
            break;
            
            case 78://multi prima
                 if (raiz.children.length > 2)   {
                   if (Ejecutar) {
                       
                      Respuesta =((SimpleNode) raiz.children[0]).name+";"; 
                      String op1 = Ejecuccion((SimpleNode) raiz.children[1]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[2]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                      Respuesta+= Multi_DIv(op1,op2,componentes[0]);
                      
                   }else{  
                        Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1])+ Ejecuccion((SimpleNode) raiz.children[2]); 
                   }
                }else{
                    if (Ejecutar) {
                         Respuesta =((SimpleNode) raiz.children[0]).name +";"+ Ejecuccion((SimpleNode) raiz.children[1]); 
                     }else{
                     Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1]); 
                     }
                }
            break;
            
            case 79://potenciar
                 if (raiz.children.length > 1)   {
                   if (Ejecutar) {
                      String op1 = Ejecuccion((SimpleNode) raiz.children[0]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[1]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                       Respuesta= potencia(op1,op2);
                   }else{
                       Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+Ejecuccion((SimpleNode) raiz.children[1]);
                   }  
                }else{
                   Respuesta = Ejecuccion((SimpleNode) raiz.children[0]); 
                }
            break;
            
            case 80://potencia prima
                 if (raiz.children.length > 2)   {
                   if (Ejecutar) {
                       
                      Respuesta =((SimpleNode) raiz.children[0]).name+";"; 
                      String op1 = Ejecuccion((SimpleNode) raiz.children[1]);
                      String comp=Ejecuccion((SimpleNode) raiz.children[2]);
                      String[] componentes =comp .split(";");
                      String op2=componentes[1];
                      
                      Respuesta+= potencia(op1,op2);
                      
                   }else{  
                        Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1])+ Ejecuccion((SimpleNode) raiz.children[2]); 
                   }
                }else{
                   if (Ejecutar) {
                         Respuesta =((SimpleNode) raiz.children[0]).name +";"+ Ejecuccion((SimpleNode) raiz.children[1]); 
                     }else{
                     Respuesta =((SimpleNode) raiz.children[0]).name + Ejecuccion((SimpleNode) raiz.children[1]); 
                     }
                }
            break;
            
            case 81://unario
                if (raiz.children.length ==3) {
                    if(Ejecutar){
                        Respuesta=Ejecuccion((SimpleNode) raiz.children[1]);
                    }else{
                        Respuesta="("+Ejecuccion((SimpleNode) raiz.children[1])+")";
                    }
                }else if (raiz.children.length ==2) {
                   if(Ejecutar){
                       String dato=Ejecuccion((SimpleNode) raiz.children[1]);
                       
                       if(isNumeric(dato)||isDouble(dato)){
                           Respuesta="-"+dato;
                       }else{
                          Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES"); 
                       }
                   }else{
                       String dato=Ejecuccion((SimpleNode) raiz.children[1]);
                       Respuesta="-"+dato;
                   }
                }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0]);
                }
            break;
            
            case 82://Expresion
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;
            
            case 87://Procedimiento exec
                if (!BASE_USO.equals("")) {
                    String Proc_name=((SimpleNode) raiz.children[0]).name;
                    
                    if(Existefuncion(Proc_name,BASE_USO)){
                        String instrucciones=Ejecutar_Proc(Proc_name,BASE_USO);
                         InputStream is = new ByteArrayInputStream(instrucciones.getBytes());
                         Analizador a = new Analizador(is, "UTF-8");
                        try {
                            SimpleNode subraiz = a.Programa();
                            Respuesta=Ejecuccion(subraiz);
                            
                            //Respuesta
                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                    }else{
                        Respuesta="No Existe el Procedimiento:"+Proc_name+" en la base:"+BASE_USO;
                    }
                    
                }else{
                    Respuesta="No hay ninguna base en uso";
                }
                    
            break;
                
            case 88://SubSentencia
                if (raiz.children.length > 1) {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]) ;

                    for (int x = 1; x < raiz.children.length; x++) {
                        Respuesta += "\n"+Ejecuccion((SimpleNode) raiz.children[x]);
                    }

                } else {
                    Respuesta = Ejecuccion((SimpleNode) raiz.children[0]);
                }
                break;
                
            
            
            case 105://logica consultas
                if (raiz.children.length >3)   {
                    
                Respuesta=Ejecuccion((SimpleNode) raiz.children[0]) +" "+Ejecuccion((SimpleNode) raiz.children[1]) +" "+Ejecuccion((SimpleNode) raiz.children[2])+" "+Ejecuccion((SimpleNode) raiz.children[3]);
                    
                }else if (raiz.children.length ==3){
                    String control=Ejecuccion((SimpleNode) raiz.children[0]);
                    
                    if(control.equals("==")||control.equals(">=")||control.equals("<=")||control.equals("<")||control.equals(">")){
                        Respuesta="usuario "+control + Ejecuccion((SimpleNode) raiz.children[1])+" "+Ejecuccion((SimpleNode) raiz.children[2]);
                    }else{
                        Respuesta=control +" "+Ejecuccion((SimpleNode) raiz.children[1])+" "+Ejecuccion((SimpleNode) raiz.children[2]);
                    }
                    
                }else if (raiz.children.length ==2){
                      Respuesta="usuario "  + Ejecuccion((SimpleNode) raiz.children[0])+" "+Ejecuccion((SimpleNode) raiz.children[1]);  
                }
            break;
            
            case 106://operador_consulta
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;    
            
            case 107:
                Respuesta=((SimpleNode) raiz.children[0]).name;
            break;
            
            case 108:
                 if (raiz.children.length >3)   {
                     Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+" "+Ejecuccion((SimpleNode) raiz.children[1])+" "+Ejecuccion((SimpleNode) raiz.children[2])+" "+Ejecuccion((SimpleNode) raiz.children[3]);
                 }else{
                    Respuesta=Ejecuccion((SimpleNode) raiz.children[0])+" password "+Ejecuccion((SimpleNode) raiz.children[1])+" "+Ejecuccion((SimpleNode) raiz.children[2]); 
                 }   
            break;
                
            default:
                Respuesta="Default ";
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
    
    String Suma_REsta(String op1,String op2,String op){
        String Respuesta="";
        
        if(Es_Variable(op1,AMBITO)){
         Variable temp=variables.Buscar(op1);
         op1=temp.getValor();
        }
        
        if(Es_Variable(op2,AMBITO)){
         Variable temp=variables.Buscar(op2);
         op2=temp.getValor();
        }
        
        switch (op) {

            case "+":
                //op1 booleanno
                if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
                    //op2 booleano
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        if ((op1.equals("true") || op1.equals("1")) || (op2.equals("true") || op2.equals("1"))) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";

                        //op2 Int
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 + Integer.parseInt(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 + Integer.parseInt(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 + Double.parseDouble(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 + Double.parseDouble(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 Int
                } else if (isNumeric(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        int r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Integer.parseInt(op1) + 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = 0 + Integer.parseInt(op1);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int   
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) + Integer.parseInt(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) + (int) Double.parseDouble(op2);
                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }

                    //op1 Double
                } else if (isDouble(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        double r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Double.parseDouble(op1) + 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = 0 + Double.parseDouble(op1);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int 
                    } else if (isNumeric(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) + Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) + Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 String    
                } else if (op1.charAt(0) == '\"') {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {

                        if (op2.equals("1")) {
                            Respuesta = "\"" + op1.substring(1, op1.length() - 1) + "verdadero\"";
                        } else if (op2.equals("0")) {
                            Respuesta = "\"" + op1.substring(1, op1.length() - 1) + "falso\"";
                        } else {
                            Respuesta = "\"" + op1.substring(1, op1.length() - 1) + op2 + "\"";
                        }

                        //op2 string   
                    } else if (op2.charAt(0) == '\"') {

                        Respuesta = "\"" + op1.substring(1, op1.length() - 1) + op2.substring(1, op2.length() - 1) + "\"";

                        //op2 todo lo demas
                    } else {
                        Respuesta = "\"" + op1.substring(1, op1.length() - 1) + op2 + "\"";
                    }
                } else if (op2.charAt(0) == '\"') {
                    Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";
                } else {
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");
                }
                break;

            case "-":
                //op1 booleanno
                if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
                    //op2 booleano
                     if (isNumeric(op2)) {
                        int r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 - Integer.parseInt(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 - Integer.parseInt(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 - Double.parseDouble(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 - Double.parseDouble(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 DAte     
                    } else if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 Int
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 Int
                } else if (isNumeric(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        int r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Integer.parseInt(op1) - 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = Integer.parseInt(op1) - 0;
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int   
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) - Integer.parseInt(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) - (int) Double.parseDouble(op2);
                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }

                    //op1 Double
                } else if (isDouble(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        double r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Double.parseDouble(op1) - 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = Double.parseDouble(op1) - 0;
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int 
                    } else if (isNumeric(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) - Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) - Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 String    
                } else if (op1.charAt(0) == '\"') {

                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");
                } else {
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");
                }
                break;
        }
        
        
        return Respuesta;
    }
    
    String Multi_DIv(String op1,String op2,String op){
        String Respuesta="";
        
         if(Es_Variable(op1,AMBITO)){
         Variable temp=variables.Buscar(op1);
         op1=temp.getValor();
        }
        
        if(Es_Variable(op2,AMBITO)){
         Variable temp=variables.Buscar(op2);
         op2=temp.getValor();
        }
        
        switch (op) {
            
            case "*":
                if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
                    //op2 booleano
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        if ((op1.equals("true") || op1.equals("1")) && (op2.equals("true") || op2.equals("1"))) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 Int
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 * Integer.parseInt(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 * Integer.parseInt(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 * Double.parseDouble(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 * Double.parseDouble(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 Int
                } else if (isNumeric(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        int r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Integer.parseInt(op1) * 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = 0 * Integer.parseInt(op1);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int   
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) * Integer.parseInt(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) * (int) Double.parseDouble(op2);
                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }

                    //op1 Double
                } else if (isDouble(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        double r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Double.parseDouble(op1) * 1;
                        } else if (op2.equals("false") || op2.equals("0")) {
                            r = 0 * Double.parseDouble(op1);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 int 
                    } else if (isNumeric(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) * Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) * Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 String    
                } else if (op1.charAt(0) == '\"') {
                    
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");
                    
                    //fecha
                } else {
                    
                    if (op2.charAt(0) == '\"') {
                     Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";   
                    }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");    
                    }
                    
                }
                
            break; 
            
            case "/":
                if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
                    //op2 booleano
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                        
                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 Int
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 / Integer.parseInt(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 / Integer.parseInt(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        if (op1.equals("true") || op1.equals("1")) {
                            r = 1 / Double.parseDouble(op2);
                        } else if (op1.equals("false") || op1.equals("0")) {
                            r = 0 / Double.parseDouble(op2);
                        }
                        Respuesta = String.valueOf(r);

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 Int
                } else if (isNumeric(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        int r = 0;
                        if (op2.equals("true") || op2.equals("1")) {
                            r = Integer.parseInt(op1) / 1;
                            Respuesta = String.valueOf(r);
                        } else if (op2.equals("false") || op2.equals("0")) {
                            Respuesta = "";
                            System.out.println("ERROR");
                        }
                        

                        //op2 int   
                    } else if (isNumeric(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) / Integer.parseInt(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        int r = 0;
                        r = Integer.parseInt(op1) / (int) Double.parseDouble(op2);
                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }

                    //op1 Double
                } else if (isDouble(op1)) {
                    //op2 boolean
                    if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                        double r = 0;
                         if (op2.equals("true") || op2.equals("1")) {
                            r = Double.parseDouble(op1) / 1;
                            Respuesta = String.valueOf(r);
                        } else if (op2.equals("false") || op2.equals("0")) {
                            Respuesta = "";
                            System.out.println("ERROR");
                        }
                        

                        //op2 int 
                    } else if (isNumeric(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) / Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 Double    
                    } else if (isDouble(op2)) {
                        double r = 0;
                        r = Double.parseDouble(op1) / Double.parseDouble(op2);

                        Respuesta = String.valueOf(r);

                        //op2 String    
                    } else if (op2.charAt(0) == '\"') {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");

                        //op2 DAte     
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    //op1 String    
                } else if (op1.charAt(0) == '\"') {
                    
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");
                    
                    //fecha
                } else {
                    
                    if (op2.charAt(0) == '\"') {
                     Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";   
                    }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES");    
                    }
                    
                }
            break;           
        }
        
        return Respuesta;
    }
    
    String potencia(String op1,String op2){
        String Respuesta="";
        
         if(Es_Variable(op1,AMBITO)){
         Variable temp=variables.Buscar(op1);
         op1=temp.getValor();
        }
        
        if(Es_Variable(op2,AMBITO)){
         Variable temp=variables.Buscar(op2);
         op2=temp.getValor();
        }
        
        if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
            //op2 booleano
            if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");

                //op2 String    
            } else if (op2.charAt(0) == '\"') {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");

                //op2 Int
            } else if (isNumeric(op2)) {
                int r = 0;
                if (op1.equals("true") || op1.equals("1")) {
                    r= (int) Math.pow(1, Integer.parseInt(op2));
                } else if (op1.equals("false") || op1.equals("0")) {
                    r= (int) Math.pow(0, Integer.parseInt(op2));
                }
                Respuesta = String.valueOf(r);

                //op2 Double    
            } else if (isDouble(op2)) {
                double r = 0;
                if (op1.equals("true") || op1.equals("1")) {
                    r =Math.pow(1, Double.parseDouble(op2));
                } else if (op1.equals("false") || op1.equals("0")) {
                    r = Math.pow(0, Double.parseDouble(op2));
                }
                Respuesta = String.valueOf(r);

                //op2 DAte     
            } else {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");
            }
            //op1 Int
        } else if (isNumeric(op1)) {
            //op2 boolean
            if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                int r = 0;
                if (op2.equals("true") || op2.equals("1")) {
                    r= (int) Math.pow(Integer.parseInt(op1),1);
                } else if (op2.equals("false") || op2.equals("0")) {
                    r= (int) Math.pow(Integer.parseInt(op1),0);
                }
                Respuesta = String.valueOf(r);

                //op2 int   
            } else if (isNumeric(op2)) {
                int r = 0;
                r= (int) Math.pow(Integer.parseInt(op1),Integer.parseInt(op2));

                Respuesta = String.valueOf(r);

                //op2 Double    
            } else if (isDouble(op2)) {
                int r = 0;
                  r= (int) Math.pow(Integer.parseInt(op1),Double.parseDouble(op2));
                Respuesta = String.valueOf(r);

                //op2 String    
            } else if (op2.charAt(0) == '\"') {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");

                //op2 DAte     
            } else {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");
            }

            //op1 Double
        } else if (isDouble(op1)) {
            //op2 boolean
            if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {
                double r = 0;
                if (op2.equals("true") || op2.equals("1")) {
                    r= Math.pow(Integer.parseInt(op1),1);
                } else if (op2.equals("false") || op2.equals("0")) {
                    r= Math.pow(Integer.parseInt(op1),0);
                }
                Respuesta = String.valueOf(r);

                //op2 int 
            } else if (isNumeric(op2)) {
                double r = 0;
                r = Math.pow(Integer.parseInt(op1),Double.parseDouble(op2));

                Respuesta = String.valueOf(r);

                //op2 Double    
            } else if (isDouble(op2)) {
                double r = 0;
                r = Math.pow(Integer.parseInt(op1),Double.parseDouble(op2));

                Respuesta = String.valueOf(r);

                //op2 String    
            } else if (op2.charAt(0) == '\"') {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");

                //op2 DAte     
            } else {
                Respuesta = "";
                System.out.println("ERROR TIPOS INCOMPATIBLES");
            }
            //op1 String    
        } else if (op1.charAt(0) == '\"') {

            Respuesta = "";
            System.out.println("ERROR TIPOS INCOMPATIBLES");

            //fecha
        } else if (op2.charAt(0) == '\"') {
            Respuesta = "\"" + op1 + op2.substring(1, op2.length() - 1) + "\"";
        } else {
            Respuesta = "";
            System.out.println("ERROR TIPOS INCOMPATIBLES");
        }
        
        
        return Respuesta;
    }
    
    String relacional(String op1,String op2,String op){
        String Respuesta="";
                
         if(Es_Variable(op1,AMBITO)){
         Variable temp=variables.Buscar(op1);
         op1=temp.getValor();
        }
        
        if(Es_Variable(op2,AMBITO)){
         Variable temp=variables.Buscar(op2);
         op2=temp.getValor();
        }
        
            switch (op) {
                
                case "==":
                    if (op1.equals("true") || op1.equals("false") || op1.equals("1") || op1.equals("0")) {
                    //op2 booleano
                        if (op2.equals("true") || op2.equals("false") || op2.equals("1") || op2.equals("0")) {

                            if ((op1.equals("true") || op1.equals("1")) == (op2.equals("true") || op2.equals("1"))) {
                                Respuesta = "true";
                            } else if ((op1.equals("false") || op1.equals("0")) == (op2.equals("false") || op2.equals("0"))) {
                                Respuesta = "true";
                            } else {
                                Respuesta = "false";
                            }

                            //op2 String    
                        } else {
                            Respuesta = "";
                            System.out.println("ERROR TIPOS INCOMPATIBLES");
                        }
                    //op1 Int
                } else if (isNumeric(op1)) {
                    //op2 boolean
                    if (isNumeric(op2)) {
                        
                        if (Integer.parseInt(op1) == Integer.parseInt(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    
                }else if (isDouble(op1)){
                    if (isDouble(op2)) {
                        
                        if (Double.parseDouble(op1) == Double.parseDouble(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else if (op1.charAt(0) == '\"'){
                    if (op2.charAt(0) == '\"'){
                     
                        if(op1.equals(op2)){
                            Respuesta="true";
                        }else{
                            Respuesta = "false";
                        }
                        
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else{
                   if(op1.equals(op2)){
                            Respuesta="true";
                        }else{
                            Respuesta = "false";
                        } 
                }
                break;
                
                case "<":
                    if (isNumeric(op1)) {
                    //op2 boolean
                    if (isNumeric(op2)) {
                        
                        if (Integer.parseInt(op1) < Integer.parseInt(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    
                }else if (isDouble(op1)){
                    if (isDouble(op2)) {
                        
                        if (Double.parseDouble(op1) < Double.parseDouble(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES"); 
                }
                break;
                
                case ">":
                    if (isNumeric(op1)) {
                    //op2 boolean
                    if (isNumeric(op2)) {
                        
                        if (Integer.parseInt(op1) > Integer.parseInt(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    
                }else if (isDouble(op1)){
                    if (isDouble(op2)) {
                        
                        if (Double.parseDouble(op1) > Double.parseDouble(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES"); 
                }
                break; 
                
                case "<=":
                    if (isNumeric(op1)) {
                    //op2 boolean
                    if (isNumeric(op2)) {
                        
                        if (Integer.parseInt(op1) <= Integer.parseInt(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    
                }else if (isDouble(op1)){
                    if (isDouble(op2)) {
                        
                        if (Double.parseDouble(op1) <= Double.parseDouble(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES"); 
                }
                break; 
                
                case ">=":
                    if (isNumeric(op1)) {
                    //op2 boolean
                    if (isNumeric(op2)) {
                        
                        if (Integer.parseInt(op1) >= Integer.parseInt(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                    
                }else if (isDouble(op1)){
                    if (isDouble(op2)) {
                        
                        if (Double.parseDouble(op1) >= Double.parseDouble(op2)) {
                            Respuesta = "true";
                        } else {
                            Respuesta = "false";
                        }
    
                    } else {
                        Respuesta = "";
                        System.out.println("ERROR TIPOS INCOMPATIBLES");
                    }
                }else{
                    Respuesta = "";
                    System.out.println("ERROR TIPOS INCOMPATIBLES"); 
                }
                break; 
            }     
            
        
        return Respuesta;
    }
}
