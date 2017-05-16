package com.makeryan.modules.mine.mvp.presenter;

import android.view.View;

import com.makeryan.lib.BR;
import com.makeryan.lib.databinding.FragmentRandomBinding;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.mine.ui.fragment.RandomFragment;

import java.util.Random;

import me.yokeyword.fragmentation.ISupport;


/**
 * Created by MakerYan on 2017/5/16 15:04.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.mine.mvp.presenter
 */
public class RandomPresenter
		extends BasePresenter {

	public RandomPresenter(ISupport iSupport) {

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

	public void init(FragmentRandomBinding binding) {

		binding.setVariable(
				BR.random,
				new Random()
						   );
		binding.setVariable(
				BR.listener,
				this
						   );
	}

	@Override
	public void onClick(View v) {

		getView().start(RandomFragment.newInstance());
	}
}
