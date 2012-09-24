/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import Expresiones.Expr;

/**
 *
 * @author diego
 */
public class ReturnStatement extends statement{
    Expr rexpr;

    public ReturnStatement(Expr rexpr) {
        this.rexpr = rexpr;
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
