package org.xoolibeut.woor.organize.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.LocalDate;

public class TaskArrangePhoto {
	private ConsoleLogger console;
	private ModelArrangePhoto moArrangePhoto;

	public TaskArrangePhoto(ConsoleLogger console, ModelArrangePhoto moArrangePhoto) {
		this.console = console;
		this.moArrangePhoto = moArrangePhoto;

	}

	public void arrangePhoto() {
		if (!Paths.get(moArrangePhoto.getSource()).toFile().exists()) {
			throw new RuntimeException("Le répertoire source " + moArrangePhoto.getSource() + " n'existe pas ");
		}
		if (!Paths.get(moArrangePhoto.getDest()).toFile().exists()) {
			throw new RuntimeException("Le répertoire de destination " + moArrangePhoto.getDest() + " n'existe pas ");
		}
		switch (moArrangePhoto.getArrangeType()) {
		case YEAR:

			break;
		case MONTH:
			arrangePhotoMonth();
			break;
		case DAY:

			break;
		default:
			break;
		}
	}

	private void arrangePhotoMonth() {
		LocalDate localDate = LocalDate.now();
		int annee = localDate.getYear();
		console.println("Année " + localDate.getYear());
		File fileArrangeAnnee = Paths.get(moArrangePhoto.getDest() + File.separator + annee).toFile();

		if (!fileArrangeAnnee.exists()) {
			fileArrangeAnnee.mkdir();
		}
		int month = localDate.getMonthValue();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		console.println("Mois " + months[month]);
		File fileArrangeMonth = Paths
				.get(moArrangePhoto.getDest() + File.separator + annee + File.separator + months[month]).toFile();
		if (!fileArrangeMonth.exists()) {
			fileArrangeMonth.mkdir();
		}
		try {
			Files.list(Paths.get(moArrangePhoto.getSource())).filter(
					path -> path.toFile().isFile() && moArrangePhoto.getExtension().contains(getExtension(path)))
					.forEach(path -> {
						try {
							Files.move(path, Paths
									.get(fileArrangeMonth.getAbsolutePath() + File.separator + path.getFileName()));
							// Files.delete(path);
						} catch (FileAlreadyExistsException e) {
							console.println("Fichier existe déja ");

						} catch (IOException e) {
							console.println("Fichier non transféré ");
							e.printStackTrace();
						}
					});
		} catch (IOException ioException) {
			throw new RuntimeException("erreur traitement", ioException);
		}

	}

	private String getExtension(Path path) {
		String extension = "";
		int i = path.toFile().getName().lastIndexOf('.');
		if (i > 0) {
			extension = path.toFile().getName().substring(i + 1);
		}
		return extension;
	}

	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		int month = localDate.getMonthValue();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		System.out.println(months[month]);
	}
}
