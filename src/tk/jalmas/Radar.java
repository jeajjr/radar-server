package tk.jalmas;

public class Radar {
	public String endereco;
	public String sentido;
	public int limiteVelocidade;
	
	public String toString() {
		return "{" + 
				endereco +
				sentido +
				limiteVelocidade + "km/h" +
				"}";				
	}
}
