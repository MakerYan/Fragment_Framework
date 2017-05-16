package com.makeryan.modules.message.mvp.presenter;

import com.makeryan.lib.BR;
import com.makeryan.lib.databinding.FragmentSiblingBinding;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.main.ui.fragment.MainFragment;
import com.makeryan.modules.message.vo.MessageVO;

import me.yokeyword.fragmentation.ISupport;


/**
 * Created by MakerYan on 2017/5/16 12:18.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.mvp.presenter
 */
public class SiblingPresenter
		extends BasePresenter {

	public SiblingPresenter(ISupport iSupport) {

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

	public void init(FragmentSiblingBinding binding) {

		MessageVO bean = (MessageVO) mExtras.getSerializable(MainFragment.BEAN);
		binding.setVariable(
				BR.bean,
				bean
						   );
	}
}
