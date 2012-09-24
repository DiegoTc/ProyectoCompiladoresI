/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Expresiones;

/**
 *
 * @author diego
 */
public abstract class OpBinExpr extends Expr{
    public Expr left,right;

    public OpBinExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
    
}
