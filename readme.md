# Cake Manager
*Waracle Code Test*

Build: [![CircleCI](https://circleci.com/gh/dididu/cake-manager/tree/master.svg?style=svg)](https://circleci.com/gh/dididu/cake-manager/tree/master)

**Running locally**

> git clone git@github.com:dididu/cake-manager.git  
> cd cake-manager/  
> mvn spring-boot:run

In your browser go to:  
*http://localhost:8282/*

**The app is already hosted on a server:**    
[http://vps270495.ovh.net:8282/](http://vps270495.ovh.net:8282/)

**Examples how to use REST API:**  
Get cakes  
*curl -X GET --header 'Accept: application/json' 'http://vps270495.ovh.net:8282/cakes'*

Create a new cake  
*curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \ 
   "title": "Cockroach cake", \ 
   "desc": "Very tasty", \ 
   "image": "" \ 
 }' 'http://vps270495.ovh.net:8282/cakes'*


REST API documentation is available via:
[http://vps270495.ovh.net:8282/swagger-ui.html#/cake-rest-controller](http://vps270495.ovh.net:8282/swagger-ui.html#/cake-rest-controller)


Runtime service status can be monitored via HTTP endpoints:  
[http://vps270495.ovh.net:8282/health](http://vps270495.ovh.net:8282/health)  
[http://vps270495.ovh.net:8282/env](http://vps270495.ovh.net:8282/env)  
[http://vps270495.ovh.net:8282/trace](http://vps270495.ovh.net:8282/trace)
etc, see [Spring Boot Actuator](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) 


---

**Change log**

- Rewrite the app using [Spring Boot](https://projects.spring.io/spring-boot/)  
- Use H2 in-memory database autoconfigured by Spring Boot as the store when running in default Spring profile (locally)
- Fix *CakeEntity*, rename to just *Cake*   
   **Assumption:** Cakes are considered identical if name, description and picture are all the same

- Rewrite pre-loading of the cakes data from JSON using ObjectMapper pre-created by Spring Boot autoconfiguration
  https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring

- Create _CakeHtmlController_ controller:
  - Responds to browser requests (Header Accept field  *text/html*)
  - Renders a view using [Thymeleaf](http://www.thymeleaf.org/) template engine
  - The template uses [Twitter Bootstrap](http://getbootstrap.com/) for html UI layout and styling
  - The rendered page contains a form for adding a new cake
  
- Create _CakeRestController_ controller:
  - Responds to REST-client requests (Header Accept field  *application/json*)
  - Has 2 methods - getting the list of cakes and adding a new cake

- Add _dockerfile_ to package the service into a [Docker](https://www.docker.com/what-docker) container
- Update application.yml to use MySQL db as persistent store when running in 'container' Spring profile (in production)
- Add _docker-compose.yml_ to use [Docker Compose](https://docs.docker.com/compose/overview/) to run cake-manager container + MySQL container together with shared configuration
- Add circle.yml to use [CircleCI](https://circleci.com/gh/dididu/cake-manager/) to automatically build the app, create the Docker container and deploy it to [Docker Hub](https://hub.docker.com/r/dididu/cake-manager/)
- Add [Swagger](https://springfox.github.io/springfox/docs/current/) auto-documentation to the service to document the REST api
- Add [Spring Boot Actuator](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) to monitor the service state at runtime via http endpoints
- Add Unit tests for CakeRestController using mocked data repository via [Mockito](http://site.mockito.org/)
- Add [Google Analytics](https://analytics.google.com/) for the main page  