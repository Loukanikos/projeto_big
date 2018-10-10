package testeSpark;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class TrabalhoEngSoft33 {

	public static void main(String[] args) {

		// Somente Windows: Erro com o winutils.exe 
		//System.setProperty("hadoop.home.dir", "C:\\Projetos\\Apache\\spark-2.3.2-bin-hadoop2.7");

		// definindo o contexto local do eclipse
		SparkConf sparkConf = new SparkConf().setAppName("testeSpark").setMaster("local");
		SparkContext sc = new SparkContext(sparkConf);

		// criando a sessao local do spark
		SparkSession spark = SparkSession.builder().sparkContext(sc).getOrCreate();

		// criando o path do arquivo
		String path = "../../../../root/Downloads/COTAHIST_A2017.TXT";
		//String path = "C:\\Projetos\\UFRJ\\dataset\\COTAHIST_A2017.TXT"; // Vinicius

		// abrindo o DRR e mapeando para a classe
		JavaRDD<Acao> acaoRDD = spark.read().textFile(path).filter(s -> s.substring(0, 2).contains("01")).toJavaRDD()
				.filter(s -> s.substring(12, 25).contains("PETR4       ")).map(new Function<String, Acao>() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Acao call(String line) throws Exception {
						String[] parts = line.split("\\t");
						Acao acao = new Acao();
						// acao.setDataPregao(LocalDate.of(Integer.parseInt(parts[0].substring(2,6)),
						// Integer.parseInt(parts[0].substring(6,8)),
						// Integer.parseInt(parts[0].substring(8,10))));
						acao.setDataPregao(parts[0].substring(2, 10));
						acao.setCodigoBDI(parts[0].substring(10, 12));
						acao.setCodigoNeg(parts[0].substring(12, 24));
						acao.setTipoMercado(parts[0].substring(24, 27));
						acao.setNomeReduzido(parts[0].substring(27, 39));
						acao.setEspecificacaoPapel(parts[0].substring(39, 49));
						acao.setPrazo(parts[0].substring(49, 52));
						acao.setMoedaRef(parts[0].substring(52, 56));
						acao.setPrecoAbertura((new Double(parts[0].substring(57, 69))) / 100);
						acao.setPrecoMaximo((new Double(parts[0].substring(69, 82))) / 100);
						acao.setPrecoMinimo((new Double(parts[0].substring(82, 95))) / 100);
						acao.setPrecoUltimoNegoc((new Double(parts[0].substring(95, 108))) / 100);
						acao.setPrecoMelhorOfertaCompra((new Double(parts[0].substring(108, 121))) / 100);
						acao.setPrecoMelhorOfertaVenda((new Double(parts[0].substring(121, 134))) / 100);
						acao.setVolumeTotalNegociado(new Long(parts[0].substring(170, 188)));

						return acao;
					}
				});
		// convertendo para dataset
		Dataset<Row> acaoDF = spark.createDataFrame(acaoRDD, Acao.class);
		//acaoDF.show(10000);
		// acaoDF.printSchema();
		// selecionando duas colunas
		Dataset<Row> selectAcaoDF = acaoDF.selectExpr("precoAbertura", "precoAbertura");
		selectAcaoDF.show();
		acaoDF.write()
		.format("com.databricks.spark.csv")
		.option("header", true)
		//.option("codec", "org.apache.hadoop.io.compress.GzipCodec")
		.save("../../../../root/Downloads/acao");
		
/*		Erro na linha abaixo. org.apache.spark.sql.AnalysisException
		List<Double> listOne = selectAcaoDF.as(Encoders.DOUBLE()).collectAsList();
		System.out.println(listOne.size());

		Encoder<Acao> encoder = Encoders.bean(Acao.class);

		System.out.println(listOne.size());*/
		
		
		System.out.println("\n\n\n========================================================\n\n\n");
		
        // Aplicando machine learning
		String[] colunas = {"precoAbertura","precoMaximo","precoMelhorOfertaCompra","precoMelhorOfertaVenda","precoMinimo","precoUltimoNegoc"};
		String rotulo = "precoAbertura";
        RegressaoLinear lr = new RegressaoLinear();
        lr.aplicar(acaoDF, colunas, rotulo);

	}

}