import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VentanaAltas extends JFrame {
    JTextField txtNombre, txtDNI, txtTelefono, txtEmail;
    Connection conexion;

    public VentanaAltas(Connection conexion){

        this.conexion=conexion;

        setBounds(100, 100, 500, 300);

        getContentPane().setLayout(new FlowLayout());

        JLabel lblNombre=new JLabel("Nombre:");
        add(lblNombre);

        txtNombre=new JTextField("", 40);
        add(txtNombre);

        txtDNI=new JTextField("DNI", 40);
        add(txtDNI);

        txtTelefono=new JTextField("Teléfono", 40);
        add(txtTelefono);

        txtEmail=new JTextField("Email", 40);
        add(txtEmail);

        JButton btnAlta=new JButton("Añadir usuario");
        btnAlta.addActionListener(new CallBackAlta());
        add(btnAlta);

    }

    private class CallBackAlta implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            PreparedStatement s=null;
            String consulta="INSERT INTO usuarios (nombre, dni, telefono, email) " +
                    "VALUES (?, ?, ?, ?)";
            try {
                s=conexion.prepareStatement(consulta);

                s.setString(1, txtNombre.getText());
                s.setString(2, txtDNI.getText());
                s.setString(3, txtTelefono.getText());
                s.setString(4, txtEmail.getText());

                s.executeUpdate();

                txtNombre.setText("");
                txtDNI.setText("");
                txtEmail.setText("");

                JOptionPane.showMessageDialog(null, "Usuario añadido");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
