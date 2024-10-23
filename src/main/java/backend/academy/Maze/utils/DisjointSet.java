package backend.academy.Maze.utils;

/**
 * Класс DisjointSet реализует структуру данных для объединения и поиска. Используется для отслеживания, к каким
 * компонентам принадлежат клетки лабиринта, и для предотвращения создания циклов в алгоритме Краскала.
 */

public class DisjointSet {
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];

        // Инициализация: каждый элемент является корнем самого себя
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int element) {
        if (parent[element] != element) {
            parent[element] = find(parent[element]);
        }
        return parent[element];
    }

    public void union(int element1, int element2) {
        int root1 = find(element1);
        int root2 = find(element2);

        // Объединяем множества только если они разные
        if (root1 != root2) {
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                rank[root1]++; // Увеличиваем ранг, если деревья одинаковой высоты
            }
        }
    }
}
