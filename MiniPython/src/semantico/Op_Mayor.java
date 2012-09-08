/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class Op_Mayor extends Expr{
    int linea;
    ASTNode expr1;
    ASTNode expr2;

    public Op_Mayor(int linea, ASTNode expr1, ASTNode expr2) {
        this.linea = linea;
        this.expr1 = expr1;
        this.expr2 = expr2;
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
