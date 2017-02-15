# Clustering Example


After deploying the application, you can use post.sh to insert sample data from sign_data.csv

Set CLUSTERING_HOSTNAME variable before running post script if you use host name other than localhost

´´´
$ ./post.sh
´´´

To retrieve example cluster by location and radius

´´´
$ curl http://localhot:8080/clustering/?lat=48.117149498800124&lon=11.375425537451923&radius=10
´´´

To retrieve all clusters

´´´
$ curl http://localhost:8080/clustering/all
´´´