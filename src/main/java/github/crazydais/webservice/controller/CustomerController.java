package github.crazydais.webservice.controller;

import github.crazydais.data.entity.CustomerEntity;
import github.crazydais.data.repository.CustomerRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {

    private final Log log = LogFactory.getLog(CustomerController.class);

    @Autowired
    private CustomerRepository custRepo;

    // Create
    @RequestMapping(value = "/api/customer/add", method = RequestMethod.POST)
    public ResponseEntity<String> addCustomer (@RequestParam(value = "firstName", required = true) String fname, @RequestParam(value = "lastName", required = true) String lname, HttpServletRequest request) {
        CustomerEntity cust = new CustomerEntity();
        try {
            cust.setFirstName(fname);
            cust.setLastName(lname);
            this.custRepo.save(cust);
            log.info(request.getMethod() + " : " + request.getServletPath() + " : " + cust.getClass().getSimpleName() + " : SUCCESS \n");
            return new ResponseEntity<>("{status: 'success'}", HttpStatus.OK);
        } catch (Exception ex) {
            log.error(request.getMethod() + " : " + request.getServletPath() + " : " + cust.getClass().getSimpleName() + " : FAILED \n", ex);
            return new ResponseEntity<>("{status: 'failed'}", HttpStatus.BAD_REQUEST);
        }
    }

    // Read
    @RequestMapping(value = "/api/customer/getById", method = RequestMethod.GET)
    public CustomerEntity getCustomerById (@RequestParam(value = "id", required = true, defaultValue = "0") Long id) {
        return custRepo.findById(id);
    }

    @RequestMapping(value = "/api/customer/getByFirst", method = RequestMethod.GET)
    public List<CustomerEntity> getCustomerByFirstName (@RequestParam(value = "name", required = true, defaultValue = "") String fname) {
        return custRepo.findByFirstName(fname);
    }

    @RequestMapping(value = "/api/customer/getByLast", method = RequestMethod.GET)
    public List<CustomerEntity> getCustomerByLastName (@RequestParam(value = "name", required = true, defaultValue = "") String lname) {
        return custRepo.findByLastName(lname);
    }

    @RequestMapping(value = "/api/customer/getAll", method = RequestMethod.GET)
    public List<CustomerEntity> getCustomers (HttpSession session, @RequestParam(required = true, defaultValue = "true") Boolean all) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return custRepo.findAll();
    }

    // Update
    @RequestMapping(value = "/api/customer/updateById", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomers (@RequestParam(value = "id", required = true) Long id,
                                                   @RequestParam(value = "firstName", required = true) String fname,
                                                   @RequestParam(value = "lastName", required = true) String lname) {
        try {
            CustomerEntity updateMe = custRepo.findById(id);
            updateMe.setFirstName(fname);
            updateMe.setLastName(lname);
            custRepo.save(updateMe);
            return new ResponseEntity<>("{status: 'success'}", HttpStatus.OK);
        } catch (Exception ex) {
            // TODO : ex
            return new ResponseEntity<>("{status: 'failed'}", HttpStatus.BAD_REQUEST);
        }
    }

    // Delete
    @RequestMapping(value = "/api/customer/deleteById", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomerById (@RequestParam(value = "id", required = true) Long id) {
        try {
            custRepo.delete(id);
            return new ResponseEntity<>("{status: 'success'}", HttpStatus.OK);
        } catch (Exception ex) {
            // TODO : ex
            return new ResponseEntity<>("{status: 'failed'}", HttpStatus.BAD_REQUEST);
        }
    }
}
