/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
public class FieldDeclNode extends ASTNode{

    private String varName;
    private ASTNode right;
    private int Linea;

    public FieldDeclNode(int line,String varName, ASTNode right) {
        this.varName = varName;
        this.right = right;
        Linea=line;
    }

    public ASTNode getRight() {
        return right;
    }

    public String getVarName() {
        return varName;
    }
    
    
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getlinea() {
        return Linea;
    }

  
    
}
