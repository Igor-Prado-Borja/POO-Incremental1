import java.util.Date;
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
	private ArrayList<Date> disponivel;
	private ArrayList<Date> alugado;
	private ArrayList<Date> bloqueado;

	public Agenda(){
		this.disponivel = new ArrayList<Date>();
		this.alugado = new ArrayList<Date>();
		this.bloqueado = new ArrayList<Date>();
	}

	public ArrayList<Date> getDisponivel(){
		return this.disponivel;
	}

	public ArrayList<Date> getAlugado(){
		return this.alugado;
	}

	public ArrayList<Date> getBloqueado(){
		return this.bloqueado;
	}

	public Status findDate(Date d){
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

	public void addDate(Date d, String state){
		if (this.findDate(d) != Status.AUSENTE){
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

	public void setState(Date d, String new_state){
		// ensure date already exists
		Status state = this.findDate(d);
		if (state == Status.AUSENTE){
			throw new RuntimeException("Tentativa de modificar data não existente");	
		}	
		// remove date 
		if (state == Status.DISPONIVEL){
			this.getDisponivel().remove(d);
		} else if (state == Status.ALUGADO){
			this.getAlugado().remove(d);
		} else if (state == Status.BLOQUEADO){
			this.getBloqueado().remove(d);
		}

		// add date again with updated state
		if (new_state.equals("disponivel")){
			this.getDisponivel().add(d);	
		} else if (new_state.equals("alugado")){
			this.getAlugado().add(d);
		} else if (new_state.equals("bloqueado")){
			this.getBloqueado().add(d);
		}
	}
}
