/**
@author Trivi
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;


public class Pedidos {
    //Servicios servicios;
    Main main;
    private JFrame frame2;//Creo el Jframe para esta clase
    private JTextField txt1 , txt2, txt3; // Declara txt1, txt2, txt3 como variables de instancia
    private JTable table; // Declara table como variable de instancia
    private String valorTextField;//Genero otra instancia para manejar los datos de textfield
    private  double totalCobrado;//Variable global para almacenar el total cobrado
    private List<List<Object>> data;//Esta lista almacena los datos de la tabla (Numericos) que despues toma reportes
    private HashMap<Integer, Double> totalesPorMesa = new HashMap<>();//Mapea los datos para el total de la mesa osea el cobro
    private HashMap<Integer, Double> totalPorMesa = new  HashMap<>();//Mapeo auxiliar de los datos de la mesa






    //Constructor de la clase Pedidos
    public Pedidos(Main main, int numMesas , String valorTextField) {

        this.valorTextField = valorTextField;
        table = new JTable();//Creo mi Jtable
        data = new ArrayList<>();//Creo mi lista de datos
        Productos productos = new Productos(this);//Creo el objeto productos para poder utilizar sus valores y metodos

        // CREACION DEL FRAME Y SUS VALORES
        frame2 = new JFrame();
        frame2.setTitle("Pedidos");
        frame2.setSize(800, 600);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setLayout(null); // Establece el administrador de diseño en null
        frame2.getContentPane().setBackground(Color.pink);//Color
        frame2.setIconImage(new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png").getImage());//Icono

        //Establecemos el logo
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\IconCoffe.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBounds(60, 10, 100, 100);
        frame2.add(imageLabel);

        //Establecemos el título
        JLabel labelCoffee = new JLabel("COFFEE SHOP");
        labelCoffee.setFont(new Font("Arial", Font.BOLD, 14));
        labelCoffee.setForeground(new Color(165, 42, 42));
        labelCoffee.setBounds(58, 100, 150, 50);
        frame2.add(labelCoffee);

        // Define los datos y las columnas de la tabla
        String[] columnNames = {"PAPAS FRITAS", "JUGO", "SODA"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Crea la tabla y la agrega a un JScrollPane
        table = new JTable(model); // Inicializa table
        JScrollPane scrollPane = new JScrollPane(table);//El JsrollPane es para que la tabla tenga barras de desplazamiento
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // Aplicar el renderer a cada columna de la tabla
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }




        // Crea un JPanel para contener la tabla
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(356, 90, 400, 300); // Define la posición y el tamaño del panel
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        frame2.add(tablePanel);

        // Crea un JComboBox y lo llena con el número de opciones especificado en el JTextField de la clase Main
        JComboBox comboBox = new JComboBox();
        int numOptions = Integer.parseInt(valorTextField);//Esta variable se alimenta desde el TextField de la clase Main
        //Con el for genero el recorrido para las mesas y poder agregarlas al JComboBox
        for (int i = 1; i <= numOptions; i++) {
            comboBox.addItem("MESA " + i);
        }
        comboBox.setBounds(20, 150, 150, 20); // Define la posición y el tamaño del JComboBox
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Obtén el número de la mesa seleccionada
                String mesa = (String) comboBox.getSelectedItem();
                int numeroMesa = Integer.parseInt(mesa.split(" ")[1]);

                // Crea una nueva tabla para la mesa seleccionada
                String[] columnNames = {"PAPAS FRITAS", "JUGO", "SODA"};

                //Crea un nuevo modelo de tabla
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                //Este modelo se alimenta de los datos de la tabla

                // Obtén los pedidos de la mesa seleccionada
                List<Object[]> pedidosMesa = productos.getPedidos(numeroMesa);

                // Agrega los pedidos a la tabla
                for (Object[] pedido : pedidosMesa) {
                    model.addRow(pedido);
                }

                // Establece el modelo de la tabla
                table.setModel(model);
                txt1.setText(""); // Reiniciar valor de txt1
                txt2.setText(""); // Reiniciar valor de txt2
                txt3.setText(""); // Reiniciar valor de txt3
            }
        });
        frame2.add(comboBox);

        // Crea un JLabel para mensaje de selección de mesa
        JLabel label = new JLabel("Selecciona la mesa:");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBounds(20, 180, 150, 20); // Define la posición y el tamaño del JLabel
        frame2.add(label);

        //Defino Jlabel para mostrar el total
        JLabel labelTotal = new JLabel();
        labelTotal.setFont(new Font("Arial", Font.PLAIN, 16));
        labelTotal.setBounds(356, 400, 400, 20); // Define la posición y el tamaño del JLabel
        frame2.add(labelTotal);

        // Crea los botones "Cobrar", "Añadir" y "Cancelar"
        JButton cobrarButton = new JButton("COBRAR");
        cobrarButton.setBounds(350, 500, 130, 50); // Define la posición y el tamaño del botón "Cobrar"
        cobrarButton.setBackground(Color.lightGray);
        cobrarButton.setBorder(BorderFactory.createEtchedBorder());
        // Agrega un ActionListener al botón "Cobrar"
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si todos los valores en la tabla son cero
                boolean todosCeros = true;
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        if ((int) model.getValueAt(i, j) != 0) {
                            todosCeros = false;
                            break;
                        }
                    }
                    if (!todosCeros) {
                        break;
                    }
                }
                // Si todos los valores son cero, mostrar un mensaje de error
                if (todosCeros) {
                    JOptionPane.showMessageDialog(null, "No es posible cobrar un pedido con todos los valores en cero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double total = 0.0;//Aqui guardo el total de la mesa

                int rowCount = model.getRowCount();//Obtengo el numero de filas de la tabla
                //Recorro la tabla para obtener el total de la mesa
                for (int i = 0; i<rowCount; i++){
                    for (int j = 0; j < model.getColumnCount(); j++){
                        String product = model.getColumnName(j);//Obtengo el nombre del producto y su columna
                        int quantity = (int) model.getValueAt(i, j);//Obtengo la cantidad del producto(su valor)
                        total += productos.getPrice(product) * quantity;//Calculo el total de la mesa
                        // multiplicando por el precio del producto
                    }
                }
                totalCobrado = total; // Actualiza el total cobrado

                // Obtén el número de la mesa seleccionada
                String mesa = (String) comboBox.getSelectedItem();
                int numeroMesa = Integer.parseInt(mesa.split(" ")[1]);

                // Almacena el total de la mesa en el HashMap
                totalesPorMesa.put(numeroMesa, total);

                labelTotal.setText("TOTAL: $ " + total);//Muestra el total de la mesa
                labelTotal.setFont(new Font("Arial", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, "PEDIDO COBRADO CON EXITO","Cobro exitoso", JOptionPane.INFORMATION_MESSAGE);
                txt1.setText(""); // Reiniciar valor de txt1
                txt2.setText(""); // Reiniciar valor de txt2
                txt3.setText(""); // Reiniciar valor de txt3
            }
        });
        frame2.add(cobrarButton);

        JButton addButton = new JButton("AÑADIR");
        addButton.setBounds(500, 500, 130, 50); // Define la posición y el tamaño del botón "Añadir"
        addButton.setBackground(Color.lightGray);
        addButton.setBorder(BorderFactory.createEtchedBorder());
        //Agrega un ActionListener al botón "Añadir"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui obtengo el texto (valor indicado de cada uno de los textfields)
                String text1 = txt1.getText();
                String text2 = txt2.getText();
                String text3 = txt3.getText();

                if (text1.isEmpty()) {
                    text1 = "0";
                }
                if (text2.isEmpty()) {
                    text2 = "0";
                }
                if (text3.isEmpty()) {
                    text3 = "0";
                }

                try {
                    //Convierto los valores de los textfields a enteros
                    int num1 = Integer.parseInt(text1);
                    int num2 = Integer.parseInt(text2);
                    int num3 = Integer.parseInt(text3);

                    //Agregar los datos a la tabla y la lista de pedidos
                    Object[] row = {num1, num2, num3};

                    // Obtén el número de la mesa seleccionada
                    String mesa = (String) comboBox.getSelectedItem();
                    //Con el metodo split obtengo el numero de la mesa
                    //.split() en Java se utiliza para dividir una cadena de texto en varias subcadenas basadas en el delimitador proporcionado.
                    int numeroMesa = Integer.parseInt(mesa.split(" ")[1]);
                    //Agrego los pedidos a la mesa
                    productos.agregarPedido(numeroMesa, row);

                    //En este if valido que los valores sean mayores o iguales a 0
                    if (num1 >= 0 && num2 >= 0 && num3 >= 0) {
                        // Agrega los datos a la tabla
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(row);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido");
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor válido");
                }
            }
        });
        frame2.add(addButton);


        //Genero el boton cancelar
        JButton cancelButton = new JButton("CANCELAR");
        cancelButton.setBounds(650, 500, 130, 50); // Define la posición y el tamaño del botón "Cancelar"
        cancelButton.setBackground(Color.lightGray);
        cancelButton.setBorder(BorderFactory.createEtchedBorder());
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpia la tabla
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                labelTotal.setText(""); // Limpia el total
            }
        });
        frame2.add(cancelButton);

        //Genero los boton Go que es el que me transporta los datos a reportes
        JButton GO = new JButton("==>>");
        GO.setBackground(Color.green);
        GO.setBorder(BorderFactory.createEtchedBorder());
        GO.setBounds(630, 10,50,30);
        GO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtengo los datos de la tabla almacenados en la lista data y se los paso a getData
                ArrayList<Object[]> data = getData();
                //En este if valido que la lista data no este vacia
                if(data.size() == 0){
                    JOptionPane.showMessageDialog(null, "Por favor, genere un pedido");
                    return;
                }
                //Creo un objeto de la clase Reportes y le paso los datos necesarios
                Reportes reportes = new Reportes(Pedidos.this, data);
                reportes.mostrarFrame(); // Muestra el frame de Reportes
                frame2.dispose();
                frame2.setVisible(false);
            }
        });
        frame2.add(GO);

        //Boton para finalizar la aplicaacion
        JButton Exit2 = new JButton("Exit");
        Exit2.setBackground(Color.red);
        Exit2.setBorder(BorderFactory.createEtchedBorder());
        Exit2.setBounds(700,10,50,30);
        Exit2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cierra mi aplicacion
                System.exit(0);

            }
        });
        frame2.add(Exit2);

        //Creo un textfield para cada producto
        txt1 = new JTextField(); // Inicializa txt1
        txt1.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font("Arial", Font.BOLD, 14);
        txt1.setFont(font);
        txt2 = new JTextField(); // Inicializa txt2
        txt2.setHorizontalAlignment(JTextField.CENTER);
        txt2.setFont(font);
        txt3 = new JTextField(); // Inicializa txt3
        txt3.setHorizontalAlignment(JTextField.CENTER);
        txt3.setFont(font);

        //Establezco las posiciones de los textfields
        txt1.setBounds(150, 270, 100, 30);
        txt2.setBounds(150, 370, 100, 30);
        txt3.setBounds(150, 470, 100, 30);

        //Agrego los textfields al frame
        frame2.add(txt1);
        frame2.add(txt2);
        frame2.add(txt3);

        //Agrego las imagenes de los productos y sus propiedades
        ImageIcon img1 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\papas-fritas (1).png");
        ImageIcon img2 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\zumo-de-naranja (1).png");
        ImageIcon img3 = new ImageIcon("C:\\Users\\trivi\\IdeaProjects\\Cafeteria_pro\\src\\IMG\\botella-de-soda (1).png");

        //Escala de las imagenes
        Image img1Scaled = img1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image img2Scaled = img2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image img3Scaled = img3.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        //Agrego las imagenes a los labels
        JLabel label1 = new JLabel(new ImageIcon(img1Scaled));
        JLabel label2 = new JLabel(new ImageIcon(img2Scaled));
        JLabel label3 = new JLabel(new ImageIcon(img3Scaled));
        JLabel labelPrecio1 = new JLabel("$10.00");
        JLabel labelPrecio2 = new JLabel("$5.00");
        JLabel labelPrecio3 = new JLabel("$8.00");



        //Establezco las posiciones de los labels
        label1.setBounds(20, 230, 100, 100);
        label2.setBounds(20, 330, 100, 100);
        label3.setBounds(20, 430, 100, 100);
        labelPrecio1.setBounds(280, 272, 50, 20);
        labelPrecio2.setBounds(280, 372, 50, 20);
        labelPrecio3.setBounds(280, 475, 50, 20);


        //Agrego los labels al frame
        frame2.add(label1);
        frame2.add(label2);
        frame2.add(label3);
        frame2.add(labelPrecio1);
        frame2.add(labelPrecio2);
        frame2.add(labelPrecio3);


        frame2.setLocationRelativeTo(null);
        frame2.setResizable(false);
        frame2.setVisible(true);
    }

    //En este metodo retorno los datos de la tabla hacia reportes
    public  JTable getTable(){
        return this.table;
    }

    //En este metodo retorno el total cobrado
    public double getTotalDeTodasLasMesas() {
        double total = 0.0;//Variable donde se almacenna el total
        //Recorro el mapa de totales por mesa para obtener el total de todas las mesas

        //Recorro el mapa de totales por mesa para obtener el total de todas las mesas (foreach)
        for (double totalMesa : totalesPorMesa.values()) {
            total += totalMesa;
        }
        return total;

    }


    public void mostrarPromedioVentasPorMesa() {

        StringBuilder mensaje = new StringBuilder();

        // Recorrer las mesas y calcular el promedio de ventas
        for (Map.Entry<Integer, Double> entry : totalesPorMesa.entrySet()) {
            int numeroMesa = entry.getKey();
            double totalMesa = entry.getValue();

            // Obtener la cantidad total de productos vendidos en esta mesa
            int cantidadTotal = 0;
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Recorrer la tabla para obtener la cantidad total de productos vendidos
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    cantidadTotal += (int) model.getValueAt(i, j);//Obtengo la cantidad total de productos vendidos
                }
            }

            // Calcular el promedio de ventas
            double promedio = totalMesa / cantidadTotal;

            // Construir el mensaje para esta mesa
            mensaje.append("El promedio de venta de la mesa ").append(numeroMesa).append(" es: $").append(String.format("%.2f", promedio)).append("\n");
        }

        // Mostrar el mensaje en un JOptionPane
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Promedio de Ventas por Mesa", JOptionPane.INFORMATION_MESSAGE);
    }


    //En este metodo retorno el producto mas vendido
    public String getProductoMasVendido() {
        HashMap<String, Integer> ventasProductos = new HashMap<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();
        // Recorrer la tabla para obtener las ventas totales de cada producto
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                String producto = model.getColumnName(j);//Obtengo el nombre del producto
                int cantidad = (int) model.getValueAt(i, j);
                // Actualizar las ventas del producto
                ventasProductos.put(producto, ventasProductos.getOrDefault(producto, 0) + cantidad);
            }
        }
        // Encontrar el producto más vendido
        String productoMasVendido = "";
        int maxVentas = 0;
        boolean primerProducto = true; // Variable para controlar el primer producto
        // Recorrer el mapa de ventas de productos, para encontrar el producto más vendido
        for (Map.Entry<String, Integer> entry : ventasProductos.entrySet()) {
            if (primerProducto) { // Si es el primer producto, establecerlo como el máximo
                productoMasVendido = entry.getKey();//Obtengo el nombre del producto
                maxVentas = entry.getValue();//Obtengo el valor de las ventas
                primerProducto = false; // Cambiar la variable para no entrar aquí nuevamente
            } else {
                // Si el producto actual tiene más ventas que el máximo, actualizar el máximo
                if (entry.getValue() > maxVentas) {
                    productoMasVendido = entry.getKey();
                    maxVentas = entry.getValue();
                }
            }
        }
        return productoMasVendido;
    }

    //En este metodo obtengo los datos de la tabla y es el que paso con el boton GO
    public ArrayList<Object[]> getData(){
        //Con estos datos obtengo los datos de la tabla, el modelo y tanto las filas como las columnas
        ArrayList<Object[]> data = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();//Obtengo el modelo de la tabla
        //Obtengo el numero de filas y columnas de la tabla
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();
        //Recorro la tabla para obtener los datos
        for (int i = 0; i < rowCount; i++) {
            Object[] row = new Object[colCount];//Este objeto se crea con la finalidad de manejar los datos de las filas
            for (int j = 0; j < colCount; j++) {
                row[j] = model.getValueAt(i, j);//obtengo los valores de la columna y la fila
            }
            data.add(row);
        }
        return data;
    }

    //En este metodo muestro el frame
    public void mostrarFrame() {
        frame2.setVisible(true);
    }

}