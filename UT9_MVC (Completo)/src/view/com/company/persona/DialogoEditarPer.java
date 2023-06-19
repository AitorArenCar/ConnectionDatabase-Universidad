package view.com.company.persona;
import view.com.company.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DialogoEditarPer extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNIF;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido1;
    private JTextField textFieldApellido2;
    private JTextField textFieldCiudad;
    private JTextField textFieldDireccion;
    private JTextField textFieldTelefono;
    private JTextField textFieldFecha;
    private JComboBox comboBoxSexo;
    private JComboBox comboBoxTipo;

    public DialogoEditarPer(String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fecha, String sexo, String tipo) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDatos(nif, nombre, apellido1, apellido2, ciudad, direccion, telefono, fecha, sexo, tipo);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(nif);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(String NIFAntiguo) {
        // add your code here
        String NIF = textFieldNIF.getText();
        String nombre = textFieldNombre.getText();
        String apellido1 = textFieldApellido1.getText();
        String apellido2 = textFieldApellido2.getText();
        String ciudad = textFieldCiudad.getText();
        String direccion = textFieldDireccion.getText();
        String telefono = textFieldTelefono.getText();
        String fecha = textFieldFecha.getText();
        String sexo = comboBoxSexo.getSelectedItem().toString();
        String tipo = comboBoxTipo.getSelectedItem().toString();

        // Establecer la información de conexión a la base de datos
        String url = "jdbc:mysql://localhost";
        String port = "3306";
        String user = "root";
        String passwd = "";
        String db = "universidad";
        String sUrl=url + ":" + port + "/" + db + "?zeroDateTimeBehavior=convertToNull";

        // Consulta SQL de inserción
        String insertQuery = "UPDATE persona SET NIF = ?, nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, sexo = ?, tipo = ? WHERE nif = ?";

        try (Connection conn = DriverManager.getConnection(sUrl, user, passwd);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {


            // Establecer los valores de los parámetros en la consulta
            stmt.setString(1, NIF);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido1);
            stmt.setString(4, apellido2);
            stmt.setString(5, ciudad);
            stmt.setString(6, direccion);
            stmt.setString(7, telefono);
            stmt.setString(8, fecha);
            stmt.setString(9, sexo);
            stmt.setString(10, tipo);
            stmt.setString(11, NIFAntiguo);

            // Ejecutar la consulta de inserción
            int rowsAffected = stmt.executeUpdate();

            // Verificar el número de filas afectadas por la inserción
            System.out.println("Filas afectadas: " + rowsAffected);

            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    
    private void setDatos(String nif, String nombre, String apellido1, String apellido2, String ciudad, String direccion, String telefono, String fecha, String sexo, String tipo){
        textFieldNIF.setText(nif);
        textFieldNombre.setText(nombre);
        textFieldApellido1.setText(apellido1);
        textFieldApellido2.setText(apellido2);
        textFieldCiudad.setText(ciudad);
        textFieldDireccion.setText(direccion);
        textFieldTelefono.setText(telefono);
        textFieldFecha.setText(fecha);
        int indice = comboBoxSexo.getSelectedIndex();
        for (int i = 0; i < comboBoxSexo.getItemCount(); i++) {
            if (comboBoxSexo.getItemAt(i).equals(sexo)) {
                indice = i;
                break;
            }
        }
        int indice2 = comboBoxTipo.getSelectedIndex();
        for (int i = 0; i < comboBoxTipo.getItemCount(); i++) {
            if (comboBoxTipo.getItemAt(i).equals(tipo)) {
                indice = i;
                break;
            }
        }
    }

    public JTextField getTextFieldNIF() {
        return textFieldNIF;
    }

    public void setTextFieldNIF(JTextField textFieldNIF) {
        this.textFieldNIF = textFieldNIF;
    }

    public JTextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public void setTextFieldNombre(JTextField textFieldNombre) {
        this.textFieldNombre = textFieldNombre;
    }

    public JTextField getTextFieldApellido1() {
        return textFieldApellido1;
    }

    public void setTextFieldApellido1(JTextField textFieldApellido1) {
        this.textFieldApellido1 = textFieldApellido1;
    }

    public JTextField getTextFieldApellido2() {
        return textFieldApellido2;
    }

    public void setTextFieldApellido2(JTextField textFieldApellido2) {
        this.textFieldApellido2 = textFieldApellido2;
    }

    public JTextField getTextFieldCiudad() {
        return textFieldCiudad;
    }

    public void setTextFieldCiudad(JTextField textFieldCiudad) {
        this.textFieldCiudad = textFieldCiudad;
    }

    public JTextField getTextFieldDireccion() {
        return textFieldDireccion;
    }

    public void setTextFieldDireccion(JTextField textFieldDireccion) {
        this.textFieldDireccion = textFieldDireccion;
    }

    public JTextField getTextFieldTelefono() {
        return textFieldTelefono;
    }

    public void setTextFieldTelefono(JTextField textFieldTelefono) {
        this.textFieldTelefono = textFieldTelefono;
    }

    public JTextField getTextFieldFecha() {
        return textFieldFecha;
    }

    public void setTextFieldFecha(JTextField textFieldFecha) {
        this.textFieldFecha = textFieldFecha;
    }

    public JComboBox getComboBoxSexo() {
        return comboBoxSexo;
    }

    public void setComboBoxSexo(JComboBox comboBoxSexo) {
        this.comboBoxSexo = comboBoxSexo;
    }

    public JComboBox getComboBoxTipo() {
        return comboBoxTipo;
    }

    public void setComboBoxTipo(JComboBox comboBoxTipo) {
        this.comboBoxTipo = comboBoxTipo;
    }

    public static void main(String[] args) {

    }
}
