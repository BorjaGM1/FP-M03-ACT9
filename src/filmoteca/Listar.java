package filmoteca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Listar {
        static void MainWindow(JFrame frame) throws SQLException {
            JDialog dialog = new JDialog(frame,true);
            dialog.setSize(550, 350);
            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            dialog.add(panel);
            placeComponents2(panel);

            dialog.setVisible(true);
        }

        private static void placeComponents2(JPanel panel) throws SQLException {
            panel.setLayout(null);
            JButton listar = new JButton("Buscar");listar.setBounds(130,70,140,30);panel.add(listar);
            JLabel director = new JLabel("Director:");director.setBounds(15,25,60,30);panel.add(director);

            JComboBox<String> combo = new JComboBox<>();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
            Statement stat = conn.createStatement();
            AtomicReference<ResultSet> rs = new AtomicReference<>(stat.executeQuery("select nombre, apellido from mibbdd.director;"));
            while(rs.get().next()){
                combo.addItem(rs.get().getString("nombre")+"-"+ rs.get().getString("apellido"));
            }
            combo.setBounds(70,25,150,30);
            panel.add(combo);
            rs.get().close();

            JLabel genero = new JLabel("Genero:");genero.setBounds(230,25,60,30);panel.add(genero);
            JComboBox<String> genre = new JComboBox<>();
            ResultSet result = stat.executeQuery("select distinct genero from mibbdd.pelicula");
            while (result.next()){
                genre.addItem(result.getString("genero"));
            }
            genre.setBounds(280,25,150,30);
            panel.add(genre);
            result.close();


            stat.close();
            conn.close();
            JTextField title = new JTextField(20);title.setBounds(80,125,100,30);title.setEnabled(false);panel.add(title);
            JTextField dirrr = new JTextField(20);dirrr.setBounds(160,125,100,30);dirrr.setEnabled(false);panel.add(dirrr);
            JTextField country = new JTextField(20);country.setBounds(80,170,100,30);country.setEnabled(false);panel.add(country);
            JTextField time = new JTextField(20);time.setBounds(160,170,100,30);time.setEnabled(false);panel.add(time);
            JTextField genr = new JTextField(20);genr.setBounds(80,210,100,30);genr.setEnabled(false);panel.add(genr);
            JButton anterior = new JButton("Anterior");anterior.setBounds(40,260,140,30);panel.add(anterior);
            JButton siguiente = new JButton("Siguiente");siguiente.setBounds(180,260,140,30);panel.add(siguiente);

            String gen = (Objects.requireNonNull(genre.getSelectedItem())).toString();
            //int finalN = n3;

            final int[][] i = new int[1][1];
            combo.addActionListener(e -> {
                genre.setEnabled(false);
                listar.addActionListener(e1 -> {
                    try {
                        i[0] = new int[]{0};
                        Connection conn1;
                        conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
                        Statement stat1;
                        stat1 = conn1.createStatement();
                        ResultSet rs1;
                        Object dir = Objects.requireNonNull(combo.getSelectedItem());
                        String direct = dir.toString();
                        String[] dirr = direct.split("-");
                        String n1 = dirr[0];
                        String n2 = dirr[1];
                        rs.set(stat1.executeQuery("select iddir from mibbdd.director where nombre='" + n1 + "' and apellido= '" + n2 + "'"));
                        int n3 = 0;
                        if (rs.get().next()) {
                            n3 = Integer.parseInt(String.valueOf(rs.get().getObject(1)));
                        }
                        rs1 = stat1.executeQuery("select * from mibbdd.pelicula where director='"+n3+"'");
                        ArrayList<String> titulos = new ArrayList<>();
                        ArrayList<String> paises = new ArrayList<>();
                        ArrayList<String> duraciones = new ArrayList<>();
                        ArrayList<String> generos = new ArrayList<>();
                        ArrayList<Integer> directores = new ArrayList<>();
                        while (rs1.next()) {
                            System.out.println(rs1.getRow());
                            titulos.add(rs1.getString(2));
                            System.out.println(titulos.toString());
                            directores.add(rs1.getInt("director"));
                            paises.add(rs1.getString(4));
                            duraciones.add(rs1.getString(5));
                            generos.add(rs1.getString(6));
                        }
                        title.setText(titulos.get(0));
                        country.setText(paises.get(0));
                        time.setText(duraciones.get(0));
                        genr.setText(generos.get(0));
                        rs1.close();
                        stat.close();
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

            });
            siguiente.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        Connection conn1;
                        conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
                        Statement stat1;
                        stat1 = conn1.createStatement();
                        ResultSet rs1;
                        Object dir = Objects.requireNonNull(combo.getSelectedItem());
                        String direct = dir.toString();
                        String[] dirr = direct.split("-");
                        String n1 = dirr[0];
                        String n2 = dirr[1];
                        rs.set(stat1.executeQuery("select iddir from mibbdd.director where nombre='" + n1 + "' and apellido= '" + n2 + "'"));
                        int n3 = 0;
                        if (rs.get().next()) {
                            n3 = Integer.parseInt(String.valueOf(rs.get().getObject(1)));
                        }
                        rs1 = stat1.executeQuery("select * from mibbdd.pelicula where director='"+n3+"'");
                        ArrayList<String> titulos = new ArrayList<>();
                        ArrayList<String> paises = new ArrayList<>();
                        ArrayList<String> duraciones = new ArrayList<>();
                        ArrayList<String> generos = new ArrayList<>();
                        ArrayList<Integer> directores = new ArrayList<>();
                        while (rs1.next()) {
                            System.out.println(rs1.getRow());
                            titulos.add(rs1.getString(2));
                            System.out.println(titulos.toString());
                            directores.add(rs1.getInt("director"));
                            paises.add(rs1.getString(4));
                            duraciones.add(rs1.getString(5));
                            generos.add(rs1.getString(6));
                        }
                        if (i[0][0] < titulos.size()){
                        i[0][0]++;
                        title.setText(titulos.get(i[0][0]));
                        country.setText(paises.get(i[0][0]));
                        time.setText(duraciones.get(i[0][0]));
                        genr.setText(generos.get(i[0][0]));
                        rs1.close();
                        stat.close();
                        conn.close();}
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            genre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    combo.setEnabled(false);
                    listar.addActionListener(e1 -> {
                        Connection conn = null;
                        try {
                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=true&useTimezone=true&serverTimezone=UTC&useServerPrepStmts=true","root","");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        Statement stat = null;
                        try {
                            assert conn != null;
                            stat = conn.createStatement();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        ResultSet rs = null;
                        try {
                            assert stat != null;
                            rs = stat.executeQuery("select * from mibbdd.pelicula where genero="+gen);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        ArrayList<String> titulos = new ArrayList<>();
                        ArrayList<String> paises = new ArrayList<>();
                        ArrayList<String> duraciones = new ArrayList<>();
                        ArrayList<String> generos = new ArrayList<>();
                        ArrayList<Integer> directores = new ArrayList<>();
                        int i = 0;
                        try {
                            while (rs.next()){
                                titulos.add(rs.getString(2));
                                directores.add(rs.getInt("director"));
                                paises.add(rs.getString(4));
                                duraciones.add(rs.getString(5));
                                generos.add(rs.getString(6));}
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            });

        }
}
