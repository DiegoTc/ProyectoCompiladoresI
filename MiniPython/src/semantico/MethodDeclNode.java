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
public class MethodDeclNode extends ASTNode{
    private String name;
    ArrayList<String> methodArguments;
    ASTNode block;
    int linea;

    public MethodDeclNode(String name, ArrayList<String> methodArguments, ASTNode block, int linea) {
        this.name = name;
        this.methodArguments = methodArguments;
        this.block = block;
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
