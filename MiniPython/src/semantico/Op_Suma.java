/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class Op_Suma extends Expr{
    
    ASTNode Expr1;
    ASTNode Expr2;
    int linea;

    public Op_Suma(ASTNode Expr1, ASTNode Expr2, int linea) {
        this.Expr1 = Expr1;
        this.Expr2 = Expr2;
        this.linea = linea;
    }
    
        
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getlinea() {
        return linea;
    }
   
    
}


