/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresiones;

/**
 *
 * @author diego
 */
public class ArrayIndexExpr extends lvalueExpr{

    Expr exprindex;
    public ArrayIndexExpr(String name, Expr exprindex) {
        super(name);
        this.exprindex=exprindex;
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
