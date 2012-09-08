/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class LeftValueExpTermino extends Expr{
    ASTNode leftValue;
    int linea;

    public LeftValueExpTermino(ASTNode leftValue, int linea) {
        this.leftValue = leftValue;
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
