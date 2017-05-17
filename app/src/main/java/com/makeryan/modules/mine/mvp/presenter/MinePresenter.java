package com.makeryan.modules.mine.mvp.presenter;

import android.view.View;

import com.makeryan.lib.BR;
import com.makeryan.lib.databinding.FragmentMineBinding;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.mine.ui.fragment.RandomFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import com.makeryan.lib.fragment.fragmentation.ISupport;


/**
 * Created by MakerYan on 2017/5/16 10:11.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.mvp.presenter
 */
public class MinePresenter
		extends BasePresenter {

	public MinePresenter(ISupport iSupport) {

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

	public void init(FragmentMineBinding binding) {

		binding.setVariable(
				BR.listener,
				this
						   );
		binding.setVariable(
				BR.random,
				new Random()
						   );
	}

	@Override
	public void onClick(View v) {

		EventBus.getDefault()
				.post(new EventBean<>(
						EventType.TARGET_FRAGMENT_IN_MAIN,
						RandomFragment.newInstance()
				));
	}
}
