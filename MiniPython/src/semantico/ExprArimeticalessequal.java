/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class ExprArimeticalessequal extends Expr{
    ASTNode leftNode;
    ArrayList<ASTNode> listas;

    public ExprArimeticalessequal(ASTNode leftNode, ArrayList<ASTNode> listas) {
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
