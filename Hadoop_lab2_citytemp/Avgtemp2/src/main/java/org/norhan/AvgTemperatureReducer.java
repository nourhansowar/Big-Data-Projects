package org.norhan;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class AvgTemperatureReducer extends MapReduceBase implements
        Reducer<Text, FloatWritable, Text, FloatWritable> {

    // This method is called for each key-value pair in the shuffle phase.
    public void reduce(Text key, Iterator<FloatWritable> values, OutputCollector<Text,
            FloatWritable> output, Reporter reporter) throws IOException {

        // Initialize the sum and count of temperatures.
        int sumTemps = 0;
        int numItems = 0;

        // Iterate over the values and sum them up.
        while (values.hasNext()) {
            sumTemps += values.next().get();
            numItems += 1;
        }

        // Calculate the average temperature.
        float averageTemp = sumTemps / numItems;

        // Emit the average temperature to the output.
        output.collect(key, new FloatWritable(averageTemp));

    }
}
