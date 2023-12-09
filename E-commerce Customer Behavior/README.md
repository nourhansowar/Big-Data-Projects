# E-commerce Customer Behavior Analysis
This project aims to examine e-commerce customer behavior using a comprehensive dataset available on Kaggle. The dataset provides valuable insights into customer behavior, including user logs and transaction data. The objective of this project is to analyze the dataset and gain insights into common purchasing patterns, product preferences, buying frequency, and the impact of time on online shopping behavior.

## Dataset
The dataset used for this project can be accessed from the following Kaggle URL: E-commerce Customer Behavior Dataset. It contains a rich collection of customer behavior data, including user logs and transaction information. Please download the dataset and ensure it is available for analysis.

## Running the Application
To run the application, we have set up a Hadoop Docker cluster. The Hadoop cluster will be responsible for storing and preprocessing the large datasets of user logs and transaction data. Additionally, we will utilize Apache Spark, a powerful distributed computing framework, to perform machine learning algorithms for analyzing customer behavior and predicting future buying patterns.

Please follow the steps below to run the application:

- Ensure that you have Docker installed on your machine.
- Clone the project repository to your local machine.
- Navigate to the project directory.
- Run the Docker Compose file using the following command:
```
docker-compose up
```
This will start the Hadoop cluster and make it available for processing the dataset.
Once the cluster is up and running, you can upload and store the dataset in the Hadoop Distributed File System (HDFS). You can use the following command to upload the dataset:
```
docker exec -it namenode hdfs dfs -put /path/to/E-commerceCustomerBehavior-Sheet1.csv /E-commerceCustomerBehavior-Sheet1.csv
```

After uploading the dataset, you can start the analysis and preprocessing using PySpark. The analysis code is available in the project repository. You can run it using the following command:
```
docker exec -it spark-master spark-submit /path/to/Analysis.py
```
```
docker exec -it spark-master spark-submit /path/to/predict.py
```

The analysis script will perform various operations on the dataset and answer the following questions:
What are the common purchasing patterns observed in the dataset, and how do they vary by demographic factors (age, location)?
Can we identify any trends in product preferences or buying frequency?
How does the time of day or week impact online shopping behavior according to the dataset?

