import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;

class Main {
    
    public static void main(String args[]) throws IOException{
        ClienteMenor jenny = new ClienteMenor();

        jenny.setId(1);
        jenny.setNombre("Jenny");
        jenny.setApellido("Quiroz");
        jenny.setGenero("Femenino");
        jenny.setFechaNacimiento("23/09/1994");
        jenny.setEstadocivil("Soltero");

        ClienteMenor jorge = new ClienteMenor();
        jorge.setId(2);
        jorge.setNombre("Jorge");
        jorge.setApellido("Giraldo");
        jorge.setGenero("Masculino");
        jorge.setFechaNacimiento("06/04/1993");
        jorge.setEstadocivil("Soltero");

        ClienteMayor beatriz = new ClienteMayor();
        beatriz.setId(3);
        beatriz.setNombre("Beatriz");
        beatriz.setApellido("Muñoz");
        beatriz.setGenero("Femenino");
        beatriz.setFechaNacimiento("14/02/1983");
        beatriz.setEstadocivil("Soltero");

        TipoItem type = new TipoLegal();
        type.setDescripcion("camisa");
        type.setIdDescripcion(1);

        TipoItem type1 = new TipoLegal();
        type1.setDescripcion("zapatos");
        type1.setIdDescripcion(2);

        TipoItem type2 = new TipoLegal();
        type2.setDescripcion("celular");
        type2.setIdDescripcion(3);

        Item item1 = new ItemLocal();
        item1.setId(1);
        item1.setDescripcion("nike 3038");
        item1.setValorUnidad(4000.0);
        item1.setTipoItem(type);

        Item item2 = new ItemLocal();
        item2.setId(2);
        item2.setDescripcion("Jordan 4835");
        item2.setValorUnidad(5000.0);
        item2.setTipoItem(type2);

        Item item3 = new ItemLocal();
        item3.setId(3);
        item3.setDescripcion("Iphone 1522");
        item3.setValorUnidad(10000.0);
        item3.setTipoItem(type2);

        ArrayList <Item> itemsFacturas = new ArrayList<>();
        itemsFacturas.add(item1);

        Factura factura1 = new FacturaPagada();
        factura1.setNroFactura(1);
        factura1.setFechaFactura("15/04/2012");
        factura1.setCliente(jenny);
        factura1.setTotalFactura(3000.0);
        factura1.setEstado("pagada");
        factura1.setItems(itemsFacturas);

        Factura factura2 = new FacturaPagada();
        factura2.setNroFactura(2);
        factura2.setFechaFactura("20/06/2012");
        factura2.setCliente(jorge);
        factura2.setTotalFactura(3000.0);
        factura2.setEstado("pendiente");
        factura2.setItems(itemsFacturas);

        Factura factura3 = new FacturaPagada();
        factura3.setNroFactura(3);
        factura3.setFechaFactura("20/01/2000");
        factura3.setCliente(beatriz);
        factura3.setTotalFactura(3000.0);
        factura3.setEstado("vencida");
        factura3.setItems(itemsFacturas);

        FactoryProvider provider = new FactoryProvider();
        AbstractFactory factoryFacturas = provider.getFactory("facturaFactory");
        AbstractFactory factoryClientes = provider.getFactory("clienteFactory");
        AbstractFactory factoryItems = provider.getFactory("itemFactory");
        AbstractFactory factoryTypeItems = provider.getFactory("tipoFactory");

        FacturaDaoImpl facturaDao = new FacturaDaoImpl();
        ClienteDaoImpl clienteDao = new ClienteDaoImpl();
        ItemDaoImpl itemDao = new ItemDaoImpl();
        TipoItemDaoImpl tipoItemDao = new TipoItemDaoImpl();

        clienteDao.addCliente(jenny);
        clienteDao.addCliente(jorge);
        clienteDao.addCliente(beatriz);
        
        itemDao.addItem(item1);
        itemDao.addItem(item2);
        itemDao.addItem(item3);

        tipoItemDao.addTipoItem(type);
        tipoItemDao.addTipoItem(type1);
        tipoItemDao.addTipoItem(type2);

        facturaDao.addFactura(factura1);
        facturaDao.addFactura(factura2);
        facturaDao.addFactura(factura3);


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bienvenido a nuestra tienda, eres el administrador? s/n");
        String res = br.readLine();
        while(true){
            if(res.equals("s")){
                break;
            }
            System.out.println("Si, usted si es administrador, Escriba s");
            res = br.readLine();
        }

        
        System.out.println("Hola Administrador, qué deseas hacer?");

        while(true){

            System.out.println("Crear Nueva factura. (c)");
            System.out.println("Ver historial de las facturas. (h)");
            System.out.println("Actualizar una factura. (a)");
            System.out.println("Borrar una factura. (b)");
            System.out.println("Deseas salir. (salir)");

            String resA = br.readLine();

            if(resA.equals("c")){

                System.out.println(" seleccinaste Crear Nueva Factura ");
                Factura newFactura;
                System.out.println("Qué tipo de factura desea crear, factura Vencida o Pagada? (v/p)");

                String tipoFactura = br.readLine();

                if(tipoFactura.equals("v")){

                    newFactura = factoryFacturas.getFactura("facturaVencida");

                }else{

                    System.out.println("Por defecto, factura Pagada.");
                    newFactura = factoryFacturas.getFactura("facturaPagada");

                }

                Random  rnd = new Random();
                int nroFactura = (int) (rnd.nextDouble() * 100 + 20);

                System.out.println("ingrese la fecha de la factura:");
                String fecha = br.readLine();

                System.out.println("ingrese el total:");
                Double total = Double.parseDouble( br.readLine() );

                System.out.println("ingrese el estado:");
                String estado = br.readLine();
                
                System.out.println("Ahora, su cliente. él es de tipo Mayor o Menor de edad? (m/n)");
                Cliente cliente;

                String tipoCliente = br.readLine();

                if(tipoCliente.equals("m") ){
                    cliente = factoryClientes.getCliente("clienteMayor");
                }else{
                    cliente = factoryClientes.getCliente("clienteMenor");
                }

                System.out.println("Ahora, empecemos a agregar los items que el cliente ha comprado:");
                ArrayList <Item> itemsComprados = new ArrayList<>();

                while(true){
                    System.out.println("A continuación, se mostrarán los items en venta:");
                    itemDao.printItem();

                    System.out.println("\n Escriba el ID del item que desea incluir a la factura");
                    int idItem = Integer.parseInt( br.readLine() );
                
                    for(Item item: itemDao.items()){
                        if( idItem == item.getId() ){
                            itemsComprados.add(item);
                        }
                    }

                    System.out.println("Desea agregar otro item? (s/n)");
                    String respNewItem = br.readLine();

                    if(respNewItem.equals("s")){

                    }else{
                        break;
                    }

                }

                newFactura.setNroFactura(nroFactura);
                newFactura.setFechaFactura(fecha);
                newFactura.setCliente(cliente);
                newFactura.setTotalFactura(total);
                newFactura.setEstado(estado);
                newFactura.setItems(itemsComprados);

                facturaDao.addFactura(newFactura);

                System.out.println("Se agregado Factura exitosamente.");

            }else if(resA.equals("h")){

                System.out.println("Imprimiendo historial de facturas...");
                System.out.println();

                //imprime todas las facturas del daoFacturas
                facturaDao.printFacturas();
                System.out.println();

            }else if(resA.equals("a")){
                facturaDao.printFacturas();
                System.out.println();
                System.out.println("Empecemos, ingresa el Numero de la factura a editar:");

                int nroFactura = Integer.parseInt( br.readLine() );

                Factura newFactura = factoryFacturas.getFactura("facturaVencida");
                
                for(Factura factura: facturaDao.facturas){
                    if( factura.getNroFactura() == nroFactura ){
                        newFactura = factura; 
                    }
                }

                System.out.println("Qué quieres cambiar de la factura?");
                System.out.println("fecha de la factura? (f)");
                System.out.println("total factura? (t)");
                System.out.println("estado de factura? (e)");

                String aspecto = br.readLine();

                if( aspecto.equals("f") ){
                    
                    System.out.println("ingrese la nueva fecha:");
                    
                    String newFecha = br.readLine();
                    newFactura.setFechaFactura(newFecha);

                    System.out.println("Fecha modificada exitosamente.\n");

                }else if( aspecto.equals("t") ){

                    System.out.println("ingrese el nuevo total:");
                    
                    Double newTotal = Double.parseDouble( br.readLine() );
                    newFactura.setTotalFactura(newTotal);

                    System.out.println("Total modificado exitosamente.\n");

                }else if( aspecto.equals("e") ){
                    System.out.println("ingrese el nuevo estado:");
                    
                    String newEstado = br.readLine();
                    newFactura.setEstado(newEstado);

                    System.out.println("Estado modificado exitosamente.\n");
                }else{
                    System.out.println("No se realizará ningún cambio.");
                }

                facturaDao.updateFactura(nroFactura, newFactura);

            }else if(resA.equals("b")){

                facturaDao.printFacturas();
                System.out.println();

                System.out.println("Por favor ingresa el numero de factura que deseas eliminar.");

                int nroFactura = Integer.parseInt( br.readLine() );
                facturaDao.delFactura(nroFactura);

                System.out.println("Factura eliminada exitosamente.\n");

            }else if(resA.equals("salir")){
                System.out.println("Cerrando sistema.Chao..");
                break;
            }
        
        }

    }
}
