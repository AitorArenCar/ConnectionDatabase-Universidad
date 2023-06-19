package view.com.company.asignatura;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DialogoEditarAsi extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNombre;
    private JComboBox comboBoxTipo;
    private JTextField texFieldCreditos;
    private JTextField textFieldsCurso;
    private JTextField textFieldCuatri;
    private JComboBox comboBoxIDProf;
    private JComboBox comboBoxIDGrado;

    public DialogoEditarAsi(String IDElemento, String nombre, String creditos, String tipo, String curso, String cuatri, String IDProfe, String IDGrado) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDatos(nombre, creditos, tipo, curso, cuatri, IDProfe, IDGrado);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(IDElemento);
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

    private void onOK(String IDElemento) {
        // add your code here
        String nombre = textFieldNombre.getText();
        String creditos = texFieldCreditos.getText();
        String tipo = comboBoxTipo.getSelectedItem().toString();
        String curso = textFieldsCurso.getText();
        String cuatrimestre = textFieldCuatri.getText();
        String profesor = comboBoxIDProf.getSelectedItem().toString();
        String grado = comboBoxIDGrado.getSelectedItem().toString();

        // Establecer la información de conexión a la base de datos
        String url = "jdbc:mysql://localhost";
        String port = "3306";
        String user = "root";
        String passwd = "";
        String db = "universidad";
        String sUrl=url + ":" + port + "/" + db + "?zeroDateTimeBehavior=convertToNull";

        // Consulta SQL de inserción
        String insertQuery = "UPDATE asignatura SET nombre = ?, creditos = ?, tipo = ?, curso = ?, cuatrimestre = ?, id_profesor = ?, id_grado = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(sUrl, user, passwd);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {


            // Establecer los valores de los parámetros en la consulta
            stmt.setString(1, nombre);
            stmt.setString(2, creditos);
            stmt.setString(3, tipo);
            stmt.setString(4, curso);
            stmt.setString(5, cuatrimestre);
            stmt.setString(6, profesor);
            stmt.setString(7, grado);
            stmt.setString(8, IDElemento);

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

    private void setDatos(String nombre, String creditos, String tipo, String curso, String cuatri, String IDProfe, String IDGrado){
        textFieldNombre.setText(nombre);
        texFieldCreditos.setText(creditos);
        textFieldsCurso.setText(curso);
        textFieldCuatri.setText(cuatri);
        int indice = comboBoxTipo.getSelectedIndex();
        for (int i = 0; i < comboBoxTipo.getItemCount(); i++){
            if(comboBoxTipo.getItemAt(i).equals(tipo)){
                indice=i;
                break;
            }
        }
        int indice2 = comboBoxIDProf.getSelectedIndex();
        for (int i = 0; i < comboBoxIDProf.getItemCount(); i++){
            if(comboBoxIDProf.getItemAt(i).equals(IDProfe)){
                indice=i;
                break;
            }
        }
        int indice3 = comboBoxIDGrado.getSelectedIndex();
        for (int i = 0; i < comboBoxIDGrado.getItemCount(); i++){
            if(comboBoxIDGrado.getItemAt(i).equals(IDGrado)){
                indice=i;
                break;
            }
        }
    }

    public JTextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public void setTextFieldNombre(JTextField textFieldNombre) {
        this.textFieldNombre = textFieldNombre;
    }

    public JComboBox getComboBoxTipo() {
        return comboBoxTipo;
    }

    public void setComboBoxTipo(JComboBox comboBoxTipo) {
        this.comboBoxTipo = comboBoxTipo;
    }

    public JTextField getTexFieldCreditos() {
        return texFieldCreditos;
    }

    public void setTexFieldCreditos(JTextField texFieldCreditos) {
        this.texFieldCreditos = texFieldCreditos;
    }

    public JTextField getTextFieldsCurso() {
        return textFieldsCurso;
    }

    public void setTextFieldsCurso(JTextField textFieldsCurso) {
        this.textFieldsCurso = textFieldsCurso;
    }

    public JTextField getTextFieldCuatri() {
        return textFieldCuatri;
    }

    public void setTextFieldCuatri(JTextField textFieldCuatri) {
        this.textFieldCuatri = textFieldCuatri;
    }

    public JComboBox getComboBoxIDProf() {
        return comboBoxIDProf;
    }

    public void setComboBoxIDProf(JComboBox comboBoxIDProf) {
        this.comboBoxIDProf = comboBoxIDProf;
    }

    public JComboBox getComboBoxIDGrado() {
        return comboBoxIDGrado;
    }

    public void setComboBoxIDGrado(JComboBox comboBoxIDGrado) {
        this.comboBoxIDGrado = comboBoxIDGrado;
    }

    public static void main(String[] args) {

    }
}
