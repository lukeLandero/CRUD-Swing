package Controlador;

import Modelo.PersonaDAO;
import Modelo.Persona;
import Vista.Vista; 
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Landero L. Lucio
 */

public class Controlador implements ActionListener {

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista V = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(Vista V) {
        this.V = V;
        this.V.btnListar.addActionListener(this);
        this.V.btnGuardar.addActionListener(this);
        this.V.btnEditar.addActionListener(this); 
        this.V.btnEliminar.addActionListener(this);
        this.V.btnListo.addActionListener(this);
        this.V.btnFiltrar.addActionListener(this);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == V.btnListar) {
        Listar(V.Tabla);
    }
    if (e.getSource() == V.btnGuardar) {
        agregar();
    }
    if (e.getSource() == V.btnEditar) {
        editar();
    }
    if (e.getSource() == V.btnEliminar) {
        eliminar();
    }
    if (e.getSource() == V.btnListo) {
        cerrarVentana();
    }
    if (e.getSource() == V.btnFiltrar) {
        filtrar(V.Tabla); 
    }
}

    // READ/MOSTRAR
    public void Listar(JTable tabla) {
    modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);
    List<Persona> lista = dao.listar(); 
    
    Object[] object = new Object[4];
    
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
        
            modelo.addRow(object);
        }
        limpiarCampos();
    }

    // CREATE
    public void agregar() {
        String nom = V.tfNombre.getText();
        String corr = V.tfCorreo.getText();
        String tel = V.tfTelefono.getText();

        if (nom.isEmpty() || corr.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(V, "Todos los campos deben ser completados");
            return;
        }

        p.setNombre(nom);
        p.setCorreo(corr);
        p.setTelefono(tel);

        int respuesta = dao.Agregar(p);
    
        if (respuesta == 1) {
            JOptionPane.showMessageDialog(V, "Usuario Agregado");
            Listar(V.Tabla);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(V, "Error al agregar el usuario");
        }
    }
    
    // UPDATE/ACTUALIZAR
    public void editar() {
    int fila = V.Tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(V, "Debe seleccionar una fila");
            return;
        }

    
    int id = Integer.parseInt(V.Tabla.getValueAt(fila, 0).toString());
    String nom = V.tfNombre.getText();
    String corr = V.tfCorreo.getText();
    String tel = V.tfTelefono.getText();

        Persona personaActual = dao.obtenerPorId(id);
        
        if (nom.isEmpty()) {
            nom = personaActual.getNombre();
        }
        if (corr.isEmpty()) {
            corr = personaActual.getCorreo(); 
        }
        if (tel.isEmpty()) {
            tel = personaActual.getTelefono(); 
        }

        p.setId(id);
        p.setNombre(nom);
        p.setCorreo(corr);
        p.setTelefono(tel);

        int respuesta = dao.Editar(p);

            if (respuesta == 1) {
                JOptionPane.showMessageDialog(V, "Usuario Editado");
                Listar(V.Tabla);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(V, "Error al editar el usuario");
            }
        }

    // DELETE/ELIMINAR
    public void eliminar() {
    int fila = V.Tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(V, "Debe seleccionar una fila de la tabla");
            return;
        }

        int id = Integer.parseInt(V.Tabla.getValueAt(fila, 0).toString());

        int respuesta = JOptionPane.showConfirmDialog(V, "¿Está seguro de que desea eliminar este empleado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                dao.Eliminar(id);
                JOptionPane.showMessageDialog(V, "Empleado Eliminado");
                Listar(V.Tabla);
            }
    }
    
    // FILTER/FILTRO
    public void filtrar(JTable tabla) {
    modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0); 
    
        String filtroID = V.tfID.getText();
        String filtroNombre = V.tfNombre.getText();
        String filtroCorreo = V.tfCorreo.getText();
        String filtroTelefono = V.tfTelefono.getText();
    
    List<Persona> lista = dao.filtrar(filtroID, filtroNombre, filtroCorreo, filtroTelefono);
    
    Object[] object = new Object[4];
    
        for (Persona persona : lista) {
            object[0] = persona.getId();
            object[1] = persona.getNombre();
            object[2] = persona.getCorreo();
            object[3] = persona.getTelefono();
            modelo.addRow(object);
        }
        limpiarCampos();
}
    // EXIT/SALIR/LISTO
    public void cerrarVentana() {
        int respuesta = JOptionPane.showConfirmDialog(V, "¿Está seguro de que desea salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            V.dispose(); 
        }
    
    }

    public void limpiarCampos() {
        V.tfID.setText("");
        V.tfNombre.setText("");
        V.tfCorreo.setText("");
        V.tfTelefono.setText("");
    }

}
