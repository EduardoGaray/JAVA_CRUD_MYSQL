import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VentanaModificar extends JFrame {
    JTextField txtNombre, txtDNI, txtTelefono, txtEmail;
    JLabel lblId;
    Connection conexion;
    ResultSet resultSet;

    public VentanaModificar(Connection conexion) {

        this.conexion = conexion;
        setBounds(100, 100, 500, 300);
        getContentPane().setLayout(new FlowLayout());

        lblId=new JLabel("0");
        add(lblId);

        txtNombre=new JTextField("", 40);
        add(txtNombre);

        txtDNI=new JTextField("DNI", 40);
        add(txtDNI);

        txtTelefono=new JTextField("Teléfono", 40);
        add(txtTelefono);

        txtEmail=new JTextField("Email", 40);
        add(txtEmail);

        JButton btnAnterior=new JButton("Anterior");
        add(btnAnterior);
        btnAnterior.addActionListener(new CallBackAnterior());

        JButton btnSiguiente=new JButton("Siguiente");
        add(btnSiguiente);
        btnSiguiente.addActionListener(new CallBackSiguiente());

        JButton btnModificar=new JButton("Modificar");
        add(btnModificar);
        btnModificar.addActionListener(new CallBackModificar());

        JButton btnEliminar=new JButton("Eliminar");
        add(btnEliminar);
        btnEliminar.addActionListener(new CallBackEliminar());

        try {
            //Creamos un statemen y realizamos la consulta

            String consulta="SELECT * FROM usuarios";
            Statement statement;
            statement = conexion.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(consulta);

            //Nos colocamos en el primer elemento de la BBDD que devuelve la consulta
            resultSet.next();

            //Actualizamos los controles con los datos del primer registro
            lblId.setText(String.valueOf(resultSet.getInt("id")));
            txtNombre.setText(resultSet.getString("nombre"));
            txtDNI.setText(resultSet.getString("dni"));
            txtTelefono.setText(resultSet.getString("telefono"));
            txtEmail.setText(resultSet.getString("email"));

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

    }

    private class CallBackEliminar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(lblId.getText());
            int respuesta = JOptionPane.showConfirmDialog(null,"¿Estás seguro?", "Warning", JOptionPane.YES_NO_OPTION);
            if(respuesta ==JOptionPane.YES_OPTION){
                String consulta = "DELETE FROM usuarios WHERE id=?";
                try{
                    PreparedStatement ps = conexion.prepareStatement(consulta);
                    ps.setInt(1,id);
                    ps.executeUpdate();
                }  catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            } else {

            }
        }
    }

    private class CallBackModificar implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(lblId.getText());
            String consulta = "UPDATE usuarios SET nombre=?, dni=?, telefono=?, email=? WHERE id=?";
            try {
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setString(1,txtNombre.getText());
                ps.setString(2,txtDNI.getText());
                ps.setString(3,txtTelefono.getText());
                ps.setString(4,txtEmail.getText());
                ps.setInt(5,id);
                ps.executeUpdate();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    private class CallBackSiguiente implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(resultSet.next()) {
                    //Actualizamos los controles con los datos del primer registro
                    lblId.setText(String.valueOf(resultSet.getInt("id")));
                    txtNombre.setText(resultSet.getString("nombre"));
                    txtDNI.setText(resultSet.getString("dni"));
                    txtTelefono.setText(resultSet.getString("telefono"));
                    txtEmail.setText(resultSet.getString("email"));
                }
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }
    }

    private class CallBackAnterior implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(resultSet.previous()) {
                    //Actualizamos los controles con los datos del primer registro
                    lblId.setText(String.valueOf(resultSet.getInt("id")));
                    txtNombre.setText(resultSet.getString("nombre"));
                    txtDNI.setText(resultSet.getString("dni"));
                    txtTelefono.setText(resultSet.getString("telefono"));
                    txtEmail.setText(resultSet.getString("email"));
                }
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }
    }
}
