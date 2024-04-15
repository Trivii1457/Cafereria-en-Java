/**
@author Trivi
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class Reportes {
    private Pedidos pedidos;
    //private Datos datos;
    JFrame frame3;

    private ArrayList<Object[]> data;



    public Reportes(Pedidos pedidos, ArrayList<Object[]> data){
        this.pedidos = pedidos;
        this.data = data;
        Productos product = new Productos(pedidos);
        ArrayList<Object[]> data1 = pedidos.getData();//En este arraylist se guardan los datos de la clase Pedidos, previamente escaneados
        JTable table = pedidos.getTable();//Aqui se guarda la tabla de pedidos
        Object valor = table.getModel().getValueAt(0, 0);//Aquui se guarda tanto el modelo de la tabla como el valor de la tabla
        //Genero el JFrame
        frame3 = new JFrame();
        frame3.setTitle("Reportes");
        frame3.setSize(600, 500);
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3.setLayout(null); // Establece el administrador de diseño en null
        frame3.getContentPane().setBackground(Color.pink);
        frame3.setIconImage(new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png").getImage());

        // Carga las imágenes
        ImageIcon imageIcon1 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png");
        Image img1 = imageIcon1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon imageIcon2 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\reporte (2).png");
        ImageIcon imageIcon3 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\reporte (1) (1).png");
        ImageIcon imageIcon4 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\caliente (1).png");

        // Crea JLabel con las imágenes
        JLabel label1 = new JLabel(new ImageIcon(img1));
        JLabel label2 = new JLabel(imageIcon2);
        JLabel label3 = new JLabel(imageIcon3);
        JLabel label4 = new JLabel(imageIcon4);

        // Ajusta las dimensiones y la posición de las imágenes
        label1.setBounds(240, 10, 100, 100);
        label2.setBounds(400, 125, 140, 140);
        label3.setBounds(400, 225, 140, 140);
        label4.setBounds(400, 325, 140, 140);

        // Agrega las imágenes al JFrame
        frame3.add(label1);
        frame3.add(label2);
        frame3.add(label3);
        frame3.add(label4);

        //En este boton enseño el resumen total de las ventas
        JButton button1 = new JButton("RESUMEN TOTAL DE LAS VENTAS");
        button1.setBackground(Color.lightGray);
        button1.setBorder(BorderFactory.createEtchedBorder());
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui es donde se utilizar el metodo de la case Pedidos para poder eseñar el total de las ventas
                double totalVentas = pedidos.getTotalDeTodasLasMesas();
                JOptionPane.showMessageDialog(frame3, "El total de las ventas es: " + totalVentas);
            }
        });

        //En este boton se muestra el promedio de ventas por mesa
        JButton button2 = new JButton("PROMEDIO DE VENTAS POR MESA");
        button2.setBackground(Color.lightGray);
        button2.setBorder(BorderFactory.createEtchedBorder());
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //metodo del promedio de ventas por mesa
                pedidos.mostrarPromedioVentasPorMesa();
            }

        });



        //En este boton se muestra el producto mas vendido
        JButton button3 = new JButton("PRODUCTO MÁS VENDIDO");
        button3.setBackground(Color.lightGray);
        button3.setBorder(BorderFactory.createEtchedBorder());
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui se utiliza el metodo de la clase Pedidos para poder enseñar el producto mas vendido
                String productoMasVendido = pedidos.getProductoMasVendido();
                JOptionPane.showMessageDialog(frame3, "El producto más vendido es: " + productoMasVendido);
            }
        });

        //Botones Funcionales
        JButton Back1 = new JButton("<=");
        Back1.setBackground(Color.yellow);
        Back1.setBorder(BorderFactory.createEtchedBorder());
        //Genero el actionListener
        Back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pedidos.mostrarFrame();//Aqui se llama al metodo mostrarFrame de la clase Pedidos para volver a la ventana anterior
                frame3.dispose();
                frame3.setVisible(false);
            }
        });

        JButton Exit1 = new JButton("Exit");
        Exit1.setBackground(Color.red);
        Exit1.setBorder(BorderFactory.createEtchedBorder());
        Exit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }//Aqui se cierra el programa
        });

        // Ajusta las dimensiones y la posición de los botones pequeños
        Back1.setBounds(440, 10, 50, 30); // Esquina superior derecha
        Exit1.setBounds(500, 10, 50, 30); // Justo debajo del primer botón pequeño

        // Agrega los botones pequeños al JFrame
        frame3.add(Back1);
        frame3.add(Exit1);

        // Ajusta las dimensiones y la posición de los botones
        button1.setBounds(80, 170, 300, 50);
        button2.setBounds(80, 270, 300, 50);
        button3.setBounds(80, 370, 300, 50);

        // Agrega los botones al JFrame
        frame3.add(button1);
        frame3.add(button2);
        frame3.add(button3);

        frame3.setLocationRelativeTo(null);//Aqui se centra el frame
        frame3.setResizable(false);//Aqui se establece que no se pueda cambiar el tamaño del frame
        frame3.setVisible(true);
    }
    public void mostrarFrame(){
        frame3.setVisible(true);
    }//Aqui se muestra el frame

};