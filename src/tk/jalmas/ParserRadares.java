package tk.jalmas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ParserRadares {
	private static final String RADAR_PREFIXO = "Radar";
	
	public static ArrayList<Radar> parse(String html, Calendar day) {
		ArrayList<Radar> radares = new ArrayList<>();
		
		//System.out.println(html);
		
		String[] splittedHTML = html.split(">");
/*
		for (String s : splittedHTML)
			System.out.println(s);
		*/
		boolean dayFound = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		int pos = 0;
		for ( ; pos < splittedHTML.length && !dayFound; pos++) {
			if (splittedHTML[pos].startsWith(sdf.format(day.getTime()))) {
				//System.out.println(splittedHTML[pos]);
				dayFound = true;
			}
		}
		boolean comecoRadares = false;
		boolean fimRadares = false;
		if (dayFound) {
			for ( ; pos < splittedHTML.length && !fimRadares; pos++) {
				if (splittedHTML[pos].startsWith(RADAR_PREFIXO)) {
					comecoRadares = true;
					radares.add(parseLinhaRadar(splittedHTML[pos]));
				}
				else if (comecoRadares) {
					fimRadares = true;
				}
			}
		}
		else {
			return null;
		}
		
		return radares;
	}
	private static Radar parseLinhaRadar (String linha) {
		Radar r = new Radar();
		
		int pos = 0;
		
		//System.out.println("parseLinhaRadar - " + linha);
		
		String[] info = linha.split(" ");
		
		// 'Radar X - '
		while (info[pos++].compareTo("-") != 0);
		
		// Endereço
		r.endereco = "";
		while (!info[pos].startsWith("("))
			r.endereco += info[pos++] + " ";
		//System.out.println("Endereço: " + r.endereco);
		
		// Sentido
		r.sentido = "";
		do {
			r.sentido += info[pos] + " ";
		} while (!info[pos++].contains(")"));
		//System.out.println("Sentido: " + r.sentido);
		
		// Velocidade
		r.limiteVelocidade = Integer.parseInt(info[pos]);
		//System.out.println("Limite: " + r.limiteVelocidade);
		
		return r;
	}
}
