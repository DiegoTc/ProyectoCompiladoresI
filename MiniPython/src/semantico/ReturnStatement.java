/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class ReturnStatement extends Statement{
    ASTNode expr;
    int linea;

    public ReturnStatement(ASTNode expr, int linea) {
        this.expr = expr;
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
