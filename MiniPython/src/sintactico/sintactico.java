/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sintactico;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import minipython.lexico.*;
import minipython.lexico.tokenlist.tokens;
import  minipython.lexico.tokenlist;
/**
 *
 * @author diego
 */
public class sintactico {
    public lexico lex;
    public int errores;
    public sintactico(String path) {
        lex=new lexico(path);
        errores=0;
        try {
            lex.cs=lex.nextSymbol();
        } catch (IOException ex) {
            Logger.getLogger(sintactico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public tokens currentToken;
    public tokens lasttoken;
    public tokenlist tk;
    boolean flag;
    public tokens nextToken() throws IOException{
        tk=lex.nextToken();
        currentToken=tk.getTipo();
        return currentToken;
    }
    
    public void start() throws IOException
    {
        while(currentToken!=tokens.EOF)
        {
            program();
        }
    }
    
    public void program() throws IOException
    {
        if(currentToken==tokens.KW_CLASS)
        {
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                currentToken=nextToken();
                if(currentToken==tokens.SIGN_DP)
                {
                    currentToken=nextToken();
                    while(currentToken==tokens.NEW_LINE)
                    {
                        field_decl();
                    }
                    while(currentToken==tokens.KW_DEF)
                    {
                        method_decl();
                    }
                }
            }
        }
    }
    
    public void field_decl() throws IOException
    {
        if(currentToken==tokens.NEW_LINE)
        {
            currentToken=nextToken();
            while(currentToken==tokens.NEW_LINE )
            {
                currentToken=nextToken();
            }
            if(currentToken==tokens.DEL_TAB)
            {
                currentToken=nextToken();
                if(currentToken==tokens.P_ID)
                {
                    assign();
                }
            }
            else
            {
                rutinaError(" una tabulacion");
                System.exit(errores);
            }
        }
    }
    
    public void method_decl() throws IOException
    {
        if(currentToken==tokens.KW_DEF)
        {
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                currentToken=nextToken();
                if(currentToken==tokens.SIGN_PARI)
                {
                    currentToken=nextToken();
                    if(currentToken==tokens.P_ID)
                    {
                        currentToken=nextToken();
                        while(currentToken==tokens.SIGN_C)
                        {
                            currentToken=nextToken();
                            if(currentToken==tokens.P_ID)
                            {
                                currentToken=nextToken();
                            }
                        }
                    }
                    if(currentToken==tokens.SIGN_PARD)
                    {
                        currentToken=nextToken();
                    }
                }
                if(currentToken==tokens.SIGN_DP)
                {
                    currentToken=nextToken();
                }
                block();
            }
        }
    }
    
    public void block() throws IOException
    {
        if(currentToken==tokens.NEW_LINE)
        {
            currentToken=nextToken();
            inicioBloque();
            //statement();
            if(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK)
            {
                statement();
                while(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK)
                {
                    statement();
                    while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                    {
                        currentToken=nextToken();
                    }
                }
            }
            finBloque();
        }
    }
    
    public void statement() throws IOException
    {
        while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.P_ID)
        {
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_PARI)
            {
                currentToken=nextToken();
                method_call();
            }
            else
            {
                assign();
            }
        }
        else if(currentToken==tokens.KW_PRINT||currentToken==tokens.KW_READ)
        {
            method_call();
        }
        else if(currentToken==tokens.KW_IF)
        {
            currentToken=nextToken();
            expr();
            if(currentToken==tokens.SIGN_DP)
            {
                currentToken=nextToken();
                block();
                while(currentToken==tokens.KW_ELIF)
                {
                    currentToken=nextToken();
                    expr();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        block();
                    }
                }
                if(currentToken==tokens.KW_ELSE)
                {
                    currentToken=nextToken();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        block();
                    }
                }
            }
        }
        else if(currentToken==tokens.KW_WHILE)
        {
            currentToken=nextToken();
            expr();
            if(currentToken==tokens.SIGN_DP)
            {
                block();
            }
        }
        
        else if(currentToken==tokens.KW_FOR)
        {
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                currentToken=nextToken();
                if(currentToken==tokens.KW_IN)
                {
                    range();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        block();
                    }
                }
            }
        }
        
        else if(currentToken==tokens.KW_RETURN)
        {
            currentToken=nextToken();
            expr();
        }
        
        else if(currentToken==tokens.KW_BREAK)
        {
            currentToken=nextToken();
        }
    }
    
    public void assign() throws IOException
    {
        lvalue();
        if(currentToken==tokens.SIGN_ASSIG)
        {
            currentToken=nextToken();
            expr();
        }
    }
    
    public void method_call() throws IOException
    {
        if(currentToken==tokens.P_ID)
        {
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_PARI)
            {
                currentToken=nextToken();
                expr();
                boolean flags=true;
                while(flags)
                {
                    if(currentToken==tokens.SIGN_C)
                    {
                        currentToken=nextToken();
                        expr();
                    }
                    else
                    {
                        flags=false;
                    }
                }
                if(currentToken==tokens.SIGN_PARD)
                {
                    currentToken=nextToken();
                }
            }
        }
        else if(currentToken==tokens.KW_PRINT)
        {
            currentToken=nextToken();
            expr();  
            boolean flags=true;
            while(flags)
            {
                if(currentToken==tokens.SIGN_C)
                {
                    currentToken=nextToken();
                    expr();
                }
                else
                {
                    flags=false;
                }
            }
        }
        else if(currentToken==tokens.KW_READ)
        {
            currentToken=nextToken();
            lvalue();
        }
    }
    public void lvalue() throws IOException
    {
        if(currentToken==tokens.P_ID)
        {
            currentToken=nextToken();
            lvalues();
        }
    }    
    
    public void lvalues() throws IOException
    {
        if(currentToken==tokens.SIGN_CORI)
        {
            currentToken=nextToken();
            expr();
            if(currentToken ==tokens.SIGN_CORD) {
                currentToken=nextToken();
            }
        }
    }
    
    public void expr() throws IOException
    {
        while(currentToken==tokens.NEW_LINE||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
           currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
           currentToken==tokens.SIGN_CORI)
        {
            exprTermino();
            if(currentToken==tokens.OP_AND||currentToken==tokens.OP_COMP||currentToken==tokens.OP_DIST||
               currentToken==tokens.OP_DIV||currentToken==tokens.OP_MAIG||currentToken==tokens.OP_MAYOR||
               currentToken==tokens.OP_MEIG||currentToken==tokens.OP_MENOR||currentToken==tokens.OP_MOD||
               currentToken==tokens.OP_MULT||currentToken==tokens.OP_NOT||currentToken==tokens.OP_NUM||
               currentToken==tokens.OP_OR||currentToken==tokens.OP_REST||currentToken==tokens.OP_SLEFT||
               currentToken==tokens.OP_SRIGHT||currentToken==tokens.OP_SUMA)
            {
                Termino();
            }
        }
        else
        {
            System.out.println("No se esperaba dicho simbolo"+ currentToken);
            System.exit(errores);
        }
    }
    
    public void exprTermino()throws IOException
    {
         if(currentToken==tokens.P_ID)
        {
            currentToken=nextToken();
            expresiones();
        }
        else if(currentToken==tokens.KW_PRINT||currentToken==tokens.KW_READ)
        {
            currentToken=nextToken();
            method_call();
        }
        else if(currentToken==tokens.LIT_NUM)
        {
            constant();
        }
        else if(currentToken==tokens.SIGN_NEG)
        {
            currentToken=nextToken();
            expr();
        }
        else if(currentToken==tokens.SIGN_PARI)
        {
            currentToken=nextToken();
            expr();
            if(currentToken==tokens.SIGN_PARD)
            {
                currentToken=nextToken();
            }
        }
        else if(currentToken==tokens.SIGN_CORI)
        {
            currentToken=nextToken();
            expr();
            while(currentToken==tokens.SIGN_C)
            {
                currentToken=nextToken();
                expr();
            }
            if(currentToken==tokens.SIGN_CORD)
            {
                currentToken=nextToken();
            }
        }
    }
    
    public void expresiones() throws IOException
    {
        while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.SIGN_CORI)
        {
            currentToken=nextToken();
            expr();
            if(currentToken ==tokens.SIGN_CORD) {
                currentToken=nextToken();
            }
        }
        else if(currentToken==tokens.SIGN_PARI)
        {
            currentToken=nextToken();
            expr();
            boolean flags=true;
            while(flags)
            {
                if(currentToken==tokens.SIGN_C)
                {
                    currentToken=nextToken();
                    expr();
                }
                else
                {
                    flags=false;
                 }
              }
              if(currentToken==tokens.SIGN_PARD)
              {
                  currentToken=nextToken();
              }
         }
    }
    
    public void Termino() throws IOException
    {
        if(currentToken==tokens.OP_AND||currentToken==tokens.OP_COMP||currentToken==tokens.OP_DIST||
           currentToken==tokens.OP_DIV||currentToken==tokens.OP_MAIG||currentToken==tokens.OP_MAYOR||
           currentToken==tokens.OP_MEIG||currentToken==tokens.OP_MENOR||currentToken==tokens.OP_MOD||
           currentToken==tokens.OP_MULT||currentToken==tokens.OP_NOT||currentToken==tokens.OP_NUM||
           currentToken==tokens.OP_OR||currentToken==tokens.OP_REST||currentToken==tokens.OP_SLEFT||
           currentToken==tokens.OP_SRIGHT||currentToken==tokens.OP_SUMA)
        {
            currentToken=nextToken();
            exprTermino();
            Termino();
        }
    }

    
    public void inicioBloque() throws IOException
    {
        if(currentToken==tokens.DEL_TAB)
        {
            if(lex.actualTab>lex.topPila)
            {
                currentToken=nextToken();
            }
            else
            {
                rutinaError("Se esperaba una tabulacion mas");
                System.exit(0);
            }

        }
    }
    
    public void finBloque() throws IOException
    {
        while(currentToken==tokens.NEW_LINE||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.DEL_DESTAB)
        {
            currentToken=nextToken();
        }
    }
    
    public void range() throws IOException
    {
        if(currentToken==tokens.LIT_NUM)
        {
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_RANG)
            {
                currentToken=nextToken();
                if(currentToken==tokens.LIT_NUM)
                {
                    currentToken=nextToken();
                }
            }
        }
    }
    
    public void op_bin() throws IOException
    {
        if(currentToken==tokens.OP_SUMA||currentToken==tokens.OP_REST||currentToken==tokens.OP_MULT||
           currentToken==tokens.OP_DIV||currentToken==tokens.OP_SLEFT||currentToken==tokens.OP_SRIGHT||
           currentToken==tokens.OP_MOD)
        {
            arith_op();
        }
        else if(currentToken==tokens.OP_MAYOR||currentToken==tokens.OP_MENOR||currentToken==tokens.OP_MAIG||
                currentToken==tokens.OP_MEIG)
        {
            rel_op();
        }
        else if(currentToken==tokens.OP_COMP||currentToken==tokens.OP_DIST)
        {
            eq_op();
        }
        else if(currentToken==tokens.OP_AND||currentToken==tokens.OP_OR||currentToken==tokens.OP_NOT)
        {
            cond_op();
        }
    }
    
    public void arith_op() throws IOException
    {
        if(currentToken==tokens.OP_SUMA||currentToken==tokens.OP_REST||currentToken==tokens.OP_MULT||
           currentToken==tokens.OP_DIV||currentToken==tokens.OP_SLEFT||currentToken==tokens.OP_SRIGHT||
           currentToken==tokens.OP_MOD)
        {
            currentToken=nextToken();
        }
    }
    
    public void rel_op() throws IOException
    {
        if(currentToken==tokens.OP_MAYOR||currentToken==tokens.OP_MENOR||currentToken==tokens.OP_MAIG||
           currentToken==tokens.OP_MEIG)
        {
            currentToken=nextToken();
        }
    }
    
    public void eq_op() throws IOException
    {
        if(currentToken==tokens.OP_COMP||currentToken==tokens.OP_DIST)
        {
            currentToken=nextToken();
        }
    }
    
    public void cond_op() throws IOException
    {
        if(currentToken==tokens.OP_AND||currentToken==tokens.OP_OR||currentToken==tokens.OP_NOT)
        {
            currentToken=nextToken();
        }
    }
    
    public void constant() throws IOException
    {
        if(currentToken==tokens.LIT_NUM||currentToken==tokens.LIT_CHCONST)
        {
            currentToken=nextToken();
        }
        else if(currentToken==tokens.B_TRUE||currentToken==tokens.B_FALSE)
        {
            bool_const();
        }
    }
    
    public void bool_const() throws IOException
    {
        if(currentToken==tokens.B_TRUE||currentToken==tokens.B_FALSE)
        {
            currentToken=nextToken();
        }
    }
    
    public void rutinaError(String error)
    {
        int linea=lex.getTotalEnters();
        System.out.println("Error en la linea "+linea+ " se esperaba "+error);
    }
}
