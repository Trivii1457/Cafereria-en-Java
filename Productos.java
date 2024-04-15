/**
@author Trivi
*/
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Productos {
    Pedidos pedidos;//Tomo todos los datos de la clase Pedidos

    //Estos son lo datos de la tabla de pedidos que los escaneamos y los guardamos en un mapa
    private Map<Integer, List<Object[]>> pedidosPorMesa;
    private Map<String, Double> PriceProduct;

    //Pasamos todos esos datos en el constructor de nuestra clase productos
    public Productos(Pedidos pedidos){
        this.pedidos = pedidos;//Aqui se almacenan los datos de la clase Pedidos
        this.pedidosPorMesa = new HashMap<>();//Aqui se almacenan los pedidos por mesa
        this.PriceProduct = new HashMap<>();//

        //Defino los precios de los productos
        PriceProduct.put("PAPAS FRITAS", 10.0);
        PriceProduct.put("JUGO", 5.0);
        PriceProduct.put("SODA", 8.0);

    }

    public double getPrice(String product){
        return PriceProduct.getOrDefault(product, 0.0);
    }
    public void agregarPedido(int mesa, Object[] pedido){
        //containsKey() es un método de la clase HashMap que devuelve true si
        // si el mapa contiene una clave específica(nombre de lo que se esta referenciando)
        if (!pedidosPorMesa.containsKey(mesa)){
            pedidosPorMesa.put(mesa, new ArrayList<>());
        }
        //Verifica lo que se agregara a la mesa
        pedidosPorMesa.get(mesa).add(pedido);
    }

    //Get de lo pedido por mesa
    public List<Object[]> getPedidos(int mesa){
        return pedidosPorMesa.getOrDefault(mesa, new ArrayList<>());//Devuelve los pedidos por mesa
    }
}
