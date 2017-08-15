package hoosasack.pillnow.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hoosasack.pillnow.Adapter.HomeAlramAdapter
import hoosasack.pillnow.Adapter.InformPillAdapter
import hoosasack.pillnow.Adapter.InformProhibitedAllergyAdapter
import hoosasack.pillnow.Data.HomeAlramData
import hoosasack.pillnow.Data.InformPillData
import hoosasack.pillnow.Data.InformProhibitedAllergyData
import hoosasack.pillnow.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_inform.*

class InformFragment : Fragment() {

    var itemsChronic: ArrayList<InformPillData> = ArrayList()
    var itemsCurrent: ArrayList<InformPillData> = ArrayList()
    var itemsProhibited: ArrayList<InformProhibitedAllergyData> = ArrayList()
    var itemsAllergy: ArrayList<InformProhibitedAllergyData> = ArrayList()
    lateinit var adapterChronic: InformPillAdapter
    lateinit var adapterCurrent: InformPillAdapter
    lateinit var adapterProhibited: InformProhibitedAllergyAdapter
    lateinit var adapterAllergy: InformProhibitedAllergyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_inform, container, false)

        adapterChronic = InformPillAdapter(context.applicationContext, itemsChronic)
        adapterCurrent = InformPillAdapter(context.applicationContext, itemsCurrent)
        adapterProhibited = InformProhibitedAllergyAdapter(context.applicationContext, itemsProhibited)
        adapterAllergy = InformProhibitedAllergyAdapter(context.applicationContext, itemsAllergy)

        chronic_pill_list?.adapter = adapterChronic
        current_pill_list?.adapter = adapterCurrent
        prohibited_list?.adapter = adapterProhibited
        allergy_list?.adapter = adapterAllergy

        return view
    }

    companion object {
        fun newInstance(): InformFragment {
            val fragment: InformFragment = InformFragment()
            return fragment
        }
    }
}
