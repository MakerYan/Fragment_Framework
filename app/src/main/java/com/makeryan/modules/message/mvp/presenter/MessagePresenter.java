package com.makeryan.modules.message.mvp.presenter;

import com.makeryan.lib.BR;
import com.makeryan.lib.databinding.FragmentMessageBinding;
import com.makeryan.lib.event.EventBean;
import com.makeryan.lib.event.EventType;
import com.makeryan.lib.mvp.presenter.BasePresenter;
import com.makeryan.modules.message.listeners.MessageListener;
import com.makeryan.modules.message.ui.fragment.SiblingFragment;
import com.makeryan.modules.message.vo.MessageVO;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.ISupport;


/**
 * Created by MakerYan on 2017/5/16 10:11.
 * Email : light.yan@qq.com
 * Personal e-mail : light.yan@qq.com
 * project name : Fragment_Framework
 * package name : com.makeryan.modules.message.mvp.presenter
 */
public class MessagePresenter
		extends BasePresenter
		implements MessageListener<MessageVO> {

	protected FragmentMessageBinding mBinding;

	public MessagePresenter(ISupport iSupport) {

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

	public void init(FragmentMessageBinding binding) {

		mBinding = binding;
		binding.setVariable(
				BR.bean,
				new MessageVO()
						   );
		binding.setVariable(
				BR.listener,
				this
						   );
	}

	/**
	 * 启动一个同级Fragment
	 */
	@Override
	public void startSibling() {

		EventBus.getDefault()
				.post(new EventBean<>(
						EventType.TARGET_FRAGMENT_IN_MAIN,
						SiblingFragment.newInstance(null)
				));
	}

	/**
	 * 启动一个同级Fragment并实时刷新数据
	 *
	 * @param params
	 */
	@Override
	public void startSibling(MessageVO params) {

		EventBus.getDefault()
				.post(new EventBean<>(
						EventType.TARGET_FRAGMENT_IN_MAIN,
						SiblingFragment.newInstance(params)
				));
	}
}
