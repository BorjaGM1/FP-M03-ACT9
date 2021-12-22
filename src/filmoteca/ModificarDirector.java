package filmoteca;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class ModificarDirector {
    static void MainWindow(JFrame frame) throws SQLException {
        JDialog dialog = new JDialog(frame,true);
        dialog.setSize(450, 320);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        dialog.add(panel);
        placeComponents2(panel);

        dialog.setVisible(true);
    }

    private static void placeComponents2(JPanel panel) throws SQLException {
        panel.setLayout(null);

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
        conn.close();

        JLabel nombre = new JLabel("Nuevo Nombre:");
        nombre.setBounds(100,65,60,30);
        panel.add(nombre);

        JTextField name = new JTextField(20);
        name.setBounds(160,65,100,30);
        panel.add(name);

        JLabel apellido = new JLabel("Nuevo Apellido:");
        apellido.setBounds(100,110,60,30);
        panel.add(apellido);

        JTextField surname = new JTextField(20);
        surname.setBounds(160,110,100,30);
        panel.add(surname);

        JButton modificarDirector = new JButton("Modificar Director");
        modificarDirector.setBounds(140,165,140,30);
        modificarDirector.addActionListener(e ->{
                try {
                    update(Objects.requireNonNull(combo.getSelectedItem()),name.getText(),surname.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
        panel.add(modificarDirector);
    }

    public static void update(Object director, String name, String surname) throws SQLException {
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
        PreparedStatement psUpdate = conn.prepareStatement("update mibbdd.director set nombre=?, apellido=? where iddir=?");
        psUpdate.setString(1,name);
        psUpdate.setString(2,surname);
        psUpdate.setInt(3,n3);
        psUpdate.executeUpdate();
        psUpdate.close();
    }
}
