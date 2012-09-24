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
public class ElseIfBlock extends statement{
    Expr condicion;
    BlockStatement statement_bloque;

    public ElseIfBlock(Expr condicion, BlockStatement statement_bloque) {
        this.condicion = condicion;
        this.statement_bloque = statement_bloque;
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
