/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class StringConstantExprTermino extends Expr {
    int linea;
    String value;

    public StringConstantExprTermino(int linea, String value) {
        this.linea = linea;
        this.value = value;
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
