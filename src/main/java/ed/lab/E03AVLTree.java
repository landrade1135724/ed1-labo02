package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {

    // Clase interna para representar los nodos del árbol AVL.
    private class Node {
        T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;  // La altura de un nodo nuevo es 1.
        }
    }

    private Node root;
    private final Comparator<T> comparator;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // Retorna la altura de un nodo (0 si es nulo)
    private int nodeHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    // Actualiza la altura del nodo actual basándose en las alturas de sus hijos.
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    // Calcula el factor de balance de un nodo.
    private int getBalance(Node node) {
        return node == null ? 0 : nodeHeight(node.left) - nodeHeight(node.right);
    }

    // Rotación derecha para reequilibrar el subárbol.
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realizar rotación
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Rotación izquierda para reequilibrar el subárbol.
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realizar rotación
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Inserción recursiva del valor en el árbol AVL.
    private Node insert(Node node, T value) {
        if (node == null) {
            return new Node(value);
        }
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            // Si el valor es igual, se ignora la inserción (no se permiten duplicados)
            return node;
        }

        // Actualizar la altura del nodo actual
        updateHeight(node);

        // Obtener el factor de balance para comprobar si el nodo se desbalanceó.
        int balance = getBalance(node);

        // Caso Izquierda Izquierda
        if (balance > 1 && comparator.compare(value, node.left.value) < 0) {
            return rightRotate(node);
        }
        // Caso Derecha Derecha
        if (balance < -1 && comparator.compare(value, node.right.value) > 0) {
            return leftRotate(node);
        }
        // Caso Izquierda Derecha
        if (balance > 1 && comparator.compare(value, node.left.value) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso Derecha Izquierda
        if (balance < -1 && comparator.compare(value, node.right.value) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    // Busca el nodo con el valor mínimo en el subárbol.
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Eliminación recursiva de un valor en el árbol AVL.
    private Node delete(Node node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            // Nodo encontrado
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                node = (temp == null) ? null : temp;
            } else {
                // Nodo con dos hijos: obtener el sucesor (mínimo del subárbol derecho)
                Node temp = minValueNode(node.right);
                node.value = temp.value;
                node.right = delete(node.right, temp.value);
            }
        }

        // Si el árbol tenía un solo nodo o se eliminó el nodo
        if (node == null) {
            return null;
        }

        // Actualizar la altura del nodo actual
        updateHeight(node);

        // Balancear el nodo si es necesario
        int balance = getBalance(node);

        // Caso Izquierda Izquierda
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        // Caso Izquierda Derecha
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Caso Derecha Derecha
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        // Caso Derecha Izquierda
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    // Búsqueda recursiva de un valor en el árbol AVL.
    private Node search(Node node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = comparator.compare(value, node.value);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return search(node.left, value);
        } else {
            return search(node.right, value);
        }
    }

    public T search(T value) {
        Node node = search(root, value);
        return node != null ? node.value : null;
    }

    // Retorna la altura del árbol (altura de la raíz).
    public int height() {
        return nodeHeight(root);
    }

    // Calcula recursivamente la cantidad de nodos en el árbol.
    private int size(Node node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    public int size() {
        return size(root);
    }

    // Representación del árbol en recorrido inorden.
    @Override
    public String toString() {
        // Si el árbol está vacío, retorna la cadena vacía.
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        return sb.toString();
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inOrder(node.left, sb);
        if (sb.length() > 0) {
            sb.append(" ");
        }
        sb.append(node.value.toString());
        inOrder(node.right, sb);
    }
}
