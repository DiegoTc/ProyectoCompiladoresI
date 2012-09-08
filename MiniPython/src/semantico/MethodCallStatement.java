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
public class MethodCallStatement extends Statement{   
    String methodName;
    ArrayList<ASTNode> methodPatams;
    int linea;

    public MethodCallStatement(String methodName, ArrayList<ASTNode> methodPatams, int linea) {
        this.methodName = methodName;
        this.methodPatams = methodPatams;
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
