package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Carrito;
import modelo.Producto;
import modelo.ProductoDAO;

public class Controlador extends HttpServlet {

    ProductoDAO pdao = new ProductoDAO();
    Producto p;
    
    
    List<Carrito> listaCarrito = new ArrayList<Carrito>();
    int item;
    double totalpagar;
    int cantidad = 1;
    
    Carrito car ;
    int idp;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            /* TODO output your page here. You may use following sample code. */
        String accion = request.getParameter("accion");
        List productos = pdao.listar();
        switch(accion){
            case "Comprar":
                totalpagar = 0.0;
                idp = Integer.parseInt(request.getParameter("id"));
                p = new Producto();
                p = pdao.listarId(idp);
                
                item = item + 1;
                
                car= new Carrito();
                car.setItem(item);
                car.setIdProducto(p.getId());
                car.setNombres(p.getNombres());
                car.setDescripcion(p.getDescripcion());
                car.setPrecioCompra(p.getPrecio());
                car.setCantidad(cantidad);
                car.setSubtotal(cantidad*p.getPrecio());
                listaCarrito.add(car);
                
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalpagar = totalpagar + listaCarrito.get(i).getSubtotal();
                }
                
                request.setAttribute("totalpagar", totalpagar);
                request.setAttribute("carrito", listaCarrito);
                request.setAttribute("contador", listaCarrito.size());
                System.out.println(listaCarrito.size());
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "AgregarCarrito":
                idp = Integer.parseInt(request.getParameter("id"));
                p = new Producto();
                p = pdao.listarId(idp);
                
                item = item + 1;
                
                car= new Carrito();
                car.setItem(item);
                car.setIdProducto(p.getId());
                car.setNombres(p.getNombres());
                car.setDescripcion(p.getDescripcion());
                car.setPrecioCompra(p.getPrecio());
                car.setCantidad(cantidad);
                car.setSubtotal(cantidad*p.getPrecio());
                
                listaCarrito.add(car);
                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Delete":
                int idproducto=Integer.parseInt(request.getParameter("idp"));
                
                for (int i = 0; i < listaCarrito.size(); i++) {
                    if (listaCarrito.get(i).getIdProducto() == idproducto) {
                        listaCarrito.remove(i);
                    }
                }
                break;
            case "Carrito":
                totalpagar = 0.0;
                request.setAttribute("carrito", listaCarrito);
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalpagar = totalpagar + listaCarrito.get(i).getSubtotal();
                }
                request.setAttribute("totalpagar", totalpagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "home":
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
                default:
                
        }   
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
