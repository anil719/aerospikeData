package com.example.aerospikeData;


import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    private AerospikeClient aerospikeClient; // Inject AerospikeClient bean

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        // Log the received employee details
        System.out.println("Received employee details: " + employee.toString());

        // Ensure PK is set before inserting into Aerospike
        if (employee.getPk() == null || employee.getPk().isEmpty()) {
            // Return a bad request response with an error message
            return ResponseEntity.badRequest().body("Error: Primary key (PK) not provided.");
        }

        // Insert employee data into Aerospike
        Key key = new Key("test", "employees", employee.getPk());
        Bin nameBin = new Bin("name", employee.getName());
        Bin companyBin = new Bin("company", employee.getCompany());
        Bin experienceBin = new Bin("experience", employee.getExperience());
        Bin ctcBin = new Bin("ctc", employee.getCtc());

        aerospikeClient.put(null, key, nameBin, companyBin, experienceBin, ctcBin);

        // Return a success response
        return ResponseEntity.ok("Employee created successfully in Aerospike.");
    }

    @GetMapping("/keys")
    public List<String> getKeys(){

        String namespace = "test";
        String set = "employees";
        List<String> keysInAerospike = new ArrayList<>();
        QueryPolicy queryPolicy = new QueryPolicy();
        Statement st = new Statement();
        st.setNamespace(namespace);
        st.setSetName(set);

        RecordSet records = aerospikeClient.query(queryPolicy, st);
        while(records.next()){
            Key key = records.getKey();
            keysInAerospike.add(key.toString());
        }
        return keysInAerospike;
    }

    @PutMapping("/renameBin")
    public void updatingBinName(){
        String namespace = "test";
        String set = "employees";
        String oldBinName = "PK";
        String newBinName = "pk";

        Key key = new Key(namespace, set, "recordKey");
        Record record = aerospikeClient.get(null, key);

        if (record != null) {
            Object pkValue = record.bins.get(oldBinName);
            if (pkValue != null) {
                Bin newPkBin = new Bin(newBinName, String.valueOf(pkValue));
                aerospikeClient.put(null, key, newPkBin);
                aerospikeClient.delete(null, key);
                System.out.println("Bin name changed successfully from 'PK' to 'pk'.");
            } else {
                System.out.println("Old bin name 'PK' not found in the record.");
            }
        } else {
            System.out.println("Record not found.");
        }
    }

    @GetMapping("/healthCheck")
    public String aeroHealthCheck(){
        return "System is working Fine ..." ;
    }
}
