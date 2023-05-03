enum ESTADOS{
    AC,
    AL,
    AP,
    AM,
    BA, 
    CE, 
    DF,
    ES,
    GO,
    MA, 
    MT,
    MS,
    MG,
    PA,
    PB,
    PR,
    PE,
    PI,
    RJ,
    RN,
    RS,
    RO,
    RR,
    SC,
    SP,
    SE,
    TO
}

class Endereco{
    private String rua;
    private Long numero;
    private String cep;
    private String estado;
    private String cidade;

    public static boolean validaEstado(String estado){
        boolean valid = false;
        for (ESTADOS i : ESTADOS.values()){
            if (estado.equals(i.name())){
                valid = true;
            }
        }
        return valid;
    }

    public Endereco(String rua, Long numero, String cep, String estado, String cidade){
        if (Endereco.validaEstado(estado)){
            this.rua = rua;
            this.numero = numero;
            this.cep = cep;
            this.estado = estado;
            this.cidade = cidade;
        } else {
            throw new IllegalArgumentException("Estado inválido: deve ser informada a sigla (em maiúsculas) de um estado brasileiro existente."); // TODO FIXME write custom exception
        }
    }

    // getters
    public String getRua(){
        return this.rua;
    }

    public Long getNumero(){
        return this.numero;
    }

    public String getCep(){
        return this.cep;
    }

    public String getEstado(){
        return this.estado;
    }

    public String getCidade(){
        return this.cidade;
    }
    // setters
    public void setRua(String rua){
        this.rua = rua;
    }

    public void setNumero(Long numero){
        this.numero = numero;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String toString(){
        return "Endereço: " + this.getRua() + ", número " + this.getNumero() + ", "
            + this.getCidade() + ", " + this.getEstado() + ", CEP: " + this.getCep();
    }

    public boolean equals(Endereco endereco){
        return this.getRua().equals(endereco.getRua()) 
                && this.getNumero().equals(endereco.getNumero()) 
                && this.getCep().equals(endereco.getCep())
                && this.getEstado().equals(endereco.getEstado()) 
                && this.getCidade().equals(endereco.getCidade());
    }
}
