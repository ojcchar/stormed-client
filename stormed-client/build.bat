call mvn clean package -DskipTests
call mvn install:install-file "-Dfile=target/stormed-client-1.3.jar" "-DgroupId=seers" "-DartifactId=stormed-client" "-Dversion=1.3" "-Dpackaging=jar"
