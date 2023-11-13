package org.norhan;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class AvgTemperature {

    public static void main(String[] args) throws Exception {
        // Create a new JobConf object.
        JobConf conf = new JobConf(AvgTemperature.class);

        // Set the job name.
        conf.setJobName("AvgTemp");

        // Set the output key and value classes.
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(FloatWritable.class);

        // Set the mapper and reducer classes.
        conf.setMapperClass(AvgTemperatureMapper.class);
        conf.setReducerClass(AvgTemperatureReducer.class);

        // Set the input and output formats.
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        // Set the input and output paths.
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        // Run the job.
        JobClient.runJob(conf);
    }
}
