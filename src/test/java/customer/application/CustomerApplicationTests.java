package customer.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.application.bean.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerApplicationTests {

    // ObjectMapper mapper;
    // ArrayList<Customer> customerList;
    // String customersJsonPath = "src/main/resources/data/customers.json"; // 4 customers
    // String customerJsonPath = "src/main/resources/data/existingCustomer.json"; // 1 customer
    // String newCustomerJsonPath = "src/main/resources/data/newCustomer.json"; // 1 customer

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() throws IOException {
       // initializeCustomersData();
    }

    /**
     * To test get all the customers.
     *
     * @throws Exception
     */
    @Test
    void test_getCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]", hasSize(4)));
    }

    /**
     * Get customer by id.
     */

    @Test
    public void test_getCustomerById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/812e2132-cffa-4bb6-8d17-b13c16b2c9b3"))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("812e2132-cffa-4bb6-8d17-b13c16b2c9b3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the new customer
     */
    @Test
    public void test_addCustomer() {
        Customer customer = new Customer("Araminta", "Ross", "309-555-1370", "1849 Harriet Ave, Auburn, NY 63102");
        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/addcustomer")
                    .content(asJsonString(customer))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andDo(print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Customer> getListOfAllCustomers() throws IOException {
        ObjectMapper mapper;
        String customersJsonPath = "src/main/data/master-customers-data.json";
        List<Customer> customers;
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        customers = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });
        return customers;
    }

   /* private void initializeCustomersData() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {
        });
    }*/

  /*  private String getCustomerJsonString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(customerJsonPath);
        Customer customer = mapper.readValue(customerFile, Customer.class);
        return mapper.writeValueAsString(customer);
    }*/

    /*private String createCustomerJsonString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(newCustomerJsonPath);
        Customer customer = mapper.readValue(customerFile, Customer.class);
        return mapper.writeValueAsString(customer);
    }*/
}
