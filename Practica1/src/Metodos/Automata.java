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
        boolean Terminar=false;
        boolean ValuarExpReg=false;
        boolean ESPACIOS=false;
        int CorchetesExpReg=0;
        int ComillasExpReg=0;
        for(int Col=0;Col<Linea.length();Col++){
            Columna++;
            Caracter=Linea.charAt(Col);
            if(Caracter=='\n'||Caracter=='\t'){}
            else if((int)Caracter<=31||(int)Caracter>=127){
                Estado=200;
                ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"NO VALIDO");}
            if(Caracter=='\n'){Fila++;Columna=0;}
            if(Terminar==true&&Caracter!=' '&&Caracter!='\t'&&Caracter!='\n'){
                Estado=200;
                ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"NADA");
            }
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
                    }else if(Caracter=='}'){
                        Token Z1=new Token(G.TOKEN.size(),"}");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Terminar=true;
                    }else if(Caracter=='%'){
                        Estado=26;
                        Token="%";
                    }else{
                        // Nombre Expresiones
                        Estado=20;
                        Token=Character.toString(Caracter);
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
                        if(ValuarExpReg==false){Estado=1;}
                        else{Estado=30;}
                        
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
                        Estado=11;
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
                    if(ValuarExpReg==false){Estado=1;}
                    else{Estado=30;}
                    break;
                case 11:
                    if(Caracter=='{'){
                        Token Z=new Token(G.TOKEN.size(),"{");
                        System.out.println(Z.toString());
                        G.TOKEN.add(Z);
                        Estado=12;
                    }
                    else if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"-");
                    }
                    else{
                        try{
                            int q=Integer.parseInt(Character.toString(Caracter));
                            System.out.println(q);
                            Token=Character.toString(Caracter);
                            Estado=18;
                        }catch(Exception E){
                            Token=Character.toString(Caracter);
                            Estado=15;
                        }
                        
                    }
                    break;
                case 12: 
                    if(Caracter=='\t'||Caracter==' '){}
                    else{
                    Token=Character.toString(Caracter);
                    Estado=13;
                    }
                    break;
                case 13:
                    if(Caracter=='}'){
                        Token Z=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z.toString());
                        G.TOKEN.add(Z);
                        Token Z1=new Token(G.TOKEN.size(),"}");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=14;
                    }else if(Caracter==','){
                        Token+=Caracter;
                        Estado=13;
                    }else if(Caracter==' '){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size()," ",Fila,Columna,"Valores sin Espacio en Conjunto");
                    }else{
                        Token+=Caracter;
                        Estado=13;
                    }
                    break;
                case 14: 
                    if(Caracter=='\t'||Caracter==' '){}
                    else if(Caracter==';'){
                        Token Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=1;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,";");
                    }
                    break;
                case 15:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='~'){
                        Token+=Caracter;
                        Estado=16;
                    }else {
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"~");
                    }
                    break;
                case 16:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"Letra");
                    }else{
                        try{
                            int q=Integer.parseInt(Character.toString(Caracter));
                            System.out.println(q);
                            Estado=200;
                            ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"Letra");
                        }catch(Exception E){
                            Token+=Caracter;
                            Token Z1=new Token(G.TOKEN.size(),Token);
                            System.out.println(Z1.toString());
                            G.TOKEN.add(Z1);
                            Token="";
                            Estado=17;
                        }
                    }
                    break;
                case 17:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter==';'){
                        Token Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=1;
                    }
                    else {
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,";");
                    }
                    break;
                case 18:
                    if(Caracter=='\n'||Caracter==' '||Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SIN ESPACIOS EN CONJUNTO",Fila,Columna,"~");
                    }
                    else if(Caracter=='~'){
                        Token+=Caracter;
                        Estado=19;
                    }else {
                        try{
                            int q=Integer.parseInt(Character.toString(Caracter));
                            System.out.println(q);
                            Token+=Caracter;
                        }catch(Exception E){
                            Estado=200;
                            ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"Digito"); 
                        } 
                    }
                    break;
                case 19:
                    if(Caracter=='\n'||Caracter==' '||Caracter=='\t'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SIN ESPACIOS EN CONJUNTO",Fila,Columna,"DIGITO");
                    }else if(Caracter==';'){
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=1;
                    }
                    else{
                        try{
                            int q=Integer.parseInt(Character.toString(Caracter));
                            System.out.println(q);
                            Token+=Caracter;
                        }catch(Exception E){
                            Estado=200;
                            ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"Digito");   
                        }
                    }
                    break;
                case 20:
                    if(Caracter==' '||Caracter=='\t'){
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=21;
                    }
                    else if(Caracter=='-'){
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="-";
                        Estado=22;
                    }
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"->");   
                    }
                    else{
                        Token+=Caracter;
                    }
                    break; 
                case 21:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"->");   
                    }
                    else if(Caracter=='-'){
                        Token+=Caracter;
                        Estado=22;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"->");   
                    }
                    break;
                case 22:
                    //signo mayor que >
                    if((int)Caracter==62){
                        Token+=Caracter;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=23;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"->");   
                    }
                    break;
                case 23:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"SALTO DE LINEA",Fila,Columna,"EXPRESION REGULAR");   
                    }else {
                        Col--;
                        Estado=24;
                    }
                    break;
                case 24:
                    if(Caracter=='\"'){
                        Token+=Caracter;
                        ComillasExpReg++;
                        ESPACIOS=true;
                        Estado=25;
                    }else if(Caracter=='{'){
                        Token+=Caracter;
                        CorchetesExpReg++;
                        Estado=25;
                    }else if(Caracter==';'){
                        Estado=1;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        if(CorchetesExpReg!=0){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"FALTAN CORCHETES DE CIERRE");  
                        }else if(ComillasExpReg!=0){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"FALTAN COMILLAS DE CIERRE");  
                        }
                        Token="";
                        Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                    }else if(Caracter==' '||Caracter=='\t'||Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"ESPACIO, TAB o ENTER",Fila,Columna,"SIN ESPACIOS FUERA DE COMILLAS");  
                    }
                    else{
                        Token+=Caracter;
                    }
                    break;
                case 25:
                    if(Caracter=='\"'){
                        Token+=Caracter;
                        ComillasExpReg--;
                        ESPACIOS=false;
                        Estado=24;
                    }else if(Caracter=='}'){
                        Token+=Caracter;
                        CorchetesExpReg--;
                        Estado=24;
                    }else if(Caracter=='{'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"{",Fila,Columna,"NO ABRIR CORCHETE ANTES DE CERRARLO");  
                    }
                    else if(Caracter==';'){
                        Estado=1;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        if(CorchetesExpReg!=0){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"FALTAN CORCHETES DE CIERRE");  
                        }else if(ComillasExpReg!=0){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"FALTAN COMILLAS DE CIERRE");  
                        }
                        Token="";
                        Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                    }else if(Caracter==' ' && ESPACIOS==true){
                        Token+=Caracter;
                    }
                    else if((Caracter==' '&&ESPACIOS==false)||Caracter=='\t'||Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"ESPACIO, TAB o ENTER",Fila,Columna,"SIN ESPACIOS FUERA DE COMILLAS");  
                    }
                    else{
                        Token+=Caracter;
                    }
                    break;
                case 26:
                    if(Caracter=='%'){
                        Token+=Caracter;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=27;
                    }else{
                        Token+=Caracter;
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"%%");  
                    }
                    break;
                case 27:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){Estado=28;}
                    else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"%%");  
                    }
                    break;
                case 28:
                    if(Caracter=='%'){
                        Token="%";
                        Estado=29;
                    }else if(Caracter==' '||Caracter=='\t'||Caracter=='\n'){}
                    else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,"%%"); 
                    }
                    break;
                case 29:
                    if(Caracter=='%'){
                        Token+=Caracter;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=30;
                        ValuarExpReg=true;
                    }else{
                        Token+=Caracter;
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"%%");  
                    }
                    break;
                case 30:
                    if(Caracter==' '||Caracter=='\t'||Caracter=='\n'){}
                    else if(Caracter=='/'){
                        Estado=4;
                        Token+=Caracter;
                    }
                    else if(Caracter=='<'){
                        Estado=9;
                        Token="<";
                    }else if(Caracter=='}'){
                        Token Z1=new Token(G.TOKEN.size(),"}");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Terminar=true;
                    }else {
                        Token=Character.toString(Caracter);
                        Estado=31;
                    }
                    break;
                case 31:
                    if(Caracter==' '||Caracter=='-'||Caracter=='\t'){
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        if(Caracter=='-'){
                        Token="-";
                        Estado=33;
                        }else{
                        Token="";
                        Estado=32;
                        }
                    }else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"ENTER",Fila,Columna,"SIN SALTO DE LINEA");
                    }else{
                        Token+=Caracter;
                    }
                    break;
                case 32:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"ENTER",Fila,Columna,"SIN SALTO DE LINEA");
                    }else if(Caracter=='-'){
                        Token="-";
                        Estado=33;
                    }
                    break;
                case 33:
                    if(Caracter=='>'){
                        Token+=Caracter;
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=34;
                    }else{
                        Token+=Caracter;
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"->");
                    }
                    break;
                case 34:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\"'){
                        Token=Character.toString(Caracter);
                        Token Z1=new Token(G.TOKEN.size(),Token);
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Token="";
                        Estado=35;
                    }else{
                        Token+=Caracter;
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Token,Fila,Columna,"\"");
                    }
                    break;
                case 35:
                    for(int q=Col;q<Linea.length();q++){
                        if(Linea.charAt(q)=='\"'){
                            Token Z1=new Token(G.TOKEN.size(),Token);
                            System.out.println(Z1.toString());
                            G.TOKEN.add(Z1);
                            Token="";
                            Z1=new Token(G.TOKEN.size(),"\"");
                            System.out.println(Z1.toString());
                            G.TOKEN.add(Z1);
                            Estado=36;
                            Col=q;
                            break;
                        }else if(Linea.charAt(q)=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"Enter",Fila,Columna,"SIN SALTO DE LINEA");
                        }else{
                        Token+=Linea.charAt(q);
                        }
                    }
                    break;
                case 36:
                    if(Caracter==' '||Caracter=='\t'){}
                    else if(Caracter=='\n'){
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),"Enter",Fila,Columna,"SIN SALTO DE LINEA");
                    }else if(Caracter==';'){
                        Token Z1=new Token(G.TOKEN.size(),";");
                        System.out.println(Z1.toString());
                        G.TOKEN.add(Z1);
                        Estado=30;
                    }else{
                        Estado=200;
                        ERRC=new Error(G.ERROR.size(),Character.toString(Caracter),Fila,Columna,";");
                    }
                    break;
                    //error automata
                case 200:
                    G.ERROR.add(ERRC);
                    System.out.println(ERRC.toString());
                    Estado=1;
                    break;
            }
        }
    }
}
