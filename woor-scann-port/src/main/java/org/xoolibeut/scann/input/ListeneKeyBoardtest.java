package org.xoolibeut.scann.input;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.Provider;

public class ListeneKeyBoardtest {

	public static void main(String[] args) {
		// Provider provider = Provider.getCurrentProvider(true);
		Provider provider = new XoolWinProvider();
		
		ListenerKey listener = new ListenerKey(provider);
		// KeyStroke.getKeyStroke(KeyEvent.CHAR_UNDEFINED);
		// provider.register(KeyStroke.getKeyStroke("shift"), listener);
		provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), listener);
		// provider.register(KeyStroke.getKeyStroke("a"), listener);
		// provider.register(KeyStroke.getKeyStroke(KeyEvent.CHAR_UNDEFINED), listener);

		// provider.register(KeyStroke.getKeyStrokeForEvent(KeyEvent.VK_C,
		// ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK), listener);
		// provider.register(MediaKey.MEDIA_NEXT_TRACK, listener);
	}

}
