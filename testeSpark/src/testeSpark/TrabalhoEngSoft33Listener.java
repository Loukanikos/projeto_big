package testeSpark;

import java.util.concurrent.TimeUnit;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.DataStreamWriter;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.StructType;

public class TrabalhoEngSoft33Listener {

	public static void main(String[] args) {

		// Somente Windows: Erro com o winutils.exe
		System.setProperty("hadoop.home.dir", "C:\\Projetos\\Apache\\spark-2.3.2-bin-hadoop2.7");

		// definindo o contexto local do eclipse
		SparkConf sparkConf = new SparkConf().setAppName("testeSpark").setMaster("local");
		SparkContext sc = new SparkContext(sparkConf);

		// criando a sessao local do spark
		SparkSession spark = SparkSession.builder().sparkContext(sc).getOrCreate();

		// criando o path do arquivo
		// String path = "../../../../root/Downloads/COTAHIST_A2017.TXT";
		String path = "C:\\Projetos\\UFRJ\\dataset\\COTAHIST_A2017.TXT"; // Vinicius

		JavaRDD<Acao> acaoRDD = spark.emptyDataFrame().toJavaRDD().map(new Function<Row, Acao>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Acao call(Row arg0) throws Exception {
				System.out.println("===============================" + arg0.toString() + "===============================");
				Acao acao = new Acao();
				return acao;
			}
		});
		// convertendo para dataset
		Dataset<Row> acaoDF = spark.createDataFrame(acaoRDD, Acao.class);
		System.out.println("============== 2 =================" + acaoDF.toString() + "===============================");
		// acaoDF.show();
		//acaoDF.printSchema();
		//StructType sch = acaoDF.schema();

		StructType esquema = acaoDF.schema();
		String diretorio = "C:\\Projetos\\UFRJ\\dataset";
		Dataset<Row> leituras = spark.readStream().schema(esquema)
				//.option("mode", "DROPMALFORMED")
				//.load(diretorio);
				.csv(diretorio);

		Dataset<Row> atividades = leituras.select("*"); // .as[Leitura]

		// org.apache.spark.sql.streaming.DataStreamWriter[org.apache.spark.sql.Row]
		DataStreamWriter<Row> ws = atividades.writeStream();
		// org.apache.spark.sql.streaming.DataStreamWriter[org.apache.spark.sql.Row]
		DataStreamWriter<Row> saida = ws.outputMode(OutputMode.Complete());
		// org.apache.spark.sql.streaming.DataStreamWriter[org.apache.spark.sql.Row]
		DataStreamWriter<Row> gatilho = saida.trigger(Trigger.ProcessingTime(5, TimeUnit.SECONDS));
		// org.apache.spark.sql.streaming.DataStreamWriter[org.apache.spark.sql.Row]
		DataStreamWriter<Row> formato = gatilho.format("console");
		// org.apache.spark.sql.streaming.StreamingQuery
		StreamingQuery query = formato.start();
		// --------------

		try {
			query.awaitTermination();
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}