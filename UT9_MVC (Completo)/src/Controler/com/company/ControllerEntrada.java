package Controler.com.company;

import Connecion.ConectionBD;
import model.com.company.*;
import view.com.company.*;
import view.com.company.asignatura.*;
import view.com.company.persona.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ControllerEntrada implements ActionListener, MouseListener, WindowListener, KeyListener {

    private final PanelVentana frEntrada = new PanelVentana();
    private final DefaultTableModel m = null;
    private Boolean whichTable=true; //true=personas    false=asignaturas

    // Constructor lanza cada uno de los procedimientos de la aplicación
    public ControllerEntrada() {
        iniciarVentana();
        iniciarEventos();
        prepararBaseDatos();
    }
    public void iniciarVentana() {
        frEntrada.setVisible(true);
    }

    public void iniciarEventos() {
        frEntrada.getButton1().addActionListener(this::actionPerformed);
        frEntrada.getButton2().addActionListener(this::actionPerformed);
        frEntrada.getButton3().addActionListener(this::actionPerformed);
        frEntrada.getButton4().addActionListener(this::actionPerformed);
        frEntrada.getPanel1().addMouseListener(this);
        frEntrada.getTable1().addMouseListener(this);
        frEntrada.addWindowListener(this);
        frEntrada.getTextField1().addKeyListener(this);

    }
    public void prepararBaseDatos() {
        if(whichTable){
            ModelPersonas entradaPer = new ModelPersonas();
            frEntrada.getTable1().setModel(entradaPer.CargaDatos(m));
            whichTable=false;
        } else{
            ModelAsignaturas entradaAsig = new ModelAsignaturas();
            frEntrada.getTable1().setModel(entradaAsig.CargaDatos(m));
            whichTable=true;
        }

    }

    public void actualizarTabla() {
        String textoBusqueda = frEntrada.getTextField1().getText();

        if (whichTable) {
            ModelAsignaturas entrada = new ModelAsignaturas();
            frEntrada.getTable1().setModel(entrada.buscarDatos(textoBusqueda.trim(), m));

        } else {
            ModelPersonas entrada = new ModelPersonas();
            frEntrada.getTable1().setModel(entrada.buscarDatos(textoBusqueda.trim(), m));
        }
    }

    public Object[] mostrarDato(int selectedRow){
        if(whichTable){
            // Obtener los datos del elemento seleccionado
            String id = frEntrada.getTable1().getValueAt(selectedRow, 0).toString();
            String nombre = frEntrada.getTable1().getValueAt(selectedRow, 1).toString();
            String creditos = frEntrada.getTable1().getValueAt(selectedRow, 2).toString();
            String tipo = frEntrada.getTable1().getValueAt(selectedRow, 3).toString();
            String curso = frEntrada.getTable1().getValueAt(selectedRow, 4).toString();
            String cuatri = frEntrada.getTable1().getValueAt(selectedRow, 5).toString();
            String idProfe = frEntrada.getTable1().getValueAt(selectedRow, 6).toString();
            String idGrado = frEntrada.getTable1().getValueAt(selectedRow, 7).toString();

            // Mostrar los datos en un cuadro de diálogo
            String mensaje = "NIF: " + id + "\n"
                    + "Nombre: " + nombre + "\n"
                    + "Creditos: " + creditos + "\n"
                    + "Tipo: " + tipo + "\n"
                    + "Curso: " + curso + "\n"
                    + "Cuatrimestre: " + cuatri + "\n"
                    + "ID_Profesor: " + idProfe + "\n"
                    + "ID_Grado: " + idGrado + "\n\n"
                    + "¿Estás seguro de que deseas borrar este elemento?";

            //ya que tenemos tanto el mensaje como el valor del id/nif devolvemos un objeto para poder devolver ambos valores
            Object[] resultado = new Object[2];
            resultado[0]=mensaje;
            resultado[1]=id;

            return resultado;
        } else {
            // Obtener los datos del elemento seleccionado
            String nif = frEntrada.getTable1().getValueAt(selectedRow, 0).toString();
            String nombre = frEntrada.getTable1().getValueAt(selectedRow, 1).toString();
            String apellido1 = frEntrada.getTable1().getValueAt(selectedRow, 2).toString();
            String apellido2 = frEntrada.getTable1().getValueAt(selectedRow, 3).toString();
            String ciudad = frEntrada.getTable1().getValueAt(selectedRow, 4).toString();
            String direccion = frEntrada.getTable1().getValueAt(selectedRow, 5).toString();
            String telefono = frEntrada.getTable1().getValueAt(selectedRow, 6).toString();
            String fecha = frEntrada.getTable1().getValueAt(selectedRow, 7).toString();
            String sexo = frEntrada.getTable1().getValueAt(selectedRow, 8).toString();
            String tipo = frEntrada.getTable1().getValueAt(selectedRow, 9).toString();

            // Mostrar los datos en un cuadro de diálogo
            String mensaje = "NIF: " + nif + "\n"
                    + "Nombre: " + nombre + "\n"
                    + "Apellido1: " + apellido1 + "\n"
                    + "Apellido2: " + apellido2 + "\n"
                    + "Ciudad: " + ciudad + "\n"
                    + "Dirección: " + direccion + "\n"
                    + "Teléfono: " + telefono + "\n"
                    + "Fecha: " + fecha + "\n"
                    + "Sexo: " + sexo + "\n"
                    + "Tipo: " + tipo + "\n\n"
                    + "¿Estás seguro de que deseas borrar este elemento?";

            //ya que tenemos tanto el mensaje como el valor del id/nif devolvemos un objeto para poder devolver ambos valores
            Object[] resultado = new Object[2];
            resultado[0]=mensaje;
            resultado[1]=nif;
            return resultado;
        }
    }
    public void borrarDato(String id){
        String url = "jdbc:mysql://localhost";
        String port = "3306";
        String db = "universidad";
        String user = "root";
        String passwd = "";
        String sUrl = url + ":" + port + "/" + db + "?zeroDateTimeBehavior=convertToNull";
        if (whichTable) {
            try (Connection connection = DriverManager.getConnection(sUrl, user, passwd)) {
                String sql = "DELETE FROM asignatura WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);

                    int filasAfectadas = statement.executeUpdate();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (Connection connection = DriverManager.getConnection(sUrl, user, passwd)) {
                String sql = "DELETE FROM persona WHERE nif = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, id);

                    int filasAfectadas = statement.executeUpdate();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Agregar":
                System.out.println("Has presionado agregar");
                if(whichTable){
                    System.out.println("Ventana Agregar Asi abierta");
                    DialogoAgregarAsi miDialogo = new DialogoAgregarAsi();
                    miDialogo.setSize(400, 400);
                    miDialogo.setLocation(400, 300);
                    miDialogo.setTitle("Entrada de Asignatura");
                    miDialogo.setVisible(true);
                } else{
                    System.out.println("Ventana Agregar Per abierta");
                    DialogoAgregarPer miDialogo = new DialogoAgregarPer();
                    miDialogo.setSize(400, 400);
                    miDialogo.setLocation(100, 50);
                    miDialogo.setTitle("Entrada de Persona");
                    miDialogo.setVisible(true);
                }
                actualizarTabla();
                break;

            case "Editar":
                System.out.println("Has presionado editar");
                if(whichTable){
                    int selectedRow = frEntrada.getTable1().getSelectedRow();
                    if(selectedRow>=0){
                        System.out.println("Ventana Editar Asi abierta");

                        //Pasar datos de la tabla a String
                        String IDElemento = frEntrada.getTable1().getValueAt(selectedRow, 0).toString();
                        String nombre = frEntrada.getTable1().getValueAt(selectedRow, 1).toString();
                        String creditos = frEntrada.getTable1().getValueAt(selectedRow, 2).toString();
                        String tipo = frEntrada.getTable1().getValueAt(selectedRow, 3).toString();
                        String curso = frEntrada.getTable1().getValueAt(selectedRow, 4).toString();
                        String cuatri = frEntrada.getTable1().getValueAt(selectedRow, 5).toString();
                        String IDProfe = frEntrada.getTable1().getValueAt(selectedRow, 6).toString();
                        String IDGrado = frEntrada.getTable1().getValueAt(selectedRow, 7).toString();

                    DialogoEditarAsi miDialogo = new DialogoEditarAsi(IDElemento, nombre, creditos, tipo, curso, cuatri, IDProfe, IDGrado);
                    miDialogo.setSize(400, 400);
                    miDialogo.setLocation(400, 300);
                    miDialogo.setTitle("Edición de Asignatura");
                    miDialogo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frEntrada, "Seleccione un elemento para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else{

                    int selectedRow = frEntrada.getTable1().getSelectedRow();
                    if(selectedRow>=0){
                        System.out.println("Ventana Editar Per abierta");

                        //Pasar datos de la tabla a String
                        String nif = frEntrada.getTable1().getValueAt(selectedRow, 0).toString();
                        String nombre = frEntrada.getTable1().getValueAt(selectedRow, 1).toString();
                        String apellido1 = frEntrada.getTable1().getValueAt(selectedRow, 2).toString();
                        String apellido2 = frEntrada.getTable1().getValueAt(selectedRow, 3).toString();
                        String ciudad = frEntrada.getTable1().getValueAt(selectedRow, 4).toString();
                        String direccion = frEntrada.getTable1().getValueAt(selectedRow, 5).toString();

                        String telefono = (frEntrada.getTable1().getValueAt(selectedRow, 6) != null) ? frEntrada.getTable1().getValueAt(selectedRow, 6).toString() : "";

                        String fecha = frEntrada.getTable1().getValueAt(selectedRow, 7).toString();
                        String sexo = frEntrada.getTable1().getValueAt(selectedRow, 8).toString();
                        String tipo = frEntrada.getTable1().getValueAt(selectedRow, 9).toString();

                        //Pasar datos por parametros para mostrarlos en la nueva ventana
                        DialogoEditarPer miDialogo = new DialogoEditarPer(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fecha, sexo, tipo);
                        miDialogo.setSize(400, 400);
                        miDialogo.setLocation(100, 50);
                        miDialogo.setTitle("Eición de Persona");
                        miDialogo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frEntrada, "Seleccione un elemento para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                actualizarTabla();
                break;

            case "Borrar":
                System.out.println("Has presionado borrar");

                // Obtener el índice de la fila seleccionada en la tabla
                int selectedRow = frEntrada.getTable1().getSelectedRow();

                if (selectedRow >= 0) {
                    Object[] resultado = mostrarDato(selectedRow);
                    //obtencion del mensaje usando el mensaje devuelto por mostrarDato
                    String mensaje = (String) resultado[0];

                    int respuesta = JOptionPane.showConfirmDialog(frEntrada, mensaje, "Borrar elemento", JOptionPane.YES_NO_OPTION);

                    if (respuesta == JOptionPane.YES_OPTION) {
                        // Realizar la eliminación del elemento usando el id devuelto por mostrarDato
                        borrarDato((String) resultado[1]);

                        // Actualizar la tabla
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(frEntrada, "Seleccione un elemento para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case "Cambiar Tabla":
                prepararBaseDatos();
                break;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("ha salido del programa");
        frEntrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ConectionBD.closeConn();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getComponent().equals(frEntrada.getTextField1()))
            System.out.println("Ha escrito la letra: " + e.getKeyChar() + " en el Jtext del usuario");
        actualizarTabla();

    }
}



