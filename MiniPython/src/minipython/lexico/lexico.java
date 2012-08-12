/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipython.lexico;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import minipython.lexico.tokenlist.tokens;
/**
 *
 * @author diego
 */
public class lexico {
    private String path;
    private long position;
    private long size;
    private File file;
    private int estado;
    public static char cs;
    private RandomAccessFile accessFile;
    private int totalEnters;
    private int totalTabs;

    public lexico(String path) 
    {
        this.path = path;
        position=0;
        estado=-1;
        totalEnters=0;
        totalTabs=0;
        file=new File(path);
        try
        {
            accessFile=new RandomAccessFile(file,"r");
            size=accessFile.length();
        }
        catch(IOException e)
        {
            
        }
    }

    public int getTotalEnters() {
        return totalEnters;
    }

    public int getTotalTabs() {
        return totalTabs;
    }
    
    public char nextSymbol() throws IOException
    {
        if(position<size)
        {
            accessFile.seek(position);
            int sym=accessFile.read();
            char symbol=(char)sym;
            position++;
            return symbol;
        }
        else
        {
            return '$';
        }
    }
    
    public tokenlist nextToken() throws IOException
    {
        String token="";
        while(true)
        {
            switch(estado)
            {
                case -1:
                    if(cs=='\n')
                    {
                        totalEnters++;
                        cs=nextSymbol();
                        estado=-1;
                    }
                    if(cs==' '||Character.isWhitespace(cs)||Character.isSpaceChar(cs))
                    {
                        cs=nextSymbol();
                        estado=-1;
                    }
                    else if(cs=='$')
                    {
                        token+=cs;
                        return new tokenlist(token, tokens.EOF);
                    }
                    else
                    {
                        estado=0;
                    }
                    break;
                
                case 0:
                    if(cs=='+')
                    {
                        cs=nextSymbol();
                        estado=1;
                    }
                    else
                    {
                        estado=2;
                    }
                    break;
               
                case 1:
                    estado=-1;
                    token="+";
                    return new tokenlist(token, tokens.OP_SUMA);
                
                case 2:
                    if(cs=='-')
                    {
                        cs=nextSymbol();
                        estado=3;
                    }
                    else
                    {
                        estado=4;
                    }
                break;
                
                case 3:
                    token="-";
                    estado=-1;
                    return new tokenlist(token, tokens.OP_REST);
                  
                case 4:
                    if(cs=='*')
                    {
                        cs=nextSymbol();
                        estado=5;
                    }
                    else
                    {
                        estado=6;
                    }
                    break;
                    
                case 5:
                    token="*";
                    estado=-1;
                    return new tokenlist(token, tokens.OP_MULT);
                    
                case 6:
                    if(cs=='/')
                    {
                        cs=nextSymbol();
                        estado=7;
                    }
                    else
                    {
                        estado=8;
                    }
                    break;
                    
                case 7:
                    token="/";
                    estado=-1;
                    return new tokenlist(token, tokens.OP_DIV);
                    
                case 8:
                    if(cs=='<')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=9;
                    }
                    else
                    {
                        estado=13;
                    }
                    break;
                    
                case 9:
                    if(cs=='<')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=10;
                    }
                    else if(cs=='=')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=11;
                    }
                    else
                    {
                        estado=12;
                    }
                    break;
                    
                case 10:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_SLEFT);
                    
                case 11:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_MEIG);
                    
                case 12:
                    estado= -1;
                    return new tokenlist(token, tokens.OP_MENOR);
                    
                case 13:
                    if(cs=='>')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=14;
                    }
                    else
                    {
                        estado=18;
                    }
                    break;
                    
                case 14:
                    if(cs=='>')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=15;
                    }
                    else if(cs=='=')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=17;
                    }
                    else
                    {
                        estado=16;
                    }
                    break;
                    
                case 15:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_SRIGHT);
                    
                case 16:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_MAYOR);
                    
                case 17:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_MAIG);
                    
                case 18:
                    if(cs=='%')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=19;
                    }
                    else
                    {
                        estado=20;
                    }
                    break;
                    
                case 19:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_MOD);
                    
                case 20:
                    if(cs=='=')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=21;
                    }
                    else
                    {
                        estado=24;
                    }
                    break;
                    
                case 21:
                    if(cs=='=')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=22;
                    }
                    else
                    {
                        estado=23;
                    }
                    break;
                    
                case 22:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_COMP);
                    
                case 23:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_ASSIG);
                    
                case 24:
                    if(cs==':')
                    {
                        token+=cs;
                        estado=25;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=26;
                    }
                    break;
                    
                case 25:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_DP);
                    
                case 26:
                    if(cs==';')
                    {
                        token+=cs;
                        estado=27;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=28;
                    }
                    break;
                    
                case 27:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_C);
                    
                case 28:
                    if(cs=='[')
                    {
                        token+=cs;
                        estado=29;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=30;
                    }
                    break;
                    
                case 29:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_CORI);
                    
                case 30:
                    if(cs==']')
                    {
                        estado=31;
                        token+=cs;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=32;
                    }
                    break;
                    
                case 31:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_CORD);
                    
                case 32:
                    if(cs=='(')
                    {
                        token+=cs;
                        estado=33;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=34;
                    }
                    break;
                    
                case 33:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_PARI);
                    
                case 34:
                    if(cs==')')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=35;
                    }
                    else
                    {
                        estado=36;
                    }
                    break;
                    
                case 35:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_PARD);
                    
                case 36:
                    if(cs=='~')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=37;
                    }
                    else
                    {
                        estado=38;
                    }
                    break;
                    
                case 37:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_NEG);
                    
                case 38:
                    if(cs=='\t')
                    {
                        totalTabs++;
                        estado=39;
                        token+=cs;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=40;
                    }
                    break;
                    
                case 39:
                    estado=-1;
                    return new tokenlist(token, tokens.DEL_TAB);
                    
                case 40:
                    if(cs == '.')
                    {
                        token+=cs;
                        estado=41;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=44;
                    }
                    break;
                    
                case 41:
                    if(cs=='.')
                    {
                        token+=cs;
                        estado=42;
                        cs=nextSymbol();
                    }
                    else
                    {
                        token+=" token no reconocido";
                        estado=-1;
                        return new tokenlist(token, tokens.ERROR);
                    }
                    break;
                    
                case 42:
                    if(cs=='.')
                    {
                        token+=cs;
                        estado=43;
                        cs=nextSymbol();
                    }
                    else
                    {
                        token+=" token no reconocido";
                        estado=-1;
                        return new tokenlist(token, tokens.ERROR);
                    }
                    break;   
                    
                case 43:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_RANG);
                    
                case 44:
                    if(cs=='a')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=45;
                    }
                    else
                    {
                        estado=48;
                    }
                    break;
                    
                case 45:
                    if(cs=='n')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=46;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 46:
                    if(cs=='d')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=47;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 47:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_AND);
                    
                case 48:
                    if(cs=='o')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=49;
                    }
                    else
                    {
                        estado=51;
                    }
                    break;
                    
                case 49:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=50;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 50:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_OR);
                    
                case 51:
                    if(cs=='n')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=52;
                    }
                    else
                    {
                        estado=55;
                    }
                    break;
                    
                case 52:
                    if(cs=='o')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=53;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 53:
                    if(cs=='t')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=54;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 54:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_NOT);
                  
                    //Btrue
                //case 55:
                    
                    
                            
            }
        }
    }
    
}
