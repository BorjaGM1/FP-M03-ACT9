package filmoteca;

import javax.swing.*;
import java.sql.*;
import java.util.Objects;

public class InsertarPelicula {
    static void MainWindow(JFrame frame) throws SQLException {
        JDialog dialog = new JDialog(frame,true);
        dialog.setSize(400, 320);
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

        JTextField title = new JTextField(20);
        title.setBounds(160,20,100,30);
        panel.add(title);

        JLabel director = new JLabel("Director:");
        director.setBounds(100,65,60,30);
        panel.add(director);

        //Añadir codigo listado directores desde base de datos
        JComboBox<String> combo = new JComboBox<>();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select nombre, apellido from mibbdd.director;");
        while(rs.next()){
            combo.addItem(rs.getString("nombre")+"-"+rs.getString("apellido"));
        }
        combo.setBounds(160,65,150,30);
        panel.add(combo);
        rs.close();
        stat.close();

        JLabel pais = new JLabel("Pais:");
        pais.setBounds(100,110,60,30);
        panel.add(pais);

        JTextField country = new JTextField(20);
        country.setBounds(160,110,100,30);
        panel.add(country);

        JLabel duracion = new JLabel("Duracion:");
        duracion.setBounds(100,155,60,30);
        panel.add(duracion);

        JTextField time = new JTextField(20);
        time.setBounds(160,155,100,30);
        panel.add(time);

        JLabel genero = new JLabel("Genero:");
        genero.setBounds(100,200,60,30);
        panel.add(genero);

        JTextField genre = new JTextField(20);
        genre.setBounds(160,200,100,30);
        panel.add(genre);

        JButton insertarPelicula = new JButton("Crear Pelicula");
        insertarPelicula.setBounds(140,245,140,30);
        insertarPelicula.addActionListener(e ->{
                try {
                    insert(title.getText(), Objects.requireNonNull(combo.getSelectedItem()),country.getText(),time.getText(),genre.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
        panel.add(insertarPelicula);
    }
    public static void insert(String title,Object director, String country, String time, String genre) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
        String direct = director.toString();
        String [] dirr = direct.split("-");
        String n1 = dirr[0];
        String n2 = dirr[1];
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select iddir from mibbdd.director where nombre='"+n1+"' and apellido= '"+n2+"'");
        if (rs.next()) {
            int n3 = Integer.parseInt(String.valueOf(rs.getObject(1)));
            PreparedStatement psInsert2 = conn.prepareStatement("insert into mibbdd.pelicula(titulo, director,pais, duracion, genero) values (?,?,?,?,?)");
            psInsert2.setString(1, title);
            psInsert2.setInt(2, n3);
            psInsert2.setString(3, country);
            psInsert2.setString(4, time);
            psInsert2.setString(5, genre);
            psInsert2.executeUpdate();
            psInsert2.close();
        }
    }
}
