package Programa;

public class Principal {

public static void main(String args[]){
//Creamos un objeto del tipo Interfaz
Interfaz info = new Interfaz();
/*El objeto tendrá los metodos de la clase interfaz, usaremos setSize para el tamaño
 y setVisible para que aparezca frente al usuario*/
info.setSize(300, 300);
info.setVisible(true);
}

}

