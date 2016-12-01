<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="me.bantling.j2ee.basics.bean.Country"%>
<%@ page import="me.bantling.j2ee.basics.bean.Region"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
  <head>
    <title>Address</title>
    <script type="text/javascript">
      var labelsByCountry = {};
      var regionsByCountry = {};
      
      function pageLoaded() {
        // Create a JS mapping of all countries to their regions
        <c:forEach var="country" items="${Country.values()}">
          labelsByCountry["${country.name()}"] = {
            regionLabel: "${country.regionLabel()}",
            postalCodeLabel: "${country.postalCodeLabel()}"
          }; 
        
          var countryRegions;
          regionsByCountry["${country.name()}"] = countryRegions = [];
        
          <c:forEach var="region" items="${Region.values()}">
            <c:if test="${region.country == country}">
              countryRegions.push({value: "${region.name()}", text: "${region.toString()}"});
            </c:if>
          </c:forEach>
        </c:forEach>
        
        // Get the province and postal code labels
        var regionLabel = document.querySelectorAll("label[for='region']")[0];
        var postalCodeLabel = document.querySelectorAll("label[for='postalCode']")[0];
        
        // Get the country and region drop downs
        var countrySelect = document.getElementById("country");
        var regionSelect = document.getElementById("region");
        
        countrySelect.addEventListener("change", function(e) {
          // Remove all but "Select a Region" from regions drop down 
          regionSelect.options.length = 1;
          
          // Populate if the country isn't "Select a Country"
          if (countrySelect.selectedIndex !== 0) {
            // Get selected country code
            var selectedCountry = countrySelect.options[countrySelect.selectedIndex].value;
            
            // Set language of labels
            countryLabels = labelsByCountry[selectedCountry];
            regionSelect.options[0].text = "Select a " + countryLabels.regionLabel;
            regionLabel.innerText = countryLabels.regionLabel;
            postalCodeLabel.innerText = countryLabels.postalCodeLabel;
            
            // Populate regions of the selected country
            var countryRegions = regionsByCountry[selectedCountry];
            for (var i in countryRegions) {
              var region = countryRegions[i];
              
              regionSelect.options[regionSelect.options.length] = new Option(region.text, region.value);
            }
          }
        });
      }
    </script>
  </head>
  <body onload="pageLoaded();">
    <h1>Address</h1>
    
    <table>
      <tbody>
        <tr>
          <th><label for="line1">Line 1</label></th>
          <td><input type="text" id="line1" name="line1" value="${address.line1}"/></td>
        </tr><tr>
          <th><label for="line2">Line 2</label></th>
          <td><input type="text" id="line2" name="line2" value="${address.line2}"/></td>
        </tr><tr>
          <th><label for="city">City</label></th>
          <td><input type="text" id="city" name="city" value="${address.city}"/></td>
        </tr><tr>
          <th><label for="country">Country</label></th>
          <td>
            <select id="country" name="country">
              <option>Select a Country</option>
              <c:forEach var="country" items="${Country.values()}">
                <option value="${country}"
                  <c:if test="${address.country == country}">selected="selected"</c:if>
                >${country.toString()}</option>
              </c:forEach>
            </select>
          </td>
        </tr><tr>
          <th>
            <label for="region">${address.country.regionLabel()}</label>
          </th>
          <td>
            <select id="region" name="region">
              <option>Select a ${address.country.regionLabel()}</option>
              <c:forEach var="region" items="${Region.values()}">
                <c:if test="${address.country == region.country}">
                  <option value="${region}"
                    <c:if test="${address.region == region}">selected="selected"</c:if>
                  >${region.toString()}</option>
                </c:if>
              </c:forEach>
            </select>
          </td>
        </tr><tr>
          <th>
            <label for="postalCode">${address.country.postalCodeLabel()}</label>
          </th>
          <td><input type="text" id="postalCode" name="postalCode" value="${address.postalCode}"/></td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
