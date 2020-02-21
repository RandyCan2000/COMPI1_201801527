/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import Globales.*;
import TDA.NodoArbol;
import TDA.TabSig;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Usuario
 */
public class METODOS {
    Globales G=new Globales();
    String Lexema="";
    NodoArbol Inicio=null;
    int contadorArbol=0;
    int NumArreglo=0;
    int n=0;
    int Identificador=0;
    int ContadorSig=0;
    String TabInfoNodo="";
    public String AbrirArchivos(){
        String Texto="";
        JFileChooser explorador = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("ARCHIVOS EXP. REG.", "er");
        explorador.setFileFilter(filtro);
        //Le cambiamos el titulo
        explorador.setDialogTitle("Abrir documento...");
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int seleccion = explorador.showDialog(null, "Abrir!");
        switch(seleccion) {
            case JFileChooser.APPROVE_OPTION:
                System.out.println("SELECCIONO ABRIR");
            break;

            case JFileChooser.CANCEL_OPTION:
             //dio click en cancelar o cerro la ventana
                System.err.println("SELECCIONO CERRAR");
            break;

            case JFileChooser.ERROR_OPTION:
                System.out.println("OCURRIO ERROR");
            break;
        }
    try{
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = explorador.getSelectedFile();
            //y guardar una ruta
            String ruta = archivo.getPath();
            G.Ruta=archivo.getPath();
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
            Texto+=linea+"\n";
         }
            fr.close();
        }
    catch(Exception e){}
    return Texto;
    }
    
    
    public DefaultTableModel cargarErrores(){
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("LEXEMA");
        model.addColumn("ESPERADO");
        model.addColumn("FILA");
        model.addColumn("COLUMNA");
        for(int i=0;i<G.ERROR.size();i++){
            model.addRow(new Object[]{G.ERROR.get(i).getID(),G.ERROR.get(i).getLexema(),G.ERROR.get(i).getEsperado(),G.ERROR.get(i).getFila(),G.ERROR.get(i).getColumna()});
        }
        return model;
    }
    public DefaultTableModel cargarTokens(){
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("LEXEMA");
        for(int i=0;i<G.TOKEN.size();i++){
            model.addRow(new Object[]{G.TOKEN.get(i).getID(),G.TOKEN.get(i).getLexema()});
        }
        return model;
    }
    
    public void GuardarArchivo(String Texto){
        try {
            PrintWriter pw = null;
            FileWriter Fw=new FileWriter (G.Ruta);
            pw = new PrintWriter(Fw);
            String[] Text=Texto.split("\n");
            for(String Linea: Text){
                pw.println(Linea);
            }
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(METODOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void GuardarComo(String Texto){
        JFileChooser explorador = new JFileChooser();
        String ruta = "";
        try{
        if(explorador.showSaveDialog(null)==explorador.APPROVE_OPTION){
            ruta = explorador.getSelectedFile().getAbsolutePath();
            PrintWriter pw = null;
            FileWriter Fw=new FileWriter (ruta);
            pw = new PrintWriter(Fw);
            String[] Text=Texto.split("\n");
            for(String Linea: Text){
                pw.print(Linea);
            }
            G.Ruta=ruta;
            pw.close();
        }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void CrearArbol(){
        contadorArbol=0;
        Inicio=null;
        for(int i=0;i<G.TOKEN.size();i++){
            if(G.TOKEN.get(i).getLexema().equals("CONJ")){
                for(int index=i;i<G.TOKEN.size();index++){
                    if(G.TOKEN.get(index).getLexema().equals(";")){i=index;break;}
                }
            }
            else if(G.TOKEN.get(i).getLexema().equals("->")){
                Lexema=G.TOKEN.get(i+1).getLexema();
                SepararExpReg(G.TOKEN.get(i+1).getLexema());
                i++;
            }
            else if(G.TOKEN.get(i).getLexema().equals("%%")){break;}
        }
    }
    
    Stack Pos=new Stack();
    Stack<String> EXPSeparada=null;
    public void SepararExpReg(String EXP){
        Stack<String> EXPSeparada2=new Stack<String>();
        EXPSeparada=new Stack<String>();
        String Pal="";
        for(int i=0;i<EXP.length();i++){
            if(EXP.charAt(i)=='.'||EXP.charAt(i)=='|'||EXP.charAt(i)=='*'||EXP.charAt(i)=='+'||EXP.charAt(i)=='?')
            {EXPSeparada2.add(Character.toString(EXP.charAt(i)));}
            else if(EXP.charAt(i)=='"'){
                Pal="\"";
                for(int b=i+1;b<EXP.length();b++){if(EXP.charAt(b)=='"'){Pal+=EXP.charAt(b);EXPSeparada2.add(Pal);i=b;break;}else{Pal+=EXP.charAt(b);}}
                Pal="";
            }else if(EXP.charAt(i)=='{'){
                for(int b=i;b<EXP.length();b++){if(EXP.charAt(b)=='}'){Pal+=EXP.charAt(b);EXPSeparada2.push(Pal);i=b;break;}else{Pal+=EXP.charAt(b);}}
                Pal="";
            }
        }
        while(!EXPSeparada2.empty()){
            EXPSeparada.push(EXPSeparada2.pop());
        }
        System.out.println("EXP SEPARADA");
        Inicio=null;
        Arbol();
        Identificador=0;
        TabInfoNodo="";
        RecorrerArbol();
        n=0;
        ArbG="";
        CrearImagenArbol();
        TablaInfoNodo();
        OrdenarTablaSiguientes();
        contadorArbol++;
    }
    
    public void Arbol(){
        System.out.println("INICIO DEL METODO");
        Inicio=null;
        Lexema=EXPSeparada.pop();
        NodoArbol Nodo=new NodoArbol();
        Nodo.setInfo(Lexema);
        System.out.println(Nodo.getInfo());
        Inicio=Nodo;
        if(Nodo.getInfo().equals(".")){
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(Nodo.getInfo().equals("|")){
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(Nodo.getInfo().equals("+")){
            AgregarIzquierda(Nodo);
        }else if(Nodo.getInfo().equals("*")){
            AgregarIzquierda(Nodo);
        }else if(Nodo.getInfo().equals("?")){
            AgregarIzquierda(Nodo);
        }
        System.out.println("FIN DEL METODO");
    }
    
    public void AgregarDerecha(NodoArbol Nodo){
        Lexema=EXPSeparada.pop();
        NodoArbol NuevoNodo=new NodoArbol();
        NuevoNodo.setInfo(Lexema);
        Nodo.setNodoDerecha(NuevoNodo);
        if(NuevoNodo.getInfo().equals(".")){
            AgregarIzquierda(NuevoNodo);AgregarDerecha(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("|")){
            AgregarIzquierda(NuevoNodo);AgregarDerecha(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("+")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("*")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("?")){
            AgregarIzquierda(NuevoNodo);
        }
        System.out.println("agregoDerecha"+NuevoNodo.getInfo());
    }
    public void AgregarIzquierda(NodoArbol Nodo){
        Lexema=EXPSeparada.pop();
        NodoArbol NuevoNodo=new NodoArbol();
        NuevoNodo.setInfo(Lexema);
        Nodo.setNodoIzquierda(NuevoNodo);
        if(NuevoNodo.getInfo().equals(".")){
            AgregarIzquierda(NuevoNodo);AgregarDerecha(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("|")){
            AgregarIzquierda(NuevoNodo);AgregarDerecha(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("+")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("*")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("?")){
            AgregarIzquierda(NuevoNodo);
        }
        System.out.println("agregoIzquierda"+NuevoNodo.getInfo());
    }
    String[] arbol;
    
    public void RecorrerArbol(){
        CalculoAnulables_E_Identificador(Inicio);
        Primeros_Ultimos(Inicio);
        G.TABS=new TabSig[1000];
        for(int i=0;i<G.TABS.length;i++){G.TABS[i]=new TabSig();}
        NumArreglo=0;
        Siguientes(Inicio);
        LlenarCaracterEnTabla(Inicio);
        System.out.println(Inicio.getInfo()+"~"+Inicio.getIdentificador()+"~"+Inicio.isAnulable()+"~"+Inicio.getPrimeros()+"~"+Inicio.getUltimos());
        TabInfoNodo=TabInfoNodo+Inicio.getInfo()+"~"+Inicio.getIdentificador()+"~"+Inicio.isAnulable()+"~"+Inicio.getPrimeros()+"~"+Inicio.getUltimos()+"\n";
        MostrarArbol(Inicio);
        for(int i=0;i<NumArreglo;i++){
        System.out.println(G.TABS[i].getCarcter()+"~"+G.TABS[i].getNumero()+"~"+G.TABS[i].getSiguiente());
        }
    }
    
    
    
    
    
    public void CalculoAnulables_E_Identificador(NodoArbol Nodo){
        if(Nodo.getInfo().equals("*")||Nodo.getInfo().equals("?")){Nodo.setAnulable(true);}
        else if(Nodo.getNodoDerecha()==null && Nodo.getNodoIzquierda()==null){
            Identificador++;
            Nodo.setIdentificador(Identificador);
        }
        if(Nodo.getNodoIzquierda()!=null){
            CalculoAnulables_E_Identificador(Nodo.getNodoIzquierda());
        }
        if(Nodo.getNodoDerecha()!=null){
            CalculoAnulables_E_Identificador(Nodo.getNodoDerecha());
        }
        if(Nodo.getInfo().equals("|")&&Nodo.getNodoDerecha()!=null && Nodo.getNodoIzquierda()!=null){
                if(Nodo.getNodoIzquierda().isAnulable()==true || Nodo.getNodoDerecha().isAnulable()==true){
                    Nodo.setAnulable(true);}
            }else if(Nodo.getInfo().equals(".") && Nodo.getNodoDerecha()!=null && Nodo.getNodoIzquierda()!=null&&Nodo.getNodoIzquierda().isAnulable()==true && Nodo.getNodoDerecha().isAnulable()==true){
                        Nodo.setAnulable(true);
            }else if(Nodo.getInfo().equals("+") && Nodo.getNodoIzquierda()!=null&&Nodo.getNodoIzquierda().isAnulable()==true){
                Nodo.setAnulable(true);
        }
    }
    
    public void Primeros_Ultimos(NodoArbol Nodo){
        if(Nodo.getNodoIzquierda()!=null){
            Primeros_Ultimos(Nodo.getNodoIzquierda());
        }
        if(Nodo.getNodoDerecha()!=null){
            Primeros_Ultimos(Nodo.getNodoDerecha());
        }
        if(Nodo.getInfo().equals("|")&&Nodo.getNodoIzquierda()!=null && Nodo.getNodoDerecha()!=null){
            Nodo.setPrimeros(Nodo.getNodoIzquierda().getPrimeros()+","+Nodo.getNodoDerecha().getPrimeros());
            Nodo.setUltimos(Nodo.getNodoIzquierda().getUltimos()+","+Nodo.getNodoDerecha().getUltimos());
        }else if(Nodo.getInfo().equals(".")&&Nodo.getNodoIzquierda()!=null && Nodo.getNodoDerecha()!=null){
            if(Nodo.getNodoIzquierda().isAnulable()==true){
                Nodo.setPrimeros(Nodo.getNodoIzquierda().getPrimeros()+","+Nodo.getNodoDerecha().getPrimeros());
            }else{Nodo.setPrimeros(Nodo.getNodoIzquierda().getPrimeros());}
            if(Nodo.getNodoDerecha().isAnulable()==true){
                Nodo.setUltimos(Nodo.getNodoIzquierda().getUltimos()+","+Nodo.getNodoDerecha().getUltimos());
            }else{Nodo.setUltimos(Nodo.getNodoDerecha().getUltimos());}
        }else if(Nodo.getInfo().equals("*")||Nodo.getInfo().equals("+")||Nodo.getInfo().equals("?")){
            if(Nodo.getNodoIzquierda()!=null){
                Nodo.setPrimeros(Nodo.getNodoIzquierda().getPrimeros());
                Nodo.setUltimos(Nodo.getNodoIzquierda().getUltimos());
            }
        }else{
            Nodo.setPrimeros(Integer.toString(Nodo.getIdentificador()));
            Nodo.setUltimos(Integer.toString(Nodo.getIdentificador()));
        }
    }
    
    void Siguientes(NodoArbol Nodo){
        if(Nodo.getNodoIzquierda()!=null){
            Siguientes(Nodo.getNodoIzquierda());
        }
        if(Nodo.getNodoDerecha()!=null){
            Siguientes(Nodo.getNodoDerecha());
        }
        if(Nodo.getInfo().equals(".") && Nodo.getNodoIzquierda()!=null && Nodo.getNodoDerecha()!=null){
            String[] NUM=Nodo.getNodoIzquierda().getUltimos().split(",");
            for(String num:NUM){
                G.TABS[NumArreglo].setNumero(Integer.parseInt(num));
                G.TABS[NumArreglo].setSiguiente(Nodo.getNodoDerecha().getPrimeros());
                NumArreglo++;
            }
        }else if(Nodo.getInfo().equals("*") && Nodo.getNodoIzquierda()!=null){
            String[] NUM=Nodo.getNodoIzquierda().getUltimos().split(",");
            for(String num:NUM){
                G.TABS[NumArreglo].setNumero(Integer.parseInt(num));
                G.TABS[NumArreglo].setSiguiente(Nodo.getNodoIzquierda().getPrimeros());
                NumArreglo++;
            }
        }else if(Nodo.getInfo().equals("+") && Nodo.getNodoIzquierda()!=null){
            String[] NUM=Nodo.getNodoIzquierda().getUltimos().split(",");
            for(String num:NUM){
                G.TABS[NumArreglo].setNumero(Integer.parseInt(num));
                G.TABS[NumArreglo].setSiguiente(Nodo.getNodoIzquierda().getPrimeros());
                NumArreglo++;
            }
        }
    }
    
    void LlenarCaracterEnTabla(NodoArbol Nodo){
        if(Nodo.getNodoIzquierda()!=null){
            LlenarCaracterEnTabla(Nodo.getNodoIzquierda());
        }
        if(Nodo.getNodoDerecha()!=null){
            LlenarCaracterEnTabla(Nodo.getNodoDerecha());
        }
        if(Nodo.getIdentificador()!=0){
            for(int i=0;i<NumArreglo;i++){
                if(Nodo.getIdentificador()==G.TABS[i].getNumero()){
                    G.TABS[i].setCarcter(Nodo.getInfo());
                }
            }
        }
    }
    
    
    public void TablaInfoNodo(){
        try{
        PrintWriter pw = null;
        FileWriter Fw=new FileWriter ("C:\\Users\\Usuario\\Desktop\\TabArbol"+contadorArbol+".dot");
        pw = new PrintWriter(Fw);
        pw.println("digraph G{");
        pw.println("node [shape=plaintext]");
        pw.println("a [label=<<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n" +
                    "  <tr>\n" +
                    "    <td><b>Nodo</b></td>\n" +
                    "    <td><b>Identificador</b></td>\n" +
                    "    <td><b>Anulable</b></td>\n" +
                    "    <td><b>Primeros</b></td>\n" +
                    "    <td><b>Ultimos</b></td>\n" +
                    "  </tr>");
        String[] Fila=TabInfoNodo.split("\n");
        boolean pr=false;
        for(String f:Fila){
            pw.println("<tr>");
            String[] Info=f.split("~");
            for(String i:Info){
                for(int q=0;q<i.length();q++){
                    if(i.charAt(q)=='>'||i.charAt(q)=='<'){pr=true;break;}
                }
                if(pr==false){pw.println("<td>"+i+"</td>");}
                else{pw.println("<td>"+"desigualdad"+"</td>");}
                pr=false;
                
            }
            pw.println("</tr>");
        }
        
        
        pw.println("</table>>];\n" +
                    "}");
        pw.close();
                String[] cmd = new String[5];
                cmd[0] = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
                cmd[1] = "-Tpng";
                cmd[2] = "C:\\Users\\Usuario\\Desktop\\TabArbol"+contadorArbol+".dot";
                cmd[3] = "-o";
                cmd[4] = "C:\\Users\\Usuario\\Desktop\\RC\\Practica1\\src\\Archivos\\Informacion\\TabArbol"+contadorArbol+".png";   
                Runtime rt = Runtime.getRuntime();
                rt.exec( cmd ); 
        
        }catch (IOException ex) {
            Logger.getLogger(METODOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    String ArbG="";
    void CrearImagenArbol(){
        try{
            PrintWriter pw = null;
            FileWriter Fw=new FileWriter ("C:\\Users\\Usuario\\Desktop\\Arbol"+contadorArbol+".dot");
            pw = new PrintWriter(Fw);
            pw.println("digraph G{");
            pw.println("N0 [label=\""+Inicio.getInfo()+"\"];");
            String NP="N"+n;
            n++;
            RecArbol2(Inicio,NP);
            pw.println(ArbG);
            pw.println("}");
            pw.close();
            
            try {
                String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
                String fileInputPath = "C:\\Users\\Usuario\\Desktop\\Arbol"+contadorArbol+".dot";
                String fileOutputPath = "C:\\Users\\Usuario\\Desktop\\RC\\Practica1\\src\\Archivos\\Arboles\\Arbol"+contadorArbol+".png";
                String tParam = "-Tpng";
                String tOParam = "-o";
                String[] cmd = new String[5];
                cmd[0] = dotPath;
                cmd[1] = tParam;
                cmd[2] = fileInputPath;
                cmd[3] = tOParam;
                cmd[4] = fileOutputPath;   
                Runtime rt = Runtime.getRuntime();
                rt.exec( cmd ); 
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
    }
        }catch (IOException ex) {
            Logger.getLogger(METODOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void RecArbol2(NodoArbol Nodo,String NomPadre){
        if(Nodo.getNodoIzquierda()!=null){
            String pal="";
            if(Nodo.getNodoIzquierda().getInfo().charAt(0)=='\"'){
                for(int i=1;i<Nodo.getNodoIzquierda().getInfo().length()-1;i++){
                    pal+=Nodo.getNodoIzquierda().getInfo().charAt(i);
                }
            }else{pal=Nodo.getNodoIzquierda().getInfo();}
            ArbG=ArbG+"N"+n+" [label=\""+pal+"\"];\n";
            ArbG=ArbG+NomPadre+"->"+"N"+n+";\n";
            String NP="N"+n;
            n++;
            RecArbol2(Nodo.getNodoIzquierda(),NP);
        }
        if(Nodo.getNodoDerecha()!=null){
            String pal="";
            if(Nodo.getNodoDerecha().getInfo().charAt(0)=='\"'){
                for(int i=1;i<Nodo.getNodoDerecha().getInfo().length()-1;i++){
                    pal+=Nodo.getNodoDerecha().getInfo().charAt(i);
                }
            }else{pal=Nodo.getNodoDerecha().getInfo();}
            ArbG=ArbG+"N"+n+" [label=\""+pal+"\"];\n";
            ArbG=ArbG+NomPadre+"->"+"N"+n+";\n";
            String NP="N"+n;
            n++;
            RecArbol2(Nodo.getNodoDerecha(),NP);
        }
    }
    
    
    void MostrarArbol(NodoArbol Nodo){
        if(Nodo.getNodoIzquierda()!=null){
            TabInfoNodo=TabInfoNodo+Nodo.getNodoIzquierda().getInfo()+"~"+Nodo.getNodoIzquierda().getIdentificador()+"~"+Nodo.getNodoIzquierda().isAnulable()+"~"+Nodo.getNodoIzquierda().getPrimeros()+"~"+Nodo.getNodoIzquierda().getUltimos()+"\n";
            System.out.println(Nodo.getNodoIzquierda().getInfo()+"~"+Nodo.getNodoIzquierda().getIdentificador()+"~"+Nodo.getNodoIzquierda().isAnulable()+"~"+Nodo.getNodoIzquierda().getPrimeros()+"~"+Nodo.getNodoIzquierda().getUltimos());
            MostrarArbol(Nodo.getNodoIzquierda());
        }
        if(Nodo.getNodoDerecha()!=null){
            TabInfoNodo=TabInfoNodo+Nodo.getNodoDerecha().getInfo()+"~"+Nodo.getNodoDerecha().getIdentificador()+"~"+Nodo.getNodoDerecha().isAnulable()+"~"+Nodo.getNodoDerecha().getPrimeros()+"~"+Nodo.getNodoDerecha().getUltimos()+"\n";
            System.out.println(Nodo.getNodoDerecha().getInfo()+"~"+Nodo.getNodoDerecha().getIdentificador()+"~"+Nodo.getNodoDerecha().isAnulable()+"~"+Nodo.getNodoDerecha().getPrimeros()+"~"+Nodo.getNodoDerecha().getUltimos());
            MostrarArbol(Nodo.getNodoDerecha());
        }
    }
    
    void OrdenarTablaSiguientes(){
        String Sig="";
        int cont=0;
        String[] A=new String[1000];
        for(int i=0;i<G.TABS.length;i++){
            if(G.TABS[i].getNumero()==0){break;}
            else{
                Sig=G.TABS[i].getNumero()+";";
                for(int j=0;j<G.TABS.length;j++){
                    if(G.TABS[j].getNumero()==0){break;}
                    else{
                        if(G.TABS[j].getNumero()==G.TABS[i].getNumero()){
                            Sig=Sig+","+G.TABS[j].getSiguiente();
                        }
                    }
                }
                A[cont]=Sig+";"+G.TABS[i].getCarcter();
                cont++;
            }
        }
        G.TABS=new TabSig[cont];
        for(int i=0;i<cont;i++){
            String[] N=A[i].split(";");
            G.TABS[i]=new TabSig();
            G.TABS[i].setNumero(Integer.parseInt(N[0]));
            G.TABS[i].setSiguiente(N[1]);
            G.TABS[i].setCarcter(N[2]);
        }
        int[] a=new int[cont];
        for(int i=0;i<cont;i++){a[i]=G.TABS[i].getNumero();}
        Arrays.sort(a);
        TabSig[] T=new TabSig[cont];
        int q=0;
        for(int num:a){
            for(int i=0;i<cont;i++){
                if(num==G.TABS[i].getNumero()){
                    T[q]=new TabSig();
                    T[q].setNumero(num);
                    T[q].setCarcter(G.TABS[i].getCarcter());
                    T[q].setSiguiente(G.TABS[i].getSiguiente());
                    G.TABS[i].setNumero(0);
                    q++;
                }
            }
        }
        for(int i=0;i<cont;i++){
            if(i==0){}
            else{
                if(T[i].getNumero()==T[i-1].getNumero()){T[i].setNumero(0);}
            }
        }
        int CR=0;
        for(int i=0;i<cont;i++){
            if(T[i].getNumero()!=0){
                CR++;
            }
        }
        G.TABS=new TabSig[CR];
        int C=0;
        for(int i=0;i<cont;i++){
            if(T[i].getNumero()!=0){
                G.TABS[C]=new TabSig();
                G.TABS[C].setNumero(T[i].getNumero());
                G.TABS[C].setCarcter(T[i].getCarcter());
                G.TABS[C].setSiguiente(T[i].getSiguiente());
                C++;
            }
        }
        for(int i=0;i<C;i++){
            System.out.println(G.TABS[i].getCarcter()+" "+G.TABS[i].getNumero()+" "+G.TABS[i].getSiguiente());
        }
        CrearTablaTransiciones();
    }
    
    void CrearTablaTransiciones(){
        try{
        PrintWriter pw = null;
        FileWriter Fw=new FileWriter ("C:\\Users\\Usuario\\Desktop\\TabTran"+contadorArbol+".dot");
        pw = new PrintWriter(Fw);
        pw.println("digraph G{");
        pw.println("node [shape=plaintext]");
        pw.println("a [label=<<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">\n");
        //Tabla de transiciones
        //se deben de omitir las desigualdades para que funcione
        //falta correccion
        pw.println("<tr>");
        pw.println("<td><b>ESTADOS</b></td>");
        for(TabSig n:G.TABS){
            pw.println("<td><b>"+n.getCarcter()+"</b></td>");
        }
        pw.println("</tr>");
        pw.println("</table>>];\n" +
                    "}");
        pw.close();
                String[] cmd = new String[5];
                cmd[0] = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
                cmd[1] = "-Tpng";
                cmd[2] = "C:\\Users\\Usuario\\Desktop\\TabTran"+contadorArbol+".dot";
                cmd[3] = "-o";
                cmd[4] = "C:\\Users\\Usuario\\Desktop\\RC\\Practica1\\src\\Archivos\\Tabla Transiciones\\TabTran"+contadorArbol+".png";   
                Runtime rt = Runtime.getRuntime();
                rt.exec( cmd ); 
        
        }catch (IOException ex) {
            Logger.getLogger(METODOS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
