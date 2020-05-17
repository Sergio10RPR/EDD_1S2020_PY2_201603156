package Estructuras;

import Datos.Blockchain;
import com.sun.jmx.mbeanserver.Util;
import java.io.FileWriter;
import java.io.PrintWriter;
import jdk.jfr.events.FileWriteEvent;


public class ListaBlockchain {
    class Nodo{
        int index;
        String timestamp;
        int nonce;
        String previoushash;
        String hash;
        Nodo previo;
        Nodo siguiente;
    }
   public Nodo header;
   public Nodo trailer;
   
   public ListaBlockchain(){
       header=new Nodo();
       trailer = new Nodo();
       header.siguiente=trailer;
       trailer.previo=header;
       header.previo=null;
       trailer.siguiente=null;
       
   }
   public void add(int index,String timestamp,int nonce,String previoushash,String hash){
       Nodo nuevoNodo = new Nodo();
       nuevoNodo.index=index;
       nuevoNodo.timestamp=timestamp;
       nuevoNodo.nonce=nonce;
       nuevoNodo.previoushash=previoushash;
       nuevoNodo.hash=hash;
       nuevoNodo.previo=header;
       nuevoNodo.siguiente=header.siguiente;
       header.siguiente.previo=nuevoNodo;
       header.siguiente=nuevoNodo;
       
   }
   public void graficarListaBlock(String path){
       Nodo nodo=trailer.previo;
       Nodo nodo2=header.siguiente;
       String dot="";
       String edge1="";
       String edge2="";
       int contador=0;
       FileWriter fichero=null;
       PrintWriter escritor = null;
       Runtime cmd = Runtime.getRuntime();
       try{
           fichero=new FileWriter("ListaBlockchain.dot");
           escritor=new PrintWriter(fichero);
           escritor.println("digraph G{\n");//apertura del archivo dot
           escritor.println("rankdir=LR;");
           escritor.println("node[shape=component,fontcolor=brown4,width=1.5,margin=0.2];\n");
           do{
               int point = nodo.hashCode();
               int point2 = nodo.previo.hashCode();
               escritor.println(point+"[label="+"\""+"INDEX:"+Integer.toString(nodo.index)+
                       "\\l"+"TIMESTAMP:"+nodo.timestamp+"\\l"+"NONCE:"+Integer.toString(nodo.nonce)
                        +"\\l"+"PREVIOUSHASH: "+nodo.previoushash+"\\l"+"HASH: "+nodo.hash+"\""+",style=filled,fillcolor=bisque1];");
               escritor.println(point2+"[label="+"\""+"NULL"+"\""+",style=filled,fillcolor=bisque1];");
               escritor.println(point+"->"+point2);
               escritor.println(point2+"->"+point);
               nodo=nodo.previo;
           }while(nodo!=header);
          escritor.println("}"); 
        cmd.exec("dot ListaBlockchain.dot -o "+ "ListaBlockchain_"+path+".png"+ " -Tpng");
        cmd.exec("rundll32 url.dll,FileProtocolHandler "+"ListaBlockchain_"+path+".png");
       }catch(Exception e){
           
       }
       finally{
        try{
            if(null!=fichero){
                fichero.close();
                       
            }
        }catch(Exception e2){
            e2.printStackTrace();
        }
    }
   }
   /*public static void main(String args[]){
       ListaBlockchain bloque=new ListaBlockchain();
       bloque.add(1,"1:4:5",345,"000","fjskfjskfjskf");
       bloque.add(2,"1:4:5",345,"000","fjskfjskfjskf");
       bloque.add(3,"1:4:5",345,"000","fjskfjskfjskf");
       bloque.add(4,"1:4:5",345,"000","fjskfjskfjskf");
       bloque.add(5,"1:4:5",345,"000","fjskfjskfjskf");
      
       bloque.graficarListaBlock();
   }*/
}
