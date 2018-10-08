package testeSpark;

import java.time.LocalDate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
public class ReadTextToRDD {
 
    public static void main(String[] args) {
        // configure spark
        SparkConf sparkConf = new SparkConf().setAppName("Read Text to RDD")
                                        .setMaster("local[2]").set("spark.executor.memory","2g");
        // start a spark context
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        
        // provide path to input text file
        String path = "../../../../root/Downloads/COTAHIST_A2017.TXT";
        
        // read text file to RDD
        //filtrando nome da empresa
        //JavaRDD<String> lines = sc.textFile(path).filter(s -> s.contains("ABC BRASIL"));
        
        //filtrando as linhas que contem dados de ações, tirando o header e o trailler
        //JavaRDD<String> lines = sc.textFile(path).filter(s -> s.substring(0,2).contains("01"))
        //		.filter(s -> s.substring(12,25).contains("ABEVA16     "));
        
        
        // collect RDD for printing
        //int i = 0;
        //for(String line:lines.collect()){
        //    System.out.println(line);
        //    if (i > 10) {
        //    	break;
        //    }
        //    i++;
        //}
        
        JavaRDD<Acao> acaoRDD = sc.textFile(path).filter(s -> s.substring(0,2).contains("01"))
        		  .map(new Function<String, Acao>() {
        		    @Override
        		    public Acao call(String line) throws Exception {
        		      String[] parts = line.split("\\t");
        		      Acao acao = new Acao();
        		      acao.setDataPregao(LocalDate.of(
        		    		  Integer.parseInt(parts[0].substring(2,6))
        		    		  , Integer.parseInt(parts[0].substring(6,8))
        		    		  , Integer.parseInt(parts[0].substring(8,10))));
        		      acao.setCodigoBDI(parts[0].substring(10,12));
        		      acao.setCodigoNeg(parts[0].substring(12,24));
        		      acao.setTipoMercado(parts[0].substring(24,27));
        		      acao.setNomeReduzido(parts[0].substring(27,39));
        		      acao.setEspecificacaoPapel(parts[0].substring(39,49));
        		      acao.setPrazo(parts[0].substring(49,52));
        		      acao.setMoedaRef(parts[0].substring(52,56));
        		      acao.setPrecoAbertura((new Double(parts[0].substring(57,69)))/100);
        		      acao.setPrecoMaximo((new Double(parts[0].substring(69,82)))/100);
        		      acao.setPrecoMinimo((new Double(parts[0].substring(82,95)))/100);
        		      acao.setPrecoUltimoNegoc((new Double(parts[0].substring(95,108)))/100);
        		      acao.setPrecoMelhorOfertaCompra((new Double(parts[0].substring(108,121)))/100);
        		      acao.setPrecoMelhorOfertaVenda((new Double(parts[0].substring(121,134)))/100);
        		      return acao;
        		    }
        		  }); 
        for (Acao acao : acaoRDD.collect()) {
			System.out.print(acao.getDataPregao());
			System.out.print(" - ");
			System.out.print(acao.getNomeReduzido());
			System.out.print(" - ");
			System.out.print(acao.getPrecoAbertura());
			System.out.print(" - ");
			System.out.println(acao.getPrecoUltimoNegoc());
		}
        
        sc.close();
    }
 
} 
