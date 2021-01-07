package customer.application.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.application.bean.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @RequestMapping("/api/customers")
    public ResponseEntity<List<Customer>> getCustomers() throws IOException {
        return new ResponseEntity<List<Customer>>(getListOfAllCustomers(), HttpStatus.OK);
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

}
