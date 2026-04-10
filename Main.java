package PRACTICA;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
    	
        System.out.println("2.2  DEMO DE GENERICIDAD");
        Paquete<Integer> pi = new Paquete<>("DEMO-01", 3.5,  1,    100, "Z1");
        Paquete<Double>  pd = new Paquete<>("DEMO-02", 2.0,  0.95, 200, "Z2");
        Paquete<String>  ps = new Paquete<>("DEMO-03", 5.0, "Alta",150, "Z3");

        System.out.println(pi);
        System.out.println(pd);
        System.out.println(ps);

        System.out.println("2.3  CARGA DESDE datos.txt");

        LectorArchivo lector = new LectorArchivo();
        lector.leer("datos.txt");

        int    numZonas  = lector.getNumZonas();
        int    porZona   = lector.getPaquetesPorZona();
        double capacidad = lector.getCapacidad();

        System.out.println("Capacidad del camión : " + capacidad + " kg");
        System.out.println("Zonas                : " + numZonas);
        System.out.println("Paquetes por zona    : " + porZona);

        Paquete<?>[][] zonas = lector.getZonas();

        System.out.println("\n── Salida 1: Matriz de paquetes por zonas ──\n");
        imprimirZonas(zonas, numZonas, porZona);

        System.out.println("2.4  ORDENAMIENTO");

        System.out.println("\n── Salida 2: Paquetes ANTES del ordenamiento ──\n");
        imprimirZonas(zonas, numZonas, porZona);
        
        Paquete<?>[][] copiaParaQuick = copiarZonas(zonas, numZonas, porZona);

        Ordenador.ordenarZonas(zonas, "insertion");
        System.out.println("── Salida 3a: Después — INSERTION SORT (mayor → menor) ──\n");
        imprimirZonas(zonas, numZonas, porZona);

        Ordenador.ordenarZonas(copiaParaQuick, "quick");
        System.out.println("── Salida 3b: Después — QUICK SORT (mayor → menor) ──\n");
        imprimirZonas(copiaParaQuick, numZonas, porZona);

        System.out.println("2.5  SELECCION PARA EL CAMION");

        Paquete<?>[] todos = Selector.aplanar(zonas, numZonas, porZona);

        System.out.println("── Salida 4a: Selección por RECURSIVIDAD ──\n");
        List<Paquete<?>> selRec = Selector.seleccionarRecursivo(todos, capacidad);
        imprimirSeleccion(selRec, capacidad);

        System.out.println("\n── Salida 4b: Selección por PROGRAMACIÓN DINÁMICA (Mochila 0/1) ──\n");
        List<Paquete<?>> selDP = Selector.seleccionarDP(todos, capacidad);
        imprimirSeleccion(selDP, capacidad);
    }

    private static Paquete<?>[][] copiarZonas(Paquete<?>[][] zonas,
                                               int numZonas, int porZona) {
        Paquete<?>[][] copia = new Paquete<?>[numZonas][porZona];
        for (int i = 0; i < numZonas; i++) {
            for (int j = 0; j < porZona; j++) {
                copia[i][j] = zonas[i][j];
            }
        }
        return copia;
    }

    private static void imprimirZonas(Paquete<?>[][] zonas,
                                       int numZonas, int porZona) {
        for (int i = 0; i < numZonas; i++) {
            for (int j = 0; j < porZona; j++) {
                System.out.println("  │  " + zonas[i][j]);
            }
            System.out.println();
        }
    }

    private static void imprimirSeleccion(List<Paquete<?>> lista, double capacidad) {
        double pesoTotal  = 0;
        int    valorTotal = 0;

        for (Paquete<?> p : lista) {
        	System.out.println("  - " + p);
            pesoTotal  += p.getPeso();
            valorTotal += p.getValorizacion();
        }

        System.out.println();
        System.out.printf("  Paquetes seleccionados : %d%n",       lista.size());
        System.out.printf("  Peso total             : %.1f kg  /  %.1f kg disponibles%n",
                          pesoTotal, capacidad);
        System.out.printf("  Valorización total     : %d%n",        valorTotal);
    }
}