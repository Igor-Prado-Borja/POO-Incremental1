import java.util.ArrayList; // array dinâmico genérico (tipo <E> qualquer)

public class Proprietario {
    private String nome;
    private String cpf;
    private String rg;
    private Endereco endereco;
    private ArrayList<Imovel> imoveis;
    private ArrayList<Boolean> disp; // disponibilidades dos imóveis

    public Proprietario(String nome, String cpf, String rg, Endereco endereco){
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.imoveis = new ArrayList<Imovel>();
        this.disp = new ArrayList<Boolean>();
    }

    /**
     * Overload passando endereço desencapsulado (atributos passados diretamente)
     * */
    public Proprietario(String nome, String cpf, String rg, String rua, Long numero, String cep, String estado, String cidade){
        this(nome, cpf, rg, new Endereco(rua, numero, cep, estado, cidade));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    // manipulando o endereço do proprietário
    //
    public Endereco getEndereco(){
        return this.endereco;
    }

    public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }

    public ArrayList<Imovel> getImoveis(){
        return this.imoveis;
    }

    public ArrayList<Boolean> getDisp(){
        return this.disp;
    }

    public void atualizaEndereco(String rua, Long numero, String cep, String estado, String cidade) {
        this.endereco = new Endereco(rua, numero, cep, estado, cidade);
    }

    /**
     * Overload sem mudar estado e cidade */
    public void atualizaEndereco(String rua, Long numero, String cep) {
        this.endereco = new Endereco(rua, numero, cep, this.endereco.getEstado(), this.endereco.getCidade());
    }

    /**
     * Nota de implementação:
     * Não é permitido cadastrar imóvel no mesmo endereço que o proprietário
     * */
    public void cadastraImovel(Imovel imovel){
        // checar se o endereço é igual ao endereço do proprietário
        if (!imovel.getEndereco().equals(this.endereco)){
            // checar se esse imóvel já foi cadastrado
            for (int i = 0; i < this.imoveis.size(); i++){
                if (this.imoveis.get(i).getNumeroIPTU() == imovel.getNumeroIPTU()){
                    throw new RuntimeException("Imóvel já cadastrado: não pode ser cadastrado novamente.");
                }
            }
            // OK, adicionar imovel
            this.imoveis.add(imovel);
            this.disp.add(true);
        } else {
            throw new IllegalArgumentException("Endereço do imóvel deve ser diferente do endereço de seu proprietário."); // TODO FIXME write custom exception
        }
    }

    /**
     * Overload: passando os atributos do imovel como argumento
     * */
    public void cadastraImovel(Long numeroIPTU, Endereco endereco, String tipo, String uso){
        Imovel i = new Imovel(numeroIPTU, endereco, tipo, uso);
        this.cadastraImovel(i);
    }

    /**
     * Overlaod: passando os atributos do imóvel e do endereço do imóvel, todos abertos/não-encapsulados
     * */
    public void cadastraImovel(Long numeroIPTU, String rua, Long numero, String cep, String estado, String cidade, String tipo, String uso){
        Imovel i = new Imovel(numeroIPTU, rua, numero, cep, estado, cidade, tipo, uso);
        this.cadastraImovel(i);
    }

    // o número de IPTU é um indicador único de um imóvel
    public void alocaImovel(Long numeroIPTU){
        if (this.imoveis.size() == 0){
            throw new RuntimeException("Nenhum imóvel foi cadastrado.");
        } else {
            for (int i = 0; i < this.imoveis.size(); i++){
                if (this.imoveis.get(i).getNumeroIPTU() == numeroIPTU){
                    if (this.disp.get(i) == false){
                        throw new RuntimeException("Imovel está cadastrado, porém está indisponivel para locação.");
                    } else {
                        this.disp.set(i, false);
                        return;
                    }
                }
            }
            throw new RuntimeException("Imóvel não está dentre os cadastrados.");
        }
    }

    /**
     * Nota de implementação: liberar um imóvel já liberado é possível e não tem efeito nenhum no estado do objeto proprietário. */
    public void liberaImovel(Long numeroIPTU){
       if (this.imoveis.size() == 0){
            throw new RuntimeException("Nenhum imóvel foi cadastrado.");
        } else {
            for (int i = 0; i < this.imoveis.size(); i++){
                if (this.imoveis.get(i).getNumeroIPTU() == numeroIPTU){
                    this.disp.set(i, true);
                    return;
                }
            }
            throw new RuntimeException("Imóvel não está dentre os cadastrados.");
        }
    }

    public String toString() {
        String s = "";
        s += "Nome: " + this.getNome() + "\n";
        s += "CPF: " + this.getCpf() + "\n";
        s += "RG: " + this.getRg() + "\n";
        s += this.endereco.toString();
        return s;
    }
}
