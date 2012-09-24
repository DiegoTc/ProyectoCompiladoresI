/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;
import Expresiones.MethodCallExpr;
/**
 *
 * @author diego
 */
public class MethodCallStatement extends statement{
    MethodCallExpr method;

    public MethodCallStatement(MethodCallExpr method) {
        this.method = method;
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
