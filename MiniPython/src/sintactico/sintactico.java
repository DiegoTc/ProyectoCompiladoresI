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
    public lexico parse;
    public int errores;
    public sintactico(String path) {
        parse=new lexico(path);
        errores=0;
        try {
            parse.cs=parse.nextSymbol();
        } catch (IOException ex) {
            Logger.getLogger(sintactico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public tokens currentToken;
    public tokens lasttoken;
    public tokenlist tk;
    boolean flag;
    public tokens nextToken() throws IOException{
        tk=parse.nextToken();
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
                    while(currentToken==tokens.P_ID)
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
    
    public void field_decl()
    {
        if(currentToken==tokens.P_ID)
        {
            assign();
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
                        while(currentToken==tokens.SIGN_C||currentToken==tokens.P_ID)
                        {
                            currentToken=nextToken();
                        }
                    }
                    if(currentToken==tokens.SIGN_PARD)
                    {
                        currentToken=nextToken();
                    }
                    if(currentToken==tokens.SIGN_DP)
                    {
                        block();
                    }
                }
            }
        }
    }
    
    public void block() throws IOException
    {
        if(currentToken==tokens.NEW_LINE)
        {
            currentToken=nextToken();
            inicioBloque();
            statement();
            if(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK)
            {
                statement();
                while(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK)
                {
                    statement();
                }
            }
            finBloque();
        }
    }
    
    public void statement() throws IOException
    {
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
    
    
}
