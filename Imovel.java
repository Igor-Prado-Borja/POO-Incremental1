public abstract class Imovel {
    private Long numeroIPTU;
    private Endereco endereco;
    private String tipo;
    private String uso;
    private Agenda agenda;
    private double valorIPTU;

    public static final int[] indicesAluguel = {0, 20, 15, 10, 5}; // multiplicador de aluguel de acordo com sazonalidade

    public Imovel(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso, double valorIPTU) {
        this.numeroIPTU = numeroIPTU;
        this.endereco = new Endereco(rua, numero, cep, estado, cidade);
        this.tipo = tipo;
        this.uso = uso;
        this.agenda = new Agenda();
        this.valorIPTU = valorIPTU;
    }

    /**
     * overload sem valorIPTU */ 
    public Imovel(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso) {
        this(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso, 0.0);
    }

    /**
        * Overload com estado default "Bahia" e cidade default "Salvador" */
    public Imovel(Long numeroIPTU, String rua, Long numero, String cep, String tipo, String uso, double valorIPTU){
        this(numeroIPTU, rua, numero, cep, "Bahia", "Salvador", tipo, uso, valorIPTU);
    }

    /**
     * Overload passando o objeto endereço já encapsulado */
    public Imovel(Long numeroIPTU, Endereco endereco, String tipo, String uso, double valorIPTU){
        this.numeroIPTU = numeroIPTU;
        this.endereco = endereco;
        this.tipo = tipo;
        this.uso = uso;
        this.agenda = new Agenda();
        this.valorIPTU = valorIPTU;
    }

    public Long getNumeroIPTU() {
        return numeroIPTU;
    }

    public void setNumeroIPTU(Long numeroIPTU) {
        this.numeroIPTU = numeroIPTU;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public Endereco getEndereco(){
        return this.endereco;
    }

    public void atualizaEndereco(String rua, Long numero, String cep, String estado, String cidade){
        this.endereco = new Endereco(rua, numero, cep, estado, cidade);
    }

    /** Overload: sem mudar estado e cidade */
    public void atualizaEndereco(String rua, Long numero, String cep){
        this.endereco = new Endereco(rua, numero, cep, this.endereco.getEstado(), this.endereco.getCidade());
    }

    public void atualizaEndereco(Endereco endereco){
        this.endereco = endereco;
    }

    public double getValorIPTU() {
        return valorIPTU;
    }

    public void setValorIPTU(double valorIPTU) {
        this.valorIPTU = valorIPTU;
    }

    public Agenda getAgenda(){
        return this.agenda;
    }

    public String toString() {
        String s = "Número IPTU: " + Long.toString(this.getNumeroIPTU()) + "\n";
        s += "Tipo: " + this.getTipo() + "\n";
        s += "Uso: " + this.getUso() + "\n"; 
        s += this.endereco.toString();
        return s;
    }
}
