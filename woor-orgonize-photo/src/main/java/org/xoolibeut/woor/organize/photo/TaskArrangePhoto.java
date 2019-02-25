package org.xoolibeut.woor.organize.photo;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.sanselan.ImageReadException;

public class TaskArrangePhoto {
	private ConsoleLoggerSpec console;
	private ModelArrangePhotoSpec moArrangePhoto;
	private TraceInfoSpec traceInfo;

	public TaskArrangePhoto(ConsoleLoggerSpec console, ModelArrangePhotoSpec moArrangePhoto, TraceInfoSpec info) {
		this.console = console;
		this.moArrangePhoto = moArrangePhoto;
		this.traceInfo = info;

	}

	public void arrangePhoto(TaskArrangeFunctionnal arrangeFunctionnal) {
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
			arrangePhotoMonth(arrangeFunctionnal);
			break;
		case DAY:

			break;
		default:
			break;
		}
	}

	private void arrangePhotoMonth(TaskArrangeFunctionnal arrangeFunctionnal) {
		LocalDate localDate = LocalDate.now();
		int annee = localDate.getYear();
		console.println("Année " + localDate.getYear());
		int month = localDate.getMonthValue();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		console.println("Mois " + months[month]);
		List<Path> pathsSource = determineListPathSource(Paths.get(moArrangePhoto.getSource()));
		AtomicInteger nbPhotos = new AtomicInteger(0);
		AtomicInteger nbEchecs = new AtomicInteger(0);
		pathsSource.forEach(pathSource -> {
			console.println("Source " + pathSource);
			traceInfo.getSources().add(pathSource.toFile().getAbsolutePath());

			try {

				Files.list(pathSource).filter(
						path -> path.toFile().isFile() && moArrangePhoto.getExtension().contains(getExtension(path)))
						.forEach(path -> {
							try {
								nbPhotos.incrementAndGet();
								int annePh=annee;
								int monthPh=month;
								String pathComplement = pathSource.toFile().getAbsolutePath().substring(
										Paths.get(moArrangePhoto.getSource()).toFile().getAbsolutePath().length());
								LocalDateTime datePrise = WoorImageExtractInfo.readAndDisplayDateMetadata(path.toFile());
								if(	datePrise!=null) {
									annePh=datePrise.getYear();
									monthPh=datePrise.getMonthValue();
								}
								File fileArrangeAnnee = Paths
										.get(moArrangePhoto.getDest() + pathComplement + File.separator + annePh)
										.toFile();
								if (!fileArrangeAnnee.exists()) {
									fileArrangeAnnee.mkdir();
								}
								File fileArrangeMonth = Paths.get(moArrangePhoto.getDest() + pathComplement
										+ File.separator + annee + File.separator + months[monthPh-1]).toFile();
								if (!fileArrangeMonth.exists()) {
									fileArrangeMonth.mkdir();
								}
								Files.move(path, Paths
										.get(fileArrangeMonth.getAbsolutePath() + File.separator + path.getFileName()));
								arrangeFunctionnal.doAfterMoveFile(Paths
										.get(fileArrangeMonth.getAbsolutePath() + File.separator + path.getFileName()));
								// Files.delete(path);
							} catch (FileAlreadyExistsException e) {
								console.println("Fichier existe déja ");
								nbEchecs.incrementAndGet();

							} catch (IOException e) {
								console.println("Fichier non transféré ");
								e.printStackTrace();
							} catch (ImageReadException e) {
								e.printStackTrace();
							}
						});

			} catch (IOException ioException) {
				throw new RuntimeException("erreur traitement", ioException);
			}
		});
		traceInfo.setSizePhoto(nbPhotos.get());
		traceInfo.setSizePhotoFail(nbEchecs.get());
		traceInfo.setSizePhotoSucces(nbPhotos.get() - nbEchecs.get());
	}

	private String getExtension(Path path) {
		String extension = "";
		int i = path.toFile().getName().lastIndexOf('.');
		if (i > 0) {
			extension = path.toFile().getName().substring(i + 1);
		}
		return extension;
	}

	private boolean fileNotInMonthAndYear(Path path) {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		List<String> listMonths = new ArrayList<>(Arrays.asList(months));
		LocalDate localDate = LocalDate.now();
		int annee = localDate.getYear();
		listMonths.add(String.valueOf(annee));
		return listMonths.contains(path.toFile().getName());

	}

	private List<Path> determineListPathSource(Path pa) {
		List<Path> paths = new ArrayList<>();
		try {
			if (Files.list(pa).count() == 0) {
				if (paths.isEmpty()) {
					paths.add(pa);
				}
				return paths;
			}
			if (fileNotInMonthAndYear(pa)) {
				paths.add(pa.getParent());
				return paths;
			}
			if (Files.list(pa).anyMatch(path -> moArrangePhoto.getExtension().contains(getExtension(path)))) {

				paths.add(pa);

			} else {
				Files.list(pa).forEach(path -> paths.addAll(determineListPathSource(path)));
			}
		} catch (IOException e) {

		}
		return paths;
	}

	public static void main(String[] args) {
		String pathParent = Paths.get("D:\\devs\\perso\\test").toFile().getAbsolutePath();
		String path = Paths.get("D:\\devs\\perso\\test").toFile().getAbsolutePath();
		System.out.println(path.substring(pathParent.length()));
		ApplicationInfo applicationInfo = new ApplicationInfo();
		ModelArrangePhoto moArrangePhoto = new ModelArrangePhoto();
		moArrangePhoto.setExtension(Arrays.asList("jpg"));
		TaskArrangePhoto task = new TaskArrangePhoto(ConsoleLogger.getInstance(applicationInfo), moArrangePhoto,
				new TraceInfo());
		List<Path> paths = task.determineListPathSource(Paths.get("D:\\devs\\perso\\test"));
		paths.forEach(System.out::println);
	}
}
