package tiendas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:db/tiendasDDBB.tmp;drop;user=admin;password=admin");
        EntityManager em = emf.createEntityManager();
        Tienda tienda1 = new Tienda("Madrid",15000);
        tienda1.addEmpleado(new Empleado("Paco","Martinez"));
        em.getTransaction().begin();
        em.persist(tienda1);
        em.getTransaction().commit();

        Tienda tienda2 = new Tienda("Bilbao",30000);
        tienda2.addEmpleado(new Empleado("Federico","De la Fuente"));
        em.getTransaction().begin();
        em.persist(tienda2);
        em.getTransaction().commit();

        Tienda tienda3 = new Tienda("Murcia",45000);
        tienda3.addEmpleado(new Empleado("Jack","James"));
        em.getTransaction().begin();
        em.persist(tienda3);
        em.getTransaction().commit();
        boolean parar = false;
        do {
            System.out.println("***** Menu de opciones *****");
            System.out.println("1- Muestra los empleados");
            System.out.println("2- Muestra las tiendas");
            System.out.println("3- Mostrar tiendas ordenadas por ventas");
            System.out.println("4- Modificar un empleado");
            System.out.println("5- Añade una tienda");
            System.out.println("0- Salir");
            int entrada = pideEntero("Elige opcion mediante un entero");
            TypedQuery<Empleado> query = em.createQuery("SELECT e FROM Empleado e", Empleado.class);
            List<Empleado> empleados = query.getResultList();
            switch (entrada) {
                case 1:
                    for (Empleado e: empleados){
                        System.out.println(e.toString());;
                    }
                    break;
                case 2:
                    TypedQuery<Tienda> query2 = em.createQuery("SELECT e FROM Tienda e", Tienda.class);
                    List<Tienda> shop = query2.getResultList();
                    for (Tienda t: shop){
                        System.out.println(t.toString());
                    }
                    break;
                case 3:
                    TypedQuery<Tienda> query3 = em.createQuery("SELECT e FROM Tienda e ORDER BY e.ventas DESC", Tienda.class);
                    List<Tienda> ventas = query3.getResultList();
                    for (Tienda o: ventas){
                        System.out.println(o.toString());
                    }
                    break;
                case 4:
                    for (Empleado e: empleados){
                        System.out.println("-->"+e.toString());
                    }
                    TypedQuery<Empleado> query4 = em.createQuery("SELECT e FROM Empleado e WHERE e.empId= :entry", Empleado.class);
                    Empleado e1 = query4.setParameter("entry", pideEntero("Elige el id del empleado a modificar")).getSingleResult();
                        System.out.println("Introduce el nombre");
                        e1.setNombre((new BufferedReader(new InputStreamReader(System.in))).readLine());
                        System.out.println("Introduce el apellido");
                        e1.setApellido((new BufferedReader(new InputStreamReader(System.in))).readLine());
                        em.getTransaction().begin();
                        em.persist(e1);
                        em.getTransaction().commit();
                    break;
                case 5:
                    System.out.println("Introduce la direccion");
                    String dir = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    int sales = pideEntero("Introduce las ventas");

                    em.getTransaction().begin();
                    em.persist(new Tienda(dir,sales));
                    em.getTransaction().commit();
                    break;
                case 0:parar = true;
                    System.out.println("Has finalizado el programa");
                    break;
                default:
                    System.out.println("Meeeec");
                    break;
            }
        } while (!parar) ;}

    public static int pideEntero(String pregunta) {
        System.out.println(pregunta);
        Scanner sc = new Scanner(System.in);
        int entrada;
        while(true){
            try{
                entrada = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Valor incorrecto. Prueba otra vez");
                sc.next();
            }
        } return entrada;
    }
}
