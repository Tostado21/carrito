/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tostado
 */
public class ProductoDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public Producto listarId(int id){
        String sql = "select * from producto where idProducto = "+id;
        Producto p = new Producto();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
            }
        } catch (Exception e) {
        }
        return p;
    }
    public List listar(){
        List<Producto> productos = new ArrayList();
        String sql = "select * from producto";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                Producto p = new Producto();                
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                productos.add(p);
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }
            
    public void listaImg(int id, HttpServletResponse response){
        String sql = "select * from producto where idProducto = "+id;
        InputStream input = null;
        OutputStream out = null;
        BufferedInputStream bfin = null;
        BufferedOutputStream bfout = null;
        try {
            out = response.getOutputStream();
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                input = rs.getBinaryStream("Foto");
            }
            bfin = new BufferedInputStream(input);
            bfout = new BufferedOutputStream(out);
            int i = 0;
            while((i = bfin.read())!= -1){
                bfout.write(i);
            }
        } catch (Exception e) {
        }
        
    }
}
