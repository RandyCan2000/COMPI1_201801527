/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Globales.Globales;
import TDA.*;
import TDA.Error;

/**
 *
 * @author Usuario
 */
public class Automata {
    // Automatas Conjuntos y Comentarios
    public Globales G=new Globales();
    
    int Estado=0;
    String Token="";
    boolean AceptadoConjuntos=true;
    char Caracter;
    Error ERRC;
    int Fila=1;
    int Columna=1;
    public void AutomataConjuntos(String Linea){
        for(int Col=0;Col<Linea.length();Col++){
            Columna++;
            Caracter=Linea.charAt(Col);
            if(Caracter=='\n'){Fila++;Columna=0;}
            switch(Estado){
                case 0:
                    if(Caracter==' '||Caracter=='\n'||Caracter=='\t'){}
                    else if(Caracter=='{'){
                        Estado=1;
                        Token TOK=new Token(G.TOKEN.size(),"{");
                        System.out.println(TOK.toString());
                        G.TOKEN.add(TOK);
                    }else{
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"{");
                        System.out.println(ERRC.toString());
                        G.ERROR.add(ERRC);
                        Col=Linea.length();
                    }
                    break;
                case 1:
                    if(Caracter==' '||Caracter=='\n'||Caracter=='\t'){}
                    else if(Caracter=='C'){
                        Token+=Caracter;
                        Estado=2;
                    }
                    else if(Caracter=='/'){
                        Estado=4;
                        Token+=Caracter;
                    }
                    else if(Caracter=='<'){
                        Estado=9;
                        Token="<";
                    }else{
                        // Nombre Expresiones Regulares o Validaciones de Expreciones Regulares
                    }
                    break;
                case 2:
                    switch(Caracter){
                        case 'O':
                            Estado=2; Token+=Caracter;
                            break;
                        case 'N':
                            Estado=2; Token+=Caracter;
                            break;
                        case 'J':
                            Estado=3; Token+=Caracter;
                            Token b=new Token(G.TOKEN.size(),Token);
                            System.out.println(b.toString());
                            G.TOKEN.add(b);Token="";
                            break;
                        default :
                            Estado=200;
                            ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"CONJ");
                            break;
                    }
                    break;
                case 3:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter==':'){
                        Token b=new Token(G.TOKEN.size(),":");
                        System.out.println(b.toString());
                        G.TOKEN.add(b);
                        Token="";
                        Estado=5;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,":");
                    }
                    break;
                case 4:
                    if(Caracter=='/'){
                        Token+=Caracter;
                        Token a=new Token(G.TOKEN.size(),Token);System.out.println(a.toString());G.TOKEN.add(a);Token="";
                        for(int aux=Col+1;aux<Linea.length();aux++){
                            if(Linea.charAt(aux)=='\n'){
                                Col=aux;Columna=0;Fila++;
                                break;
                            }else{Token+=Linea.charAt(aux);}
                        }
                        Token b=new Token(G.TOKEN.size(),Token);
                        System.out.println(b.toString());
                        G.TOKEN.add(b);
                        Token="";
                        Estado=1;
                    }
                    break;
                case 5:
                    if(Caracter==' '||Caracter=='\t'){}else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"Variable");
                    }
                    else{
                        Token+=Caracter;
                        Estado=6;
                    }
                    break;
                case 6:
                    if(Caracter==' '||Caracter=='\t'){
                        Token b=new Token(G.TOKEN.size(),Token);
                        System.out.println(b.toString());
                        G.TOKEN.add(b);Token="";Estado=7;
                    }else if(Caracter=='-'){
                        Token b=new Token(G.TOKEN.size(),Token);
                        System.out.println(b.toString());
                        G.TOKEN.add(b);Token="-";Estado=8;
                    }
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"->");}
                    else{
                        if(Caracter=='-'){
                        Token b=new Token(G.TOKEN.size(),Token);
                        System.out.println(b.toString());
                        Token="";
                        G.TOKEN.add(b);
                        Estado=7;
                        Token="-";
                        }else{Token+=Caracter;}
                        
                    }
                    break;
                case 7:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"-");
                    }
                    else if(Caracter=='-'){
                        Token="-";
                        Estado=8;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"-");
                    }
                    break;
                case 8: 
                    // signo > mayor que
                    if((int)Caracter==62){
                        Token b=new Token(G.TOKEN.size(),"->");
                        System.out.println(b.toString());
                        Token="";
                        G.TOKEN.add(b);
                        Estado=1;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"->");}
                    break;
                case 9:
                    if(Caracter=='!'){
                        Token+='!';
                        Token b=new Token(G.TOKEN.size(),Token);
                        System.out.println(b.toString());
                        G.TOKEN.add(b);
                        Token="";
                        Estado=10;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"<!");
                    }
                    break;
                case 10:
                    for(int aux=Col;aux<Linea.length();aux++){
                        try{
                            if(Linea.charAt(aux)=='!'&&Linea.charAt(aux+1)=='>'){
                                Col=aux+1;
                                break;
                            }
                            else if(Linea.charAt(aux)=='\n'){
                            Token+=Linea.charAt(aux);
                            Fila++;Columna=1;
                            }else{
                            Token+=Linea.charAt(aux);
                            }
                        }catch(Exception E){
                            aux--;
                            Estado=200;
                            ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"!>");
                        }
                    }
                    Token b=new Token(G.TOKEN.size(),Token);
                    System.out.println(b.toString());
                    G.TOKEN.add(b);
                    Token="";
                    Token a=new Token(G.TOKEN.size(),"!>");
                    System.out.println(a.toString());
                    G.TOKEN.add(a);
                    Token="";
                    Estado=1;
                    break;
                    //error automata conjuntos
                case 200:
                    G.ERROR.add(ERRC);
                    System.out.println(ERRC.toString());
                    Estado=1;
                    break;
            }
        }
    }
}
