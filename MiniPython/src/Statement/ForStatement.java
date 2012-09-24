/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;
import Expresiones.Expr;
import Expresiones.IDExpr;
/**
 *
 * @author diego
 */
public class ForStatement extends cycleStatement{
    IDExpr var;
    Expr exprInicial;
    Expr exprFinal;
    BlockStatement bloque;

    public ForStatement(IDExpr var, Expr exprInicial, Expr exprFinal, BlockStatement bloque) {
        this.var = var;
        this.exprInicial = exprInicial;
        this.exprFinal = exprFinal;
        this.bloque = bloque;
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
