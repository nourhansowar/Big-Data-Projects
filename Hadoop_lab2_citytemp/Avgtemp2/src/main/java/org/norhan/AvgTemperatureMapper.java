package org.norhan;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.commons.lang3.StringUtils;

public class AvgTemperatureMapper extends MapReduceBase implements
        Mapper<LongWritable, Text, Text, FloatWritable> {

    // This method is called for each input record.
    public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {

        // Split the input record into a line array.
        String[] line = value.toString().split(",");

        // Get the date part and temperature.
        String datePart = line[0];
        String temp = line[1];

        // Parse the temperature as a float.
        float floatTemp = Float.parseFloat(temp);

        // Create a FloatWritable object with the temperature value.
        FloatWritable floatWritable = new FloatWritable(floatTemp);

        // Emit the (date part, temperature) pair to the output.
        output.collect(new Text(datePart), floatWritable);

    }
}
