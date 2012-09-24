/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SentenciasProgramNode;

import java.util.ArrayList;
import Statement.BlockStatement;
/**
 *
 * @author diego
 */
public class MethodDeclaration extends ProgramNodeSentence{
    String methodName;
    ArrayList<String> methosArgs;
    BlockStatement block;

    public MethodDeclaration(String methodName, ArrayList<String> methosArgs, BlockStatement block) {
        this.methodName = methodName;
        this.methosArgs = methosArgs;
        this.block = block;
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
