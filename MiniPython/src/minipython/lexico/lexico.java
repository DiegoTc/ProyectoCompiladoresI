/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipython.lexico;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Stack;
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
    public  char cs;
    private RandomAccessFile accessFile;
    private int totalEnters;
    private int totalTabs;
    private Stack pila;
    public lexico(String path) 
    {
        this.path = path;
        position=0;
        estado=-1;
        totalEnters=0;
        totalTabs=0;
        file=new File(path);
        pila=new Stack();
        pila.push(0);
        try
        {
            accessFile=new RandomAccessFile(file,"r");
            size=accessFile.length();
        }
        catch(IOException e)
        {
            
        }
    }

    private int getTopPila()
    {
        Object a=pila.lastElement();
        int num=Integer.parseInt(a.toString());
        return num;
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
    
    private String lasttoken;
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
                        lasttoken="\n";
                        return new tokenlist(token, tokens.NEW_LINE);
                    }
                    if(lasttoken=="\n")
                    {
                        int contador=0;
                        if(Character.isWhitespace(cs)||cs==' '){
                            while(Character.isWhitespace(cs)||cs==' ')
                            {
                                cs=nextSymbol();
                                contador++;
                            }
                            int top=getTopPila();
                            if(contador>top)
                            {
                                pila.push(contador);
                                estado=-1;
                                return new tokenlist(token, tokens.DEL_TAB);
                            }
                            else
                            {
                                pila.pop();
                                top=getTopPila();
                                while(top!=contador)
                                {
                                    if(pila.isEmpty())
                                    {
                                        return new tokenlist("EROR", tokens.ERROR);
                                    }
                                    pila.pop();
                                    top=getTopPila();
                                }
                                return new tokenlist(token, tokens.DEL_DESTAB);
                            }
                        }
                        if(cs=='\t')
                        {
                            while(cs=='\t')
                            {
                                cs=nextSymbol();
                                contador++;
                            }
                            int top=getTopPila();
                            if(contador>=top)
                            {
                                pila.push(contador);
                                estado=-1;
                                return new tokenlist(token, tokens.DEL_TAB);
                            }
                            else
                            {
                                pila.pop();
                                top=getTopPila();
                                while(top!=contador)
                                {
                                    if(pila.isEmpty())
                                    {
                                        return new tokenlist("EROR", tokens.ERROR);
                                    }
                                    pila.pop();
                                    top=getTopPila();
                                }
                                return new tokenlist(token, tokens.DEL_DESTAB);
                            }
                        }
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
                        estado=112;
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
                        estado=112;
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
                        estado=112;
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
                        estado=112;
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
                        estado=112;
                    }
                    break;
                    
                case 54:
                    estado=-1;
                    return new tokenlist(token, tokens.OP_NOT);
                  
                    //Btrue
                case 55:
                    if(cs=='t')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=56;
                    }
                    else
                    {
                        estado=60;
                    }
                    break;
                    
                case 56:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=57;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 57:
                    if(cs=='u')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=58;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 58:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=59;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 59:
                    estado=-1;
                    return new tokenlist(token, tokens.B_TRUE);
                    
                case 60:
                    if(cs=='f')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=61;
                    }
                    else
                    {
                        estado=66;
                    }
                    break;
                    
                case 61:
                    if(cs=='a')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=62;
                    }
                    else
                    {
                        estado=88;
                    }
                    break;
                    
                case 62:
                    if(cs=='l')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=63;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 63:
                    if(cs=='s')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=64;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 64:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=65;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 65:
                    estado=-1;
                    return new tokenlist(token, tokens.B_FALSE);
                    
                case 66:
                    if(cs=='c')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=67;
                    }
                    else
                    {
                        estado=72;
                    }
                    break;
                            
                case 67:
                    if(cs=='l')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=68;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 68:
                    if(cs=='a')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=69;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 69:
                    if(cs=='s')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=70;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 70:
                    if(cs=='s')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=71;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 71:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_CLASS);
                    
                case 72:
                    if(cs=='i')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=73;
                    }
                    else
                    {
                        estado=77;
                    }
                    break;
                    
                case 73:
                    if(cs=='=')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=74;
                    }
                    else if(cs=='f')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=75;
                    }
                    else if(cs=='n')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=76;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 74:
                   estado=-1;
                   return new tokenlist(token, tokens.OP_DIST);
                   
                case 75:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_IF);
                    
                case 76:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_IN);
                    
                case 77:
                    if(cs=='d')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=78;
                    }
                    else
                    {
                        estado=81;
                    }
                    break;
                    
                case 78:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=79;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 79:
                    if(cs=='f')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=80;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 80:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_DEF);
                    
                case 81:
                    if(cs=='w')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=82;
                    }
                    else
                    {
                        estado=91;
                    }
                    break;
                    
                case 82:
                    if(cs=='h')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=83;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 83:
                    if(cs=='i')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=84;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 84:
                    if(cs=='l')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=85;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 85:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=86;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 86:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_WHILE);
                    
                case 88:
                    if(cs=='o')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=89;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 89:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=90;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 90:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_FOR);
                    
                case 91:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=92;
                    }
                    else
                    {
                        estado=98;
                    }
                    break;
                    
                case 92:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=93;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 93:
                    if(cs=='t')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=94;
                    }
                    else if(cs=='a')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=118;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 94:
                    if(cs=='u')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=95;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 95:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=96;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 96:
                    if(cs=='n')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=97;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 97:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_RETURN);
                    
                case 98:
                    if(cs=='b')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=99;
                    }
                    else
                    {
                        estado=104;
                    }
                    break;
                    
                case 99:
                    if(cs=='r')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=100;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 100:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=101;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 101:
                    if(cs=='a')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=102;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 102:
                    if(cs=='k')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=103;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 103:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_BREAK);
                    
                case 104:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=105;
                    }
                    else
                    {
                        estado=111;
                    }
                    break;
                    
                case 105:
                    if(cs=='l')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=106;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 106:
                    if(cs=='s')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=107;
                    }
                    else if(cs=='i')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=109;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 107:
                    if(cs=='e')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=108;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 108:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_ELSE);
                    
                case 109:
                    if(cs=='f')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=110;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 110:
                    estado=-1;
                    return new tokenlist(token, tokens.KW_ELIF);
                    
                case 111:
                    if(Character.isLetter(cs))
                    {
                        estado=112;
                        token+=cs;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=114;
                    }
                    break;
                    
                case 112:
                    if(Character.isLetter(cs)||cs=='_')
                    {
                        estado=112;
                        token+=cs;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=113;
                    }
                    break;
                    
                case 113:
                    estado=-1;
                    return new tokenlist(token, tokens.P_ID);
                    
                case 114:
                    if(Character.isDigit(cs))
                    {
                        token+=cs;
                        estado=115;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=116;
                    }
                    break;
                    
                case 115:
                    if(Character.isDigit(cs))
                    {
                        token+=cs;
                        estado=115;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=-1;
                        return new tokenlist(token, tokens.LIT_NUM);
                    }
                    break;
                    
                case 116:
                    if(Character.isLetter(cs))
                    {
                        token+=cs;
                        estado=117;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=121;
                    }
                    break;
                          
                case 117:
                    estado=-1;
                    return new tokenlist(token, tokens.LIT_CHCONST);
                    
                case 118:
                    if(cs=='d')
                    {
                        token+=cs;
                        cs=nextSymbol();
                        estado=119;
                    }
                    else
                    {
                        estado=112;
                    }
                    break;
                    
                case 119:
                   estado=-1;
                   return new tokenlist(token, tokens.KW_READ);
                   
                case 120:
                    estado=-1;
                    token+=cs;
                    return new tokenlist(token, tokens.ERROR);
                    
                case 121:
                    if(cs==',')
                    {
                        estado=122;
                        token+=cs;
                        cs=nextSymbol();
                    }
                    else
                    {
                        estado=120;
                    }
                    break;
                    
                case 122:
                    estado=-1;
                    return new tokenlist(token, tokens.SIGN_C);
                default:
                    estado=-1;
                    token+=cs;
                    return new tokenlist(token, tokens.ERROR);
            }
        }
    }
    
}
