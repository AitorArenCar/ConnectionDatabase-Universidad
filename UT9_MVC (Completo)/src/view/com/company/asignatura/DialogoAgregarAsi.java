package view.com.company.asignatura;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class DialogoAgregarAsi extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNombre;
    private JTextField textFieldCreditos;
    private JComboBox comboBoxTipo;
    private JTextField textFieldCurso;
    private JTextField textFieldCuatri;
    private JComboBox comboBoxIDProfe;
    private JComboBox comboBoxIDGrado;


    public DialogoAgregarAsi() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

    private void onOK() {
        // add your code here
        String nombre = textFieldNombre.getText();
        String creditos = textFieldCreditos.getText();
        String tipo = comboBoxTipo.getSelectedItem().toString();
        String curso = textFieldCurso.getText();
        String cuatrimestre = textFieldCuatri.getText();
        String profesor = comboBoxIDProfe.getSelectedItem().toString();
        String grado = comboBoxIDGrado.getSelectedItem().toString();

        // Establecer la información de conexión a la base de datos
        String url = "jdbc:mysql://localhost";
        String port = "3306";
        String user = "root";
        String passwd = "";
        String db = "universidad";
        String sUrl=url + ":" + port + "/" + db + "?zeroDateTimeBehavior=convertToNull";

        // Consulta SQL de inserción
        String insertQuery = "INSERT INTO asignatura (nombre, creditos, tipo, curso, cuatrimestre, id_profesor, id_grado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    public JTextField getTextFieldNombre() {
        return textFieldNombre;
    }

    public void setTextFieldNombre(JTextField textFieldNombre) {
        this.textFieldNombre = textFieldNombre;
    }

    public JTextField getTextFieldCreditos() {
        return textFieldCreditos;
    }

    public void setTextFieldCreditos(JTextField textFieldCreditos) {
        this.textFieldCreditos = textFieldCreditos;
    }

    public JComboBox getComboBoxTipo() {
        return comboBoxTipo;
    }

    public void setComboBoxTipo(JComboBox comboBoxTipo) {
        this.comboBoxTipo = comboBoxTipo;
    }

    public JTextField getTextFieldCurso() {
        return textFieldCurso;
    }

    public void setTextFieldCurso(JTextField textFieldCurso) {
        this.textFieldCurso = textFieldCurso;
    }

    public JTextField getTextFieldCuatri() {
        return textFieldCuatri;
    }

    public void setTextFieldCuatri(JTextField textFieldCuatri) {
        this.textFieldCuatri = textFieldCuatri;
    }

    public JComboBox getComboBoxIDProfe() {
        return comboBoxIDProfe;
    }

    public void setComboBoxIDProfe(JComboBox comboBoxIDProfe) {
        this.comboBoxIDProfe = comboBoxIDProfe;
    }

    public JComboBox getComboBoxIDGrado() {
        return comboBoxIDGrado;
    }

    public void setComboBoxIDGrado(JComboBox comboBoxIDGrado) {
        this.comboBoxIDGrado = comboBoxIDGrado;
    }

    public static void main(String[] args) {
        DialogoAgregarAsi dialog = new DialogoAgregarAsi();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
