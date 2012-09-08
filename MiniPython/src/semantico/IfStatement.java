/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class IfStatement extends Statement{
    ASTNode expr;
    ASTNode ifBlock;
    ArrayList<ASTNode> elifBlockList;
    ASTNode elseBlock;
    int linea;

    public IfStatement(ASTNode expr, ASTNode ifBlock, ArrayList<ASTNode> elifBlockList, ASTNode elseBlock, int linea) {
        this.expr = expr;
        this.ifBlock = ifBlock;
        this.elifBlockList = elifBlockList;
        this.elseBlock = elseBlock;
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
