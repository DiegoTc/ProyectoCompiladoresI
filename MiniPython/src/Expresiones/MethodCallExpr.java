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
public class MethodCallExpr extends Expr{
    String name;
    ArrayList<Expr> params;

    public MethodCallExpr(String name, ArrayList<Expr> params) {
        this.name = name;
        this.params = params;
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
