/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Usuario
 */
public class METODOS {
    public String AbrirArchivos(){
        String Texto="";
        JFileChooser explorador = new JFileChooser("\\DESKTOP\\");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("ARCHIVOS TXT", "TXT");
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
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null)
            Texto+=linea+"\n";
        }
    catch(Exception e){}
    return Texto;
    }
}
