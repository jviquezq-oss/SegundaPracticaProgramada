import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Indica si el nodo es una hoja
    private boolean esHoja;

    // Claves almacenadas en el nodo
    private List<Integer> claves;

    // Hijos del nodo. En las hojas esta lista permanece vacía
    private List<NodoArbolBMas> hijos;

    // Datos asociados a las claves.
    // Solo los nodos hoja almacenan datos
    private List<String> datos;

    // Referencia a la siguiente hoja del árbol
    private NodoArbolBMas siguiente;


    public NodoArbolBMas(boolean esHoja) {

        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.hijos = new ArrayList<>();

        if (esHoja) {
            this.datos = new ArrayList<>();
        } else {
            this.datos = null;
        }

        this.siguiente = null;
    }


    public boolean esHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }


    public List<Integer> getClaves() {
        return claves;
    }

    public void setClaves(List<Integer> claves) {
        this.claves = claves;
    }


    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }


    public List<String> getDatos() {
        return datos;
    }

    public void setDatos(List<String> datos) {
        this.datos = datos;
    }


    public NodoArbolBMas getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoArbolBMas siguiente) {
        this.siguiente = siguiente;
    }
}