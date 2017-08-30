/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1;

import comunicacion.Datos;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;




/**
 *
 * @author Dannek
 */
public class HiloServidor  implements Runnable{
    
    Ventana p;
    
  
    HiloServidor(Ventana v){
        p=v;
        
       
    }
    
    @Override
    public void run() {
        Manejo manejo=new Manejo();
        manejo.CrearMaestro();
            while(true){
                try {
                    TServerSocket serverTransport=new TServerSocket(8080);
                    Datos.Processor processor= new Datos.Processor(manejo);
                    TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
                    server.serve();
                } catch (TTransportException ex) {
                    
                }
                
            }
    }
    
    
   
    
}
