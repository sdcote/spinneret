# Spinneret
A small, lightweight HTTP server library to add a secure service port to your code.

The primary use case for this project is to easily add a securable HTTP listener to your code with a little code as possible.

This code is currently being used to provide RESTful web services to extract application performance metrics in applications in support of DevOps Information and Reporting practices. All connections are expected to be internal only (enforced by ACL rules).

# Features
* SSL support
* Simple access control list
* Simple Denial of Service detection and handling
* Simple authentication (AuthN) and authorization (AuthZ) model
* Expandable responders
