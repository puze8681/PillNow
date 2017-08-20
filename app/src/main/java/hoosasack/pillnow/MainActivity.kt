package hoosasack.pillnow

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import hoosasack.pillnow.Adapter.FragmentPagerAdapter
import hoosasack.pillnow.Util.Font.FontActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic("alarm")
        FirebaseInstanceId.getInstance().getToken()

        val viewPagerAdapter: FragmentPagerAdapter = FragmentPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(viewPageChangeListener)

        tabLayout.setupWithViewPager(viewPager)
        initTabLayout()
        viewPager.currentItem = 2
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    val viewPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            offTabLayout()
            for (index in 0..4) {
                when (index) {
                    0 -> if(index==position)tabLayout.getTabAt(position)!!.setIcon(R.drawable.ic_home_finddrug_enable)
                    1 -> if(index==position)tabLayout.getTabAt(position)!!.setIcon(R.drawable.ic_home_scan_enable)
                    2 -> if(index==position)tabLayout.getTabAt(position)!!.setIcon(R.drawable.ic_home_home_enable)
                    3 -> if(index==position)tabLayout.getTabAt(position)!!.setIcon(R.drawable.ic_home_summary_enable)
                    4 -> if(index==position)tabLayout.getTabAt(position)!!.setIcon(R.drawable.ic_home_setting_enable)
                }
            }
        }
    }

    fun initTabLayout() : Unit {
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_finddrug)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_home_scan)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_home_home_enable)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_home_summary)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_home_setting)
    }

    fun offTabLayout() : Unit {
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_finddrug)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_home_scan)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_home_home)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_home_summary)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_home_setting)
    }
}
