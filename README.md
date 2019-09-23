# Spring boot+Gemfire example
###### Using Spring rest + Gemfire Repository + Gemfire Function

- Developed under STS
- Using Maven and Spring Boot

## Client Side
- gemfire-hello-rep

## Server Side
- gemfire-hello-function

## Tips
- Implement CRUD is very similar like other data base repositories such as JPA. It is also fine to implement CrudRepository directly
- To implement Equi Join query at 2 partitioned region
  1. Client side please look at IBookMasterCustomFunctionExecutions and IExtendedBookMasterRepositories
  2. Server side please loot at BookMasterCustomFunction
- please do not forget to colocated 2 regions by implementing server side configuration at AccessingGemFireDataRestApplicationConfiguration, if we would like to have join query
- Due to gemfire is a in memory database, we need to initilize data each time after application boot. Sampple data is at data_json.txt
