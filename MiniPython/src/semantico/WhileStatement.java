/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class WhileStatement extends Statement{
    ASTNode expr;
    ASTNode block;
    int linea;

    public WhileStatement(ASTNode expr, ASTNode block, int linea) {
        this.expr = expr;
        this.block = block;
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
