package filmoteca;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class ModificarPelicula {
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



        JLabel director = new JLabel("Director:");
        director.setBounds(100,65,60,30);
        panel.add(director);

        //Añadir codigo listado directores desde base de datos
        JComboBox<String> combo = new JComboBox<>();

        ResultSet rs = stat.executeQuery("select nombre, apellido from mibbdd.director;");
        while(rs.next()){
            combo.addItem(rs.getString("nombre")+"-"+rs.getString("apellido"));
        }
        combo.setBounds(160,65,150,30);
        combo.setEnabled(false);
        panel.add(combo);
        rs.close();
        stat.close();
        conn.close();

        JLabel pais = new JLabel("Pais:");
        pais.setBounds(100,110,60,30);
        panel.add(pais);

        JTextField country = new JTextField(20);
        country.setBounds(160,110,100,30);
        country.setEnabled(false);
        panel.add(country);

        JLabel duracion = new JLabel("Duracion:");
        duracion.setBounds(100,155,60,30);
        panel.add(duracion);

        JTextField time = new JTextField(20);
        time.setBounds(160,155,100,30);
        time.setEnabled(false);
        panel.add(time);



        JLabel genero = new JLabel("Genero:");
        genero.setBounds(100,200,60,30);
        panel.add(genero);

        JTextField genre = new JTextField(20);
        genre.setBounds(160,200,100,30);
        genre.setEnabled(false);
        panel.add(genre);

        title.addActionListener(e -> {
            combo.setEnabled(true);
            country.setEnabled(true);
            time.setEnabled(true);
            genre.setEnabled(true);
        });

        JButton modificarPelicula = new JButton("Modificar Pelicula");
        modificarPelicula.setBounds(160,245,140,30);
        panel.add(modificarPelicula);
        modificarPelicula.addActionListener(e ->{
                try {
                    update(Objects.requireNonNull(title.getSelectedItem()), Objects.requireNonNull(combo.getSelectedItem()),country.getText(),time.getText(),genre.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        );
    }
    public static void update (Object title, Object director, String country, String time, String genre) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true", "root", "");
        String direct = director.toString();
        String title1 = title.toString();
        String[] dirr = direct.split("-");
        String n1 = dirr[0];
        String n2 = dirr[1];
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select iddir from mibbdd.director where nombre='" + n1 + "' and apellido= '" + n2 + "'");
        int n3 = 0;
        if (rs.next()) {
            n3 = Integer.parseInt(String.valueOf(rs.getObject(1)));
        }
        PreparedStatement psUpdate = conn.prepareStatement("update mibbdd.pelicula set director=?,pais=?,duracion=?,genero=? where titulo=?");
        psUpdate.setInt(1,n3);
        psUpdate.setString(2,country);
        psUpdate.setString(3,time);
        psUpdate.setString(4,genre);
        psUpdate.setString(5,title1);
        psUpdate.executeUpdate();
        psUpdate.close();
    }
}
