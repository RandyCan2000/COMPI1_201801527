/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDA;

/**
 *
 * @author Usuario
 */
public class Error {
    private int ID;
    private String Lexema;
    private int fila;
    private int Columna;
    private String Esperado;

    public Error(int ID, String Lexema, int fila, int Columna, String Esperado) {
        this.ID = ID;
        this.Lexema = Lexema;
        this.fila = fila;
        this.Columna = Columna;
        this.Esperado = Esperado;
    }

    

    public Error() {
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the Lexema
     */
    public String getLexema() {
        return Lexema;
    }

    /**
     * @param Lexema the Lexema to set
     */
    public void setLexema(String Lexema) {
        this.Lexema = Lexema;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the Columna
     */
    public int getColumna() {
        return Columna;
    }

    /**
     * @param Columna the Columna to set
     */
    public void setColumna(int Columna) {
        this.Columna = Columna;
    }

    /**
     * @return the Esperado
     */
    public String getEsperado() {
        return Esperado;
    }

    /**
     * @param Esperado the Esperado to set
     */
    public void setEsperado(String Esperado) {
        this.Esperado = Esperado;
    }
    
    
    
    
}
