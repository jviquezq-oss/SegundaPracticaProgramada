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
    public void insertar(int clave, String dato) {

        insertarRecursivo(this.raiz, clave, dato);

        if (raiz.getClaves().size() == orden) {

            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);

            nuevaRaiz.getHijos().add(raiz);

            dividirHijo(nuevaRaiz, 0);

            raiz = nuevaRaiz;
        }
    }

    // Inserción recursiva de una llave
    // Permite que los nodos se desborden temporalmente y los divide al regresar de la recursión
    private void insertarRecursivo(NodoArbolBMas nodo, int clave, String dato) {
        int i = nodo.getClaves().size() - 1;
        if (nodo.esHoja()) {
            while (i >= 0 && clave < nodo.getClaves().get(i)) {
                i--;
            }
            int posicion = i + 1;
            nodo.getClaves().add(posicion, clave);
            nodo.getDatos().add(posicion, dato);
        } else {
            while (i >= 0 && clave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            insertarRecursivo(nodo.getHijos().get(i), clave, dato);
            if (nodo.getHijos().get(i).getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        int mitad = orden / 2;
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());

        if (nodoLleno.esHoja()) {
            //divide las claves entre las dos hojas
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            //divide los datos manteniendo la misma posicion que sus claves
            nuevoNodo.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(mitad, nodoLleno.getDatos().size())));
            nodoLleno.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(0, mitad)));

            //enlaza la nueva hoja con las demas hojas del arbol
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());
            nodoLleno.setSiguiente(nuevoNodo);

            //la primera clave de la nueva hoja se copia al padre
            int claveMedia = nuevoNodo.getClaves().get(0);
            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        } else {
            //en nodos internos la clave media sube al padre
            int claveMedia = nodoLleno.getClaves().get(mitad);

            //divide las claves del nodo interno
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            //redistribuye las referencias a los hijos
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
    public boolean buscar(int clave) {
        NodoArbolBMas hoja = buscarHoja(clave);
        return hoja.getClaves().contains(clave);
    }

    public String buscarDato(int clave) {
        NodoArbolBMas hoja = buscarHoja(clave);
        int posicion = hoja.getClaves().indexOf(clave);

        if (posicion == -1) {
            return null;
        }

        return hoja.getDatos().get(posicion);
    }
    // Búsqueda recursiva privada de un nodo a partir de uno inicial
    /*private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
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
    }*/

    private NodoArbolBMas buscarHoja(int clave) {
        NodoArbolBMas actual = raiz;

        while (!actual.esHoja()) {
            int i = 0;

            while (i < actual.getClaves().size() && clave >= actual.getClaves().get(i)) {
                i++;
            }

            actual = actual.getHijos().get(i);
        }

        return actual;
    }

    // Eliminación
    public void eliminar(int clave) {
        boolean eliminado = eliminarRecursivo(raiz, clave);

        if (!eliminado) {
            return;
        }

        if (!raiz.esHoja() && raiz.getClaves().isEmpty()) {
            raiz = raiz.getHijos().get(0);
        }
    }

    private boolean eliminarRecursivo(NodoArbolBMas nodo, int clave) {
        if (nodo.esHoja()) {
            int posicion = nodo.getClaves().indexOf(clave);
            if (posicion == -1) {
                return false;
            }
            nodo.getClaves().remove(posicion);
            nodo.getDatos().remove(posicion);
            return true;
        }

        int i = 0;
        while (i < nodo.getClaves().size() && clave >= nodo.getClaves().get(i)) {
            i++;
        }

        boolean eliminado = eliminarRecursivo(nodo.getHijos().get(i), clave);

        if (!eliminado) {
            return false;
        }

        NodoArbolBMas hijo = nodo.getHijos().get(i);

        if (hijo.esHoja()) {
            if (hijo.getClaves().size() < orden / 2) {
                if (!redistribuirHoja(nodo, i)) {
                    fusionarHojas(nodo, i);
                }
            }
        } else {
            if (tieneUnderflowInterno(hijo)) {
                if (!redistribuirInterno(nodo, i)) {
                    fusionarInternos(nodo, i);
                }
            }
        }

        actualizarSeparadores(nodo);
        return true;
    }
    private boolean tieneUnderflowInterno(NodoArbolBMas nodo) {
        return nodo.getHijos().size() < orden / 2;
    }
    private boolean redistribuirHoja(NodoArbolBMas padre, int indice) {
        NodoArbolBMas hijo = padre.getHijos().get(indice);

        if (indice > 0) {
            NodoArbolBMas hermanoIzquierdo = padre.getHijos().get(indice - 1);

            if (hermanoIzquierdo.getClaves().size() > orden / 2) {
                int ultimaPosicion = hermanoIzquierdo.getClaves().size() - 1;

                hijo.getClaves().add(0, hermanoIzquierdo.getClaves().remove(ultimaPosicion));
                hijo.getDatos().add(0, hermanoIzquierdo.getDatos().remove(ultimaPosicion));

                padre.getClaves().set(indice - 1, hijo.getClaves().get(0));
                return true;
            }
        }

        if (indice < padre.getHijos().size() - 1) {
            NodoArbolBMas hermanoDerecho = padre.getHijos().get(indice + 1);

            if (hermanoDerecho.getClaves().size() > orden / 2) {
                hijo.getClaves().add(hermanoDerecho.getClaves().remove(0));
                hijo.getDatos().add(hermanoDerecho.getDatos().remove(0));

                padre.getClaves().set(indice, hermanoDerecho.getClaves().get(0));
                return true;
            }
        }

        return false;
    }
    private void fusionarHojas(NodoArbolBMas padre, int indice) {
        NodoArbolBMas hijo = padre.getHijos().get(indice);

        if (indice > 0) {
            NodoArbolBMas hermanoIzquierdo = padre.getHijos().get(indice - 1);

            hermanoIzquierdo.getClaves().addAll(hijo.getClaves());
            hermanoIzquierdo.getDatos().addAll(hijo.getDatos());
            hermanoIzquierdo.setSiguiente(hijo.getSiguiente());

            padre.getHijos().remove(indice);
            padre.getClaves().remove(indice - 1);
        } else {
            NodoArbolBMas hermanoDerecho = padre.getHijos().get(indice + 1);

            hijo.getClaves().addAll(hermanoDerecho.getClaves());
            hijo.getDatos().addAll(hermanoDerecho.getDatos());
            hijo.setSiguiente(hermanoDerecho.getSiguiente());

            padre.getHijos().remove(indice + 1);
            padre.getClaves().remove(indice);
        }
    }
    private boolean redistribuirInterno(NodoArbolBMas padre, int indice) {
        NodoArbolBMas hijo = padre.getHijos().get(indice);

        if (indice > 0) {
            NodoArbolBMas hermanoIzquierdo = padre.getHijos().get(indice - 1);

            if (hermanoIzquierdo.getHijos().size() > orden / 2) {
                hijo.getClaves().add(0, padre.getClaves().get(indice - 1));

                int ultimaClave = hermanoIzquierdo.getClaves().size() - 1;
                padre.getClaves().set(indice - 1, hermanoIzquierdo.getClaves().remove(ultimaClave));

                int ultimoHijo = hermanoIzquierdo.getHijos().size() - 1;
                hijo.getHijos().add(0, hermanoIzquierdo.getHijos().remove(ultimoHijo));

                return true;
            }
        }

        if (indice < padre.getHijos().size() - 1) {
            NodoArbolBMas hermanoDerecho = padre.getHijos().get(indice + 1);

            if (hermanoDerecho.getHijos().size() > orden / 2) {
                hijo.getClaves().add(padre.getClaves().get(indice));
                padre.getClaves().set(indice, hermanoDerecho.getClaves().remove(0));
                hijo.getHijos().add(hermanoDerecho.getHijos().remove(0));

                return true;
            }
        }

        return false;
    }
    private void fusionarInternos(NodoArbolBMas padre, int indice) {
        NodoArbolBMas hijo = padre.getHijos().get(indice);

        if (indice > 0) {
            NodoArbolBMas hermanoIzquierdo = padre.getHijos().get(indice - 1);

            hermanoIzquierdo.getClaves().add(padre.getClaves().get(indice - 1));
            hermanoIzquierdo.getClaves().addAll(hijo.getClaves());
            hermanoIzquierdo.getHijos().addAll(hijo.getHijos());

            padre.getClaves().remove(indice - 1);
            padre.getHijos().remove(indice);
        } else {
            NodoArbolBMas hermanoDerecho = padre.getHijos().get(indice + 1);

            hijo.getClaves().add(padre.getClaves().get(indice));
            hijo.getClaves().addAll(hermanoDerecho.getClaves());
            hijo.getHijos().addAll(hermanoDerecho.getHijos());

            padre.getClaves().remove(indice);
            padre.getHijos().remove(indice + 1);
        }
    }
    private void actualizarSeparadores(NodoArbolBMas nodo) {
        for (int i = 1; i < nodo.getHijos().size(); i++) {
            NodoArbolBMas actual = nodo.getHijos().get(i);

            while (!actual.esHoja()) {
                actual = actual.getHijos().get(0);
            }

            if (!actual.getClaves().isEmpty()) {
                nodo.getClaves().set(i - 1, actual.getClaves().get(0));
            }
        }
    }
    // Recorrido por rango (Debe mostrar los datos correspondientes al rango recuperado.)
    public void recorrerRango(int clave, int n) {
        if (n <= 0) {
            return;
        }

        NodoArbolBMas actual = buscarHoja(clave);
        int posicion = actual.getClaves().indexOf(clave);

        if (posicion == -1) {
            System.out.println("La clave no existe en el arbol");
            return;
        }

        int recorridos = 0;

        while (actual != null && recorridos < n) {
            while (posicion < actual.getClaves().size() && recorridos < n) {
                System.out.println(actual.getDatos().get(posicion));
                posicion++;
                recorridos++;
            }
            actual = actual.getSiguiente();
            posicion = 0;
        }
    }
}