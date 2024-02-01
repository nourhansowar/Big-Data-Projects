# Session Duration Calculator

This project is a Flink application that calculates the session duration for each IP address based on a given dataset. The session duration results are saved to a file for further analysis.

## Table of Contents
 - Introduction
 - Prerequisites
 - Installation
 - Usage
 - Dataset
 - Output

## Introduction

The Session Duration Calculator is a Flink application that processes a dataset containing IP addresses and timestamps and calculates the session duration for each IP address. It uses Flink's windowing and event time processing capabilities to group IP addresses into sessions and calculate the duration of each session.

The application reads the dataset from a CSV file, assigns timestamps to the events using watermarks, and then applies a tumbling window of 30 seconds to group events belonging to the same IP address. For each window, the session start time, session end time, and session duration are calculated and saved to a file.

## Prerequisites
Before running the Session Duration Calculator, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or higher
- Apache Flink 1.14.0 or higher

## Installation

To install Apache Flink locally and run the JAR file, follow these steps:

### Download Apache Flink:

Visit the Apache Flink website (https://flink.apache.org/) and navigate to the Downloads page.

Choose the desired Flink version and download the binary distribution for your operating system.

Extract the Flink archive:

Open a terminal and navigate to the directory where you downloaded the Flink binary distribution.

##### Extract the archive using the following command:
```
tar -xzf flink-<version>.tgz
```
Replace <version> with the version number of the downloaded Flink distribution.

##### Set up Flink:

Navigate to the extracted Flink directory using the command:
```
cd flink-<version>
```
##### Configure Flink by editing the conf/flink-conf.yaml file as needed. For local development, the default configuration should work fine.

##### Start the Flink cluster:

Execute the following command to start the Flink cluster:
```
./bin/start-cluster.sh
```
This command will start the JobManager and TaskManager processes locally.

##### Verify the Flink cluster is running:

Open a web browser and visit the Flink Dashboard at http://localhost:8081.
The Flink Dashboard provides an overview of the running Flink cluster and allows you to monitor job execution.


##### Build the project:


After a successful build code using Intellij IDEA, you will find the JAR file ip.jar in the out directory.

## Usage

To use the Session Duration Calculator, follow these steps:

#### Input dataset:

Create a CSV file containing IP addresses and timestamps in the format: <IP address>,<timestamp>. For example:
apache
```
192.168.0.1,2024-01-01T09:00:00Z
192.168.0.2,2024-01-01T09:01:00Z
...
```

Note that the timestamp should be in ISO 8601 format.

## Run the Flink job using the following command:

```
./bin/flink run -c SessionDurationCalculator /path/to/outputjarfile --datafile <path_to_input_file> --outputfile <path_to_output_file>
```
Replace /path/to/outputjarfile with the actual path to your output JAR file generated from the build process.
Replace <path_to_input_file> with the path to your input dataset file.
Replace <path_to_output_file> with the desired path for the output file.
Wait for the Flink job to complete. The session duration results will be saved to the specified output file.

#### Output Example 

```
IP: 192.168.0.1, Session Start: 2024-01-01T09:00:00Z, Session End: 2024-01-01T09:00:30Z, Session Duration: 30000 milliseconds
IP: 192.168.0.2, Session Start: 2024-01-01T09:01:00Z, Session End: 2024-01-01T09:01:30Z, Session Duration: 30000 milliseconds
...

```


