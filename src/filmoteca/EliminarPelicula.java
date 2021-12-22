package filmoteca;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class EliminarPelicula {

    static void MainWindow(JFrame frame) throws SQLException {
        JDialog dialog = new JDialog(frame, true);
        dialog.setSize(350, 200);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        dialog.add(panel);
        placeComponents2(panel);

        dialog.setVisible(true);
    }
    public static void placeComponents2(JPanel panel) throws SQLException {
        panel.setLayout(null);
        JLabel titulo = new JLabel("Titulo:");
        titulo.setBounds(100,20,60,30);
        panel.add(titulo);

        JComboBox<String> title = new JComboBox<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("select titulo from mibbdd.pelicula");
        while (result.next()){
            title.addItem(result.getString("titulo"));
        }
        title.setBounds(160,20,100,30);
        panel.add(title);
        result.close();

        JButton eliminarPelicula = new JButton("Eliminar Pelicula");
        eliminarPelicula.setBounds(160,120,140,30);
        panel.add(eliminarPelicula);
        eliminarPelicula.addActionListener(e ->{
                try {
                    delete(Objects.requireNonNull(title.getSelectedItem()));
                } catch (SQLException ex) {
                    ex.printStackTrace();}
            }
        );
    }
    public static void delete(Object title) throws SQLException {
        String title1 = title.toString();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true", "root", "");
        Statement stat = conn.createStatement();
        PreparedStatement psDelete = conn.prepareStatement("delete from mibbdd.pelicula where titulo=?");
        psDelete.setString(1,title1);
        psDelete.executeUpdate();
        psDelete.close();
    }
}
