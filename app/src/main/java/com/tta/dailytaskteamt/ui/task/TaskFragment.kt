import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tta.core_base.BaseFragment
import com.tta.core_utils.extension.toast
import com.tta.dailytaskteamt.databinding.FragmentTaskBinding
import com.tta.dailytaskteamt.ui.task.adapter.TestAdapter


class TaskFragment : BaseFragment<FragmentTaskBinding>() {
    override var isTerminalBackKeyActive: Boolean = false

    companion object {
        fun newInstance(): TaskFragment {
            val bundle = Bundle()
            val taskFragment = TaskFragment()
            taskFragment.arguments = bundle
            return taskFragment
        }
    }

    private val testList = arrayListOf<String>()
    private lateinit var testAdapter: TestAdapter

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentTaskBinding {
        return FragmentTaskBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        super.initView()

        for (i in 0 until 30) {
            testList.add(i.toString())
        }
        testAdapter = TestAdapter(testList)
        binding.rcvInProgress.adapter = testAdapter
        testAdapter.itemClickListener = { data, _ ->
            activity?.toast("Data: $data")
        }
        binding.circularProgress.setProgress(85f)
        val animator = ValueAnimator.ofFloat(0f, 85f)
        animator.duration = 3000 // Thời gian 1 giây
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            binding.circularProgress.setProgress(progress)
        }
        animator.start()
    }
}