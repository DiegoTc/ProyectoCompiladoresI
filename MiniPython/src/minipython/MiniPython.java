/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipython;
import com.sun.xml.internal.ws.api.pipe.NextAction;
import java.io.IOException;
import minipython.lexico.*;

/**
 *
 * @author diego
 */
public class MiniPython {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String path="/home/diego/UNITEC/2012/III_Periodo/Compiladores_I/Proyecto/Proyecto_CompiladoresI/ProyectoCompiladoresI/MiniPython/sample1.py";
        lexico lex=new lexico(path);
        lex.cs=lex.nextSymbol();
        tokenlist token=lex.nextToken();
        while(token.getTipo()!=tokenlist.tokens.EOF)
        {
            System.out.println(token.getTipo()+" contenido: "+token.toString());
            token=lex.nextToken();
        }
    }
}
