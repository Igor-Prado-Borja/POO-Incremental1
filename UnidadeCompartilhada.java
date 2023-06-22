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

    public String toString(){
        String cond_indent = "";
        for (int i = 0; i < 4; i++){
            cond_indent += " ";
        } // 4 spaces

        String base_s = super.toString();
        base_s += "Informações do condomínio:\n";
        base_s += cond_indent + "ID do condomínio: " + this.getIdCondominio() + "\n";
        String indented_address = this.getEnderecoCondominio().toString().replaceAll("\n", cond_indent + "\n");
        base_s += cond_indent + indented_address + "\n";
        for (int i = 0; i < this.getAreasLazer().size(); i++){
            base_s += "Área de lazer " + Integer.toString(i) + ": " + this.getAreasLazer().get(i) + "\n";
        }
        return base_s;
    }
}
