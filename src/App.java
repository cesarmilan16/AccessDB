import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Configurar la información de conexión
        String url = "jdbc:mysql://localhost:3306/gestion_empleados";
        String usuario = "root";
        String contraseña = "Cesarmilan1996";
        Connection conexion = null;

        // Establecer la conexión
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos MySQL!");

            // Iniciar la transacción
            conexion.setAutoCommit(false);

            // Definir la consulta SQL para insertar empleados
            String consultaAgregar = "INSERT INTO empleados (nombre, apellido, edad, puesto) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(consultaAgregar);

            // Agregar empleado 1
            pstmt.setString(1, "Eduardo");
            pstmt.setString(2, "Pérez");
            pstmt.setInt(3, 30); // Edad de Eduardo
            pstmt.setString(4, "Analista"); // Puesto de Eduardo
            pstmt.executeUpdate();

            // Agregar empleado 2
            pstmt.setString(1, "Marta");
            pstmt.setString(2, "Fernández");
            pstmt.setInt(3, 25); // Edad de Marta
            pstmt.setString(4, "Gerente"); // Puesto de Marta
            pstmt.executeUpdate();

            // Definir la consulta SQL para actualizar un empleado
            String consultaActualizar = "UPDATE empleados SET nombre = ?, edad = ?, puesto = ? WHERE apellido = ?";
            PreparedStatement pstmtActualizar = conexion.prepareStatement(consultaActualizar);

            // Actualizar el empleado con apellido "Fernández"
            pstmtActualizar.setString(1, "Laura");
            pstmtActualizar.setInt(2, 28); // Nueva edad de Laura
            pstmtActualizar.setString(3, "Asistente"); // Nuevo puesto de Laura
            pstmtActualizar.setString(4, "Fernández");
            pstmtActualizar.executeUpdate();

            // Consulta para obtener información específica de los empleados
            String consultaEmpleados = "SELECT * FROM empleados WHERE puesto = ?";
            PreparedStatement pstmtEmpleados = conexion.prepareStatement(consultaEmpleados);
            pstmtEmpleados.setString(1, "Asistente");
            ResultSet rs = pstmtEmpleados.executeQuery();

            // Procesar resultados de la consulta
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre") + ", Apellido: " + rs.getString("apellido")
                 + ", Edad: " + rs.getInt("edad") + ", Puesto: " + rs.getString("puesto"));
            }

            // Confirmar la transacción
            conexion.commit();
            System.out.println("Se agregaron los nuevos empleados, se actualizó un empleado y se consultó la información de empleados.");

            // No olvides cerrar la conexión cuando hayas terminado
            conexion.close();
        } catch (SQLException e) {
            // Manejar cualquier excepción que ocurra al intentar conectar a la base de datos
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch(SQLException excep) {
                    System.err.println("Error al hacer rollback de la transacción: " + excep.getMessage());
                }
            }
        }
    }
}