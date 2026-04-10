package PRACTICA;
import java.io.*;

public class LectorArchivo {

    private int capacidad;
    private int numZonas;
    private int paquetesPorZona;

    @SuppressWarnings("unchecked")
    private Paquete<?>[][] zonas;

    @SuppressWarnings("unchecked")
    public void leer(String rutaArchivo) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

            // 1. Cabecera de configuración
            capacidad       = Integer.parseInt(br.readLine().split("=")[1].trim());
            numZonas        = Integer.parseInt(br.readLine().split("=")[1].trim());
            paquetesPorZona = Integer.parseInt(br.readLine().split("=")[1].trim());

            // 2. Inicializar matriz
            zonas = new Paquete[numZonas][paquetesPorZona];

            // 3. Saltar hasta encontrar la línea de encabezado de columnas
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().startsWith("Codigo")) break;
            }

            // 4. Leer paquetes — usar el número de zona del CSV ✅
            // Contador de cuántos paquetes se han insertado en cada fila
            int[] contadorPorZona = new int[numZonas];

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // ignorar líneas en blanco

                String[] partes = linea.split(",");

                String codigo       = partes[0].trim();
                double peso         = Double.parseDouble(partes[1].trim());
                String prioridad    = partes[2].trim();
                int    valorizacion = Integer.parseInt(partes[3].trim());
                int    zonaNum      = Integer.parseInt(partes[4].trim()); // ✅ usar zona del CSV

                int fila = zonaNum - 1; // zonas van de 1..N, índice de 0..N-1
                int col  = contadorPorZona[fila];

                zonas[fila][col] = new Paquete<>(codigo, peso, prioridad,
                                                 valorizacion, String.valueOf(zonaNum));
                contadorPorZona[fila]++;
            }
        }
    }

    public int      getCapacidad()       { return capacidad; }
    public int      getNumZonas()        { return numZonas; }
    public int      getPaquetesPorZona() { return paquetesPorZona; }
    public Paquete<?>[][] getZonas()     { return zonas; }
}