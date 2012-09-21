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
import sun.org.mozilla.javascript.ast.AstNode;
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
    
    public void start() throws IOException
    {
        while(currentToken!=tokens.EOF)
        {
            program();
        }
    }
    
    public ProgramNode program() throws IOException
    {
        ProgramNode p = null;
        String name=null;
        ArrayList<FieldDeclNode> fielddecl_list = null;
        ArrayList<MethodDeclNode> methodecl_list=null;
        if(currentToken==tokens.KW_CLASS)
        {
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                name=currentToken.toString();
                currentToken=nextToken();
                if(currentToken==tokens.SIGN_DP)
                {
                    currentToken=nextToken();
                    while(currentToken==tokens.NEW_LINE)
                    {
                        FieldDeclNode fiedecl=field_decl();
                        fielddecl_list.add(fiedecl);
                    }
                    while(currentToken==tokens.KW_DEF)
                    {
                        MethodDeclNode method=method_decl();
                        methodecl_list.add(method);
                    }
                }
            }
            int linea=lex.getTotalEnters();
            p= new ProgramNode(linea, name, fielddecl_list, methodecl_list);
            return p;
        }
        else
        {
            rutinaError();
            return p;
           
        }
    }
    
    public FieldDeclNode field_decl() throws IOException
    {
        String name=null;
        AssignStatement right=null;
        FieldDeclNode f=null;
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
                    name=currentToken.toString();
                    right=assign();
                }
                int linea=lex.getTotalEnters();
                f=new FieldDeclNode(linea, name, right);
                return f;
            }
            else
            {
                rutinaError();
                return f;
            }
        }
        return f;
    }
    
    public MethodDeclNode method_decl() throws IOException
    {
        String name;
        ArrayList<String> argumentos=null;
        BlockNode bloque=null;
        MethodDeclNode metodo=null;
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
                        argumentos.add(currentToken.toString());
                        currentToken=nextToken();
                        while(currentToken==tokens.SIGN_C)
                        {
                            currentToken=nextToken();
                            if(currentToken==tokens.P_ID)
                            {
                                argumentos.add(currentToken.toString());
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
                    bloque=block();
                }
                int linea=lex.getTotalEnters();
                return metodo=new MethodDeclNode(name, argumentos, bloque, linea);
            }
        }
        return metodo;
    }
    
    public BlockNode block() throws IOException
    {
        ArrayList<Statement> state=null;
        BlockNode bloque=null;
        if(currentToken==tokens.NEW_LINE)
        {
            currentToken=nextToken();
            inicioBloque();
            //statement();
            if(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK||currentToken==tokens.KW_READ)
            {
                state.add(statement());
                while(currentToken==tokens.P_ID||currentToken==tokens.KW_PRINT||currentToken==tokens.KW_IF||currentToken==tokens.KW_WHILE||
               currentToken==tokens.KW_FOR||currentToken==tokens.KW_RETURN||currentToken==tokens.KW_BREAK||currentToken==tokens.KW_READ)
                {
                    state.add(statement());
                    while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                    {
                        currentToken=nextToken();
                    }
                }
            }
            finBloque();
        }
        return bloque;
    }
    
    public Statement statement() throws IOException
    {
        while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
        {
            currentToken=nextToken();
        }
        if(currentToken==tokens.P_ID)
        {
            String n=currentToken.toString();
            currentToken=nextToken();
            if(currentToken==tokens.SIGN_PARI)
            {
                
                MethodCallStatement callstatement=null;
                ArrayList<ASTNode> parameters =method_call();
                int linea=lex.getTotalEnters();
                callstatement=new MethodCallStatement(n, parameters, linea);
                return callstatement;
            }
            else
            {
                AssignStatement assignState=null;
                assignState=assign();
                return assignState;
            }
        }
        else if(currentToken==tokens.KW_PRINT||currentToken==tokens.KW_READ)
        {
            MethodCallStatement callstatement=null;
            ArrayList<ASTNode> parameters =method_call();
            int l=lex.getTotalEnters();
            callstatement=new MethodCallStatement(currentToken.toString(), parameters, l);
            return callstatement;
        }
        else if(currentToken==tokens.KW_IF)
        {
            currentToken=nextToken();
            Expr e=null;
            e=expr();
            if(currentToken==tokens.SIGN_DP)
            {
                currentToken=nextToken();
                BlockNode b=block();
                BlockNode elseblock=null;
                ArrayList<BlockNode> elifblocks=null;
                ArrayList<Expr> elifexpressions = null;
                while(currentToken==tokens.KW_ELIF)
                {
                    currentToken=nextToken();
                    elifexpressions.add(expr());;
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        elifblocks.add(block());
                    }
                }
                if(currentToken==tokens.KW_ELSE)
                {
                    currentToken=nextToken();
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        elseblock=block();
                    }
                }
                int linea=lex.getTotalEnters();
                IfStatement if_node=new IfStatement(e, b, elifexpressions, elifblocks, elseblock, linea);
                return if_node;
            }
            
        }
        else if(currentToken==tokens.KW_WHILE)
        {
            currentToken=nextToken();
            Expr e=null;
            BlockNode b=null;
            e=expr();
            if(currentToken==tokens.SIGN_DP)
            {
                currentToken=nextToken();
               
                b=block();
            }
            int linea=lex.getTotalEnters();
            return new WhileStatement(e, b, linea);
        }
        
        else if(currentToken==tokens.KW_FOR)
        {
            String varname;
            Expr e1=null;
            Expr e2=null;
            BlockNode forblock=null;
            currentToken=nextToken();
            if(currentToken==tokens.P_ID)
            {
                varname=currentToken.toString();
                currentToken=nextToken();
                if(currentToken==tokens.KW_IN)
                {
                    currentToken=nextToken();
                    range(e1,e2);
                    if(currentToken==tokens.SIGN_DP)
                    {
                        currentToken=nextToken();
                        forblock=block();
                    }
                }
                int linea=lex.getTotalEnters();
                return new ForStatement(varname, e2, e2, forblock, linea);
            }
        }
        
        else if(currentToken==tokens.KW_RETURN)
        {
            currentToken=nextToken();
            Expr e=expr();
            int linea=lex.getTotalEnters();
            return new ReturnStatement(e, linea);
        }
        
        else if(currentToken==tokens.KW_BREAK)
        {
            currentToken=nextToken();
            int linea=lex.getTotalEnters();
            return new BreakStatement(linea);
        }
        else
        {
           int linea=lex.getTotalEnters();
            return new ErrorStatement(linea);
        }
        int linea=lex.getTotalEnters();
        return new ErrorStatement(linea);
    }
    
    public AssignStatement assign() throws IOException
    {
        ASTNode leftvalue=null;
        leftvalue=lvalue();
        Expr e=null;
        AssignStatement assig=null;
        if(currentToken==tokens.SIGN_ASSIG)
        {
            currentToken=nextToken();
            e=expr();
            int linea=lex.getTotalEnters();
            assig=new AssignStatement(leftvalue, e, linea);
        }
        return assig;
    }
    
    public ArrayList<ASTNode> method_call() throws IOException
    {
        ArrayList<ASTNode> lista=null;
           
            if(currentToken==tokens.SIGN_PARI)
            {
                currentToken=nextToken();
                if(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB||currentToken==tokens.P_ID||
                   currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                   currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
                {    
                    Expr e1=expr();
                    lista.add(e1);
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
                                Expr e2=expr();
                                lista.add(e2);
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
                }
                while(currentToken==tokens.DEL_TAB||currentToken==tokens.NEW_LINE)
                {
                    currentToken=nextToken();
                }
                return lista;
            }
        
        else if(currentToken==tokens.KW_PRINT)
        {
            currentToken=nextToken();
            Expr e1=expr();
            lista.add(e1);  
            boolean flags=true;
            while(flags)
            {
                if(currentToken==tokens.SIGN_C)
                {
                    currentToken=nextToken();
                    Expr e2=expr();
                    lista.add(e2);
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
            return lista;
        }
        else if(currentToken==tokens.KW_READ)
        {
            currentToken=nextToken();
            lista.add(lvalue());
            while(currentToken==tokens.DEL_TAB||currentToken==tokens.NEW_LINE)
            {
                currentToken=nextToken();
            }
            return lista;
        }
    }
    public ASTNode lvalue() throws IOException
    {
        String name=null;
        ASTNode val=null;
        int linea = 0;
        if(currentToken==tokens.P_ID)
        {
            name=currentToken.toString();
            currentToken=nextToken();
            val=lvalues();
            linea=lex.getTotalEnters();
            if(val==null)
            {
                return new SimpleLeftValue(name, linea);
            }
            else
            {
                return new ArrayIndexLeftValue(name, val, linea);
            }
        }
        return new ErrorStatement(linea);
    }    
    
    public ASTNode lvalues() throws IOException
    {
        ASTNode node=null;
        if(currentToken==tokens.SIGN_CORI)
        {
            currentToken=nextToken();
            node=expr();
            if(currentToken ==tokens.SIGN_CORD) {
                currentToken=nextToken();
                return node;
            }
            return node=null;
        }
        return node;
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
            LeftValueExpr e1=exprTermino();
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
        else if(currentToken==tokens.LIT_NUM||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE||currentToken==tokens.LIT_CHCONST)
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
            if(currentToken!=tokens.SIGN_PARD)
            {
                expr();
            }
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
                while(currentToken==tokens.NEW_LINE ||currentToken==tokens.DEL_TAB)
                {
                    currentToken=nextToken();
                }
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
            if(currentToken==tokens.P_ID||
                currentToken==tokens.LIT_NUM||currentToken==tokens.SIGN_NEG||currentToken==tokens.SIGN_PARI||
                currentToken==tokens.SIGN_CORI||currentToken==tokens.B_FALSE||currentToken==tokens.B_TRUE)
            {
                exprTermino();
                Termino();
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
    
    public void range(Expr e1, Expr e2) throws IOException
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
        int linea=0;
        if(currentToken==tokens.LIT_NUM)
        {
            linea=lex.getTotalEnters();
            int a= Integer.parseInt(currentToken.toString());
            currentToken=nextToken();
            NumberExprTermino num=new NumberExprTermino(a, linea);
            
        }
        else if(currentToken==tokens.LIT_CHCONST)
        {
            linea=lex.getTotalEnters();
            String a=currentToken.toString();
            currentToken=nextToken();
            return new StringConstantExprTermino(linea, a);
            
        }
        else if(currentToken==tokens.B_TRUE||currentToken==tokens.B_FALSE)
        {
            BoolExprTermino b=bool_const();
            return b;
        }
        return new ErrorExpr(linea);
    }
    
    public BoolExprTermino bool_const() throws IOException
    {
        BoolExprTermino b=null;
        int linea=0;
        if(currentToken==tokens.B_TRUE)
        {
            linea=lex.getTotalEnters();
            currentToken=nextToken();
            return b=new BoolExprTermino(linea, true);
        }
        else if(currentToken==tokens.B_FALSE)
        {
            linea=lex.getTotalEnters();
            currentToken=nextToken();
            return b=new BoolExprTermino(linea, false);
        }
        return b;
    }
    
    public void rutinaError()
    {
        int linea=lex.getTotalEnters();
        System.out.println("Error en la linea "+linea);
        System.exit(0);
    }
}
