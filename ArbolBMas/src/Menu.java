import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArbolBMas arbol;

    public Menu(ArbolBMas arbol) {
        this.arbol = arbol;
    }

    public void iniciar() throws IOException {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    insertar();
                    break;
                case 2:
                    buscar();
                    break;
                case 3:
                    eliminar();
                    break;
                case 4:
                    recorrerRango();
                    break;
                case 5:
                    arbol.imprimirArbol();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n===== Menu =====");
        System.out.println("1. Insertar");
        System.out.println("2. Buscar");
        System.out.println("3. Eliminar");
        System.out.println("4. Recorrer rango");
        System.out.println("5. Imprimir arbol");
        System.out.println("0. Salir");
    }

    private void insertar() throws IOException {
        int clave = leerEntero("Ingrese la clave: ");

        if (arbol.buscar(clave)) {
            System.out.println("La clave ya existe en el arbol.");
            return;
        }

        System.out.print("Ingrese el dato: ");
        String dato = br.readLine();

        arbol.insertar(clave, dato);
        System.out.println("Dato insertado correctamente.");
    }

    private void buscar() throws IOException {
        int clave = leerEntero("Ingrese la clave a buscar: ");

        if (!arbol.buscar(clave)) {
            System.out.println("La clave no existe en el arbol.");
            return;
        }

        String dato = arbol.buscarDato(clave);

        System.out.println("Clave encontrada.");
        System.out.println("Clave: " + clave);
        System.out.println("Dato: " + dato);
    }

    private void eliminar() throws IOException {
        int clave = leerEntero("Ingrese la clave a eliminar: ");

        if (!arbol.buscar(clave)) {
            System.out.println("La clave no existe en el arbol.");
            return;
        }

        arbol.eliminar(clave);
        System.out.println("Clave eliminada correctamente.");
    }

    private void recorrerRango() throws IOException {
        int clave = leerEntero("Ingrese la clave inicial: ");
        int cantidad = leerEntero("Ingrese la cantidad de elementos a recorrer: ");

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que 0.");
            return;
        }

        arbol.recorrerRango(clave, cantidad);
    }

    private int leerEntero(String mensaje) throws IOException {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }
}