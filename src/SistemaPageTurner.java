import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SistemaPageTurner {
    private List<Libro> libros;
    private List<Cliente> clientes;
    private List<Venta> ventas;
    private List<Reserva> reservas;

    public SistemaPageTurner() {
        this.libros = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public boolean registrarVenta(Cliente cliente, Libro libro, int cantidad, LocalDate fecha) {
        if (!libro.tieneStock(cantidad)) {
            System.out.println("Error: Stock insuficiente para el libro '" + libro.getTitulo() + "'");
            return false;
        }
        if (libro.descontarStock(cantidad)) {
            Venta venta = new Venta(fecha, cantidad, cliente, libro);
            ventas.add(venta);
            System.out.println("Venta registrada exitosamente: " + venta);
            return true;
        }
        return false;
    }

    public boolean registrarReserva(Cliente cliente, Libro libro, LocalDate fecha) {
        if (libro.tieneStock(1)) {
            System.out.println("El libro '" + libro.getTitulo() + "' tiene stock disponible. Se sugiere realizar una compra.");
            return false;
        }
        Reserva reserva = new Reserva(fecha, cliente, libro);
        reservas.add(reserva);
        System.out.println("Reserva registrada exitosamente: " + reserva);
        return true;
    }

    public List<Reserva> obtenerReservasPorLibro(Libro libro) {
        List<Reserva> reservasLibro = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getLibro().equals(libro)) {
                reservasLibro.add(reserva);
            }
        }
        return reservasLibro;
    }

    public int contarReservasPorLibro(Libro libro) {
        int count = 0;
        for (Reserva reserva : reservas) {
            if (reserva.getLibro().equals(libro)) {
                count++;
            }
        }
        return count;
    }

    public Map<Libro, Integer> obtenerVentasPorLibro() {
        Map<Libro, Integer> ventasPorLibro = new HashMap<>();
        for (Venta venta : ventas) {
            ventasPorLibro.put(venta.getLibro(), 
                ventasPorLibro.getOrDefault(venta.getLibro(), 0) + venta.getCantidad());
        }
        return ventasPorLibro;
    }

    public Map<Libro, Double> obtenerIngresosPorLibro() {
        Map<Libro, Double> ingresosPorLibro = new HashMap<>();
        for (Venta venta : ventas) {
            ingresosPorLibro.put(venta.getLibro(), 
                ingresosPorLibro.getOrDefault(venta.getLibro(), 0.0) + venta.calcularMonto());
        }
        return ingresosPorLibro;
    }

    public double obtenerTotalIngresos() {
        double total = 0;
        for (Venta venta : ventas) {
            total += venta.calcularMonto();
        }
        return total;
    }

    public void generarReporteVentas() {
        System.out.println("\n========== REPORTE DE VENTAS ==========");
        Map<Libro, Integer> ventasPorLibro = obtenerVentasPorLibro();
        Map<Libro, Double> ingresosPorLibro = obtenerIngresosPorLibro();
        if (ventasPorLibro.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Libro libro : ventasPorLibro.keySet()) {
            int cantidadVendida = ventasPorLibro.get(libro);
            double ingresos = ingresosPorLibro.getOrDefault(libro, 0.0);
            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("  Cantidad vendida: " + cantidadVendida);
            System.out.println("  Ingresos generados: S/ " + String.format("%.2f", ingresos));
            System.out.println("  Stock actual: " + libro.getStock());
            System.out.println();
        }
        System.out.println("TOTAL DE INGRESOS: S/ " + String.format("%.2f", obtenerTotalIngresos()));
        System.out.println("=======================================");
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
    
    public List<Reserva> getReservas() {
        return reservas;
    }
}
