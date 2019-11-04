pragma solidity >=0.4.22 <0.6.0;

// ----------------------------------------------------------------------------
// 'Capstone' customer contract
//
// Deployed to : Rinkeby
// Created by  : Sandeep Singh
// Company     : Wipro
//
// ----------------------------------------------------------------------------


contract CapstoneCustomer {
    struct Customer {
        string firstName;
        string lastName;
        string state;
        string city;
        string streetAddress;
        uint dob;
    }
    
  
    mapping (string => Customer) customers;
    string [] customerSSNIds;
     
    function addCustomer( string memory ssn, string memory firstName, string memory lastName, string memory city, string memory state, string memory streetAddress, uint dob) public {
        customers[ssn] = Customer(firstName, lastName, city, state, streetAddress, dob);
        customerSSNIds.push(ssn);
        
    }
    
    
     function updateCustomer( string memory ssn, string memory firstName, string memory lastName, string memory city, string memory state, string memory streetAddress, uint dob) public {
        customers[ssn].firstName = firstName;
        customers[ssn].lastName = lastName;
        customers[ssn].city = city;
        customers[ssn].state = state;
        customers[ssn].streetAddress = streetAddress;
        customers[ssn].dob = dob;
        
    }
    
    function getCustomerBySSN(string memory ssn) public view returns (string memory firstName, string memory lastName, string memory city, string memory state, string memory streetAddress, uint dob) {
        firstName = customers[ssn].firstName;
        lastName =  customers[ssn].lastName;
        city = customers[ssn].city;
        state  = customers[ssn].state;
        streetAddress = customers[ssn].streetAddress;
        dob = customers[ssn].dob;
    }
    
    function getCustomerCount() public view returns (uint count) {
       count = customerSSNIds.length;
    }
    
    function getCustomerById(uint id) public view returns (string memory ssn, string memory firstName, string memory lastName, string memory city, string memory state, string memory streetAddress, uint dob) {
        ssn = customerSSNIds[id];
        firstName = customers[ssn].firstName;
        lastName =  customers[ssn].lastName;
        city = customers[ssn].city;
        state  = customers[ssn].state;
        streetAddress = customers[ssn].streetAddress;
        dob = customers[ssn].dob;
    }
    

    
}

