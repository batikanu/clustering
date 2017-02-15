# Clustering Example

Clone the repository

´´´
$ git clone https://github.com/batikanu/clustering.git
´´´ 

Build application 

´´´
$ cd clustering/
$ mvn clean install
´´´

Now you can deploy the generated clustering-0.war file into tomcat server via application manager.


After deploying the application, you can use post.sh to insert sample data from sign_data.csv
Set CLUSTERING_HOSTNAME variable before running post script if you use host name other than localhost.

NOTE: This application doesn't contains any database for persistence. Instead all the data inserted during a session is kept in memory due to singleton instance of the application. Please restart the server to reset the data in memory. 

´´´
$ ./post.sh
´´´
 
If you deploy the application via application manager manually, tomcat will serve the application with war file name as "clustering-0". In this case you can run post.sh like

´´´
$ CLUSTERING_HOSTNAME=clustering-0 ./post.sh
´´´

To retrieve example cluster by location and radius

´´´
$ curl http://localhot:8080/clustering/?lat=48.117149498800124&lon=11.375425537451923&radius=10
´´´

To retrieve all clusters

´´´
$ curl http://localhost:8080/clustering/all
´´´