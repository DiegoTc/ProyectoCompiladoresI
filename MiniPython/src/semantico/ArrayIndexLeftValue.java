/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class ArrayIndexLeftValue extends LeftValue{
    String varname;
    ASTNode expr;
    int linea;

    public ArrayIndexLeftValue(String varname, ASTNode expr, int linea) {
        this.varname = varname;
        this.expr = expr;
        this.linea = linea;
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
