package com.makeryan.modules.main.mvp.presenter;

import com.makeryan.lib.constants.RequestCodeConstants;
import com.makeryan.lib.databinding.FragmentMainBinding;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.fragment.fragmentation.ISupport;
import com.makeryan.lib.mvp.presenter.BasePresenter;


/**
 * Created by MakerYan on 2017/5/15 21:02.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.lib
 */
public class MainPresenter
		extends BasePresenter {

	public MainPresenter(ISupport iSupport) {

		super(iSupport);
	}

	@Override
	public void resume() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {

	}

	public void init(FragmentMainBinding binding) {

	}


	@Override
	public void onEventBus(EventBean event) {

		ISupport support = getView();
		if (support == null) {
			return;
		}
		int tag = event.getTag();
		if (tag == EventType.TARGET_FRAGMENT_IN_MAIN) {
			support.startForResult(
					event.targetFragment,
					RequestCodeConstants.MAIN_TO_TARGET_FRAGMENT
								  );
		} else if (tag == EventType.TARGET_FRAGMENT_IN_MAIN_BY_NAME) {
			support.startForResult(
					event.targetFragmentByName,
					RequestCodeConstants.MAIN_TO_TARGET_FRAGMENT
								  );
		}
	}
}
