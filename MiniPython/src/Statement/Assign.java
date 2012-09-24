/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;
import Expresiones.Expr;
import Expresiones.lvalueExpr;
/**
 *
 * @author diego
 */
public class Assign extends statement{
    Expr right;
    lvalueExpr left;

    public Assign(Expr right, lvalueExpr left) {
        this.right = right;
        this.left = left;
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
