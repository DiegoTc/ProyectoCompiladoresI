/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SentenciasProgramNode;
import Statement.Assign;
/**
 *
 * @author diego
 */
public class FieldDeclaration extends ProgramNodeSentence{
    Assign statement_decl;

    public FieldDeclaration(Assign statement_decl) {
        this.statement_decl = statement_decl;
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
