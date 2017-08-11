package hoosasack.pillnow.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoosasack.pillnow.R

class InformFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_inform, container, false)
        return view
    }

    companion object {
        fun newInstance(): InformFragment {
            val fragment: InformFragment = InformFragment()
            return fragment
        }
    }
}
