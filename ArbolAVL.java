package Estructuras;

import java.io.FileWriter;
import java.io.PrintWriter;


public class  ArbolAVL{
       static public Nodo raiz;
    
    public static class Nodo{
        private String Categoria;
        private int balance;
        private int altura;
        private Nodo izquierdo; //left
        private Nodo derecho;   //rigth
        private Nodo padre;
        private static int correlativo=1;
        private int id;
        
        Nodo(String categoria,Nodo padre){
            this.Categoria=categoria;
            this.padre=padre;
            this.id=correlativo++;
        }
        
         public void graficar(String path){
        FileWriter fichero=null;
        PrintWriter escritor;
        try{
            fichero=new FileWriter("arbolAvl.dot");
            escritor=new PrintWriter(fichero);
            escritor.print(getCodigoGraphviz());
        }catch(Exception e){
            System.out.println("Error al escribir el archivo auxiliar");
        }finally{
            try{
                if(null!=fichero){
                  fichero.close();  
                }
            }catch(Exception e){
               System.out.println("Error al cerrar el archivo auxiliar");
            }
        }
        try{
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o "+path+" arbolAvl.dot");
            //Thread.sleep(500);
        }catch(Exception ex){
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
            
        }
    }
    public String getCodigoGraphviz(){
        return "digraph grafica{\n"+
                "rankdir=TB;\n"+
                "node [shape=record,style=filled,fillcolor=seashell];\n"+
                getCodigoInterno()+
                "}\n";
    }
     public String getCodigoInterno(){
        String etiqueta;
        if(izquierdo==null && derecho==null){
            etiqueta="nodo"+id+"[label=\""+Categoria+"\"];\n";
        }else{
            etiqueta="nodo"+id+"[label=\"<C0>|"+Categoria+"|<C1>\"];\n";
        }
        if(izquierdo!=null){
            etiqueta=etiqueta+izquierdo.getCodigoInterno()+
                    "nodo"+id+":C0->nodo"+izquierdo.id+"\n";
        }
        if(derecho!=null){
            etiqueta=etiqueta+derecho.getCodigoInterno()+
                    "nodo"+id+":C1->nodo"+derecho.id+"\n";
        }
        return etiqueta;
    }
    
       
        
    }
     
    
     public boolean insertar(String categoria){
            if(raiz==null){
                raiz=new Nodo(categoria, null);
                return true;
            }
            Nodo n = raiz;
            while(true){
                if(n.Categoria.equals(categoria)){
                    return false;
                }
                Nodo padre = n;
                boolean aIzquierda = n.Categoria.compareTo(categoria)>0;
                n = aIzquierda ? n.izquierdo : n.derecho;
                
                if(n==null){
                    if(aIzquierda){
                        padre.izquierdo = new Nodo(categoria,padre);
                    }else{
                        padre.derecho = new Nodo(categoria,padre);
                    }
                    rebalance(padre);
                    break;
                    
                }
            }
            return true;
     }
     private void eliminar(Nodo nodo){
         if(nodo.izquierdo == null && nodo.derecho == null){
             if(nodo.padre==null){
                 raiz=null;
             }else{
                 Nodo padre = nodo.padre;
                 if(padre.izquierdo==nodo){
                     padre.izquierdo = null;
                 }else{
                     padre.derecho=null;
                 }
                 rebalance(padre);
             }
             return;
         }
         if(nodo.izquierdo!=null){
             Nodo hijo = nodo.izquierdo;
             while(hijo.derecho!=null){
                 hijo=hijo.derecho;
             }
             nodo.Categoria=hijo.Categoria;
             eliminar(hijo);
         }
         else{
             Nodo hijo = nodo.derecho;
             while(hijo.izquierdo!=null){
                 hijo=hijo.izquierdo;
             }
             nodo.Categoria=hijo.Categoria;
             eliminar(hijo);
         }
     }
    public void eliminar(String categoria){
        if(raiz==null){
            return;
        }
        Nodo hijo = raiz;
        while(hijo!=null){
            Nodo nodo = hijo;
            hijo = categoria.compareTo(nodo.Categoria)>=0 ? nodo.derecho:nodo.izquierdo;
            if(categoria==nodo.Categoria){
                eliminar(nodo);
                return;
            }
        }
    }
    private void rebalance(Nodo n){
        setBalance(n);
        if(n.balance==-2){
            if(altura(n.izquierdo.izquierdo)>= altura(n.izquierdo.derecho)){
                n=rotarDerecha(n);
            }else{
                n=rotarIzquierdaDerecha(n);
            }
           }
           else if(n.balance==2){
                if(altura(n.derecho.derecho)>= altura(n.derecho.izquierdo)){
                    n=rotarIzquierda(n);
                }   
                else{
                    n=rotarDerechaIzquierda(n);
                }
           }
        if(n.padre!=null){
            rebalance(n.padre);
        }else{
            raiz=n;
        }
    }
    private Nodo rotarIzquierda(Nodo a){
        Nodo b = a.derecho;
        b.padre = a.padre;
        a.derecho = b.izquierdo;
        if(a.derecho!=null){
            a.derecho.padre=a;
        }
        b.izquierdo=a;
        a.padre=b;
        if(b.padre!=null){
            if(b.padre.derecho==a){
                b.padre.derecho=b;
            }else{
                b.padre.izquierdo=b;
            }
        }
        setBalance(a,b);
        return b;
    }
    private Nodo rotarDerecha(Nodo a){
          Nodo b = a.izquierdo;
        b.padre = a.padre;
        a.izquierdo = b.derecho;
        if(a.izquierdo!=null){
            a.izquierdo.padre=a;
        }
        b.derecho=a;
        a.padre=b;
        if(b.padre!=null){
            if(b.padre.derecho==a){
                b.padre.derecho=b;
            }else{
                b.padre.izquierdo=b;
            }
        }
        setBalance(a,b);
        return b;
    }
    private Nodo rotarIzquierdaDerecha(Nodo n){
        n.izquierdo=rotarIzquierda(n.izquierdo);
        return rotarDerecha(n);
    }
    private Nodo rotarDerechaIzquierda(Nodo n){
        n.derecho=rotarDerecha(n.derecho);
        return rotarIzquierda(n);
    }
    private void setBalance(Nodo... nodos){
        for(Nodo n : nodos){
            reAltura(n);
            n.balance = altura(n.derecho)-altura(n.izquierdo);
        }
    }
    private void reAltura(Nodo n){
        if(n != null){
            n.altura=1+Math.max(altura(n.izquierdo),altura(n.derecho));
        }
    }
    private int altura(Nodo n){
        if(n==null){
            return -1;
        }
        return n.altura;
    }
    public void preOrder(Nodo raiz){
        if(raiz==null){
            return;
        }else{
            System.out.println(raiz.Categoria+"->");
            preOrder(raiz.izquierdo);
            preOrder(raiz.derecho);
            
        }
    }
    public static void main(String args[]){
           ArbolAVL tree = new ArbolAVL();
        
        /*System.out.println("Inserting values 1 to 10");
        for (int i = 1; i < 5; i++){
            tree.insertar(String.valueOf(i));
        }*/
        
        tree.insertar("Informativos");
        tree.insertar("Revista");
        tree.insertar("Drama");
        tree.insertar("Sagas");
        tree.insertar("Consulta");
        tree.insertar("Biografia");
 
    
        System.out.println();
        //tree.preOrder(tree.root);
        tree.raiz.graficar("ImagenArbolAVL.jpg");
        tree.preOrder(tree.raiz);
    }
}
