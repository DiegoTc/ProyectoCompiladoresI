/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class AssignStatement extends Statement {
    ASTNode leftValue;
    ASTNode rightValue;
    int linea;

    public AssignStatement(ASTNode leftValue, ASTNode rightValue, int linea) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
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
