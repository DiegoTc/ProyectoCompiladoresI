/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class BoolExprTermino extends Expr{
    int linea;
    boolean value;

    public BoolExprTermino(int linea, boolean value) {
        this.linea = linea;
        this.value = value;
    }

    @Override
    public int getlinea() {
        return linea;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
