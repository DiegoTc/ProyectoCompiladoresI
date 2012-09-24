/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresiones;

/**
 *
 * @author diego
 */
public class exprsuma extends OpBinExpr{

    public Expr left, right;
    public exprsuma(Expr left, Expr right) {
        super(left, right);
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
