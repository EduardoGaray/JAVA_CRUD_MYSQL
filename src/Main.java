import java.sql.*;
import java.util.Scanner;

public class Main {
    static Connection conexion;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/telosabes2", "root", "");
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        crearTabla();
        //altaACapon();
        //altaScanner();
        VentanaPrincipal v=new VentanaPrincipal(conexion);
        v.setVisible(true);

    }

    public static void crearTabla(){
        Statement stmt= null;
        try {
            stmt = conexion.createStatement();
            String CREATE_TABLE_SQL="CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INT NOT NULL,"
                    + "nombre VARCHAR(100) NOT NULL,"
                    + "dni VARCHAR(25) NOT NULL,"
                    + "telefono VARCHAR(25) NOT NULL,"
                    + "email VARCHAR(100) NOT NULL,"
                    + "PRIMARY KEY (id))";
            stmt.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void altaACapon(){
        Statement stmt=null;
        try{
            stmt=conexion.createStatement();
            String consulta="INSERT INTO usuarios (nombre, dni, telefono, email) " +
                    "VALUES ('María', '12345678D', '968123456', 'maria@cesurformacion.com');";
            stmt.executeUpdate(consulta);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void altaScanner(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Dime el nombre");
        String nombre=scanner.next();
        System.out.println("Dime el DNI");
        String dni=scanner.next();
        System.out.println("Dime el teléfono");
        String telefono=scanner.next();
        System.out.println("Dime el correo electrónico");
        String email=scanner.next();

        PreparedStatement s=null;
        String consulta="INSERT INTO usuarios (nombre, dni, telefono, email) " +
                "VALUES (?, ?, ?, ?)";
        try {
            s=conexion.prepareStatement(consulta);

            s.setString(1, nombre);
            s.setString(2, dni);
            s.setString(3, telefono);
            s.setString(4, email);

            s.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
