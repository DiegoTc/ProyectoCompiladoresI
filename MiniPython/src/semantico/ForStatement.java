/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class ForStatement extends Statement{
    String varName;
    ASTNode exprInicial;
    ASTNode exprFinal;
    ASTNode block;
    int linea;

    public ForStatement(String varName, ASTNode exprInicial, ASTNode exprFinal, ASTNode block, int linea) {
        this.varName = varName;
        this.exprInicial = exprInicial;
        this.exprFinal = exprFinal;
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
