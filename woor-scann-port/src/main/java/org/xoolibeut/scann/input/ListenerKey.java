package org.xoolibeut.scann.input;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

public class ListenerKey implements HotKeyListener {
	private Provider provider;

	public ListenerKey(Provider provider) {
		this.provider = provider;
	}

	@Override
	public void onHotKey(HotKey hotKey) {
		System.out.println(hotKey);

	}

}
