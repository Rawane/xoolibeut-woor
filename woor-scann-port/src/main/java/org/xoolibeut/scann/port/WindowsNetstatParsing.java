package org.xoolibeut.scann.port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.xoolibeut.scann.ExecutorCommand;
import org.xoolibeut.scann.ScannResultCommand;
import org.xoolibeut.scann.Sys;

/**
 * Classe pour éxécuter le process netStat
 * 
 * @author AC75094508
 *
 */
public class WindowsNetstatParsing implements NetstatParsing, ScannResultCommand<TagInfo> {
	private List<TagInfo> tags;

	@Override
	public List<TagInfo> retrieveAllInfostraffic() throws IOException, InterruptedException {

		ExecutorCommand.execute(Sys.Win, "netstat -bf", this);
		return tags;
	}

	@Override
	public List<TagInfo> retrieveListenningPort() throws IOException, InterruptedException {
		ExecutorCommand.execute(Sys.Win, "netstat -bf", this);
		return tags.stream().filter(tag -> NetworkKeysWindows.LISTENNING.equals(tag.getTypeConnection()))
				.collect(Collectors.toList());

	}

	@Override
	public void doResult(InputStream inputStream) {
		tags = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<TagInfo> tagInfos = reader.lines()
				.filter(line -> line.length() > 1 && !line.contains("Connexions") && !line.contains("Proto"))
				.map(line -> new TagInfo(line)).collect(Collectors.toList());
		TagInfo currentTag = null;
		for (int i = 0; i < tagInfos.size(); i++) {
			if ((tagInfos.get(i).getProtocole() == null || tagInfos.get(i).getProtocole().isEmpty())
					&& tagInfos.get(i).getProcessus() != null) {
				currentTag.setProcessus(tagInfos.get(i).getProcessus());
			} else {
				currentTag = tagInfos.get(i);
				tags.add(currentTag);
			}
		}

	}

	@Override
	public void setSatatus(int code) {

	}

	public static void main(String[] args) {
		WindowsNetstatParsing netstat = new WindowsNetstatParsing();
		try {
			List<TagInfo> tagInfos = netstat.retrieveAllInfostraffic();
			System.out.println(tagInfos);
		} catch (IOException | InterruptedException e) {

			e.printStackTrace();
		}
	}

}
