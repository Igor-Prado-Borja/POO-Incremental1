public class Imovel {
    private Long numeroIPTU;
    private Endereco endereco;
    private String tipo;
    private String uso;

    public Imovel(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso) {
        this.numeroIPTU = numeroIPTU;
        this.endereco = new Endereco(rua, numero, cep, estado, cidade);
        this.tipo = tipo;
        this.uso = uso;
    }

    /**
        * Overload com estado default "Bahia" e cidade default "Salvador" */
    public Imovel(Long numeroIPTU, String rua, Long numero, String cep, String tipo, String uso){
        this(numeroIPTU, rua, numero, cep, "Bahia", "Salvador", tipo, uso);
    }

    /**
     * Overload passando o objeto endereço já encapsulado */
    public Imovel(Long numeroIPTU, Endereco endereco, String tipo, String uso){
        this.numeroIPTU = numeroIPTU;
        this.endereco = endereco;
        this.tipo = tipo;
        this.uso = uso;
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

    public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }

    public void atualizaEndereco(String rua, Long numero, String cep, String estado, String cidade){
        this.endereco = new Endereco(rua, numero, cep, estado, cidade);
    }

    /** Overload: sem mudar estado e cidade */
    public void atualizaEndereco(String rua, Long numero, String cep){
        this.endereco = new Endereco(rua, numero, cep, this.endereco.getEstado(), this.endereco.getCidade());
    }

    public String toString() {
        String s = "Tipo: " + this.getTipo() + "\n";
        s += "Uso: " + this.getUso() + "\n"; 
        s += this.endereco.toString();
        return s;
    }
}
