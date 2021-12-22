package filmoteca;

import java.sql.*;

public class BBDD {
    private static final String datosConexion = "jdbc:mysql://localhost:3306/";
    private static final String baseDatos = "miBBDD";
    private static final String usuario = "root";
    private static final String password = "";
    private Connection con;

    public BBDD() throws SQLException {
        {
            try {
                con = DriverManager.getConnection(datosConexion + "?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true", usuario,
                    password);
                try {
                    //CREO LA BASE DE DATOS SI NO EXISTE
                    crearBDD();
                    //CREO LAS TABLAS SI NO EXISTEN
                    crearTablas();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /*PreparedStatement psInsert = con.prepareStatement("insert into mibbdd.director (nombre, apellido) values (?,?)");
        psInsert.setString(1,"Stanley");
        psInsert.setString(2,"Kubrick");
        psInsert.executeUpdate();
        psInsert.setString(1,"Quentin");
        psInsert.setString(2,"Tarantino");
        psInsert.executeUpdate();
        psInsert.setString(1,"Charles");
        psInsert.setString(2,"Chaplin");
        psInsert.executeUpdate();
        psInsert.close();
         */
        /*PreparedStatement psInsert2 = con.prepareStatement("insert into mibbdd.pelicula(titulo, director, pais, duracion, genero) values (?,?,?,?,?)");
        psInsert2.setString(1,"La naranja mecanica");
        psInsert2.setInt(2,1);
        psInsert2.setString(3,"USA");
        psInsert2.setString(4,"136 minutos");
        psInsert2.setString(5,"Drama, comedia negra");
        psInsert2.executeUpdate();
        psInsert2.setString(1,"Full Metal Jacked");
        psInsert2.setInt(2,1);
        psInsert2.setString(3,"USA");
        psInsert2.setString(4,"116 minutos");
        psInsert2.setString(5,"Drama belico");
        psInsert2.executeUpdate();
        psInsert2.setString(1,"El gran dictador");
        psInsert2.setInt(2,3);
        psInsert2.setString(3,"USA");
        psInsert2.setString(4,"124 minutos");
        psInsert2.setString(5,"Comedia dramatica, satira");
        psInsert2.executeUpdate();
        psInsert2.close();
         */
    }

    private void crearBDD() throws Exception {
        String query = "create database if not exists " + baseDatos + ";";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            con = DriverManager.getConnection(datosConexion + baseDatos + "?useSSL=true",
                usuario, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }

    private void crearTablas() throws Exception {
        String query = "CREATE TABLE IF NOT EXISTS Director (iddir int primary key auto_increment," +
            "nombre varchar(50)," +
            "apellido varchar(50)" +
            ");";
        String query2 = "CREATE TABLE IF NOT EXISTS Pelicula (id int primary key auto_increment," +
            "titulo varchar(50)," +
            "director int," +
            "pais varchar(50)," +
            "duracion varchar (50)," +
            "genero varchar (50)"+
            ");";
        //String query3 = "ALTER TABLE  mibbdd.Pelicula  ADD (CONSTRAINT fk_iddir FOREIGN KEY (director) REFERENCES mibbdd.Director(iddir))";
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
           // stmt.executeUpdate(query3);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
        }
    }
}
