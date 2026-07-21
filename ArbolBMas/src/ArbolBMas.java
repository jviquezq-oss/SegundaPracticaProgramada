import java.util.ArrayList;

public class ArbolBMas {

    // Atributos
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos
    // Constructor
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave en el árbol B+
    public void insertar(int clave) {
        // Se inserta primero de forma recursiva
        insertarRecursivo(this.raiz, clave);

        // Se verifica si la raíz se desbordó
        if(this.raiz.getClaves().size() == orden) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
    }

    // Inserción recursiva de una llave
    // Permite que los nodos se desborden temporalmente y los divide al regresar de la recursión
    private void insertarRecursivo(NodoArbolBMas nodo, int llave) {
        int i = nodo.getClaves().size() - 1;

        if(nodo.esHoja()) {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            nodo.getClaves().add(i + 1, llave);
        } else {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);

            // Llamada recursiva para insertar la llave primero en el hijo correspondiente
            insertarRecursivo(hijo, llave);

            // Evaluación de desbordamiento al retornar de la recursividad
            if(hijo.getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // El padre recibe como separador la primera llave del nuevo nodo
            int llaveMedia = nuevoNodo.getClaves().getFirst();
            padre.getClaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo
            int claveMedia = nodoLleno.getClaves().get(mitad);
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja > " : "Interno > ") + nodo.getClaves());
        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Búsqueda de un nodo como parte de la interfaz pública del árbol
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva privada de un nodo a partir de uno inicial
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;
        while(i < nodo.getClaves().size() && llave > nodo.getClaves().get(i)) {
            i++;
        }
        if(i < nodo.getClaves().size() && llave == nodo.getClaves().get(i)) {
            return true;
        }
        if(nodo.esHoja()) {
            return false;
        } else {
            return buscarNodo(nodo.getHijos().get(i), llave);
        }
    }

    // Eliminación

    // Recorrido por rango (Debe mostrar los datos correspondientes al rango recuperado.)
}