package view;

import model.Cidade;
import model.ListaCidades;
import model.Previsao;
import model.PrevisaoCidade;
import model.parse.XStreamParser;
import model.service.WeatherForecastService;

public class Main {

	public static void main(String[] args) {

		try {

			String cidadesXML = WeatherForecastService.cidades("Alfenas");

			XStreamParser<PrevisaoCidade, ListaCidades> xspCidades = new XStreamParser();

			ListaCidades listaCidades = xspCidades.cidades(cidadesXML);

			for (Cidade c : listaCidades.getCidades()) {

				String previsaoXML = WeatherForecastService.previsoesParaSeteDias(c.getId());
				
				XStreamParser<PrevisaoCidade, ListaCidades> xspPrevisoes = new XStreamParser();

				PrevisaoCidade pc = xspPrevisoes.previsao(previsaoXML);

				System.out.printf("previsão para: %s %s\n", c.getNome(), c.getUf());

				for (Previsao p : pc.getPrevisoes()) {
					System.out.printf("Máx: %s, Min: %s, Tempo: %s\n", p.getMaxima(), p.getMinima(), p.getTempo());
				}
			}

		} catch (Exception ioe) {

			ioe.printStackTrace();

		}

	}
}