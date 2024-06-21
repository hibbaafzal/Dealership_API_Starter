package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.LeaseContractsDao;
import com.ps.dealership_api_starter.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public class LeaseContractController implements LeaseContractsDao {

    @RestController
    @RequestMapping("leasecontracts")
    @CrossOrigin
    public class LeaseContractsController {

        private LeaseContractDao leaseContractDao;

        @Autowired
        public LeaseContractController(LeaseContractDao leaseContractDao) {
            this.leaseContractDao = leaseContractDao;
        }

        @GetMapping("{id}")
        public LeaseContract getLeaseContractById(@PathVariable Long id) {
            try {
                return leaseContractDao.getLeaseContractById(id);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @PostMapping("")
        public LeaseContract addLeaseContract(@RequestBody LeaseContract leaseContract) {
            try {
                return leaseContractDao.createLeaseContract(leaseContract);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
