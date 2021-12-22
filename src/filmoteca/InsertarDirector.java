package filmoteca;

import javax.swing.*;
import java.sql.*;

public class InsertarDirector {
    static void MainWindow(JFrame frame) {
        JDialog dialog = new JDialog(frame, true);
        dialog.setSize(450, 250);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        dialog.add(panel);
        placeComponents2(panel);

        dialog.setVisible(true);
    }

    private static void placeComponents2(JPanel panel) {
        panel.setLayout(null);

        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(100,20,60,30);
        panel.add(nombre);

        JTextField name = new JTextField(20);
        name.setBounds(160,20,100,30);
        panel.add(name);

        JLabel apellido = new JLabel("Apellido:");
        apellido.setBounds(100,65,60,30);
        panel.add(apellido);

        JTextField surname = new JTextField(20);
        surname.setBounds(160,65,100,30);
        panel.add(surname);

        JButton insertarDirector = new JButton("Crear Director");
        insertarDirector.setBounds(140,110,140,30);
        insertarDirector.addActionListener(e ->{
                try {
                    insert(name.getText(), surname.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
        panel.add(insertarDirector);
    }

    public static void insert (String name, String surname) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
        Statement stat = conn.createStatement();
        PreparedStatement psInsert = conn.prepareStatement("insert into mibbdd.director(nombre, apellido) VALUES (?,?)");
        psInsert.setString(1,name);
        psInsert.setString(2,surname);
        psInsert.executeUpdate();
        psInsert.close();
    }



}
