/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Statement;

import java.util.ArrayList;
import Expresiones.*;
/**
 *
 * @author diego
 */
public class ifStatement extends statement{
   
    Expr condicion;
    BlockStatement bloque;
    ArrayList<ElseIfBlock> elseifBlocklist;
    BlockStatement elsebloque;

    public ifStatement(Expr condicion, BlockStatement bloque, ArrayList<ElseIfBlock> elseifBlocklist, BlockStatement elsebloque) {
        this.condicion = condicion;
        this.bloque = bloque;
        this.elseifBlocklist = elseifBlocklist;
        this.elsebloque = elsebloque;
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
