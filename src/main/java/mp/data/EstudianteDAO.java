package mp.data;

import static mp.connection.Conexion.getConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mp.domain.Estudiante;

//DAO - DATA ACCESS OBJECT
public class EstudianteDAO {

  public List<Estudiante> listarEstudiantes() {
    List<Estudiante> estudiantes = new ArrayList<>();
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexion();
    String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
    try {
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        Estudiante estudiante = new Estudiante();
        estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setApellido(rs.getString("apellido"));
        estudiante.setTelefono(rs.getString("telefono"));
        estudiante.setEmail(rs.getString("email"));
        estudiantes.add(estudiante);
      }
    } catch (Exception e) {
      System.out.println("Ocurrio un error al seleccionar datos: " + e.getMessage());
    } finally {
      try {
        con.close();
      } catch (Exception e) {
        System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
      }
    }

    return estudiantes;
  }

  public boolean buscarEstudiantePorId(Estudiante estudiante) {
    PreparedStatement ps;
    ResultSet rs;
    Connection con = getConexion();
    String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, estudiante.getIdEstudiante());
      rs = ps.executeQuery();
      if (rs.next()) {
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setApellido(rs.getString("apellido"));
        estudiante.setTelefono(rs.getString("telefono"));
        estudiante.setEmail(rs.getString("email"));
        return true;
      }
    } catch (Exception e) {
      System.out.println("Ocurrio un error al buscar estudiante: " + e.getMessage());
    } finally {
      try {
        con.close();
      } catch (Exception e) {
        System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean agregarEstudiante(Estudiante estudiante) {
    PreparedStatement ps;
    Connection con = getConexion();
    String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
        " VALUES(?, ?, ?, ?)";

    try {
      ps = con.prepareStatement(sql);
      ps.setString(1, estudiante.getNombre());
      ps.setString(2, estudiante.getApellido());
      ps.setString(3, estudiante.getTelefono());
      ps.setString(4, estudiante.getEmail());
      ps.execute();
      return true;
    } catch (Exception e) {
      System.out.println("Ocurrio un error al agregar estudiante: " + e.getMessage());
    } finally {
      try {
        con.close();
      } catch (Exception e) {
        System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean modificar(Estudiante estudiante) {
    PreparedStatement ps;
    Connection con = getConexion();
    String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, " +
        " email=? WHERE id_estudiante=?";

    try {
      ps = con.prepareStatement(sql);
      ps.setString(1, estudiante.getNombre());
      ps.setString(2, estudiante.getApellido());
      ps.setString(3, estudiante.getTelefono());
      ps.setString(4, estudiante.getEmail());
      ps.setInt(5, estudiante.getIdEstudiante());
      ps.execute();
      return true;
    } catch (Exception e) {
      System.out.println("Ocurrio un error al modificar el usuario: " + e.getMessage());
    } finally {
      try {
        con.close();
      } catch (Exception e) {
        System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
      }
    }

    return false;
  }

  public boolean eliminar(Estudiante estudiante) {
    PreparedStatement ps;
    Connection con = getConexion();
    String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, estudiante.getIdEstudiante());
      ps.execute();
      return true;
    } catch (Exception e) {
      System.out.println("Error al eliminar estudiante: " + e.getMessage());
    } finally {
      try {
        con.close();
      } catch (Exception e) {
        System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
      }
    }

    return false;
  }

}
