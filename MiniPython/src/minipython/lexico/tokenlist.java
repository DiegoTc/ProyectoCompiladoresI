/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipython.lexico;

/**
 *
 * @author diego
 */
public class tokenlist {
    public enum tokens
    {
        OP_SUMA,
        OP_REST,
        OP_MULT,
        OP_DIV,
        OP_SLEFT,
        OP_SRIGHT,
        OP_MOD,
        OP_MENOR,
        OP_MAYOR,
        OP_MEIG,
        OP_MAIG,
        OP_COMP,
        OP_DIST,
        OP_AND,
        OP_OR,
        OP_NOT,
        OP_NUM,
        LIT_NUM,
        LIT_CHCONST,
        B_TRUE,
        B_FALSE,
        KW_CLASS,
        P_ID,
        KW_DEF,
        KW_IF,
        KW_ELIF,
        KW_ELSE,
        KW_WHILE,
        KW_FOR,
        KW_IN,
        KW_RETURN,
        KW_BREAK,
        KW_PRINT,
        KW_READ,
        SIGN_DP,
        SIGN_C,
        SIGN_CORI,
        SIGN_CORD,
        SIGN_PARD,
        SIGN_PARI,
        SIGN_NEG,
        DEL_TAB,
        SIGN_ASSIG,
        SIGN_RANG,
        EOF,
        ERROR
    }
    
    private String contenido;
    private tokens tipo;

    public tokenlist(String contenido, tokens tipo) {
        this.contenido = contenido;
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public tokens getTipo() {
        return tipo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setTipo(tokens tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString()
    {
        switch(tipo)
        {
            case OP_SUMA: return contenido;
            case OP_REST: return contenido;
            case OP_MULT: return contenido;
            case OP_DIV: return contenido; 
            case OP_SLEFT: return contenido;
            case OP_SRIGHT: return contenido;
            case OP_MOD:return contenido;
            case OP_MENOR: return contenido;
            case OP_MAYOR: return contenido;
            case OP_MEIG: return contenido;
            case OP_MAIG: return contenido;
            case OP_COMP: return contenido;
            case OP_DIST: return contenido;
            case OP_AND: return contenido;
            case OP_OR: return contenido;
            case OP_NOT: return contenido;
            case OP_NUM:return contenido;
            case LIT_NUM: return contenido;
            case LIT_CHCONST: return contenido;
            case B_TRUE: return contenido;
            case B_FALSE: return contenido;
            case KW_CLASS: return contenido;
            case P_ID: return contenido;
            case KW_DEF: return contenido;
            case KW_IF: return contenido;
            case KW_ELIF: return contenido;
            case KW_ELSE: return contenido;
            case KW_WHILE: return contenido;
            case KW_FOR: return contenido;
            case KW_IN: return contenido;
            case KW_RETURN: return contenido;
            case KW_BREAK: return contenido;
            case KW_PRINT: return contenido;
            case KW_READ: return contenido;
            case SIGN_DP: return contenido;
            case SIGN_C: return contenido;
            case SIGN_CORI: return contenido;
            case SIGN_CORD: return contenido;
            case SIGN_PARD: return contenido;
            case SIGN_PARI: return contenido;
            case SIGN_NEG: return contenido;
            case DEL_TAB: return contenido;
            case SIGN_ASSIG: return contenido;
            case SIGN_RANG: return contenido;
            case EOF: return contenido;
            case ERROR: return contenido;
            default:return "Token Invalido";
        }
    }
}
