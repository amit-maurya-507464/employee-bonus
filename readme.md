**About**
String Boot project for employee rest APIs

**Swagger url**
``
http://localhost:8080/swagger-ui/index.html#
``

**Postman Collection**
```Bonus app.postman_collection.json```

**API Usage:**

1. Post API Payload --> POST http://localhost:8080/tci/employee-bonus

```
{
   "employees":[
      {
         "empName":"raj singh",
         "department":"accounts",
         "amount":5000,
         "currency":"INR",
         "joiningDate":"May-20-2022",
         "exitDate":"May-20-2023"
      },
      {
         "empName":"pratap m",
         "department":"accounts",
         "amount":3000,
         "currency":"INR",
         "joiningDate":"Jan-01-2021",
         "exitDate":"May-20-2023"
      },
      {
         "empName":"sushmita lal",
         "department":"IT",
         "amount":4000,
         "currency":"INR",
         "joiningDate":"Jan-01-2021",
         "exitDate":"Dec-31-2021"
      },
      {
         "empName":"sam",
         "department":"Operations",
         "amount":2500,
         "currency":"USD",
         "joiningDate":"May-20-2022",
         "exitDate":"May-20-2023"
      },
      {
         "empName":"john",
         "department":"Operations",
         "amount":2500,
         "currency":"USD",
         "joiningDate":"Jan-20-2023",
         "exitDate":"Dec-30-2024"
      },
      {
         "empName":"susan",
         "department":"IT",
         "amount":700,
         "currency":"USD",
         "joiningDate":"Jan-01-2022",
         "exitDate":"Dec-31-2022"
      }
   ]
} 
```

Response ==> 
 ```Employees saved successfully ```


2. Get API request ==> http://localhost:8080/tci/employee-bonus?date=May-27-2022

Response ==> 

```
 {
  "data": {
    "USD": [
      {
        "empName": "sam",
        "amount": 2500
      },
      {
        "empName": "john",
        "amount": 2500
      },
      {
        "empName": "susan",
        "amount": 700
      }
    ],
    "INR": [
      {
        "empName": "raj singh",
        "amount": 5000
      },
      {
        "empName": "pratap m",
        "amount": 3000
      }
    ]
  },
  "errorMessage": ""
} 
```



