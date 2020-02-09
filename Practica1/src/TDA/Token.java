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
public class Token {
    private int ID;
    private String Lexema;

    public Token(int ID, String Lexema) {
        this.ID = ID;
        this.Lexema = Lexema;
    }

    public Token() {
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

    @Override
    public String toString() {
        return this.ID+" "+this.Lexema;
    }
    
    
}
