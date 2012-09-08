/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class ExprTermino extends Expr {
    ASTNode exprter;
    ASTNode term;
    int linea;

    public ExprTermino(ASTNode exprter, ASTNode term, int linea) {
        this.exprter = exprter;
        this.term = term;
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
