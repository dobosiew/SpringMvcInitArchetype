# Spring mvc init archetype

This is archetype of simple hello world project written in spring framework.
This project includes basic setup to enable some features of spring modules.
Version of this project written using SpringBoot can be found [here](https://github.com/zbyszekd/SpringBootInitArchetype)
## HOWTO

* `git clone https://github.com/zbyszekd/SpringMvcInitArchetype.git`
* `cd SpringMvcInitArchetype`
* `mvn install`
* go to path where you want to start project and run:
    
    ```
    mvn archetype:generate \
            -DarchetypeGroupId=com.zbyszekd \
            -DarchetypeArtifactId=spring-mvc-init-archetype \
            -DarchetypeVersion=1.0.0-SNAPSHOT \
            -DgroupId=${project gorupId}\
            -DartifactId=${project artifactId} \
            -Dversion=${project version}
    ```