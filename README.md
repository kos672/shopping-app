# Shopping backend application (Spring Boot based)

### API documentation available at
* http://localhost:8080/swagger-ui/index.html

### Technical considerations
* For the simplicity's sake in-memory (H2) database is used (for real-world scenarios persistent data storage should be used)
* For the simplicity's sake no authentication/authorization mechanism was applied (for real-world scenarios JWT can be used, as pair of regular/refresh token)
* Scalability/redundancy considerations: in order to be able to have multiple active instances of this application some adjustments related to syncrhonization should be done. 
Concurrent updates are preserved by Optimistic locking mechanism.


