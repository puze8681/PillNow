package hoosasack.pillnow.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoosasack.pillnow.R

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_setting, container, false)
        return view
    }

    companion object {
        fun newInstance(): SettingFragment {
            val fragment: SettingFragment = SettingFragment()
            return fragment
        }
    }
}
