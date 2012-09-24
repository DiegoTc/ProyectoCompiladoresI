/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipython;
import java.io.IOException;
import minipython.lexico.lexico;
import minipython.lexico.tokenlist;
import sintactico.sintactico;
import sintactico.sintactico1;
/**
 *
 * @author diego
 */
public class MiniPython {

    /**
     * @param args the command line arguments
     * @throws IOException  
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
       /* String path;
        if (args.length != 1) 
        {
            System.out.println("usage: java -jar MiniPython.jar <archivo de entrada>");
            System.exit(0);
        }
        
        path=args[0];
        */
        
        //String path="/home/diego/UNITEC/2012/III_Periodo/Compiladores_I/Proyecto/Proyecto_CompiladoresI/ProyectoCompiladoresI/MiniPython/sample1.py";
       /*lexico lex=new lexico(path);
        lex.cs=lex.nextSymbol();
        tokenlist token=lex.nextToken();
        while(token.getTipo()!=tokenlist.tokens.EOF)
        {
            System.out.println(token.getTipo()+" contenido: "+token.toString());
            token=lex.nextToken();
        }*/
        String path="/home/diego/UNITEC/2012/III_Periodo/Compiladores_I/Proyecto/Proyecto_CompiladoresI/ProyectoCompiladoresI/MiniPython/Ejemplos/sample1.py";
        sintactico1 sin=new sintactico1(path);
        sin.currentToken=sin.nextToken();
        while(sin.currentToken!=tokenlist.tokens.EOF)
        {
            sin.start();
        }
        System.out.println("Termino");
        
    }
}
