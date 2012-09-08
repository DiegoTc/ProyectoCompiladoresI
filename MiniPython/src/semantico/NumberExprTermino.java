/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class NumberExprTermino extends Expr{
    int value;
    int linea;

    public NumberExprTermino(int value, int linea) {
        this.value = value;
        this.linea = linea;
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
