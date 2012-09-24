/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class BlockStatement extends statement{
    ArrayList<statement> statements;

    public BlockStatement(ArrayList<statement> statements) {
        this.statements = statements;
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
