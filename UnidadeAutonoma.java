class UnidadeAutonoma extends Imovel{
    private double areaUtil;
    private double areaConstruida;
    private static final double multiplicador = 15;

    // replicando os overloads da classe pai Imovel
    public UnidadeAutonoma(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso, double valorIPTU, double areaUtil, double areaConstruida) {
        super(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso, valorIPTU);
        this.areaUtil = areaUtil;
        this.areaConstruida = areaConstruida;
    }

    // overload sem valorIPTU
    public UnidadeAutonoma(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso, double areaUtil, double areaConstruida) {
        super(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso);
        this.areaUtil = areaUtil;
        this.areaConstruida = areaConstruida;
    }

    // overload com estado default "Bahia" e cidade default "Salvador"
    public UnidadeAutonoma(Long numeroIPTU, String rua, Long numero, String cep, String tipo, String uso, double valorIPTU, double areaUtil, double areaConstruida){
        super(numeroIPTU, rua, numero, cep, tipo, uso, valorIPTU);
        this.areaUtil = areaUtil;
        this.areaConstruida = areaConstruida;
    }

    // overload passando o objeto endereço já encapsulado
    public UnidadeAutonoma(Long numeroIPTU, Endereco endereco, String tipo, String uso, double valorIPTU, double areaUtil, double areaConstruida){
        super(numeroIPTU, endereco, tipo, uso, valorIPTU);
        this.areaUtil = areaUtil;
        this.areaConstruida = areaConstruida;
    }

    public double getAreaUtil() {
        return areaUtil;
    }

    public void setAreaUtil(double areaUtil) {
        this.areaUtil = areaUtil;
    }

    public double getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public double calculaAluguel(int sazonalidade){
        if (0 <= sazonalidade && sazonalidade <= 4){
            return this.getAreaConstruida() * UnidadeAutonoma.multiplicador * (1 + (double)Imovel.indicesAluguel[sazonalidade] / 100.0);
        } else {
            throw new IllegalArgumentException("Sazonalidade inválida.");
        }
    }

    public String toString(){
        String base_s = super.toString();
        base_s += "Área útil: " + Double.toString(this.getAreaUtil()) + "\n";
        base_s += "Área construída: " + Double.toString(this.getAreaConstruida()) + "\n";
        return base_s;
    }
}
