package PRACTICA;

import java.util.ArrayList;
import java.util.List;

public class Selector {

    public static List<Paquete<?>> seleccionarRecursivo(
            Paquete<?>[] paquetes, double capacidad) {

        List<Paquete<?>> seleccionados = new ArrayList<>();
        resolverRecursivo(paquetes, 0, capacidad, 0, new ArrayList<>(),
                          seleccionados, new int[]{0});
        return seleccionados;
    }

    private static void resolverRecursivo(
            Paquete<?>[] paquetes,
            int          indice,
            double       capacidadRestante,
            int          valorActual,
            List<Paquete<?>> actuales,
            List<Paquete<?>> mejores,
            int[]        mejorValor) {

        if (indice == paquetes.length) {
            if (valorActual > mejorValor[0]) {
                mejorValor[0] = valorActual;
                mejores.clear();
                mejores.addAll(actuales);
            }
            return;
        }

        Paquete<?> p = paquetes[indice];

        resolverRecursivo(paquetes, indice + 1, capacidadRestante,
                          valorActual, actuales, mejores, mejorValor);

        if (p.getPeso() <= capacidadRestante) {
            actuales.add(p);
            resolverRecursivo(paquetes, indice + 1,
                              capacidadRestante - p.getPeso(),
                              valorActual + p.getValorizacion(),
                              actuales, mejores, mejorValor);
            actuales.remove(actuales.size() - 1); // backtracking
        }
    }

    public static List<Paquete<?>> seleccionarDP(
            Paquete<?>[] paquetes, double capacidad) {

        final int ESCALA = 10; 
        int n = paquetes.length;
        int W = (int) Math.round(capacidad * ESCALA);

        int[] pesos = new int[n];
        for (int i = 0; i < n; i++) {
            pesos[i] = (int) Math.round(paquetes[i].getPeso() * ESCALA);
        }

        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w];

                if (pesos[i - 1] <= w) {
                    int conPaquete = dp[i - 1][w - pesos[i - 1]]
                                    + paquetes[i - 1].getValorizacion();
                    if (conPaquete > dp[i][w]) {
                        dp[i][w] = conPaquete;
                    }
                }
            }
        }
        List<Paquete<?>> seleccionados = new ArrayList<>();
        int w = W;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                seleccionados.add(0, paquetes[i - 1]);
                w -= pesos[i - 1];
            }
        }

        return seleccionados;
    }

    public static Paquete<?>[] aplanar(Paquete<?>[][] zonas,
                                       int numZonas,
                                       int porZona) {
        Paquete<?>[] todos = new Paquete<?>[numZonas * porZona];
        int idx = 0;
        for (int i = 0; i < numZonas; i++) {
            for (int j = 0; j < porZona; j++) {
                todos[idx++] = zonas[i][j];
            }
        }
        return todos;
    }
}