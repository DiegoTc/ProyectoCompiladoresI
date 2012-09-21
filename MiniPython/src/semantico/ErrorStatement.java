/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class ErrorStatement extends Statement{

    int linea;

    public ErrorStatement(int linea) {
        this.linea = linea;
    }
    
    @Override
    public String toString() {
       return "Error en la linea"+linea;
    }

    @Override
    public int getlinea() {
        return linea;
    }
    
}
