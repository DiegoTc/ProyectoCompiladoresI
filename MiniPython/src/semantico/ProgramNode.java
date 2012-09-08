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
public class ProgramNode extends ASTNode{

    private String name;
    private ArrayList<ASTNode> fielddecl_list;
    private ArrayList<ASTNode> methoddecl_list;
    int linea;

    public ProgramNode(int line,String name, ArrayList<ASTNode> fielddecl_list, ArrayList<ASTNode> methoddecl_list) {
        this.name = name;
        this.fielddecl_list = fielddecl_list;
        this.methoddecl_list = methoddecl_list;
        this.linea=line;
    }

    public ArrayList<ASTNode> getFielddecl_list() {
        return fielddecl_list;
    }

    public ArrayList<ASTNode> getMethoddecl_list() {
        return methoddecl_list;
    }

    public String getName() {
        return name;
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
