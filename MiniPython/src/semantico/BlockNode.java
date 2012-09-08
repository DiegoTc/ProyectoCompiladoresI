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
public class BlockNode extends ASTNode{
    ArrayList<ASTNode> statements;
    int linea;

    public BlockNode(ArrayList<ASTNode> statements, int linea) {
        this.statements = statements;
        this.linea = linea;
    }

    public ArrayList<ASTNode> getStatements() {
        return statements;
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
