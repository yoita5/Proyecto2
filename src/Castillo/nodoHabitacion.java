
package Castillo;
import Castillo.registroHistorial;

public class nodoHabitacion{
    int valor;
    registroHistorial[] historialHab;
    nodoHabitacion izquierda;
    nodoHabitacion derecha;

    public nodoHabitacion(int valor) {
        this.valor = valor;
        izquierda = null;
        derecha = null;
        historialHab = new registroHistorial[0];
    }
    
    static nodoHabitacion nuevoNodo(int item)
    {
        nodoHabitacion temp = new nodoHabitacion(item);
        temp.izquierda = temp.derecha = null;
        return temp;
    }
    
    static nodoHabitacion insertar(nodoHabitacion nodo, int valor)
    {
        // If the tree is empty, return a new node
        if (nodo == null)
            return nuevoNodo(valor);
  
        // Otherwise, recur down the tree
        if (valor < nodo.valor) {
            nodo.izquierda = insertar(nodo.izquierda, valor);
        }
        else if (valor > nodo.valor) {
            nodo.derecha = insertar(nodo.derecha, valor);
        }
  
        // Return the node
        return nodo;
    }
    
    nodoHabitacion buscar(nodoHabitacion root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.valor == key)
            return root;
 
        // Key is greater than root's key
        if (root.valor < key)
            return buscar(root.derecha, key);
 
        // Key is smaller than root's key
        return buscar(root.izquierda, key);
    }
    
    nodoHabitacion busqueda(nodoHabitacion root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.valor == key)
            return root;
 
        // Key is greater than root's key
        if (root.valor < key)
            return busqueda(root.derecha, key);
 
        // Key is smaller than root's key
        return busqueda(root.izquierda, key);
    }
 
}
