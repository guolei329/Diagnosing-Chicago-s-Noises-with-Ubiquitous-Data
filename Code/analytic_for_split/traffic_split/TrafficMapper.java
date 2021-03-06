import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class TrafficMapper
	extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {

		String line = value.toString();
		String[] p = line.split("\t");
		int region_id = Integer.parseInt(p[0]);
		String time = p[1].substring(0,10);
		int read = Integer.parseInt(p[2]);
		double west = Double.parseDouble(p[3]);
		double east = Double.parseDouble(p[4]);
		double south = Double.parseDouble(p[5]);
		double north = Double.parseDouble(p[6]);
		String x = null;
		String y = null;
		double lat = 0.0;
		double longi = 0.0;
		for (int i=0; i<=19; i++){
    		for (int j=0; j<=17; j++){
     			if (north >= 41.637916+0.02*(2*i+1)/2  && south <= 41.637916+0.02*(2*i+1)/2 
					&& east >= -87.862653+0.02*(2*j+1)/2  && west <= -87.862653+0.02*(2*j+1)/2){
					lat = 41.637916+0.02*(2*i+1)/2;
					longi = -87.862653+0.02*(2*j+1)/2;
					BigDecimal latDeci = new BigDecimal(lat);
					latDeci = latDeci.setScale(6,BigDecimal.ROUND_HALF_DOWN);
					BigDecimal logiDeci = new BigDecimal(longi);
					logiDeci = logiDeci.setScale(6,BigDecimal.ROUND_HALF_DOWN);
					String k = Integer.toString(i) + "\t" + Integer.toString(j);
					String res = latDeci + "\t" + logiDeci + "\t" + Integer.toString(read);
					context.write(new Text(k), new Text(res));
    		}
     	}
	}
}
}
