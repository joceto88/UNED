package Programa;
 
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.commons.codec.digest.DigestUtils;
 
@SuppressWarnings("serial")
public class Interfaz extends JFrame {
     
    //Estos seran los elementos que tendra nla interfaz
    String usuario;
    String contra;
    JTextField Tusuario;
    JPasswordField Tcontra;
     
    //El constructor es que ser� llamado se cree una instancia de la clase, es donde crearemos los elementos de la interfaz
    public Interfaz(){
        //Es el titulo que llevar� la ventana
        super("Login de la aplicaci�n");
        //Dejar� el layout como null para poder poner los elementos mediante el metodo setBounds
        setLayout(null);
        //Aqu� se define el comportamiento que tendr� la ventana al pulsarse el bot�n de cerrar
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocation nos permitir� indicar donde se posicionar� la venta al abrirse
        this.setLocation(300, 200);
         
 
        //Se crea el label y la caja de texto donde se colocar� el usuario
        JLabel Lusuario = new JLabel("Usuario");
        Lusuario.setBounds(10, 50, 150, 30);
        add(Lusuario);
         
        Tusuario = new JTextField();
        Tusuario.setBounds(100, 50, 150, 30);
        add(Tusuario);
         
 
        //Se crea el label y la caja de contrase�a
        JLabel Lcontra = new JLabel("Contrase�a");
        Lcontra.setBounds(10, 100, 150, 30);
        add(Lcontra); 
        Tcontra = new JPasswordField();
        Tcontra.setBounds(100, 100, 150, 30);

        add(Tcontra);
         
        //Se crea el boton y tambi�n se define el comportamiento que tendr�, en este caso invocaremos el m�todo Verificacion
        JButton ingresar = new JButton("Ingresar");
        ingresar.setBounds(80, 150, 150, 30);
        ingresar.setContentAreaFilled(false);
        ingresar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Verificacion();
            }
        });
        add(ingresar);
         
    }
     
    public int Verificacion() {
 
        //Aqu� guardaremos el contenido de las cajas de texto y se las asignaremos a las variables
        usuario = Tusuario.getText();
        contra = DigestUtils.md5Hex(Tcontra.getPassword().toString());         
        //Creamos un objeto del tipo conexi�n para realizar la validaci�n de los datos intruducidos por el usuario
        Conexion log = new Conexion();
        //Invoncamos el m�todo login que requiere 2 paramentros, en este caso los 2 strings que tiene la informaci�n del usuario
        return log.ValidaUsuario(usuario,contra);

    }
    
 
}