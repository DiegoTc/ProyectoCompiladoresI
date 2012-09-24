/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresiones;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class ArrayIndexExp extends Expr{
    ArrayList<Expr> elements;

    public ArrayIndexExp(ArrayList<Expr> elements) {
        this.elements = elements;
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
