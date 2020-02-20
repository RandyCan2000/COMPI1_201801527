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
        private String Primeros;
        private String Ultimos;
        private NodoArbol NodoDerecha;
        private NodoArbol NodoIzquierda;
    public NodoArbol() {
        this.Info = null;
        this.Identificador = 0;
        this.Anulable = false;
        this.Primeros = "";
        this.Ultimos = "";
        this.NodoDerecha = null;
        this.NodoIzquierda = null;
    }
    public String getInfo() {return Info;}
    public void setInfo(String Info) {this.Info = Info;}
    public int getIdentificador() {return Identificador;}
    public void setIdentificador(int Identificador) {this.Identificador = Identificador;}
    public boolean isAnulable() {return Anulable;}
    public void setAnulable(boolean Anulable) {this.Anulable = Anulable;}
    public String getPrimeros() {return Primeros;}
    public void setPrimeros(String Primeros) {this.Primeros = Primeros;}
    public String getUltimos() {return Ultimos;}
    public void setUltimos(String Ultimos) {this.Ultimos = Ultimos;}
    public NodoArbol getNodoDerecha() {return NodoDerecha;}
    public void setNodoDerecha(NodoArbol NodoDerecha) {this.NodoDerecha = NodoDerecha;}
    public NodoArbol getNodoIzquierda() {return NodoIzquierda;}
    public void setNodoIzquierda(NodoArbol NodoIzquierda) {this.NodoIzquierda = NodoIzquierda;}
   
}
