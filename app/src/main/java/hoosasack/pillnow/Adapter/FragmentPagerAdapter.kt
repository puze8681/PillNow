package hoosasack.pillnow.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import hoosasack.pillnow.Fragment.*

/**
 * Created by parktaejun on 2017. 8. 10..
 */
class FragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MapFragment.newInstance()
            1 -> ScanFragment.newInstance()
            2 -> HomeFragment.newInstance()
            3 -> InformFragment.newInstance()
            4 -> SettingFragment.newInstance()
            else -> null!!
        }
    }

    override fun getCount(): Int {
        return 5

    }
}