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
public class TabSig {
    
    private String Carcter;
    private int Numero;
    private String Siguiente;

    public TabSig() {
        this.Carcter = "";
        this.Numero = 0;
        this.Siguiente = "";
    }

    public String getCarcter() {
        return Carcter;
    }
    public void setCarcter(String Carcter) {
        this.Carcter = Carcter;
    }

    /**
     * @return the Numero
     */
    public int getNumero() {
        return Numero;
    }

    /**
     * @param Numero the Numero to set
     */
    public void setNumero(int Numero) {
        this.Numero = Numero;
    }

    /**
     * @return the Siguiente
     */
    public String getSiguiente() {
        return Siguiente;
    }

    /**
     * @param Siguiente the Siguiente to set
     */
    public void setSiguiente(String Siguiente) {
        this.Siguiente = Siguiente;
    }
    
    
    
    
}
