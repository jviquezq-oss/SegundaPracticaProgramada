import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Atributos
    private boolean esHoja;
    private List<Integer> claves;
    private List<NodoArbolBMas> hijos; // Vacía si es una hoja
    // datos (Puede ser simplemente un ArrayList de Strings. Debe ser null si es un nodo interno.)
    // siguiente (Es una instancia de la misma clase. Debe ser null si es un nodo interno.)

    // Métodos
    // Constructor (Debe modificarse para que el nodo sea capaz de almacenar los datos.)
    public NodoArbolBMas(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }

    // Getters
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getClaves() {
        return claves;
    }

    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    // Setters
    public void setClaves(List<Integer> claves) {
        this.claves = claves;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }
}
