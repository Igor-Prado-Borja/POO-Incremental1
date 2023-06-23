import java.time.LocalDate;
import java.util.ArrayList;

enum Status{
	DISPONIVEL("disponivel"), 
	ALUGADO("alugado"), 
	BLOQUEADO("bloqueado"), 
	AUSENTE("ausente");

	private String status;
	Status(String status){
		this.status = status;
	}
}

class Agenda{
	private ArrayList<LocalDate> disponivel;
	private ArrayList<LocalDate> alugado;
	private ArrayList<LocalDate> bloqueado;

	public Agenda(){
		this.disponivel = new ArrayList<LocalDate>();
		this.alugado = new ArrayList<LocalDate>();
		this.bloqueado = new ArrayList<LocalDate>();
	}

	public ArrayList<LocalDate> getDisponivel(){
		return this.disponivel;
	}

	public ArrayList<LocalDate> getAlugado(){
		return this.alugado;
	}

	public ArrayList<LocalDate> getBloqueado(){
		return this.bloqueado;
	}

	public Status findLocalDate(LocalDate d){
		for (int i = 0; i < this.getDisponivel().size(); i++){
			if (this.getDisponivel().get(i).equals(d)){
				return Status.DISPONIVEL;
			}
		}
		for (int i = 0; i < this.getAlugado().size(); i++){
			if (this.getAlugado().get(i).equals(d)){
				return Status.ALUGADO;
			}
		}
		for (int i = 0; i < this.getBloqueado().size(); i++){
			if (this.getBloqueado().get(i).equals(d)){
				return Status.BLOQUEADO;
			}
		}
		return Status.AUSENTE;
	}

	public void addLocalDate(LocalDate d, String state){
		if (this.findLocalDate(d) != Status.AUSENTE){
			throw new RuntimeException("Tentativa de adicionar data já registrada");
		}
		if (state.equals("disponivel")){
			this.getDisponivel().add(d);
		} else if (state.equals("alugado")){
			this.getAlugado().add(d);
		} else if (state.equals("bloqueado")){
			this.getBloqueado().add(d);
		} else {
			throw new RuntimeException("Estado de ocupação da entidade naquela data inválido. Estados posíveis: 'disponível', 'alugado', 'bloqueado'");
		}
	}

	public void setState(LocalDate d, String new_state){
		// ensure LocalDate already exists
		Status state = this.findLocalDate(d);
		if (state == Status.AUSENTE){
			throw new RuntimeException("Tentativa de modificar data não existente");	
		}	
		// remove LocalDate 
		if (state == Status.DISPONIVEL){
			this.getDisponivel().remove(d);
		} else if (state == Status.ALUGADO){
			this.getAlugado().remove(d);
		} else if (state == Status.BLOQUEADO){
			this.getBloqueado().remove(d);
		}

		// add LocalDate again with upLocalDated state
		if (new_state.equals("disponivel")){
			this.getDisponivel().add(d);	
		} else if (new_state.equals("alugado")){
			this.getAlugado().add(d);
		} else if (new_state.equals("bloqueado")){
			this.getBloqueado().add(d);
		}
	}

	public boolean checkInformacaoAusente(LocalDate dataInicial, LocalDate dataFinal){
		// Retorna se existe data com informação ausente no período
		for (LocalDate data = dataInicial; data.isBefore(dataFinal); data = data.plusDays(1)){
			if (this.findLocalDate(data) == Status.AUSENTE){
				return true;
			}
		}
		return false;
	}

	public LocalDate findInformacaoAusente(LocalDate dataInicial, LocalDate dataFinal){
		// Retorna primeira data com informação ausente no período
		for (LocalDate data = dataInicial; data.isBefore(dataFinal); data = data.plusDays(1)){
			if (this.findLocalDate(data) == Status.AUSENTE){
				return data;
			}
		}
		return null;
	}

	public boolean checkDisponibilidade(LocalDate dataInicial, LocalDate dataFinal){
		for (LocalDate data = dataInicial; data.isBefore(dataFinal); data = data.plusDays(1)){
            if (this.findLocalDate(data) != Status.DISPONIVEL){
                return false;
            }
        }
		return true;
	}

	public LocalDate findIndisponibilidade(LocalDate dataInicial, LocalDate dataFinal){
		for (LocalDate data = dataInicial; data.isBefore(dataFinal); data = data.plusDays(1)){
			if (this.findLocalDate(data) != Status.DISPONIVEL){
				return data;
			}
		}
		return null;
	}
}
