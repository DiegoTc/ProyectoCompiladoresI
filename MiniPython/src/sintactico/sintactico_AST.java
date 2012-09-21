/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sintactico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import minipython.lexico.*;
import minipython.lexico.tokenlist.tokens;
import  minipython.lexico.tokenlist;
import semantico.*;
/**
 *
 * @author diego
 */
public class sintactico_AST {
    public lexico lex;
    public int errores;
    public sintactico_AST(String path) {
        lex=new lexico(path);
        errores=0;
        try {
            lex.cs=lex.nextSymbol();
        } catch (IOException ex) {
            Logger.getLogger(sintactico_AST.class.getName()).log(Level.SEVERE, null, ex);
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
    /*
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
        else
        {
            rutinaError();
           
        }
    }
    
    public FieldDeclNode field_decl() throws IOException
    {
        String varName="";
        ASTNode right=null;
        int Linea=lex.getTotalEnters();
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
                    varName=currentToken.toString();
                    right=assign();
                    return new FieldDeclNode(Linea, varName, right);
                }
            }
            else
            {
                rutinaError();
            }
        }
        return new FieldDeclNode(Linea, varName, right);
    }
    
    public MethodDeclNode method_decl() throws IOException
    {
        String name=null;
        ArrayList<String> methodArguments=null;
        ASTNode block=null;
        int linea=lex.getTotalEnters();
        if(currentToken==tokens.KW_DEF)
        {
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                name=currentToken.toString();
                currentToken=nextToken();
                if(currentToken==tokens.SIGN_PARI)
                {
                    currentToken=nextToken();
                    if(currentToken==tokens.P_ID)
                    {
                        methodArguments.add(currentToken.toString());
                        currentToken=nextToken();
                        while(currentToken==tokens.SIGN_C)
                        {
                            currentToken=nextToken();
                            if(currentToken==tokens.P_ID)
                            {
                                methodArguments.add(currentToken.toString());
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
                    block=block();
                }
            }
        }
        return new MethodDeclNode(name, methodArguments, block, linea);
    }
    
    public BlockNode block() throws IOException
    {
         ArrayList<ASTNode> statements=null;
         int linea=0;
         if(currentToken==tokens.NEW_LINE)
         {
            currentToken=nextToken();
            inicioBloque();
            //statement();
            if(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK||currentToken==tokens.KW_READ)
            {
                Statement s=statement();
                statements.add(s);
                while(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK||currentToken==tokens.KW_READ)
                {
                    Statement state=statement();
                    statements.add(state);
                    while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                    {
                        currentToken=nextToken();
                    }
                }
            }
            finBloque();
         }
         linea=lex.getTotalEnters();
         return new BlockNode(statements, linea);
    }
    
    public Statement statement() throws IOException
    {
        while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.P_ID)
        {
            String id=currentToken.toString();
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_PARI)
            {
                //currentToken=nextToken();
                MethodCallStatement methodSta=method_call(id);
                return methodSta;
            }
            else
            {
                AssignStatement assigSta=assign();
                return assigSta;
            }
        }
        else if(currentToken==tokens.KW_PRINT||currentToken==tokens.KW_READ)
        {
            MethodCallStatement methodSta=method_call(currentToken.toString());
            return methodSta;
        }
        else if(currentToken==tokens.KW_IF)
        {
            ASTNode expr_=null;
            ASTNode ifBlock=null;
            ArrayList<ASTNode> elifBlockList=null;
            BlockNode elseBlock=null;
            int linea;
            currentToken=nextToken();
            expr_=expr();
            if(currentToken==tokens.SIGN_DP)
            {
                currentToken=nextToken();
                ifBlock=block();
                while(currentToken==tokens.KW_ELIF)
                {
                    ASTNode ex;
                    ASTNode block;
                    int l=lex.getTotalEnters();
                    currentToken=nextToken();
                    ex=expr();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        block=block();
                        ElseIfBlock elifB=new ElseIfBlock(ex, block, l);
                        elifBlockList.add(elifB);
                    }
                }
                if(currentToken==tokens.KW_ELSE)
                {
                    currentToken=nextToken();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        elseBlock=block();
                    }
                }
            }
            return new IfStatement(expr_, ifBlock, elifBlockList, elseBlock, linea);
        }
        else if(currentToken==tokens.KW_WHILE)
        {
            ASTNode ex=null;
            ASTNode block=null;
            int linea=lex.getTotalEnters();
            currentToken=nextToken();
            ex=expr();
            if(currentToken==tokens.SIGN_DP)
            {
                currentToken=nextToken();
                block=block();
            }
            return new WhileStatement(ex, block, linea);
        }
        
        else if(currentToken==tokens.KW_FOR)
        {
            String varName="";
            ASTNode exprInicial=null;
            ASTNode exprFinal=null;
            ASTNode block=null;
            int linea=-1;
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                currentToken=nextToken();
                if(currentToken==tokens.KW_IN)
                {
                    currentToken=nextToken();
                    range(exprInicial, exprFinal);
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        block=block();
                    }
                }
            }
            return new ForStatement(varName, exprInicial, exprFinal, block, linea);
        }
        
        else if(currentToken==tokens.KW_RETURN)
        {
            ASTNode expr;
            int linea=lex.getTotalEnters();
            currentToken=nextToken();
            expr=expr();
            return new ReturnStatement(expr, linea);
        }
        
        else if(currentToken==tokens.KW_BREAK)
        {
            int linea=lex.getTotalEnters();
            currentToken=nextToken();
            return new BreakStatement(linea);
        }
        else
        {
            return new ErrorStatement(lex.getTotalEnters());
        }
    }
    
    public AssignStatement assign() throws IOException
    {
        ASTNode leftNode=null, rightNode=null;
        int linea=lex.getTotalEnters();
        leftNode=lvalue();
        if(currentToken==tokens.SIGN_ASSIG)
        {
            currentToken=nextToken();
            rightNode=expr();
            //return new AssignStatement(leftNode, rightNode, linea);
        }
        return new AssignStatement(leftNode, rightNode, linea);
    }
    
     public MethodCallStatement method_call(String name) throws IOException
    {
        String methodName=name;
        ArrayList<ASTNode> methodParams = null;
        int linea=lex.getTotalEnters();
        if(currentToken==tokens.SIGN_PARI)
        {
            currentToken=nextToken();
            if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
               currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                   currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
            {
                methodParams.add(expr());
                boolean flags=true;
                while(flags)
                {
                    if(currentToken==tokens.SIGN_C)
                    {
                        currentToken=nextToken();
                        if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
                           currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                           currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
                        {
                            methodParams.add(expr());
                        }
                    }
                    else
                    {
                        flags=false;
                    }
                }
            }
            if(currentToken==tokens.SIGN_PARD)
            {
                currentToken=nextToken();
                while(currentToken==tokens.DEL_TAB||currentToken==tokens.NEW_LINE)
                {
                    currentToken=nextToken();
                }
            }
            else
            {
                rutinaError();
            }
        }
        
        else if(currentToken==tokens.KW_PRINT)
        {
            currentToken=nextToken();
            methodParams.add(expr()); 
            boolean flags=true;
            while(flags)
            {
                if(currentToken==tokens.SIGN_C)
                {
                    currentToken=nextToken();
                    methodParams.add(expr());
                }
                else
                {
                    flags=false;
                }
            }
            while(currentToken==tokens.DEL_TAB||currentToken==tokens.NEW_LINE)
            {
                currentToken=nextToken();
            }
        }
        else if(currentToken==tokens.KW_READ)
        {
            currentToken=nextToken();
            methodParams.add(expr());
            while(currentToken==tokens.DEL_TAB||currentToken==tokens.NEW_LINE)
            {
                currentToken=nextToken();
            }
        }
        return new MethodCallStatement(methodName, methodParams, linea);
    }
     
    public SimpleLeftValue lvalue() throws IOException
    {
        String varname=null;
        int linea=0;
        if(currentToken==tokens.P_ID)
        {
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_CORI)
            {
                ASTNode expr=lvalues();
                return new SimpleLeftValue(varname, expr, linea);
            }
            return new SimpleLeftValue(varname, linea);
        }
        SimpleLeftValue s=null;
        return s;
    }    
    
    public ASTNode lvalues() throws IOException
    {
        ASTNode e=null;
        if(currentToken==tokens.SIGN_CORI)
        {
            currentToken=nextToken();
            e=expr();
            if(currentToken ==tokens.SIGN_CORD) {
                currentToken=nextToken();
                return e;
            }
        }
        return e;
    }
    
    public Expr expr() throws IOException
    {
        while(currentToken==tokens.NEW_LINE||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
           currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
           currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
        {
            Expr e=exprTermino();
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
            rutinaError();
        }
    }
    
    public Expr exprTermino()throws IOException
    {
        
        if(currentToken==tokens.LIT_NUM||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE||currentToken==tokens.LIT_CHCONST)
        {
            Expr e=constant();
            return e;
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
                while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                {
                    currentToken=nextToken();
                }
            }
        }
        else if(currentToken==tokens.SIGN_CORI)
        {
            ArrayList<ASTNode> methodParams=null;
            currentToken=nextToken();
            methodParams.add(expr());
            while(currentToken==tokens.SIGN_C)
            {
                currentToken=nextToken();
                methodParams.add(expr());
            }
            if(currentToken==tokens.SIGN_CORD)
            {
                currentToken=nextToken();
                while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                {
                    currentToken=nextToken();
                }
            }
            return new MethodCallExpTermino(lex.getTotalEnters(), methodParams);
        }
    }
    /*
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
            if(currentToken!=tokens.SIGN_PARD)
            {
                expr();
            }
            //expr();
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
        else
        {
            rutinaError();
        }
    }*/
    /*
    public void Termino(Expr e) throws IOException
    {
        if(currentToken==tokens.OP_AND||currentToken==tokens.OP_COMP||currentToken==tokens.OP_DIST||
           currentToken==tokens.OP_DIV||currentToken==tokens.OP_MAIG||currentToken==tokens.OP_MAYOR||
           currentToken==tokens.OP_MEIG||currentToken==tokens.OP_MENOR||currentToken==tokens.OP_MOD||
           currentToken==tokens.OP_MULT||currentToken==tokens.OP_NOT||currentToken==tokens.OP_NUM||
           currentToken==tokens.OP_OR||currentToken==tokens.OP_REST||currentToken==tokens.OP_SLEFT||
           currentToken==tokens.OP_SRIGHT||currentToken==tokens.OP_SUMA)
        {
            
            
            
            
            
            
            currentToken=nextToken();
            if(currentToken==tokens.P_ID||
                currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
            {
                exprTermino();
                
            }
            else
            {
                rutinaError();
            }
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
                rutinaError();
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
    
    public void range(ASTNode e1, ASTNode e2) throws IOException
    {
        if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
           currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
           currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
        {
            e1=expr();
            if(currentToken==tokens.SIGN_RANG)
            {
                currentToken=nextToken();
                 if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
                    currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                    currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
                 {
                     e2=expr();
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
    
    public Expr constant() throws IOException
    {
        int linea=lex.getTotalEnters();
        if(currentToken==tokens.LIT_NUM)
        {
            NumberExprTermino num=new NumberExprTermino(Integer.parseInt(currentToken.toString()), linea);
            currentToken=nextToken();
            return num;
        }
        else if(currentToken==tokens.B_TRUE)
        {
            Expr b=bool_const();
            return b;
        }
        else if(currentToken==tokens.LIT_CHCONST)
        {
            StringConstantExprTermino string=new StringConstantExprTermino(linea, currentToken.toString());
            currentToken=nextToken();
            return string;
        }
        else
        {
            return new ErrorExpr(linea);
        }
    }
    
    public Expr bool_const() throws IOException
    {
        int linea=lex.getTotalEnters();
        if(currentToken==tokens.B_TRUE)
        {
            BoolExprTermino b=new BoolExprTermino(linea, true);
            currentToken=nextToken();
            return b;
        }
        else if(currentToken==tokens.B_FALSE)
        {
            BoolExprTermino b=new BoolExprTermino(linea, false);
            currentToken=nextToken();
            return b;
        }
        else
        {
            return new ErrorExpr(linea);
        }
    }
    
    public void rutinaError()
    {
        int linea=lex.getTotalEnters();
        System.out.println("Error en la linea "+linea);
        System.exit(0);
    }*/
}
