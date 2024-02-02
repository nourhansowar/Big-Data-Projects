package norhan;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.HashSet;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import java.util.HashSet;
import java.util.Set;


public class SessionDurationCalculator {

    public static void main(String[] args) throws Exception {
        // Path to the input file containing IP addresses and timestamps
        java.lang.String datafile = "/home/norhanswar/Career/Master/BigData/Labs/OneDrive_1_1-10-2024/ipAndTimestamp.csv";

        // Create a Flink StreamExecutionEnvironment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Read the data from the source data and assign timestamps with watermarks
        DataStream<Tuple2<String, String>> inputStream = env.addSource(new SourceFunction<Tuple2<String, String>>() {
            @Override
            public void run(SourceContext<Tuple2<String, String>> sourceContext) throws Exception {
                BufferedReader reader;
                try {
                    // Open the file for reading
                    reader = new BufferedReader(new FileReader(datafile));

                    // Read each line of the file
                    String line = reader.readLine();
                    while (line != null) {
                        if (line.split(",").length > 1) {
                            // Parse the line into a Tuple2 (IP address, timestamp) and emit
                            sourceContext.collect(new Tuple2<>(
                                    line.split(",")[0],
                                    line.split(",")[1]
                            ));
                        }
                        // Read the next line
                        line = reader.readLine();
                    }
                    // Close the reader after processing the file
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void cancel() {
            }
        }).assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple2<String, String>>forBoundedOutOfOrderness(Duration.ofMillis(1000))
                .withTimestampAssigner((event, ts) -> Instant.parse(event.f1).toEpochMilli()));

        // Calculate session duration
        DataStreamSink<Object> print = inputStream
                .keyBy(e -> e.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(30)))
                .process(new ProcessWindowFunction<Tuple2<String, String>, Object, String, TimeWindow>() {


                    Set<String> distinctIPs = new HashSet<String>(); // Specify String as the type parameter

                    @Override
                    public void process(String key, ProcessWindowFunction<Tuple2<String, String>, Object, String, TimeWindow>.Context context,
                                        Iterable<Tuple2<String, String>> iterable, Collector<Object> collector) throws Exception {

                           if (!distinctIPs.contains(key)) {
                            long sess_st = Long.MAX_VALUE;
                            long sess_end = Long.MIN_VALUE;

                            for (Tuple2<String, String> element : iterable) {
                                long timestamp = Instant.parse(element.f1).toEpochMilli();
                                sess_st = Math.min(sess_st, timestamp);
                                sess_end = Math.max(sess_end, timestamp);
                            }

                            long Sess_dur = sess_end - sess_st;

                            String result = "IP: " + key + ", Start: " + Instant.ofEpochMilli(sess_st) +
                                    ", End: " + Instant.ofEpochMilli(sess_end) + ", Duration: " + Sess_dur + " milliseconds";

                            collector.collect(result);

                            // Add the IP to the set of distinct IPs
                            distinctIPs.add(key);
                        }
                    }
                }).print();

        // Execute the Flink program
        env.execute("Flink Program for Session Duration Calculation");
    }
}