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
public class ExprArimeticaDistint extends Expr{
    ASTNode leftNode;
    ArrayList<ASTNode> listas;

    public ExprArimeticaDistint(ASTNode leftNode, ArrayList<ASTNode> listas) {
        this.leftNode = leftNode;
        this.listas = listas;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getlinea() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
