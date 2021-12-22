package filmoteca;

import javax.swing.*;
import java.sql.SQLException;

public class main {
    public static void main(String[] args){
        try {

            MainWindow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void MainWindow() throws SQLException {
        JFrame frame = new JFrame("Filmoteca");
        frame.setSize(450, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel,null);

        frame.setVisible(true);
    }
    private static void placeComponents(JPanel panel,JFrame frame)  {
        panel.setLayout(null);

        JButton insertarPelicula = new JButton("Insertar Pelicula");
        insertarPelicula.setBounds(60,45,140,30);
        insertarPelicula.addActionListener(e ->{
            try {
                InsertarPelicula.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });


        JButton modificarPelicula = new JButton("Modificar Pelicula");
        modificarPelicula.setBounds(60,105,140,30);
        modificarPelicula.addActionListener(e ->{
            try {
                ModificarPelicula.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton eliminarPelicula = new JButton("Eliminar Pelicula");
        eliminarPelicula.setBounds(60,165,140,30);
        eliminarPelicula.addActionListener(e ->{
            try {
                EliminarPelicula.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton insertarDirector = new JButton("Insertar Director");
        insertarDirector.setBounds(240,45,140,30);
        insertarDirector.addActionListener(e -> InsertarDirector.MainWindow(frame));

        JButton modificarDirector = new JButton("Modificar Director");
        modificarDirector.setBounds(240,105,140,30);
        modificarDirector.addActionListener(e ->{
            try {
                ModificarDirector.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton eliminarDirector = new JButton("Eliminar Director");
        eliminarDirector.setBounds(240,165,140,30);
        eliminarDirector.addActionListener(e ->{
            try {
                EliminarDirector.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton listado = new JButton("Listados");
        listado.setBounds(150,220,140,30);
        listado.addActionListener(e -> {
            try {
                Listar.MainWindow(frame);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(insertarPelicula);
        panel.add(modificarPelicula);
        panel.add(eliminarPelicula);
        panel.add(insertarDirector);
        panel.add(modificarDirector);
        panel.add(eliminarDirector);
        panel.add(listado);
    }
}
