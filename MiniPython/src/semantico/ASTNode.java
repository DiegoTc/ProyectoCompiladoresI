/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author diego
 */
abstract class ASTNode {
    private  int linea;
    @Override
    public abstract String toString();
    public abstract int getlinea();

}


