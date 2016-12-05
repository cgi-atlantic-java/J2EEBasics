package me.bantling.j2ee.basics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.bantling.j2ee.basics.bean.Address;
import me.bantling.j2ee.basics.bean.Country;
import me.bantling.j2ee.basics.bean.Region;

/**
 * Persist an {@link Address} to the database
 */
public class AddressDAO {
  /**
   * Create the table
   * 
   * Wouldn't normally have this in a DAO, but since the database is in memory, the table has to be created at
   * every startup.
   * 
   * @param conn
   * @throws SQLException
   */
  public static void createTable(
    final Connection conn
  ) throws SQLException {
    try (
      final Statement stmt = conn.createStatement();
    ) {
      stmt.execute(
        "create table address(" +
        "  id          integer primary key," +
        "  line1       varchar(32) not null," +
        "  line2       varchar(32) default null," +
        "  city        varchar(32) not null," +
        "  country     varchar(32) not null," +
        "  region      varchar(2) not null," +
        "  postal_code varchar(16) not null," +
        "  constraint address_id_pos check(id > 0)," +
        "  constraint address_line1_ne check(line1 != '')," +
        "  constraint address_line2_ne check(line2 != '')," +
        "  constraint address_city_ne check(city != '')," +
        "  constraint address_country_ne check(country != '')," +
        "  constraint region_ne check(region != '')," +
        "  constraint address_postal_code_ne check(postal_code != '')" +
        ")"
      );
    }
  }
  
  /**
   * Insert an Address
   * 
   * @param conn
   * @param address
   * @throws SQLException
   */
  public static void insert(
    final Connection conn,
    final Address address
  ) throws SQLException {
    // We can prevent SQL injection attacks by always using a PreparedStatement for DML
    try (
      final PreparedStatement stmt = conn.prepareStatement(
        "insert into address (" +
        "  id," +
        "  line1," +
        "  line2," +
        "  city," +
        "  country," +
        "  region," +
        "  postal_code" +
        ") values (?, ?, ?, ?, ?, ?, ?)"
      );
    ) {
      int i = 1;
      stmt.setInt(i++, address.getId());
      stmt.setString(i++, address.getLine1());
      stmt.setString(i++, address.getLine2());
      stmt.setString(i++, address.getCity());
      stmt.setString(i++, address.getCountry().name());
      stmt.setString(i++, address.getRegion().name());
      stmt.setString(i++, address.getPostalCode());
      
      final int affected = stmt.executeUpdate();
      if (affected != 1) {
        throw new SQLException("Expected one row to be affected, actually affected " + affected);
      }
      
      conn.commit();
    } catch (final SQLException e) {
      conn.rollback();
      
      throw e;
    }
  }
  
  /**
   * Select an Address by id
   * 
   * @param conn
   * @param id
   * @return
   * @throws SQLException
   */
  public static Address select(
    final Connection conn,
    final int id
  ) throws SQLException {
    Address address;
    
    try (
      final PreparedStatement stmt = conn.prepareStatement(
        "select id, line1, line2, city, country, region, postal_code" +
        "  from address" +
        " where id = ?"
      );
    ) {
      stmt.setInt(1, id);
      
      try (
        final ResultSet rs = stmt.executeQuery();
      ) {
        if (rs.next()) {
          int i = 1;
          
          address = new Address(
            rs.getInt(i++),                     // id
            rs.getString(i++),                  // line1
            rs.getString(i++),                  // line2
            rs.getString(i++),                  // city
            Country.valueOf(rs.getString(i++)), // country
            Region.valueOf(rs.getString(i++)),  // region
            rs.getString(i++)                   // postal_code
          );
          
          if (rs.next()) {
            // Must never have multiple addresses
            throw new SQLException("Multiple Addresses with id = " + id);
          }
        } else {
          // Must find an address by the id given
          throw new SQLException("No Address found with id = " + id);
        }
      }
    }
    
    return address;
  }
  
  /**
   * Update an Address by id
   * 
   * @param conn
   * @param address
   * @throws SQLException
   */
  public static void update(
    final Connection conn,
    final Address address
  ) throws SQLException {
    try (
      final PreparedStatement stmt = conn.prepareStatement(
        "update address set" +
        "       line1 = ?," +
        "       line2 = ?," +
        "       city = ?," +
        "       country = ?," +
        "       region = ?," +
        "       postal_code = ?" +
        " where id = ?"
      );
    ) {
      int i = 1;
      stmt.setString(i++, address.getLine1());
      stmt.setString(i++, address.getLine2());
      stmt.setString(i++, address.getCity());
      stmt.setString(i++, address.getCountry().name());
      stmt.setString(i++, address.getRegion().name());
      stmt.setString(i++, address.getPostalCode());
      stmt.setInt(i++, address.getId());
      
      final int affected = stmt.executeUpdate();
      if (affected != 1) {
        throw new SQLException(
          "Expected one row to be affected, actually affected " + affected
        );
      }
      
      conn.commit();
    } catch (final SQLException e) {
      conn.rollback();
      
      throw e;
    }
  }
}
