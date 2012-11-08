package tr.edu.gsu.sine;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.ext.FlexibleMetric;

/**
 * Evaluation of flexible metric with all of parameters names.
 */
public class FlexibleMetricEval extends Thread {

	/**
	 * Tuple of two parameters names and their similarity score.
	 */
	private static class SingleEval implements Comparable<SingleEval> {
		float score;
		String name1;
		String name2;

		SingleEval(float score, String name1, String name2) {
			this.score = score;
			this.name1 = name1;
			this.name2 = name2;
		}

		@Override
		public int compareTo(SingleEval other) {
			int res = Math.round((other.score - this.score) * 1000);
			if (res == 0)
				res = name1.compareTo(other.name1);
			if (res == 0)
				res = name2.compareTo(other.name2);
			return res;
		}

		@Override
		public String toString() {
			DecimalFormat fmt = (DecimalFormat) DecimalFormat
					.getPercentInstance();
			String fmtScore = fmt.format(Math.round(score * 1000f) / 1000d);
			return fmtScore + " <- " + name1 + " ~ " + name2;
		}
	}

	// Arguments
	private FlexibleMetric metric;
	private String[] names;
	private File baseDir;

	// Results
	private float[][] metricMatrix;
	private SortedSet<SingleEval> sortedEvals;

	public FlexibleMetricEval(FlexibleMetric fm, Collection col, File outDir) {
		// Create an array of all parameters names, without duplicates.
		SortedSet<String> sortedNames = new TreeSet<String>();
		for (Parameter p : col.getParameters()) {
			sortedNames.add(p.getName());
		}
		metric = fm;
		baseDir = outDir;
		names = sortedNames.toArray(new String[sortedNames.size()]);
		metricMatrix = new float[names.length][names.length];
		sortedEvals = new TreeSet<SingleEval>();
	}

	private void evaluate() {
		// For each distinct couple of names.
		for (int i = 0; i < names.length; i++) {
			for (int j = i; j < names.length; j++) {
				float similarity = metric.getSimilarity(names[i], names[j]);
				// fill matrix
				metricMatrix[i][j] = similarity;
				metricMatrix[j][i] = similarity;
				// fill sorted list
				sortedEvals.add(new SingleEval(similarity, names[i], names[j]));
			}
		}
	}

	private void writeList() throws FileNotFoundException {
		String filename = "metric-" + metric.toString().toLowerCase() + ".txt";
		File listFile = new File(baseDir, filename);
		Sine.getLogger().info(
				"Writing " + metric + " metric's scores to: " + listFile);
		PrintWriter pw = new PrintWriter(listFile);
		for (SingleEval se : sortedEvals) {
			pw.println(se.toString());
		}
		pw.close();
	}

	private void writeImage() throws IOException {
		String filename = "metric-" + metric.toString().toLowerCase() + ".png";
		File imgFile = new File(baseDir, filename);
		Sine.getLogger().info(
				"Writing " + metric + " metric's image to: " + imgFile);
		BufferedImage image = new BufferedImage(names.length, names.length,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < names.length; i++) {
			for (int j = i; j < names.length; j++) {
				Color color = new Color(1f, 1 - metricMatrix[i][j], 0f);
				image.setRGB(i, j, color.getRGB());
			}
		}
		ImageIO.write(image, "png", imgFile);
	}

	@Override
	public void run() {
		try {
			evaluate();
			writeList();
			writeImage();
		} catch (IOException e) {
			Sine.getLogger().severe("Eval. " + metric + ": " + e.getMessage());
			e.printStackTrace();
		} finally {
			Sine.getLogger().info("Getting done with evaluation of " + metric);
			metricMatrix = null;
			sortedEvals = null;
		}
	}

}
