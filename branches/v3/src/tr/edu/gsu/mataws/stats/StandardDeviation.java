/*
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services
 * Copyright 2011 Cihan Aksoy & Koray Mançuhan
 * 
 * This file is part of Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services is 
 * free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Mataws - Multimodal Automatic Tool for the Annotation of  Web Services 
 * is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Mataws - Multimodal Automatic Tool for the Annotation of  Web Services.
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package tr.edu.gsu.mataws.stats;

public class StandardDeviation {

	public double StandardDeviationMean(double[] data) {
		// sd is sqrt of sum of (values-mean) squared divided by n - 1
		// Calculate the mean
		double mean = 0;
		final int n = data.length;
		if (n < 2) {
			return Double.NaN;
		}
		for (int i = 0; i < n; i++) {
			mean += data[i];
		}
		mean /= n;
		// calculate the sum of squares
		double sum = 0;
		for (int i = 0; i < n; i++) {
			final double v = data[i] - mean;
			sum += v * v;
		}
		// Change to ( n - 1 ) to n if you have complete data instead of a
		// sample.
		return Math.sqrt(sum / (n - 1));
	}

	public double standardDeviationCalculate(double[] data) {
		final int n = data.length;
		if (n < 2) {
			return Double.NaN;
		}
		double avg = data[0];
		double sum = 0;
		for (int i = 1; i < data.length; i++) {
			double newavg = avg + (data[i] - avg) / (i + 1);
			sum += (data[i] - avg) * (data[i] - newavg);
			avg = newavg;
		}
		// Change to ( n - 1 ) to n if you have complete data instead of a
		// sample.
		return Math.sqrt(sum / (n - 1));
	}

	/*public static void main(String[] args) {
		double[] data = { 60, 100, 90 };
		System.out.println(StandardDeviationMean(data));
		System.out.println(standardDeviationCalculate(data));
	}*/
}
