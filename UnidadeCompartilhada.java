import java.util.ArrayList;

class UnidadeCompartilhada extends Imovel{
    private static final double descontoSemUnidadeLazer = 0.9;

    private String idCondominio;
    private Endereco enderecoCondominio;
    private ArrayList<String> areasLazer;

    // replicando os overloads da classe pai Imovel

    public UnidadeCompartilhada(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso, double valorIPTU, String idCondominio, Endereco enderecoCondominio) {
        super(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso, valorIPTU);
        this.idCondominio = idCondominio;
        this.enderecoCondominio = enderecoCondominio;
        this.areasLazer = new ArrayList<String>();
    }

    // overload sem valorIPTU
    public UnidadeCompartilhada(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso, String idCondominio, Endereco enderecoCondominio) {
        super(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso);
        this.idCondominio = idCondominio;
        this.enderecoCondominio = enderecoCondominio;
        this.areasLazer = new ArrayList<String>();
    }

    // overload com estado default "Bahia" e cidade default "Salvador"
    public UnidadeCompartilhada(Long numeroIPTU, String rua, Long numero, String cep, String tipo, String uso, double valorIPTU, String idCondominio, Endereco enderecoCondominio){
        super(numeroIPTU, rua, numero, cep, tipo, uso, valorIPTU);
        this.idCondominio = idCondominio;
        this.enderecoCondominio = enderecoCondominio;
        this.areasLazer = new ArrayList<String>();
    }

    // overload passando o objeto endereço já encapsulado
    public UnidadeCompartilhada(Long numeroIPTU, Endereco endereco, String tipo, String uso, double valorIPTU, String idCondominio, Endereco enderecoCondominio){
        super(numeroIPTU, endereco, tipo, uso, valorIPTU);
        this.idCondominio = idCondominio;
        this.enderecoCondominio = enderecoCondominio;
        this.areasLazer = new ArrayList<String>();
    }

    public String getIdCondominio() {
        return idCondominio;
    }

    public void setIdCondominio(String idCondominio) {
        this.idCondominio = idCondominio;
    }

    public Endereco getEnderecoCondominio() {
        return enderecoCondominio;
    }

    public void setEnderecoCondominio(Endereco enderecoCondominio) {
        this.enderecoCondominio = enderecoCondominio;
    }

    public ArrayList<String> getAreasLazer() {
        return areasLazer;
    }

    public void addAreaLazer(String areaLazer) {
        this.areasLazer.add(areaLazer);
    }

    public void removeAreaLazer(String areaLazer) {
        this.areasLazer.remove(areaLazer);
    }

    public double calculaAluguel(int sazonalidade){
        if (0 <= sazonalidade && sazonalidade <= 4){
            double valorReferencia = this.getValorIPTU() * this.getAreasLazer().size(); // valor de referência = valor IPTU * número de áreas de lazer
            double valorFinal = valorReferencia * (1 + (double)Imovel.indicesAluguel[sazonalidade] / 100.0);
            if (this.getAreasLazer().size() > 0){
                return valorFinal;
            } else {
                return UnidadeCompartilhada.descontoSemUnidadeLazer * valorFinal;
            }
        } else {
            throw new IllegalArgumentException("Sazonalidade inválida.");
        }
    }

}
