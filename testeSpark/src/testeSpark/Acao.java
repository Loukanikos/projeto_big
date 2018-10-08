package testeSpark;

import java.io.Serializable;
import java.time.LocalDate;

public class Acao implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8748585889974697303L;
	private LocalDate dataPregao;
	private String codigoBDI;
	private String codigoNeg;
	private String tipoMercado;
	private String nomeReduzido;
	private String especificacaoPapel;
	private String prazo;
	private String moedaRef;
	private Double precoAbertura;
	private Double precoMaximo;
	private Double precoMinimo;
	private Double precoUltimoNegoc;
	private Double precoMelhorOfertaCompra;
	private Double precoMelhorOfertaVenda;
	private Long   volumeTotalNegociado;
	
	public Long getVolumeTotalNegociado() {
		return volumeTotalNegociado;
	}
	public void setVolumeTotalNegociado(Long volumeTotalNegociado) {
		this.volumeTotalNegociado = volumeTotalNegociado;
	}
	public Acao() {
		super();
	}
	public Acao(String string, String string2) {
		super();
	}
	public LocalDate getDataPregao() {
		return dataPregao;
	}
	public void setDataPregao(LocalDate dataPregao) {
		this.dataPregao = dataPregao;
	}
	public String getCodigoBDI() {
		return codigoBDI;
	}
	public void setCodigoBDI(String codigoBDI) {
		this.codigoBDI = codigoBDI;
	}
	public String getCodigoNeg() {
		return codigoNeg;
	}
	public void setCodigoNeg(String codigoNeg) {
		this.codigoNeg = codigoNeg;
	}
	public String getTipoMercado() {
		return tipoMercado;
	}
	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	public String getNomeReduzido() {
		return nomeReduzido;
	}
	public void setNomeReduzido(String nomeReduzido) {
		this.nomeReduzido = nomeReduzido;
	}
	public String getEspecificacaoPapel() {
		return especificacaoPapel;
	}
	public void setEspecificacaoPapel(String especificacaoPapel) {
		this.especificacaoPapel = especificacaoPapel;
	}
	public String getPrazo() {
		return prazo;
	}
	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}
	public String getMoedaRef() {
		return moedaRef;
	}
	public void setMoedaRef(String moedaRef) {
		this.moedaRef = moedaRef;
	}
	public Double getPrecoAbertura() {
		return precoAbertura;
	}
	public void setPrecoAbertura(Double precoAbertura) {
		this.precoAbertura = precoAbertura;
	}
	public Double getPrecoMaximo() {
		return precoMaximo;
	}
	public void setPrecoMaximo(Double precoMaximo) {
		this.precoMaximo = precoMaximo;
	}
	public Double getPrecoMinimo() {
		return precoMinimo;
	}
	public void setPrecoMinimo(Double precoMinimo) {
		this.precoMinimo = precoMinimo;
	}
	public Double getPrecoUltimoNegoc() {
		return precoUltimoNegoc;
	}
	public void setPrecoUltimoNegoc(Double precoUltimoNegoc) {
		this.precoUltimoNegoc = precoUltimoNegoc;
	}
	public Double getPrecoMelhorOfertaCompra() {
		return precoMelhorOfertaCompra;
	}
	public void setPrecoMelhorOfertaCompra(Double precoMelhorOfertaCompra) {
		this.precoMelhorOfertaCompra = precoMelhorOfertaCompra;
	}
	public Double getPrecoMelhorOfertaVenda() {
		return precoMelhorOfertaVenda;
	}
	public void setPrecoMelhorOfertaVenda(Double precoMelhorOfertaVenda) {
		this.precoMelhorOfertaVenda = precoMelhorOfertaVenda;
	}
}
