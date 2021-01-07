package customer.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.application.bean.Customer;
import customer.application.controller.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper mapper;
	ArrayList<Customer> customerList;

	String customersJsonPath = "src/main/resources/data/customers.json"; // 4 customers
	String customerJsonPath = "src/main/resources/data/existingCustomer.json"; // 1 customer
	String newCustomerJsonPath = "src/main/resources/data/newCustomer.json"; // 1 customer

	@BeforeEach
	void setUp() throws IOException {
		initializeCustomersData();
	}

	/**
	 * To test get all the customers.
	 * @throws Exception
	 */
	@Test
	void test_getCustomers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*]").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[*]",hasSize(4)));
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void initializeCustomersData() throws IOException {
		mapper = new ObjectMapper();
		File customersFile = new File(customersJsonPath);
		customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {});
	}

	private String getCustomerJsonString() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File customerFile = new File(customerJsonPath);
		Customer customer = mapper.readValue(customerFile, Customer.class);
		return mapper.writeValueAsString(customer);
	}

	private String createCustomerJsonString() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File customerFile = new File(newCustomerJsonPath);
		Customer customer = mapper.readValue(customerFile, Customer.class);
		return mapper.writeValueAsString(customer);
	}
}

	git init
	git add README.md
		git commit -m "first commit"
		git branch -M main
		git remote add origin https://github.com/shashi2k6/customerapi.git
		git push -u origin main
