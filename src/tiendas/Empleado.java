package tiendas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Empleado {
    @Id @GeneratedValue int empId;
    private String nombre;
    private String apellido;
    @ManyToOne private Tienda tienda;

    public Empleado(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Empleado() {

    }

    public void setTienda(Tienda tienda){
        this.tienda = tienda;
    }

    public int getEmpId() {
        return empId;
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    @Override
    public String toString() {
        return "Empleado--> " + "ID: "+empId+
            "nombre: '" + nombre + '\'' +
            ", apellido: '" + apellido + '\'';
    }
}
