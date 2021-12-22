package tiendas;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tienda{
    @Id  @GeneratedValue  int	id;
    private String direccion;
    private int ventas;
    @OneToMany(cascade= CascadeType.ALL,mappedBy = "Tienda",orphanRemoval = true)
    private List<Empleado> empleados = new ArrayList<>();

    public Tienda(String direccion, int ventas) {
        this.direccion = direccion;
        this.ventas = ventas;
    }

    public Tienda() {

    }

    public int getId() {return id;}
    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public int getVentas() {return ventas;}
    public void setVentas(int ventas) {this.ventas = ventas;}

    public void addEmpleado(Empleado e){
        e.setTienda(this);
        this.empleados.add(e);
    }

    @Override
    public String toString() {
        return "Tienda--> " +
            "id: " + id +
            ", direccion: '" + direccion + '\'' +
            ", ventas: " + ventas +
            ", empleados: " + empleados.toString();
    }
}
