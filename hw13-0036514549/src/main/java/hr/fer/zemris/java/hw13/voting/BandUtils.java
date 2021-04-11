package hr.fer.zemris.java.hw13.voting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility class containing methods for working with bands for which votes are
 * recorded in a voting application and where all bands are defined in a file of
 * specified format and votes are stored in another file of specified format.
 * 
 * @author Matija Frandolić
 */
public class BandUtils {

	/**
	 * Returns list of all bands that are defined in a file at the given path. 
	 * Votes for all bands are set to zero.
	 * 
	 * @param  definitionFileName path to the file with definition of all bands
	 * @return                    list of all bands that are defined in a file
	 * @throws IOException        if I/O error occurs
	 */
	public static List<Band> getAllBands(String definitionFileName) throws IOException {
		Path definitionFilePath = Paths.get(definitionFileName);
		BufferedReader reader = Files.newBufferedReader(definitionFilePath);
		List<Band> bands = new ArrayList<>();
		
		String line;
		while ((line = reader.readLine()) != null) {
			String[] lineSplit = line.split("\t");
			
			int id = Integer.parseInt(lineSplit[0]);
			String name = lineSplit[1];
			String link = lineSplit[2];
			
			bands.add(new Band(id, name, link, 0));
		}
		reader.close();
		
		return bands;
	}
	
	/**
	 * Returns sorted map of votes for bands with keys set to IDs of bands and values
	 * set to number of votes. Band is not put into map if it has zero votes.
	 * Empty map is returned if file with stored votes doesn't exist.
	 * 
	 * @param  resultsFileName path to the file with stored votes for bands
	 * @return                 sorted map of votes for bands with keys set to IDs 
	 *                         of bands and values set to number of votes
	 * @throws IOException     if I/O error occurs
	 */
	public static Map<Integer, Integer> getVotes(String resultsFileName) throws IOException {
		Path resultsFilePath = Paths.get(resultsFileName);
		
		if (!Files.exists(resultsFilePath)) {
			return new TreeMap<>();
		}
		
		Map<Integer, Integer> votes = new TreeMap<>();
		BufferedReader reader = Files.newBufferedReader(resultsFilePath);
		
		String line;
		while ((line = reader.readLine()) != null) {
			String[] lineSplit = line.split("\t");
			int id = Integer.parseInt(lineSplit[0]);
			int numberOfVotes = Integer.parseInt(lineSplit[1]);
			votes.put(id, numberOfVotes);
		}
		reader.close();
		
		return votes;
	}
	
	/**
	 * Writes the map of votes for bands to the file at the given path. The given
	 * map must have keys set to IDs of bands and values set to number of votes.
	 * 
	 * @param  resultsFileName path to the file in which to store votes for bands
	 * @param  votes           map of votes for bands with keys set to IDs of bands 
	 *                         and values set to number of votes
	 * @throws IOException     if I/O error occurs
	 */
	public static void writeVotes(String resultsFileName, Map<Integer, Integer> votes) 
			throws IOException {
		
		Path resultsFilePath = Paths.get(resultsFileName);
		Writer writer = Files.newBufferedWriter(resultsFilePath);
		
		for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
			int id = entry.getKey();
			int numberOfVotes = entry.getValue();
			writer.write(String.format("%d\t%d%n", id, numberOfVotes));
		}
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Returns sorted list of all bands that are defined in a file at the path
	 * {@code definitionFileName} with votes set to corresponding values in a file
	 * at the path {@code resultsFileName}.
	 * 
	 * @param  definitionFileName path to the file with definition of all bands
	 * @param  resultsFileName    path to the file with stored votes for bands
	 * @return                    sorted list of all bands with votes for each band
	 * @throws IOException        if I/O error occurs
	 */
	public static List<Band> getBandsAndVotes(String definitionFileName, 
			String resultsFileName) throws IOException {
			
		Map<Integer, Integer> votes = BandUtils.getVotes(resultsFileName);				
		List<Band> bands = BandUtils.getAllBands(definitionFileName);
		
		for (Band band : bands) {
			Integer numberOfVotes = votes.get(band.getId());
			band.setNumberOfVotes(numberOfVotes != null ? numberOfVotes : 0);
		}
		
		bands.sort(
			Comparator.comparing(Band::getNumberOfVotes)
			          .reversed()
			          .thenComparing(Band::getName)
			          .thenComparing(Band::getId)
		);
		
		return bands;
	}
	
}
