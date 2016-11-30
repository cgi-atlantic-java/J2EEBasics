package me.bantling.j2ee.basics.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.bantling.j2ee.basics.bean.Address;
import me.bantling.j2ee.basics.bean.Country;
import me.bantling.j2ee.basics.bean.Region;

@WebServlet(
  name = "Address",
  urlPatterns = "/address",
  loadOnStartup = 1
)
public class AddressServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(AddressServlet.class);
  
  @Override
  public void init(
  ) throws ServletException {
    // Create an address table and insert one row into it that we can search for
    try {
      final InitialContext ctx = new InitialContext();
      final DataSource ds = (DataSource)(ctx.lookup("jdbc/mem"));
      
      try (
        final Connection conn = ds.getConnection();
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
        
        stmt.executeQuery(
          "insert into address values(" +
          "  1," +
          "  '7071 Bayers Rd'," +
          "  null," +
          "  'Halifax'," +
          "  'CAN'," +
          "  'NS'," +
          "  'B3L 2C2'" +
          ")"
        );
      }
    } catch (final Exception e) {
      throw new ServletException(e);
    }
    
    log.info("initialized HSQL address table");
  }

  @Override
  protected void doGet(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws ServletException, IOException {
    log.info("> doGet");
    
    // Retrieve the one address
    final Address address;
    
    try {
      final InitialContext ctx = new InitialContext();
      final DataSource ds = (DataSource)(ctx.lookup("jdbc/mem"));
      
      try (
        final Connection conn = ds.getConnection();
        final Statement stmt = conn.createStatement();
        final ResultSet rs = stmt.executeQuery(
          "select id, line1, line2, city, country, region, postal_code from address"
        );
      ) {        
        if (rs.next()) {
          address = new Address(
            rs.getInt(1),                     // id
            rs.getString(2),                  // line1
            rs.getString(3),                  // line2
            rs.getString(4),                  // city
            Country.valueOf(rs.getString(5)), // country
            Region.valueOf(rs.getString(6)),  // region
            rs.getString(7)                   // postal_code
          );
          
          if (rs.next()) {
            // Must never have multiple addresses
            throw new ServletException("Multiple Addresses");
          }
        } else {
          // Must always have an address
          throw new ServletException("No Addresses");
        }
      }
    } catch (final Exception e) {
      throw new ServletException(e);
    }
    
    // Provide the address to the view
    request.setAttribute("address", address);
  
    // Render the view
    request.getRequestDispatcher("/WEB-INF/jsp/address.jsp").forward(request, response);
    
    log.info("< doGet");
  }
  
  @Override
  public void destroy(
  ) {
    log.info("destroyed");
  }
}
