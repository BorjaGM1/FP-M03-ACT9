package filmoteca;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class EliminarDirector {
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
        JLabel titulo = new JLabel("Director:");
        titulo.setBounds(100,20,60,30);
        panel.add(titulo);

        JComboBox<String> combo = new JComboBox<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select nombre, apellido from mibbdd.director;");
        while(rs.next()){
            combo.addItem(rs.getString("nombre")+"-"+rs.getString("apellido"));
        }
        combo.setBounds(160,20,150,30);
        panel.add(combo);
        rs.close();
        stat.close();

        JButton eliminarDirectorDirector = new JButton("Eliminar Director");
        eliminarDirectorDirector.setBounds(140,65,140,30);
        eliminarDirectorDirector.addActionListener(e ->{
                try {
                    delete(Objects.requireNonNull(combo.getSelectedItem()), panel);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
        panel.add(eliminarDirectorDirector);
    }

    public static void delete (Object director, JPanel panel) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true", "root", "");
        String direct = director.toString();
        String[] dirr = direct.split("-");
        String n1 = dirr[0];
        String n2 = dirr[1];
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select iddir from mibbdd.director where nombre='" + n1 + "' and apellido= '" + n2 + "'");
        int n3 = 0;
        if (rs.next()) {
            n3 = Integer.parseInt(String.valueOf(rs.getObject(1)));
        }
        rs.close();
        rs = stat.executeQuery("select * from mibbdd.pelicula where director="+n3);
        if (!rs.next()){
            PreparedStatement psDelete = conn.prepareStatement("delete from mibbdd.director where iddir=?");
            psDelete.setInt(1,n3);
            psDelete.executeUpdate();
            psDelete.close();
        }else{

            JOptionPane.showMessageDialog(panel,
                "WARNING.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            rs.close();
            stat.close();
        }
    }
}
