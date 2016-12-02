package me.bantling.j2ee.basics.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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
import me.bantling.j2ee.basics.dao.AddressDAO;
import me.bantling.j2ee.basics.form.ObjectCreators;
import me.bantling.j2ee.basics.validation.ValidationError;
import me.bantling.j2ee.basics.validation.Validators;

/**
 * Servlet to handle reading and writing {@link Address} objects
 */
@WebServlet(
  name = "Address",
  urlPatterns = "/address",
  loadOnStartup = 1
)
public class AddressServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(AddressServlet.class);
  
  /**
   * Create the address table and insert one row into it
   */
  @Override
  public void init(
  ) throws ServletException {
    // Create an address table and insert one row into it that we can search for
    try {
      final InitialContext ctx = new InitialContext();
      final DataSource ds = (DataSource)(ctx.lookup("jdbc/mem"));
      
      try (
        final Connection conn = ds.getConnection();
      ) {
        AddressDAO.createTable(conn);
        AddressDAO.insert(
          conn,
          new Address(
            1,
            "7071 Bayers Rd",
            null,
            "Halifax",
            Country.CAN,
            Region.NS,
            "B3L 2C2"
          )
        );
      }
    } catch (final Exception e) {
      throw new ServletException(e);
    }
    
    log.info("initialized HSQL address table");
  }

  /**
   * Get the single address for display
   */
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
      ) {
        address = AddressDAO.select(conn, 1);
      }
    } catch (final Exception e) {
      throw new ServletException(e);
    }
    
    log.debug("Retrieved Address = {}", address);
    
    // Provide the address to the view
    request.setAttribute("address", address);
  
    // Render the view
    request.getRequestDispatcher("/WEB-INF/jsp/address.jsp").forward(request, response);
    
    log.info("< doGet");
  }
  
  /**
   * Update the single address
   */
  @Override
  protected void doPost(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws ServletException, IOException {
    log.info("> doPost");
    
    // Get the address from the form parameters
    final Address address = ObjectCreators.ADDRESS_CREATOR.create(request);
    log.debug("Address = {}", address);
    
    // Validate the address
    final List<ValidationError> errors = Validators.ADDRESS_VALIDATOR.validate(address);
    log.debug("Validation errors = {}", errors);
    
    // If there are no errors, save the address in the database
    if (errors.isEmpty()) {
      try {
        final InitialContext ctx = new InitialContext();
        final DataSource ds = (DataSource)(ctx.lookup("jdbc/mem"));
        
        try (
          final Connection conn = ds.getConnection();
        ) {
          AddressDAO.update(conn, address);
        }
      } catch (final Exception e) {
        throw new ServletException(e);
      }
    } else {
      // Provide the errors to the view
      request.setAttribute("errors", errors);
    }
    
    /*
     * Regardless, provide the given address to the view. If rhe given address is invalid, it lets the user see the
     * invalid data and correct it.
     */
    request.setAttribute("address", address);
  
    // Render the address
    request.getRequestDispatcher("/WEB-INF/jsp/address.jsp").forward(request, response);

    log.info("< doPost");
  }
  
  @Override
  public void destroy(
  ) {
    log.info("destroyed");
  }
}
