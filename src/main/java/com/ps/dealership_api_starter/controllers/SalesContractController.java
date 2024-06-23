package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("salescontracts")
@CrossOrigin
public class SalesContractController {

    private SalesContractDao salesContractDao;

    @Autowired
    public void SalesContractController(SalesContractDao salesContractDao) {
        this.salesContractDao = salesContractDao;
    }

    @GetMapping("{id}")
    public SalesContract getSalesContractById(@PathVariable Long id) {
        try {

            var salesContract = salesContractDao.getSalesContractById(id);
            if (salesContract == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return (SalesContract) salesContract;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching sales contract");
        }
    }

    @PostMapping("")
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        try {
            return salesContractDao.createSalesContract(salesContract);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while adding sales contract");
        }
    }
}
