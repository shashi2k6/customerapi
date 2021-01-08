package customer.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.application.bean.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> getCustomers() throws IOException {
        return new ResponseEntity<List<Customer>>(getListOfAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/api/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") String customerId) throws IOException {
        return new ResponseEntity<Customer>(getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping("/api/addcustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws IOException {
        return new ResponseEntity<String>(createNewCustomer(customer), HttpStatus.OK);
    }

    public Customer getCustomerById(String customerId) throws IOException {
        List<Customer> customerList =  getListOfAllCustomers();
        for(Customer customer : customerList){
            if(customer.getId().equals(customerId)){
                return customer;
            }
        }
        return null;
    }

    private List<Customer> getListOfAllCustomers() throws IOException {
        ObjectMapper mapper;
        //String customersJsonPath = "src/main/data/master-customers-data.json";
        String customersJsonPath = "src/main/resources/data/customers.json"; // 4 customers
        List<Customer> customers;
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        customers = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {});
        return customers;
    }

    public String createNewCustomer(Customer customer) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String customersJsonPath = "src/main/data/master-customers-data.json";
        File customersFile = new File(customersJsonPath);
        try {
            List<Customer> customerList =  getListOfAllCustomers();
            customerList.add(customer);
            String customerJson = mapper.writeValueAsString(customerList);
            Files.write(Paths.get(customersJsonPath), customerJson.getBytes(), StandardOpenOption.CREATE);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        return customer.getId();
    }


}
