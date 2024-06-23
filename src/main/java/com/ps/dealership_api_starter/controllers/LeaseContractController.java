 package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.LeaseContractDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public abstract class LeaseContractController implements LeaseContractDao {

    @RestController
    @RequestMapping("leasecontracts")
    @CrossOrigin

    public class LeaseContractsController {

        private LeaseContractDao leaseContractDao;

        @Autowired
        public LeaseContractsController(LeaseContractDao leaseContractsDao) {
            this.leaseContractDao = leaseContractsDao;
        }

        @GetMapping("{id}")
        public LeaseContract getLeaseContractById(@PathVariable Long id) {
            try {

                var leaseContract = leaseContractDao.getLeaseContractById(id);

                if (leaseContract == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                }
                return leaseContract;

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

            }
        }

        @PostMapping("")
        public LeaseContract addLeaseContract(@RequestBody LeaseContract leaseContract) {
            try
            {
                return leaseContractDao.createLeaseContract(leaseContract);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

            }
        }
    }
}
