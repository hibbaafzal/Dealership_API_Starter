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
            return salesContractDao.getSalesContractById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {

        }
    }

    @PostMapping("")
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        try {
            return salesContractDao.createSalesContract(salesContract);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

