/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class Op_Resta extends  Expr{
    ASTNode Expr1;
    ASTNode Expr2;
    int linea;

    public Op_Resta(ASTNode Expr1, ASTNode Expr2, int linea) {
        this.Expr1 = Expr1;
        this.Expr2 = Expr2;
        this.linea = linea;
    }

        
    @Override
    public int getlinea() {
        return linea;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
