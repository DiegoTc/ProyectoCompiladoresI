/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class LeftValueExpr extends Expr{
    ASTNode leftValue;
    int linea;

    public LeftValueExpr(ASTNode leftValue, int linea) {
        this.leftValue = leftValue;
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
