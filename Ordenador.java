package PRACTICA;

public class Ordenador {

    // =========================================================
    // COMPARADOR — maneja orden semántico de prioridad String
    // Alta(3) > Media(2) > Baja(1) > orden natural del resto
    // =========================================================
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> int comparar(
            Paquete<T> a, Paquete<T> b) {

        // Si la prioridad es String, usar orden semántico ✅
        if (a.getPrioridad() instanceof String) {
            return Integer.compare(
                ((Paquete<?>) a).getPrioridadNumerica(),
                ((Paquete<?>) b).getPrioridadNumerica()
            );
        }
        // Para Integer, Double u otros: orden natural de Comparable
        return a.getPrioridad().compareTo(b.getPrioridad());
    }

    // =========================================================
    // INSERTION SORT  (Tipo 1) — mayor a menor
    // =========================================================
    public static <T extends Comparable<T>>
    void insertionSort(Paquete<T>[] zona) {

        int n = zona.length;
        for (int i = 1; i < n; i++) {
            Paquete<T> clave = zona[i];
            int j = i - 1;

            // Avanzar mientras el elemento de la izquierda es MENOR que la clave
            while (j >= 0 && comparar(zona[j], clave) < 0) {
                zona[j + 1] = zona[j];
                j--;
            }
            zona[j + 1] = clave;
        }
    }

    // =========================================================
    // QUICK SORT  (Tipo 2) — mayor a menor
    // =========================================================
    public static <T extends Comparable<T>>
    void quickSort(Paquete<T>[] zona, int izq, int der) {

        if (izq < der) {
            int pivotIdx = particionar(zona, izq, der);
            quickSort(zona, izq, pivotIdx - 1);
            quickSort(zona, pivotIdx + 1, der);
        }
    }

    private static <T extends Comparable<T>>
    int particionar(Paquete<T>[] zona, int izq, int der) {

        Paquete<T> pivot = zona[der];
        int i = izq - 1;

        for (int j = izq; j < der; j++) {
            // Mayor a menor: intercambiar si el actual es MAYOR que el pivot
            if (comparar(zona[j], pivot) > 0) {
                i++;
                Paquete<T> temp = zona[i];
                zona[i]         = zona[j];
                zona[j]         = temp;
            }
        }

        Paquete<T> temp = zona[i + 1];
        zona[i + 1]     = zona[der];
        zona[der]       = temp;

        return i + 1;
    }

    // =========================================================
    // Ordena TODAS las zonas con el algoritmo elegido
    // =========================================================
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>>
    void ordenarZonas(Paquete<?>[][] zonas, String algoritmo) {

        for (int i = 0; i < zonas.length; i++) {
            Paquete<T>[] zona = (Paquete<T>[]) zonas[i];

            if (algoritmo.equalsIgnoreCase("insertion")) {
                insertionSort(zona);
            } else if (algoritmo.equalsIgnoreCase("quick")) {
                quickSort(zona, 0, zona.length - 1);
            } else {
                throw new IllegalArgumentException(
                    "Algoritmo desconocido: " + algoritmo +
                    ". Use 'insertion' o 'quick'.");
            }
        }
    }
}