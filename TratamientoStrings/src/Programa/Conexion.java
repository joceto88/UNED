package Programa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class Conexion {
	public Connection AccesoBD() {

		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return null;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;

		}

		if (connection != null) {
			return connection;
		} else {
			System.out.println("Conexión vacía");
			return null;
		}
	}

	public int ValidaUsuario(String usuario, String password) {
		
		int resultado;
		//Generamos la query para validar el usuario
		String sql = "SELECT * FROM \"DSS\".t_usuarios where \"nombre_usuario\"=?";
		//Invocamos al metodo AccesoDB para que nos devuelva la conexión con la BBDD
		Connection connection = new Conexion().AccesoBD();
		CachedRowSet resultadoConsultaUser;
		try {
		//Se forma la query con el PreparedStatement para lanzarla posteriormente
			resultadoConsultaUser = new CachedRowSetImpl();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();
			resultadoConsultaUser.populate(rs);
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error al ejecutar la query");
			return -1;
		}
		int contador = 0;
		try {
		//Recorremos el resultado para comprobar si el usuario está logado o no
			while (resultadoConsultaUser.next()) {
				String aux = resultadoConsultaUser.getString(2);
				contador++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//Error al intentar comprobar el usuario
			cerrarResultado(resultadoConsultaUser);
			return -1;

		}
		if (contador == 0) {
			//Query sin resultados- el usuario no está registrado.
			System.err.println("Usuario no existente.");
			try {
				resultadoConsultaUser.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		//El usuario existe
		resultado = 1;
		if(resultado!=1)
		{
			//Si no se ha encontrado el usuario fin del método.
			cerrarResultado(resultadoConsultaUser);
			return resultado;
		}
		//Ahora se forma la query para comprobar que la password es la esperada.
		sql = "SELECT * FROM \"DSS\".t_usuarios where \"nombre_usuario\"=? and \"password\"=?";
		connection = new Conexion().AccesoBD();
		try {
			//Se forma la query con el PreparedStatement para lanzarla posteriormente
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, usuario);
			String auxi= password.toString();
			ps.setString(2,auxi);
			ResultSet rs = ps.executeQuery();
			cerrarResultado(resultadoConsultaUser);
			resultadoConsultaUser = new CachedRowSetImpl();
			resultadoConsultaUser.populate(rs);
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			//Error
			e.printStackTrace();
			System.err.println("Error al ejecutar la query");
			cerrarResultado(resultadoConsultaUser);
			return -1;
		}
		contador = 0;

		try {
			//Recorremos el resultado para comprobar si el usuario está logado o no
			while (resultadoConsultaUser.next()) {
				String aux = resultadoConsultaUser.getString(3);
				contador++;
			}
		} catch (SQLException e) {
			cerrarResultado(resultadoConsultaUser);
			return -1;
		}
		if (contador == 0) {
			//No hay resultados en el sistema para ese user-password
			System.err.println("Password invalida");
			cerrarResultado(resultadoConsultaUser);
			return 2;
		}
		//Password y User están en el sistema
		resultado=3;
		cerrarResultado(resultadoConsultaUser);
		return resultado;
		/* VALORES DE LOS POSIBLES RESULTADOS
		 * -1 -Error
		 * 0 User no encontrado
		 * 1 User existente
		 * 2 Password incorrecta
		 * 3 Password y user OK
		 */
	}
	private int cerrarResultado(CachedRowSet cr)
	{
		try {
			cr.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
