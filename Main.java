/**
@author Trivi
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Se importan las librerias necesarias

public class Main extends JFrame{


    private JTextField textField;
    //Generamos esta instancia para poder utilizarla en la clase Pedidos
    //Para alimentar el combobox de dicha clase

    public Main(){
        //Generamos los parametros del Jframe
        setTitle("COFFE SHOP");
        setSize(400, 500);//Tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.pink);//Color
        setIconImage(new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png").getImage());
        setLayout(null);

        // Creamos el label
        JLabel label = new JLabel("INDIQUE EL NUMERO DE MESAS");
        label.setBounds(90, 225, 220, 60);//Parametros de posicion y tamaño
        label.setFont(new Font("Arial", Font.BOLD, 13));//Fuente y tamaño
        add(label, BorderLayout.CENTER );

        // Creamos el textfield
        textField = new JTextField();
        textField.setBounds(120, 290, 120, 30);
        textField.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font("Arial", Font.BOLD, 16);
        textField.setFont(font);
        add(textField, BorderLayout.CENTER);

        //Creamo el boton
        JButton button = new JButton("ACEPTAR");
        button.setBounds(120, 340, 120, 50);
        button.setBackground(new Color(165, 42, 42));
        button.setForeground(new Color(0,0,0));
        button.setBorder(BorderFactory.createEtchedBorder());//Aqui cambiamos el estilo del borde del boton
        // Agregamos el evento al boton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //En este int guardamos el valor que se ingresa en el textfield
                    int numMesas = Integer.parseInt(textField.getText());
                    if (numMesas <= 0) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor mayor a 0");
                        return;
                    }
                    String valorTextField = textField.getText(); // Obtén el valor del textField
                    new Pedidos(Main.this, numMesas, valorTextField); // Pasa el valor del textField al constructor de Pedidos
                    dispose();
                    setVisible(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido");
                }
            }
        });

        // Agregamos el boton al JFrame
        add(button, BorderLayout.CENTER);

        // Cargamos la imagen
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBounds(85, -10, 200, 200);
        add(imageLabel);

        // Creamos el label de bienvenida
        JLabel welcomeLabel = new JLabel("WELCOME TO COFFE SHOP");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(165, 42, 42));
        welcomeLabel.setBounds(60, 180, 300, 30);
        add(welcomeLabel, BorderLayout.CENTER);


        setLocationRelativeTo(null);//Centramos la ventana
        setResizable(false);//Quitamos la opcion de maximizar
        setVisible(true);
    }

    //Main, para inicializar la aplicacion
    public static void main(String[] args) {
        new  Main();

    }
}