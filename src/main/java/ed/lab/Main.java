package ed.lab;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        // Prueba del ejercicio 1: Invertir árbol binario
        TreeNode<Integer> root1 = new TreeNode<>(4);
        root1.left = new TreeNode<>(2);
        root1.right = new TreeNode<>(7);
        root1.left.left = new TreeNode<>(1);
        root1.left.right = new TreeNode<>(3);
        root1.right.left = new TreeNode<>(6);
        root1.right.right = new TreeNode<>(9);

        E01InvertBT inverter = new E01InvertBT();
        TreeNode<Integer> inverted = inverter.invertTree(root1);
        System.out.println("Árbol invertido: " + inverted.value); // Debería mostrar la raíz del árbol invertido

        // Prueba del ejercicio 2: k-ésimo elemento más pequeño
        // En el árbol BST anterior: inorden es [1,2,3,4,6,7,9]
        E02KthSmallest kthFinder = new E02KthSmallest();
        int kth = kthFinder.kthSmallest(root1, 3);
        System.out.println("El 3er elemento más pequeño es: " + kth); // Debería mostrar 3

        // Prueba del ejercicio 3: Árbol AVL
        E03AVLTree<Integer> avlTree = new E03AVLTree<>(Integer::compare);
        System.out.println("Búsqueda en árbol AVL vacío: " + avlTree.search(5)); // null
        avlTree.insert(5);
        avlTree.insert(3);
        avlTree.insert(1);
        System.out.println("Búsqueda del 5 en AVL: " + avlTree.search(5)); // 5
        System.out.println("Búsqueda del 1 en AVL: " + avlTree.search(1)); // 1
        System.out.println("Tamaño del AVL: " + avlTree.size()); // 3
        System.out.println("Altura del AVL: " + avlTree.height()); // Altura según balanceo
        avlTree.delete(3);
        System.out.println("Búsqueda del 3 tras borrarlo: " + avlTree.search(3)); // null
        avlTree.insert(4);
        System.out.println("Búsqueda del 4 tras insertarlo: " + avlTree.search(4)); // 4
    }
}
