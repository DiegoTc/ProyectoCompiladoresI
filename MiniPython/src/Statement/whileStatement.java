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

public class whileStatement extends cycleStatement{
    Expr condicion;
    BlockStatement bloque;

    public whileStatement(Expr condicion, BlockStatement bloque) {
        this.condicion = condicion;
        this.bloque = bloque;
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
