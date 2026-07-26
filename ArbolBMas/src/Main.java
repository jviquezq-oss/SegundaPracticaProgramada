import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ArbolBMas arbol = new ArbolBMas(4);
        Menu menu = new Menu(arbol);

        try {
            menu.iniciar();
        } catch (IOException e) {
            System.out.println("Error al leer los datos.");
        }
    }
}