package br.com.kneesapp.base.dao;

import java.sql.Connection;

/**
 *
 * @author andre.andrade
 */
public interface IPathImage {
    
    String readPath(Connection conn) throws Exception;
}
