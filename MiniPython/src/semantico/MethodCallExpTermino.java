/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class MethodCallExpTermino extends Expr{
    int linea;
    //String methodname;
    ArrayList<ASTNode> methodParams;

    public MethodCallExpTermino(int linea,  ArrayList<ASTNode> methodParams) {
        this.linea = linea;
        //this.methodname = methodname;
        this.methodParams = methodParams;
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
