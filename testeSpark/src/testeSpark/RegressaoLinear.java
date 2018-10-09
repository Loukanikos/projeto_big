package testeSpark;

import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;

public class RegressaoLinear {
	
	public void aplicar(Dataset<Row> dados) {
		// Load training data.
/*		Dataset<Row> training = spark.read().format("libsvm")
		  .load("data/mllib/sample_linear_regression_data.txt");*/
		
		String[] colunas = {"precoAbertura","precoMaximo","precoMelhorOfertaCompra","precoMelhorOfertaVenda","precoMinimo","precoUltimoNegoc"};

		VectorAssembler assembler = new VectorAssembler().setInputCols(colunas).setOutputCol("features");
		
		 Dataset<Row> features = assembler.transform(dados);
		
		LinearRegression lr = new LinearRegression()
		  .setMaxIter(10)
		  .setRegParam(0.3)
		  .setElasticNetParam(0.8)
		  .setFeaturesCol("features")
		  .setLabelCol("precoAbertura");

		// Fit the model.
		LinearRegressionModel lrModel = lr.fit(features);

		// Print the coefficients and intercept for linear regression.
		System.out.println("Coefficients: " + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

		// Summarize the model over the training set and print out some metrics.
		LinearRegressionTrainingSummary trainingSummary = lrModel.summary();
		System.out.println("numIterations: " + trainingSummary.totalIterations());
		System.out.println("objectiveHistory: " + Vectors.dense(trainingSummary.objectiveHistory()));
		trainingSummary.residuals().show();
		System.out.println("RMSE: " + trainingSummary.rootMeanSquaredError());
		System.out.println("r2: " + trainingSummary.r2());
	}

}
