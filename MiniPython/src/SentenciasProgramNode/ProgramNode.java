/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SentenciasProgramNode;

import java.util.ArrayList;

/** 
 *
 * @author diego
 */
public class ProgramNode extends ProgramNodeSentence{
    ArrayList<FieldDeclaration> field_decl;
    ArrayList<MethodDeclaration> method_decl;

    public ProgramNode(ArrayList<FieldDeclaration> field_decl, ArrayList<MethodDeclaration> method_decl) {
        this.field_decl = field_decl;
        this.method_decl = method_decl;
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
