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
public class NodoArbol {
        private String Info;
        private int Identificador;
        private boolean Anulable;
        private int[] Primeros;
        private int[] Ultimos;
        private NodoArbol NodoDerecha=null;
        private NodoArbol NodoIzquierda=null;
        private NodoArbol Unico=null;

    public NodoArbol(String Info, int Identificador, boolean Anulable, int[] Primeros, int[] Ultimos) {
        this.Info = Info;
        this.Identificador = Identificador;
        this.Anulable = Anulable;
        this.Primeros = Primeros;
        this.Ultimos = Ultimos;
    }

    public String getInfo() {return Info;}
    public void setInfo(String Info) {this.Info = Info;}
    public int getIdentificador() {return Identificador;}
    public void setIdentificador(int Identificador) {this.Identificador = Identificador;}
    public boolean isAnulable() {return Anulable;}
    public void setAnulable(boolean Anulable) {this.Anulable = Anulable;}
    public int[] getPrimeros() {return Primeros;}
    public void setPrimeros(int[] Primeros) {this.Primeros = Primeros;}
    public int[] getUltimos() {return Ultimos;}
    public void setUltimos(int[] Ultimos) {this.Ultimos = Ultimos;}
    public NodoArbol getNodoDerecha() {return NodoDerecha;}
    public void setNodoDerecha(NodoArbol NodoDerecha) {this.NodoDerecha = NodoDerecha;}
    public NodoArbol getNodoIzquierda() {return NodoIzquierda;}
    public void setNodoIzquierda(NodoArbol NodoIzquierda) {this.NodoIzquierda = NodoIzquierda;}
    public NodoArbol getUnico() {return Unico;}
    public void setUnico(NodoArbol Unico) {this.Unico = Unico;}

    
        
    
    
    
    
    
}
