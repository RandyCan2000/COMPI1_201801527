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
        for(int i=0;i<G.TOKEN.size();i++){
            if(G.TOKEN.get(i).getLexema().equals("CONJ")){
                for(int index=i;i<G.TOKEN.size();index++){
                    if(G.TOKEN.get(index).getLexema().equals(";")){i=index;break;}
                }
            }
            else if(G.TOKEN.get(i).getLexema().equals("->")){
                JOptionPane.showMessageDialog(null,G.TOKEN.get(i+1).getLexema());
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
        Arbol();
        RecorrerArbol();
    }
    
    public void Arbol(){
        System.out.println("INICIO DEL METODO");
        Inicio=null;
        Lexema=EXPSeparada.pop();
        NodoArbol Nodo=new NodoArbol();
        Nodo.setInfo(Lexema);
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
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(NuevoNodo.getInfo().equals("|")){
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(NuevoNodo.getInfo().equals("+")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("*")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("?")){
            AgregarIzquierda(NuevoNodo);
        }
        System.out.println("agregoDerecha");
    }
    public void AgregarIzquierda(NodoArbol Nodo){
        Lexema=EXPSeparada.pop();
        NodoArbol NuevoNodo=new NodoArbol();
        NuevoNodo.setInfo(Lexema);
        Nodo.setNodoIzquierda(NuevoNodo);
        if(NuevoNodo.getInfo().equals(".")){
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(NuevoNodo.getInfo().equals("|")){
            AgregarIzquierda(Nodo);AgregarDerecha(Nodo);
        }else if(NuevoNodo.getInfo().equals("+")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("*")){
            AgregarIzquierda(NuevoNodo);
        }else if(NuevoNodo.getInfo().equals("?")){
            AgregarIzquierda(NuevoNodo);
        }
        System.out.println("agregoIzquierda");
    }
    String arbol="";
    public void RecorrerArbol(){
        if(Inicio!=null){
            System.out.println(Inicio.getInfo());
        }
    }
    
    public void LecturaNodoDerecha(NodoArbol  Nodo){
        
    }
    public void LecturaNodoIzquierda(NodoArbol Nodo){
        
    }
    
}
