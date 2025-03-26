package ed.lab;

public class E02KthSmallest {

    // Variables de control para el recorrido inorden
    private int count;
    private int result;

    public int kthSmallest(TreeNode<Integer> root, int k) {
        count = 0;
        result = -1;  // Suponiendo que -1 no es un valor válido en el árbol (o se puede tratar de otro modo)
        inOrder(root, k);
        return result;
    }

    private void inOrder(TreeNode<Integer> node, int k) {
        if (node == null) {
            return;
        }
        inOrder(node.left, k);
        count++;
        if (count == k) {
            result = node.value;
            return;
        }
        inOrder(node.right, k);
    }
}
