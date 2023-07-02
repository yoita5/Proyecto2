package Castillo;
class Nodo {
    int valor;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(int valor) {
        this.valor = valor;
        izquierda = null;
        derecha = null;
    }
    
    private Nodo añadirNodo(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor);
        }

        if (valor < actual.valor) {
            actual.izquierda = añadirNodo(actual.izquierda, valor);
        } else if (valor > actual.valor) {
            actual.derecha = añadirNodo(actual.derecha, valor);
        } else {
            return actual;
        }
        return actual;
    }
}