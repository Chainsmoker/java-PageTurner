import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SistemaPageTurner sistema = new SistemaPageTurner();

        Libro libro1 = new Libro("Programación en Java", "Luis Joyanes", "978-84-9732-177-2", 120.00, 5);
        Libro libro2 = new Libro("Estructuras de Datos", "Mark Allen Weiss", "978-0-321-64323-4", 150.00, 2);
        Libro libro3 = new Libro("Bases de Datos", "Abraham Silberschatz", "978-0-07-352332-3", 180.00, 0);
        
        sistema.registrarLibro(libro1);
        sistema.registrarLibro(libro2);
        sistema.registrarLibro(libro3);

        Cliente cliente1 = new Cliente("Juan Pérez", "12345678", "juan@email.com");
        Cliente cliente2 = new Cliente("María García", "87654321", "maria@email.com");
        Cliente cliente3 = new Cliente("Carlos López", "45678912", "carlos@email.com");

        sistema.registrarCliente(cliente1);
        sistema.registrarCliente(cliente2);
        sistema.registrarCliente(cliente3);

        System.out.println("\n--- Registro de Ventas ---");
        sistema.registrarVenta(cliente1, libro1, 2, LocalDate.of(2024, 1, 15));
        sistema.registrarVenta(cliente2, libro1, 1, LocalDate.of(2024, 1, 16));
        sistema.registrarVenta(cliente1, libro2, 2, LocalDate.of(2024, 1, 17));
        
        System.out.println("\n--- Intento de venta sin stock ---");
        sistema.registrarVenta(cliente3, libro2, 5, LocalDate.of(2024, 1, 18));

        System.out.println("\n--- Registro de Reservas ---");
        sistema.registrarReserva(cliente3, libro3, LocalDate.of(2024, 1, 15));
        sistema.registrarReserva(cliente1, libro3, LocalDate.of(2024, 1, 16));

        System.out.println("\n--- Intento de reserva con stock disponible ---");
        sistema.registrarReserva(cliente2, libro1, LocalDate.of(2024, 1, 17));

        System.out.println("\n--- Consulta de Reservas por Libro ---");
        System.out.println("Reservas para '" + libro3.getTitulo() + "': " + sistema.contarReservasPorLibro(libro3));
        
        for (Reserva r : sistema.obtenerReservasPorLibro(libro3)) {
            System.out.println("  - " + r.getCliente().getNombre() + " (" + r.getFecha() + ")");
        }

        sistema.generarReporteVentas();
    }
}
